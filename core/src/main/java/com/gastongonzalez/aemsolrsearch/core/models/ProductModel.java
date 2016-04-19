package com.gastongonzalez.aemsolrsearch.core.models;

import com.headwire.aemsolrsearch.services.SolrConfigurationService;
import org.apache.commons.collections.CollectionUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class)
public class ProductModel
{
    private static final Logger LOG = LoggerFactory.getLogger(ProductModel.class);
    private static final String PDP_REQ_ATTRIB = "pdp.movie";

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

    private MovieDocument movie;

    @PostConstruct
    protected void init()
    {
        if (isPdpInitialized()) {
            LOG.warn("PDP is already initialized.");
            return;
        }

        final String sku = getProductSku();

        if (StringUtils.isNotBlank(sku))
        {
            movie = findProductBySku(sku);
            request.setAttribute(PDP_REQ_ATTRIB, movie);
        }
        else
        {
            LOG.warn("Skipping SKU lookup since SKU is not available in selector.");
        }
    }

    private boolean isPdpInitialized() {
        return request.getAttribute(PDP_REQ_ATTRIB) != null;
    }

    public MovieDocument getMovie()
    {
        return movie;
    }

    private String getProductSku()
    {
        final RequestPathInfo pathInfo = request.getRequestPathInfo();
        final String selectors[] = pathInfo.getSelectors();

        return (selectors != null && selectors.length == 1) ? selectors[0] : "";
    }

    /**
     * @param sku Movie SKU
     * @return a movie product if found, otherwise {@code null}.
     */
    private MovieDocument findProductBySku(final String sku) {

        // Get the appropriate SolrJ client as configured by AEM Solr Search
        SolrClient solrclient = solrConfigurationService.getQueryingSolrClient();

        // Build a fielded SKU-based query
        final String skuQuery = String.format("sku:%s", sku);
        SolrQuery query = new SolrQuery(skuQuery);
        query.setRows(1);

        try
        {
            LOG.info("Executing product query: '{}'", query);

            // Search against the movies collection
            QueryResponse response = solrclient.query("movies", query);

            // Convert the Solr response to our well-defined movie POJO
            List<MovieDocument> movies = response.getBeans(MovieDocument.class);

            return CollectionUtils.isNotEmpty(movies) ? movies.get(0) : null;

        } catch (Exception e)
        {
            LOG.error("Unable to perform product query: '{}'", skuQuery, e);
        }

        return null;
    }
}
