# AEM Solr Search: Product Sample

This project was created as companion code for a couple of my 2016 talks:

* _Advanced AEM Search - Consuming External Content and Enriching Content with Apache Camel_ - [CIRCUIT 2016](http://www.circuitdevcon.com/en/speakers.html). 
* _Do you need an external search platform for Adobe Experience Manager?_  - [IMMERSE'16](https://docs.adobe.com/dev/products/aem/events/0416/sessions.html)

This project is built on top of [_AEM Solr Search_](http://www.aemsolrsearch.com) and provides a demo eCommerce site in which the product integration is handled exclusively by Apache Solr.

## Modules

* `core`: Java code for the product demo.
* `ui.apps`: Product demo templates and components.
* `ui.content`: Product demo site.
* `camel-aem-to-solr `: Standalone Apache Camel application for indexing AEM content into Solr.
* `camel-products-to-solr`: Standalone Apache Camel application for ingesting Best Buy movie products into Solr.
* `demo-stack-vagrant`: Vagrant image that includes Solr and ActiveMQ..

## Requirements

* Java 8 
* Adobe AEM 6.1 (Only tested with AEM 6.1 SP1) 
* Maven 3.2.x
* Vagrant: Required to run `demo-stack-vagrant`
* VirtualBox: Required to run `demo-stack-vagrant`
* AEM Solr Search 2.0.0
* Best Buy API Key

## How to Deploy the Demo

1. Install [Vagrant](https://www.vagrantup.com/downloads.html). This project uses Vagrant to fully 
   provision Solr 5.4.1 in SolrCloud mode.
  
2. Install [VirtualBox](https://www.virtualbox.org/wiki/Downloads). The _Vagrantfile_ packaged with 
   this project uses VirtualBox.

3. Start AEM 6.1 author.

4. Clone, compile and install [AEM Solr Search 2.0.0](https://github.com/headwirecom/aem-solr-search) to AEM author.
   You may want to clone this project in another directory (i.e., outside of this project).

        $ git clone https://github.com/headwirecom/aem-solr-search.git
        $ cd aem-solr-search
        $ mvn clean install -Pauto-deploy-all
        
5. Provision the demo stack (e.g., Solr and ActiveMQ).
 
        $ cd <root path to this project>/demo-stack-vagrant
        $ vagrant up
        
6. Deploy the AEM Solr Search Product Demo to AEM author.
       
        $ cd ../
        $ mvn clean install -PautoInstallPackage
    
After the installation, the following links may be useful:

* [SolrCloud](http://localhost:8983/solr/#/)
* [ActiveMQ](http://localhost:8161/admin) (admin / admin)
* [Demo Search Page](http://localhost:4502/content/aemsolrsearch-product-sample/en/search.html)

## Indexing Content

### Product Content

Index the Best uy movie product data. Refer to `camel-products-to-solr/README.md`.

### AEM Content

1. Change into the `camel-aem-to-solr` module and run Camel.

        $ cd camel-aem-to-solr
        $ ./run.sh

2. In a new terminal, tail the log file so that you can monitor the JMS index listener in AEM.

        $ tail -n0 -f /path-to-aem/crx-quickstart/logs/aemsolrsearch-product-sample.log


3. Create a page in AEM under `/content/aemsolrsearch-product-sample/en`. Notice
   that the event is handled by the JMS listener; sent to the `aem-index` topic in
   ActiveMQ; read by Camel; and sent to Solr for indexing.
