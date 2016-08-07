## Install Tomcat

$catalina_home = '/opt/tomcat'

tidy { "tidy_tomcat":
    path => $catalina_home,
    recurse => true,
    backup => false,
    matches => ["LICENSE", "NOTICE", "RELEASE-NOTES", "RUNNING.txt", "logs/catalina.out", "temp/safeToDelete.tmp", "webapps/*", "bin/*.bat"]
}

notify {"Finished puppet script!":}
