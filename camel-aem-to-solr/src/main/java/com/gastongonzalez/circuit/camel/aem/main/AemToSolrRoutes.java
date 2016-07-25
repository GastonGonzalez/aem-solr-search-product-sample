package com.gastongonzalez.circuit.camel.aem.main;

import com.gastongonzalez.circuit.camel.aem.PropertiesHelper;
import com.gastongonzalez.circuit.camel.aem.processor.JmsDocToSolrDoc;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.camel.main.Main;

/**
 * Camel route definitions for reading AEM index requests off an ActiveMQ topic and sending the request to
 * Solr for indexing.
 *
 * Note: This is not production-ready code. This was written as demo code for my presentation at CIRCUIT 2016.
 *
 * @author Gaston Gonzalez
 */
public class AemToSolrRoutes {

    private Main camelMain;

    public static void main( String[] args ) throws Exception {
        AemToSolrRoutes aemToSolrRoutes = new AemToSolrRoutes();
        aemToSolrRoutes.boot();
    }

    private static class AemRouteToSolrRouteBuilder extends RouteBuilder {

        public void configure() throws Exception {

            from("activemq://topic:{{activemq.topic}}?clientId={{activemq.client.id}}&durableSubscriptionName={{activemq.durable.subscription.name}}")
                .choice()
                .when(simple("${body[aem.op]} == 'ADD_DOC'"))
                    .to("direct:solrAdd")
                .when(simple("${body[aem.op]} == 'DELETE_DOC'"))
                    .to("direct:solrDelete")
                .otherwise()
                    .log("Unknown JMS operation")
            .end()
            ;

            from("direct:solrAdd")
                .process(new JmsDocToSolrDoc())
                .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_INSERT))
                .to("solrCloud://{{solr.host}}:{{solr.port}}/solr/{{solr.collection}}?zkHost={{solr.zkhost}}&collection={{solr.collection}}")
                .to("direct:solrCommit")
            ;

            from("direct:solrDelete")
                .process(new JmsDocToSolrDoc())
                .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_DELETE_BY_QUERY))
                .to("solrCloud://{{solr.host}}:{{solr.port}}/solr/{{solr.collection}}?zkHost={{solr.zkhost}}&collection={{solr.collection}}")
                .to("direct:solrCommit")
            ;

            from("direct:solrCommit")
                .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_COMMIT))
                .to("solrCloud://{{solr.host}}:{{solr.port}}/solr/{{solr.collection}}?zkHost={{solr.zkhost}}&collection={{solr.collection}}")
            ;
        }
    }

    private void boot() throws Exception {

        // Create main wrapper
        camelMain = new Main();
        CamelContext camelContext = camelMain.getOrCreateCamelContext();

        // Initialize our properties file so that we can support property placeholder in our endpoint URIs
        PropertiesComponent propertiesComponent = camelContext.getComponent("properties", PropertiesComponent.class);
        propertiesComponent.setLocation("classpath:indexer.properties");
        propertiesComponent.setSystemPropertiesMode(PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        camelMain.bind("properties", propertiesComponent);

        // Configure ActiveMQ
        ActiveMQComponent activemq = ActiveMQComponent.activeMQComponent(
                PropertiesHelper.getProperty(PropertiesHelper.ACTIVEMQ_BROKER_URL_KEY, camelContext));
        activemq.setUserName(PropertiesHelper.getProperty(PropertiesHelper.ACTIVEMQ_USER_KEY, camelContext));
        activemq.setPassword(PropertiesHelper.getProperty(PropertiesHelper.ACTIVEMQ_PASS_KEY, camelContext));
        activemq.setTrustAllPackages(true);
        camelMain.bind("activemq", activemq);

        camelMain.addRouteBuilder(new AemRouteToSolrRouteBuilder());

        camelMain.run();
    }
}
