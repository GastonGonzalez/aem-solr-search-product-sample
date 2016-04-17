package com.gastongonzalez.aemsolrsearch.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = SlingHttpServletRequest.class)
public class ProductModel
{
    private static final Logger LOG = LoggerFactory.getLogger(ProductModel.class);

    @Inject
    private SlingSettingsService settings;

    @Inject
    @Self
    private SlingHttpServletRequest request;

    @Inject
    @Named("sling:resourceType")
    @Default(values = "No resourceType")
    protected String resourceType;

    private String name;

    @PostConstruct
    protected void init()
    {
        this.name = "Some Best Buy Product";
        getProductSku();
    }

    public String getName()
    {
        return name;
    }

    private String getProductSku() {

        final RequestPathInfo pathInfo = request.getRequestPathInfo();
        final String selectors[] = pathInfo.getSelectors();

        LOG.warn("Product selector available: {}", selectors == null ? "no" : "yes");

        return "123456";
    }
}
