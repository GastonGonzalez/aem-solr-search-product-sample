package com.gastongonzalez.circuit.camel.aem.main;

import com.gastongonzalez.circuit.camel.aem.IndexerConstants;
import com.gastongonzalez.circuit.camel.aem.PropertiesHelper;
import com.gastongonzalez.circuit.camel.aem.processor.JmsDoc2SolrDoc;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Camel route definitions for reading AEM index requests off an ActiveMQ topic and sending the request to
 * Solr for indexing.
 *
 * Note: This is not production-ready code. This was written as demo code for presentation at CIRCUIT 2016.
 */
public class AemToSolrRoutes
{
    public static void main( String[] args ) throws Exception
    {
        CamelContext camelContext = new DefaultCamelContext();

        PropertiesComponent propertiesComponent = camelContext.getComponent("properties", PropertiesComponent.class);
        propertiesComponent.setLocation("classpath:indexer.properties");
        propertiesComponent.setSystemPropertiesMode(PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE);

        ActiveMQComponent activemq = ActiveMQComponent.activeMQComponent(
                PropertiesHelper.getProperty(PropertiesHelper.ACTIVEMQ_BROKER_URL_KEY, camelContext));
        activemq.setUserName(PropertiesHelper.getProperty(PropertiesHelper.ACTIVEMQ_USER_KEY, camelContext));
        activemq.setPassword(PropertiesHelper.getProperty(PropertiesHelper.ACTIVEMQ_PASS_KEY, camelContext));
        activemq.setTrustAllPackages(true);
        camelContext.addComponent("activemq", activemq);

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception
            {
                from("activemq://topic:{{activemq.topic}}?clientId={{activemq.client.id}}&durableSubscriptionName={{activemq.durable.subscription.name}}")
                .process(new JmsDoc2SolrDoc())
                .choice()
                    .when(exchangeProperty(IndexerConstants.JMS_AEM_OP_TYPE).isEqualTo(IndexerConstants.JMS_AEM_OP_ADD))
                        .to("direct:solrAdd")
                    .when(exchangeProperty(IndexerConstants.JMS_AEM_OP_TYPE).isEqualTo(IndexerConstants.JMS_AEM_OP_DELETE))
                        .to("direct:solrDelete")
                    .otherwise()
                        .log("Unknown JMS operation")
                .end()
                ;

                from("direct:solrAdd")
                    .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_INSERT))
                    .to("solrCloud://{{solr.host}}:{{solr.port}}/solr/{{solr.collection}}?zkHost={{solr.zkhost}}&collection={{solr.collection}}")
                    .to("direct:solrCommit")
                ;

                // Set the document ID on the body
                from("direct:solrDelete")
                    .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_DELETE_BY_ID))
                    .setBody(header(SolrConstants.FIELD + "id"))
                    .to("solrCloud://{{solr.host}}:{{solr.port}}/solr/{{solr.collection}}?zkHost={{solr.zkhost}}&collection={{solr.collection}}")
                    .to("direct:solrCommit")
                ;

                from("direct:solrCommit")
                    .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_COMMIT))
                    .to("solrCloud://{{solr.host}}:{{solr.port}}/solr/{{solr.collection}}?zkHost={{solr.zkhost}}&collection={{solr.collection}}")
                ;
            }
        });

        camelContext.start();
        Thread.sleep(1000 * 60 * 30); // 30 min
        camelContext.stop();
    }
}
