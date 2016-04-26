package com.gastongonzalez.aemsolrsearch.core.models;

import com.headwire.aemsolrsearch.services.SolrConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class)
public class MoreLikeThisModel
{
    private static final Logger LOG = LoggerFactory.getLogger(MoreLikeThisModel.class);

    @Inject
    @Self
    private SlingHttpServletRequest request;

    @Inject
    @Named("sling:resourceType")
    @Default(values = "No resourceType")
    protected String resourceType;

    @Inject
    @Reference
    private SolrConfigurationService solrConfigurationService;

    private List<MovieDocument> movies;

    @PostConstruct
    protected void init()
    {
        final String sku = getProductSku();

        if (StringUtils.isNotBlank(sku))
        {
            movies = findMoreLikeThisBySku(sku);
        }
    }

    public List<MovieDocument> getMovies()
    {
        return movies;
    }

    private String getProductSku()
    {
        final RequestPathInfo pathInfo = request.getRequestPathInfo();
        final String selectors[] = pathInfo.getSelectors();

        return (selectors != null && selectors.length == 1) ? selectors[0] : "";
    }

    private List<MovieDocument> findMoreLikeThisBySku(final String sku) {

        List<MovieDocument> movies = new ArrayList<MovieDocument>();

        // Get the appropriate SolrJ client as configured by AEM Solr Search
        SolrClient solrclient = solrConfigurationService.getQueryingSolrClient();

        // Build a fielded SKU-based query
        final String skuQuery = String.format("id:%s", sku);
        SolrQuery query = new SolrQuery(skuQuery);

        // MLT does not fully work in SolrCloud. See https://issues.apache.org/jira/browse/SOLR-5480
        query.setRequestHandler("/mlt");
        query.setRows(6);
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "text");
        query.set(MoreLikeThisParams.QF, "text");
        query.set(MoreLikeThisParams.BOOST, true);
        query.set(MoreLikeThisParams.MIN_WORD_LEN, 3);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, 0);

        try
        {
            LOG.info("MLT: Executing More Like This query: '{}'", query);

            // Search against the movies collection
            QueryResponse response = solrclient.query("movies", query);

            if (response.getResults() != null)
            {
                movies = response.getBeans(MovieDocument.class);
                for (MovieDocument movie: movies) {
                    LOG.info("MLT: {}", movie);
                }
            } else {
                LOG.warn("MLT: No More Like This results available possibly due to SOLR-5480");
            }

        } catch (Exception e)
        {
            LOG.error("Unable to perform more like this query: '{}'", skuQuery, e);
        }

        return movies;
    }
}
