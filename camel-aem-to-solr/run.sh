#!/bin/bash
#
# A wrapper script for building and executing Camel as a standalone application.

mvn clean compile exec:java -Dexec.mainClass=com.gastongonzalez.circuit.camel.aem.main.AemToSolrRoutes
