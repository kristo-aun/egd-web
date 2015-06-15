#!/bin/sh

#mvn install spring-boot:repackage -Dmaven.test.skip=true
mvn clean package -Dmaven.test.skip=true -Pprod -e
