# Install Maven3 in Linux

Download latest Apache Maven3
====
	http://maven.apache.org/download.cgi


Unpack
====
	tar -xf apache-maven-3*


Relocate Maven3 binaries
====
	mv apache-maven-3* /opt/maven3


Check if Java is installed
====
	java -version


Setup Maven3 path and Environment variables
====
	nano ~/.bashrc


Insert these lines at the end
====
	export M3_HOME=/opt/maven3
	export M3=$M3_HOME/bin
	export PATH=$M3:$PATH


Logout, login
====

Test Maven3 installation
====
	mvn -version


Successful reply exmple
====
	Apache Maven 3.2.1 (ea8b2b07643dbb1b84b6d16e1f08391b666bc1e9; 2014-02-14T19:37:52+02:00)
	Maven home: /usr/share/maven3
	Java version: 1.8.0_31, vendor: Oracle Corporation
	Java home: /usr/lib/jvm/jdk8/jre
	Default locale: en_US, platform encoding: UTF-8
	OS name: "linux", version: "3.13.0-45-generic", arch: "amd64", family: "unix"
