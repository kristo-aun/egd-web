#!/bin/sh

#mvn install spring-boot:repackage -Dmaven.test.skip=true
mvn clean package -Dmaven.test.skip=true -Pprod -e

#rm target/ROOT.war
#mv target/ROOT.war.original target/ROOT.war
