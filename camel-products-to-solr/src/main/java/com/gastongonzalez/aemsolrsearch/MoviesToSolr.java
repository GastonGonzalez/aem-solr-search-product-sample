package com.gastongonzalez.aemsolrsearch;

import com.gastongonzalez.aemsolrsearch.transform.GsonToSolrMovie;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.apache.camel.impl.DefaultCamelContext;

public class MoviesToSolr
{
    public static void main( String[] args ) throws Exception
    {
        CamelContext context = new DefaultCamelContext();

        PropertiesComponent propertiesComponent = context.getComponent("properties", PropertiesComponent.class);
        propertiesComponent.setLocation("classpath:movies.properties");
        propertiesComponent.setSystemPropertiesMode(PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception
            {
                from("timer://foo?repeatCount=1&delay=1000")
                    .to("http4://api.bestbuy.com/v1/subsets/productsMovie.json.zip?apiKey={{bestbuy.api.key}}")
                        .setHeader(Exchange.FILE_NAME, constant("productsMovie.json.zip"))
                        .to("file:data/zip?doneFileName=${file:name}.done");

                from("file:data/zip?noop=true&doneFileName=productsMovie.json.zip.done")
                    .split(new ZipSplitter())
                        .streaming().to("file:data/json?doneFileName=${file:name}.done");

                from("file:data/json?noop=true&doneFileName=${file:name}.done")
                    .process(new JsonToProductProcessor())
                        .split().body()
                            .bean(new GsonToSolrMovie())
                            .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_ADD_BEAN))
                            .to("solrCloud://{{solr.host}}:{{solr.port}}/solr/{{solr.collection}}?zkHost={{solr.zkhost}}&collection={{solr.collection}}");
            }
        });

        context.start();
        Thread.sleep(1000 * 60 * 30); // 30 min
        context.stop();
    }
}
