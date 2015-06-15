#!/bin/sh

#mvn install spring-boot:repackage -Dmaven.test.skip=true
mvn package -Dmaven.test.skip=true -Pprod -e
