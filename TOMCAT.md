# Tomcat
This guide describes how to set up and secure Tomcat 8.0 for EsutoniaGoDesu purposes.
Keep in mind that this guide is Linux-specific. If you need to install Tomcat on Windows or some other OS, you should have enough
sysadmin knowledge to get it set up and secure.

These guides might be helpful as well:

- **Java**: [setting up Oracle Java in Debian](https://www.digitalocean.com/community/tutorials/how-to-manually-install-oracle-java-on-a-debian-or-ubuntu-vps)
- **Tomcat**: [setup](http://tomcat.apache.org/tomcat-8.0-doc/setup.html)
- **Certificate** [configuring identity and trust](http://docs.oracle.com/cd/E13222_01/wls/docs103/secmanage/identity_trust.html)

## Setting up Tomcat
Download and install Tomcat manually<br/>
```
wget http://mirror.metrocast.net/apache/tomcat/tomcat-8/v8.0.15/bin/apache-tomcat-8.0.15.tar.gz
```

Extract the archive<br/>
```
sudo tar -xvf apache-tomcat-8.0.15.tar.gz /usr/local/tomcat/
```

You should have rights to the tomcat folder<br/> 
```
sudo chown -R my_username:my_username /usr/local/tomcat/
```

## Securing HTTP
Secring a site requires creating an SSL certificate and securing your Tomcat server. 


```
cd /usr/local/tomcat/
mkdir conf/ssl/ && cd conf/ssl/
```
 
### Private key & keystore
Generate a private key. Make sure you keep this file secret at all times.<br/>
```
openssl genrsa -out rootCA.key 2048
```

Lets create a CA certificate based on the private key.
Normally, the certificate would have to be authorized by a proper CA, but for testing purposes it's a great way to secure sites.
It seems that this practice is quite popular with internal corporate websites as well.<br/>
```
openssl req -x509 -new -nodes -key rootCA.key -out rootCA.crt -days 1825 "CN=*.koodur.com, OU=Development, O=Mintal, L=Tallinn, S=Harju, C=EE"
```
 
Tomcat needs a Java keystore file<br/>
```
keytool -genkey -keyalg RSA -alias tomcat -dname "CN=localhost, OU=EsutoniaGoDesu, O=Mintal, L=Tallinn, S=Harju, C=EE" -keystore momo-dev.keystore
```
 
### Authorization

Apply for a signed certificate<br/>
```
keytool -certreq -alias tomcat -keyalg RSA -file momo-dev.csr -keystore momo-dev.keystore -validity 1825
```
 
Sign the certificate request yourself<br/>
```
openssl x509 -req -in momo-dev.csr -CA rootCA.crt -CAkey rootCA.key -CAcreateserial -out momo-dev.crt -days 1825
```
 
### Securing tomcat

Install CA to keystore<br/>
```
keytool -import -alias root -keystore momo-dev.keystore -trustcacerts -file rootCA.crt
```

Install CA reply to keystore<br/>

```
keytool -import -alias tomcat -keystore momo-dev.keystore -file momo-dev.crt
```
 
### Install your root CA
To make your browsers trust the site, you need to manually install
either tomcat's certificate or your CA certificate to the browsers. 
It is preferrable to install the CA, because then you'll be able to secure other sites as well without
installing their certificate each time.

- **Chrome**: [guide](http://portal.threatpulse.com/docs/sol/Content/03Solutions/ManagePolicy/SSL/ssl_chrome_cert_ta.htm)
- **Firefox**: [guide](http://wiki.wmtransfer.com/projects/webmoney/wiki/Installing_root_certificate_in_Mozilla_Firefox)
- **Windows**: IE uses Windows's own authority management - [guide](https://msdn.microsoft.com/en-us/library/cc750534.aspx)



## JNDI


## PSI Probe
Currently the one [psi-probe](https://github.com/testdriven/psi-probe) fork that seems to be working properly on Tomcat 8 is [Andresol's psi-probe-plus](https://github.com/andresol/psi-probe-plus).
You can get rid of the excessive connectors by removing them from pom.xml as we only need a connector for Tomcat 8.

PS! Global datasource monitoring is still not working. The latest version of Tomcat that shows them is 7.0.53.
