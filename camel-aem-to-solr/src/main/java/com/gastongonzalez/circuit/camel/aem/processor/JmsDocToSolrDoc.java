package com.gastongonzalez.circuit.camel.aem.processor;

import com.gastongonzalez.circuit.camel.aem.IndexerConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.solr.SolrConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.gastongonzalez.circuit.camel.aem.IndexerConstants.*;

/**
 * JmsDocToSolr is responsible for copying all the AEM document key/value field pairs and setting them on
 * as headers on the current message. Since message headers are not guaranteed to be preserved across processors,
 * this processor should be used immediately before the Solr endpoint.
 *
 * Note: This is not production-ready code. This was written as demo code for presentation at CIRCUIT 2016.
 */
public class JmsDocToSolrDoc implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(JmsDocToSolrDoc.class);

    public void process(Exchange exchange) throws Exception {

        HashMap<String, Object> jmsBody = exchange.getIn().getBody(HashMap.class);

        LOG.debug("Converting doc: '{}' for operation: '{}'", jmsBody.get(JMS_AEM_DOC_ID), jmsBody.get(JMS_AEM_OP_TYPE));

        for(Map.Entry<String, Object> entry : jmsBody.entrySet()) {
            if (entry.getKey().startsWith(IndexerConstants.JMS_AEM_FIELD_PREFIX)) {
                final String solrField = entry.getKey().replace(IndexerConstants.JMS_AEM_FIELD_PREFIX, SolrConstants.FIELD);
                LOG.debug("Adding Solr header: '{}'='{}'", solrField, entry.getValue());
                exchange.getIn().setHeader(solrField, entry.getValue());
            }
        }
    }
}
