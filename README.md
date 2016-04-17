# AEM Solr Search: Product Sample

This project was created as companion code for a couple of my 2016 talks:

* _Advanced AEM Search - Consuming External Content and Enriching Content with Apache Camel_ - [CIRCUIT 2016](http://www.circuitdevcon.com/en/speakers.html). 
* _Do you need an external search platform for Adobe Experience Manager?_  - [IMMERSE'16](https://docs.adobe.com/dev/products/aem/events/0416/sessions.html)

This project is built on top of [_AEM Solr Search_](http://www.aemsolrsearch.com) and provides a demo eCommerce site in which the product integration is handled exclusively by Apache Solr.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, templates, runmode specific configs as well as Hobbes-tests
* ui.content: contains sample content using the components from the ui.apps

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage
    
Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish
    
Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
