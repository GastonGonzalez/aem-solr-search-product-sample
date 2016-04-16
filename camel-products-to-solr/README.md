Camel Products to Solr
======================

The `camel-products-to-solr` project is responsible for indexing products from the Best Buy API into Solr. It is
intended to provide a possible solution for indexing external product content. For the purpose of demonstration only
a subset of the Best Buy products are extracted. Specifically, movies are extracted, transformed and indexed into
a sample movies collection.

Prerequisites 
-------------

1. Register for a Best Buy API key at [https://developer.bestbuy.com/](https://developer.bestbuy.com/).
2. Edit `src/main/resources/movies.properties` and update the `bestbuy.api.key` property with your API key.

Indexing Content with Camel
---------------------------

Perform the following from this directory to perform a full product index.

    $ mvn clean compile exec:java -Dexec.mainClass=com.gastongonzalez.aemsolrsearch.MoviesToSolr

or

    $ ./run.sh
