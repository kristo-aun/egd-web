#!/bin/sh
mvn clean spring-boot:run -e -Pdev --log-file=mvn.log > run.log
#mvn clean spring-boot:run -e -Pdev
