#!/bin/bash
#
# Description: Installs and configures Solr 5.4.1 from scratch.

SOLR_VERSION=5.4.1
SOLR_TGZ_URL=https://archive.apache.org/dist/lucene/solr/$SOLR_VERSION/solr-$SOLR_VERSION.tgz
SOLR_HOST_NODE1="solrnode1"
SOLR_HOST_NODE2="solrnode2"
SOLR_HOME=/opt/solr-home
SOLR_INSTALL_DIR=/opt/solr

CWD=`pwd`

echo "Downloading Solr ${SOLR_VERSION}..."
curl -s -O "$SOLR_TGZ_URL"

echo "Updating /etc/hosts..."
echo "127.0.0.1 $SOLR_HOST_NODE1" >> /etc/hosts
echo "127.0.0.1 $SOLR_HOST_NODE2" >> /etc/hosts

echo "Installing Solr $SOLR_VERSION..."
cd /opt
tar -xzf $INSTALL_SCRIPTS/solr-$SOLR_VERSION.tgz
ln -s /opt/solr-$SOLR_VERSION $SOLR_INSTALL_DIR
cp -r /home/vagrant/sync/solr-home /opt

cd $SOLR_INSTALL_DIR

echo "Creating Solr user and fixing permissions..."
useradd solr
chown -R solr:solr $SOLR_HOME $SOLR_INSTALL_DIR /opt/solr-$SOLR_VERSION

# Install System V scripts
$INSTALL_SCRIPTS/install-solr-services.sh

echo "Starting Solr node 1..."
service solr1 start

echo "Uploading movies config set to Zookeeper"
$SOLR_INSTALL_DIR/server/scripts/cloud-scripts/zkcli.sh -zkhost 127.0.0.1:9983 -cmd upconfig -confname movies -confdir $SOLR_HOME/configsets/movies/conf

echo "Starting Solr node 2..."
service solr2 start

echo "Creating collection..."
curl "http://localhost:8983/solr/admin/collections?action=CREATE&name=movies&numShards=2&replicationFactor=2&maxShardsPerNode=2&collection.configName=movies"

cat << EOF

*****************************************************************************************
* On your host OS, please add the following host entries for ZooKeeper to work correctly:
* 
* 127.0.0.1 $SOLR_HOST_NODE1
* 127.0.0.1 $SOLR_HOST_NODE2
*****************************************************************************************
EOF
