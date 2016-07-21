package com.gastongonzalez.circuit.camel.aem;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Note:This is not production-ready code.This was written as demo code for presentation at CIRCUIT 2016.
 */
public class PropertiesHelper {

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesHelper.class);

    public static final String ACTIVEMQ_USER_KEY = "activemq.user";
    public static final String ACTIVEMQ_USER_DEFAULT = "admin";
    public static final String ACTIVEMQ_PASS_KEY = "activemq.pass";
    public static final String ACTIVEMQ_PASS_DEFAULT = "admin";
    public static final String ACTIVEMQ_TOPIC_KEY = "activemq.topic";
    public static final String ACTIVEMQ_TOPIC_DEFAULT = "nutch-index-topic";
    public static final String ACTIVEMQ_BROKER_URL_KEY = "activemq.broker.url";
    public static final String ACTIVEMQ_BROKER_URL_DEFAULT = "tcp://localhost:61616";
    public static final String ACTIVEMQ_CLIENT_ID_KEY = "activemq.client.id";
    public static final String ACTIVEMQ_CLIENT_ID_DEFAULT = "aem-indexer-osgi";
    public static final String ACTIVEMQ_DURABLE_SUBSCRIPTION_NAME_KEY = "activemq.durable.subscription.name";
    public static final String ACTIVEMQ_DURABLE_SUBSCRIPTION_NAME_DEFAULT = "aem-indexer-osgi-subscriber";
    public static final String SOLR_HOST_KEY = "solr.host";
    public static final String SOLR_HOST_DEFAULT = "127.0.0.1";
    public static final String SOLR_PORT_KEY = "solr.port";
    public static final String SOLR_PORT_DEFAULT = "8983";
    public static final String SOLR_COLLECTION_KEY = "solr.collection";
    public static final String SOLR_COLLECTION_DEFAULT = "movies";
    public static final String SOLR_ZKHOST_KEY = "solr.zkhost";
    public static final String SOLR_ZKHOST_DEFAULT = "localhost:9983";

    public static Properties DEFAULT_PROPERTIES;

    static {
        DEFAULT_PROPERTIES = new Properties();

        DEFAULT_PROPERTIES.put(ACTIVEMQ_USER_KEY, ACTIVEMQ_USER_DEFAULT);
        DEFAULT_PROPERTIES.put(ACTIVEMQ_PASS_KEY, ACTIVEMQ_PASS_DEFAULT);
        DEFAULT_PROPERTIES.put(ACTIVEMQ_TOPIC_KEY, ACTIVEMQ_TOPIC_DEFAULT);
        DEFAULT_PROPERTIES.put(ACTIVEMQ_BROKER_URL_KEY, ACTIVEMQ_BROKER_URL_DEFAULT);
        DEFAULT_PROPERTIES.put(ACTIVEMQ_CLIENT_ID_KEY, ACTIVEMQ_CLIENT_ID_DEFAULT);
        DEFAULT_PROPERTIES.put(ACTIVEMQ_DURABLE_SUBSCRIPTION_NAME_KEY, ACTIVEMQ_DURABLE_SUBSCRIPTION_NAME_DEFAULT);

        DEFAULT_PROPERTIES.put(SOLR_HOST_KEY, SOLR_HOST_DEFAULT);
        DEFAULT_PROPERTIES.put(SOLR_PORT_KEY, SOLR_PORT_DEFAULT);
        DEFAULT_PROPERTIES.put(SOLR_COLLECTION_KEY, SOLR_COLLECTION_DEFAULT);
        DEFAULT_PROPERTIES.put(SOLR_ZKHOST_KEY, SOLR_ZKHOST_DEFAULT);
    }

    public static String getProperty(String propertyName, CamelContext context) {
        PropertiesComponent propertiesComponent = (PropertiesComponent) context.getComponent("properties");
        String property = null;
        try {
            property = propertiesComponent.parseUri("{{" + propertyName + "}}");
        } catch (Exception e) {
            LOG.warn("Failed to obtain property", e);
        }
        LOG.debug("Property for key: '{}': '{}'", propertyName, property);
        return property;
    }

    public static PropertiesComponent getProperties() {
        PropertiesComponent propertiesComponent = new PropertiesComponent();
        propertiesComponent.setLocation("classpath:indexer.properties");
        propertiesComponent.setSystemPropertiesMode(PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        propertiesComponent.setInitialProperties(DEFAULT_PROPERTIES);

        return propertiesComponent;
    }
}
