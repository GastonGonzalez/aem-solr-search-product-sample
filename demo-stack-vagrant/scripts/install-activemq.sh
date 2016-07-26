#!/bin/bash
#
# Description: Installs ActiveMQ 5.13.4 from scratch.

ACTIVEMQ_VERSION=5.13.4
ACTIVEMQ_TARBALL=apache-activemq-$ACTIVEMQ_VERSION-bin.tar.gz
ACTIVEMQ_TGZ_URL=https://archive.apache.org/dist/activemq/$ACTIVEMQ_VERSION/$ACTIVEMQ_TARBALL
ACTIVEMQ_HOME=/opt/apache-activemq

SAVE_CWD=`pwd`

echo "Downloading ActiveMQ ${ACTIVEMQ_VERSION}..."
curl -s -O "${ACTIVEMQ_TGZ_URL}"

echo "Installing ActiveMQ ${ACTIVEMQ_VERSION}..."
cd /opt
tar -xzf $INSTALL_SCRIPTS/$ACTIVEMQ_TARBALL
ln -s /opt/apache-activemq-$ACTIVEMQ_VERSION $ACTIVEMQ_HOME

echo "Creating activemq user and fixing permissions..."
useradd activemq
chown -R activemq:activemq $ACTIVEMQ_HOME /opt/apache-activemq-$ACTIVEMQ_VERSION

echo "Installing startup script..."
cp $INSTALL_HOME/sync/artifacts/activemq /etc/init.d
chmod 700 /etc/init.d/activemq
chown root:root /etc/init.d/activemq
chkconfig activemq on

echo "Starting ActiveMQ..."
service activemq start

cd $SAVE_CWD
