Camel AEM to Solr
======================

The `camel-aem-to-solr` project is responsible reading AEM indexing requests off an ActiveMQ topic, performing
one or more preprocessing steps, and finally, sending the index request to Solr.

Note: This is not production-ready code. This was written as demo code for my presentation at CIRCUIT 2016.

Indexing Content with Camel
---------------------------

Perform the following from this directory to perform a full product index.

    $ ./run.sh
