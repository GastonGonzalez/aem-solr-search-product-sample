package com.gastongonzalez.aemsolrsearch.core.listeners;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.gastongonzalez.aemsolrsearch.core.listeners.JmsIndexerConstants.*;

@Component(immediate = true)
@Service(value = EventHandler.class)
@Properties({
        @Property(name = EventConstants.EVENT_TOPIC, value = {PageEvent.EVENT_TOPIC})
})
/**
 * Note:This is not production-ready code.This was written as demo code for presentation at CIRCUIT 2016.
 */
public class JmsIndexListener implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(JmsIndexListener.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    public void handleEvent(final Event event) {

        LOG.debug("JMS index listener event received: {}", event.getTopic());

        PageEvent pageEvent = PageEvent.fromEvent(event);
        if (null == pageEvent) {
            return;
        }

        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
            for (Iterator<PageModification> iter = pageEvent.getModifications(); iter.hasNext(); )
                handlePageModification(iter.next(), resourceResolver);
        } catch (Exception e) {
            LOG.error("Could not get ResourceResolver instance or handle page modification", e);
            return;
        } finally {
            if (resourceResolver != null && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
    }

    protected void handlePageModification(PageModification pageModification, ResourceResolver resourceResolver) {

        final String crxPath = pageModification.getPath();
        Page page = null;

        LOG.debug("Handling page modification event for path: '{}'", crxPath);

        switch (pageModification.getType()) {
            case CREATED:
            case MODIFIED:
            case RESTORED:
                page = resourceResolver.getResource(crxPath).adaptTo(Page.class);
                addPage(page);
                break;
            case DELETED:
                removePage(crxPath);
                break;
            case MOVED:
                removePage(crxPath);
                page = resourceResolver.getResource(pageModification.getDestination()).adaptTo(Page.class);
                addPage(page);
                break;
        }

    }

    protected void removePage(String crxPath) {
        LOG.info("Send JMS INDEX_DELETE operation for CRX Path: '{}'", crxPath);

        HashMap<String, Object> jmsDoc = createJmsDocByOp(JMS_AEM_OP_DELETE);
        jmsDoc.put(JMS_AEM_FIELD_PREFIX + "id", crxPath);

        logDoc(jmsDoc);

        sendJmsDoc(jmsDoc);
    }

    protected void addPage(Page page) {
        LOG.info("Send JMS INDEX_ADD operation");

        HashMap<String, Object> jmsDoc = createJmsDocByOp(JMS_AEM_OP_ADD);
        jmsDoc.put(JMS_AEM_FIELD_PREFIX + "id", page.getPath());
        jmsDoc.put(JMS_AEM_FIELD_PREFIX + "crxPath", page.getPath());
        jmsDoc.put(JMS_AEM_FIELD_PREFIX + "url", page.getPath() + ".html");
        jmsDoc.put(JMS_AEM_FIELD_PREFIX + "name", page.getTitle());
        jmsDoc.put(JMS_AEM_FIELD_PREFIX + "description", page.getDescription());

        logDoc(jmsDoc);

        sendJmsDoc(jmsDoc);
    }

    private HashMap createJmsDocByOp(String operationType) {

        HashMap jmsDoc = new HashMap<String, Object>();
        jmsDoc.put(JMS_AEM_OP_TYPE, operationType);
        return jmsDoc;
    }

    protected void sendJmsDoc(HashMap<String, Object> jmsDoc) {

        final String brokerUrl = "tcp://localhost:61616";
        final String topicName = "aem-index";

        LOG.info("Sending test JMS message to topic '{}' at '{}'", topicName, brokerUrl);

        // Note: This is not production-ready code. Consider making the following customizations:
        // 1. Define JMS properties using configuration admin (e.g, broker URL and topic name)
        // 2. Move this listener into its own bundle:
        //    a. Initialize the factory, connection, session and MessageProducer once.
        //    b. Re-initialize the above as needed during the various OSGi lifecycle phases.
        //    c. Use pooling with JMS.
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = factory.createConnection();

            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic(topicName);

            ObjectMessage message = null;
            MessageProducer producer = session.createProducer(topic);

            message = session.createObjectMessage(jmsDoc);
            producer.send(message);
            connection.stop();
        } catch (Exception e) {
            LOG.error("Error sending JMS message", e);
        }

    }

    private void logDoc(HashMap<String, Object> doc) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("JMS document model:");
            for (Map.Entry<String, Object> entry : doc.entrySet()) {
                LOG.debug("{}={}", entry.getKey(), entry.getValue());
            }
        }
    }
}

