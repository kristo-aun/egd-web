#!/bin/sh
mvn clean package -Pprod -Dmaven.test.skip=true -e
