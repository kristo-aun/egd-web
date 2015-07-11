#!/bin/sh

#tomcat-muisb shutdown
sh /usr/local/tomcat/bin/shutdown.sh

#remove old files
rm -R /usr/local/tomcat/webapps/ROOT/ /usr/local/tomcat/webapps/ROOT.war /usr/local/tomcat/conf/Catalina /usr/local/tomcat/temp/* /usr/local/tomcat/work/*

#build new muis war
sh dev-package.sh

#copy war to tomcat-muisb webapps
mv target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

#reset logs
cat /dev/null > /usr/local/tomcat/logs/catalina.out

#tomcat-muisb startup
sh /usr/local/tomcat/bin/startup.sh && tail -f /usr/local/tomcat/logs/catalina.out
