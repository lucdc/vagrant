#!/usr/bin/env bash
 
# update apt
sudo apt-get update
 
# install java
sudo apt-get install openjdk-7-jre-headless -y
 
# install elasticsearch
wget https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-1.1.1.deb
sudo dpkg -i elasticsearch-1.1.1.deb
sudo service elasticsearch start
 
# install head plugin
sudo /usr/share/elasticsearch/bin/plugin -install mobz/elasticsearch-head


# install mongodb
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | sudo tee /etc/apt/sources.list.d/mongodb.list
sudo apt-get update
sudo apt-get install -y mongodb-org

cp /vagrant/files/mongod.conf /etc/mongod.conf

sudo service mongod restart