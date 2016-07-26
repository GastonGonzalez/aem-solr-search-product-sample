package com.gastongonzalez.circuit.camel.aem.enrich;

import com.gastongonzalez.circuit.camel.aem.IndexerConstants;
import org.apache.camel.Exchange;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.HashMap;

import static com.gastongonzalez.circuit.camel.aem.IndexerConstants.*;

/**
 * Created by gaston on 7/25/16.
 */
public class AnalyticsAggregationStrategy implements AggregationStrategy
{
    private static final Logger LOG = LoggerFactory.getLogger(AnalyticsAggregationStrategy.class);
    public static final String SOLR_ANALYTICS_PAGE_VIEWS = SolrConstants.FIELD + "analytics_page_views";

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        if (null == newExchange) { return oldExchange; }

        try
        {
            // Get document
            HashMap<String, Object> jmsDoc = oldExchange.getIn().getBody(HashMap.class);
            final String docId = (String) jmsDoc.get(JMS_AEM_DOC_ID);

            // Get analytics file
            BufferedReader reader = new BufferedReader(newExchange.getIn().getBody(Reader.class));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                // Skip comments
                if (line.startsWith("#")) { continue; }

                String tokens[] = line.split(",");
                if (tokens != null && tokens.length == 2)
                {
                    String analyticsId = tokens[0];
                    String analyticsPageViews = tokens[1];
                    if (docId.equals(analyticsId))
                    {
                        LOG.info("Adding Solr header: '{}'='{}'", SOLR_ANALYTICS_PAGE_VIEWS, analyticsPageViews);
                        oldExchange.getIn().setHeader(SOLR_ANALYTICS_PAGE_VIEWS, analyticsPageViews);
                    }
                }
            }
        }
        catch (Exception e) {
            LOG.error("Unable to enrich document with analytics data", e);
        }

        return oldExchange;
    }
}
