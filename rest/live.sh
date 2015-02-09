#!/bin/sh
mvn -Pprod package -Dmaven.test.skip=true -e
