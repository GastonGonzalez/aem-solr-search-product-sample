package com.gastongonzalez.circuit.camel.aem;

/**
 * Note: This is not production-ready code. This was written as demo code for presentation at CIRCUIT 2016.
 */
public interface IndexerConstants {

    public static final String JMS_AEM_FIELD_PREFIX = "aem.field.";
    public static final String JMS_AEM_DOC_ID = "aem.field.id";
    public static final String JMS_AEM_OP_TYPE = "aem.op";
    public static final String JMS_AEM_OP_ADD = "ADD_DOC";
    public static final String JMS_AEM_OP_DELETE = "DELETE_DOC";
    public static final String JMS_AEM_OP_UNKNOWN = "UNKKNOWN_OP";
}
