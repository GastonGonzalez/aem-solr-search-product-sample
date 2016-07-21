package com.gastongonzalez.circuit.camel.aem.processor;

import com.gastongonzalez.circuit.camel.aem.IndexerConstants;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.solr.SolrConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Note: This is not production-ready code. This was written as demo code for presentation at CIRCUIT 2016.
 */
public class JmsDoc2SolrDoc implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(JmsDoc2SolrDoc.class);

    public void process(Exchange exchange) throws Exception {

        HashMap<String, Object> jmsBody = exchange.getIn().getBody(HashMap.class);
        for(Map.Entry<String, Object> entry : jmsBody.entrySet()) {

            if (entry.getKey().startsWith(IndexerConstants.JMS_AEM_FIELD_PREFIX)) {
                final String solrField = entry.getKey().replace(IndexerConstants.JMS_AEM_FIELD_PREFIX, SolrConstants.FIELD);
                LOG.debug("Setting Solr header: {}={}", solrField, entry.getValue());
                exchange.getIn().setHeader(solrField, entry.getValue());
            }
        }

        final String docId = jmsBody.containsKey(IndexerConstants.JMS_AEM_DOC_ID)
                ? (String) jmsBody.get(IndexerConstants.JMS_AEM_DOC_ID) : "unknown-doc-id";

        // Determine the operation type requested and set the exhange header
        final String jmsOp = jmsBody.containsKey(IndexerConstants.JMS_AEM_OP_TYPE)
                ? (String) jmsBody.get(IndexerConstants.JMS_AEM_OP_TYPE) : IndexerConstants.JMS_AEM_OP_UNKNOWN;
        exchange.setProperty(IndexerConstants.JMS_AEM_OP_TYPE, jmsOp);

        LOG.info("Processing JMS operation: '{}' with doc id: '{}' ", jmsOp, docId);
    }
}
