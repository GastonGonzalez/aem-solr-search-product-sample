#!/bin/bash

echo "Installing start up scripts..."
cp $INSTALL_HOME/sync/artifacts/solr1 /etc/init.d
cp $INSTALL_HOME/sync/artifacts/solr2 /etc/init.d
chmod 755 /etc/init.d/solr*

echo "Copying environment settings..."
cp $INSTALL_HOME/sync/artifacts/solr*.in.sh /etc/default

echo "Enabling Solr services..."
chkconfig solr1 on
chkconfig solr2 on
