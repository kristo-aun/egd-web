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
wget http://mirror.metrocast.net/apache/tomcat/tomcat-8/v8.0.21/bin/apache-tomcat-8.0.21.tar.gz
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
Securing a site requires creating an SSL certificate and installing it to your Tomcat server.

Lets create a folder for the neccessary files under tomcat/conf because it's good to have all configuration files at one place.<br/>
```
cd /usr/local/tomcat/
mkdir conf/ssl/ && cd conf/ssl/
```
 
### Private key & keystore
Generate a private key. Make sure you keep this file secret at all times. You need openssl and keytool to execute the following commands in terminal. <br/>
```
openssl genrsa -out rootCA.key 2048
```

Lets create a CA certificate based on the private key.
Normally, the certificate would have to be authorized by a proper CA, but for testing purposes it's a great way to secure your sites.
It seems that this practice is quite popular with internal corporate websites as well.<br/>
```
openssl req -x509 -new -nodes -key rootCA.key -out rootCA.crt -days 1825 -subj "/CN=*.somedomain.com/OU=MyOrganizationalUnit/O=MyOrganization/L=MyCity/S=MyState/C=EE/emailAddress=someone@somewhere.org"
```
 
Tomcat needs a Java keystore file<br/>
```
keytool -genkey -keyalg RSA -alias tomcat -dname "CN=localhost, OU=Development, O=EsutoniaGoDesu, L=Tallinn, S=Harju, C=EE" -keystore localhost.keystore
```
<br/>Common practice is to use changeit for keystore password. Don't enter keypassword for tomcat (3. prompt).
 
### Authorization

Apply for a signed certificate<br/>
```
keytool -certreq -alias tomcat -keyalg RSA -file localhost.csr -keystore localhost.keystore
```
 
Sign the certificate request yourself<br/>
```
openssl x509 -req -in localhost.csr -CA rootCA.crt -CAkey rootCA.key -CAcreateserial -out localhost.crt -days 1825
```
 
### Securing tomcat

Install CA to keystore<br/>
```
keytool -import -alias root -keystore localhost.keystore -trustcacerts -file rootCA.crt
```
<br/>Type yes when asked to trust this certificate


Install CA reply to keystore<br/>

```
keytool -import -alias tomcat -keystore localhost.keystore -file localhost.crt
```
<br/>You should get "Certificate reply was installed in keystore" for reply. 
 
 
### Install your CA certificate to browsers
To make your browsers trust the site, you need to manually install
either tomcat's certificate or your CA certificate to your browsers.
It is preferrable to install the CA, because then you'll be able to secure other sites as well without
installing their certificate each time.

- **Chrome**: [guide](http://portal.threatpulse.com/docs/sol/Content/03Solutions/ManagePolicy/SSL/ssl_chrome_cert_ta.htm)
- **Firefox**: [guide](http://wiki.wmtransfer.com/projects/webmoney/wiki/Installing_root_certificate_in_Mozilla_Firefox)
- **Windows**: IE uses Windows's own authority management - [guide](https://msdn.microsoft.com/en-us/library/cc750534.aspx)



## JNDI


## PSI Probe
Only [Andresol's psi-probe-plus](https://github.com/andresol/psi-probe-plus) fork from the [psi-probe](https://github.com/testdriven/psi-probe) seems to be working properly on Tomcat 8.
Before compiling you might want to get rid of the excessive connectors (5,6,7) by removing them from pom.xml.

PS! Global datasource monitoring is still not working in this version of PSI Probe.
If you can't live without, install Tomcat 7.0.53 or earlier.


## Helpful keystore commands
Verify the list of certs in trust store using
```
keytool -list -keystore egd.truststore
```
<br/>Truststore should only contain root CAs. Default EGD truststore contains www.sk.ee certificates for Mobile-ID authentication purposes.

## Email

Using Java email service requires you to to trust external service providers. Here is how to trust Gmail by adding their base64 certificate to truststore.
By default Gmail's certificate is already in egd.truststore.

    openssl s_client -connect smtp.gmail.com:465 > gmail.crt

Now remove everything except the certificate and then add the certificate to truststore

    keytool -import -alias smtp.gmail.com -keystore egd.truststore -file gmail.crt
    
    
## Bing translation service
Same thing with has to be done with Bing translator API.

    openssl s_client -connect datamarket.accesscontrol.windows.net:443 > ms.crt    

Now remove everything except the certificate and then add the certificate to truststore
    
    keytool -import -alias datamarket.accesscontrol.windows.net -keystore egd.truststore -file ms.crt
