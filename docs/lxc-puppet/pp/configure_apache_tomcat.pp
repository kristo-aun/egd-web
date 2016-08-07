## Install Tomcat

$host = localhost

tomcat::config::server::host {$host:
    app_base => 'webapps' ,
    server_config => "/opt/tomcat/conf/server.xml" ,
    additional_attributes => {
   	    'undeployOldVersions' => 'true'
    }
}

notify {"Finished puppet script!":}