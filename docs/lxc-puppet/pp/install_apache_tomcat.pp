## Install Tomcat

$catalina_home = '/opt/tomcat'

tomcat::install {$catalina_home :
    source_url => 'http://www-us.apache.org/dist/tomcat/tomcat-8/v8.5.4/bin/apache-tomcat-8.5.4.tar.gz'
}

tomcat::instance {'default':
    catalina_home => $catalina_home ,
}

notify {"Finished puppet script!":}