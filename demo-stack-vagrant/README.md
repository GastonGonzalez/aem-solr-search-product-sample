# About This Project

This project is a Vagrant image for use with the demo code. It provides fully configured instances
of Apache Solr and ActiveMQ. The provisioning process configures Solr in SolrCloud mode, with two
nodes and an embedded ZooKeeper instance. 

**Please note, this stack is not suitale for production deployments and is only designed to support 
the demo for my CIRCUIT presentation.**

# Requirements

* VirtualBox - https://www.virtualbox.org/wiki/Downloads
* Vagrant - https://www.vagrantup.com/downloads.html


# Provisioning Solr 5

1. Change into the root of this directory: `aem-solr-search-product-sample/demo-stack-vagrant`

2. Provision the VM with Vagrant:

        $ vagrant up

3. In order for the SolrJ client to work nicely with ZooKeeper, you will need to add the following entries to your host 
   operating system's hosts file:

        127.0.0.1 solrnode1
        127.0.0.1 solrnode2 

4. Once the provisioning is done, you can access Solr and ActiveMQ via a web browser from your host
   operating system.

    * [http://solrnode1:8983/solr](http://solrnode1:8983/solr)
    * [http://solrnode2:7574/solr](http://solrnode2:7574/solr)
    * [localhost:8161/admin](localhost:8161/admin)
  
    
# Development Recommendations

This project is intended to help your team version your Solr project. The typical development flow looks as follows:

1. Make your desired modification to `aemsolrsearch-vagrant/solr-home`.

2. Destroy the current VirtualBox VM and provision a new VM. This will rebuild Solr from scratch using the configuration
   in this project.

        $ vagrant destroy -f && vagrant up
        
3. Re-index your content.
