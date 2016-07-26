#!/bin/bash
#
# Description: Provisioning script for my CIRCUIT talk. Creates a CentOS VM with ActiveMQ and Solr.

export INSTALL_HOME=/home/vagrant
export INSTALL_SCRIPTS=$INSTALL_HOME/sync/scripts

SAVE_CWD=`pwd`

echo "Installing OS packages..."
yum -y install curl lsof net-tools java-1.8.0-openjdk-devel 

cd $INSTALL_SCRIPTS

./install-solr.sh
./install-activemq.sh

cd $SAVE_CWD
