## Puppet installation instructions for CentOS 6  

### Required tools for installation

    yum -y install tar wget git

### Add PuppetLabs repository & install Puppet from package
    
    rpm -ivh http://yum.puppetlabs.com/puppetlabs-release-el-6.noarch.rpm  
    yum install puppet-server  

### Check for Puppet version

    puppet --version    

### Intall required Puppet modules
    
    puppet module install puppetlabs-stdlib  
    puppet module install puppet-archive
    puppet module install puppetlabs-tomcat          
  
#### You should now have puppet installed at "/etc/puppet/" .  

### Install the Puppet module required for Oracle JDK installation via Git
  
    git clone https://github.com/tylerwalts/puppet-jdk_oracle.git /etc/puppet/modules/jdk_oracle

### A fix to a missing Hiera configuration. ("Warning: Config file /etc/puppet/hiera.yaml not found, using Hiera defaults")

    ln -s /etc/hiera.yaml /etc/puppet/hiera.yaml
  
## Install Oracle JDK 8   
### First, change the parameters inside the Puppet script to include the latest Oracle JDK 8. You will find the correct build number from the binary download link. (http://download.oracle.com/otn-pub/java/jdk/8u101-b13/jdk-8u101-linux-x64.tar.gz)
  
    puppet apply pp/install_oracle_jdk.pp

## Install Tomcat
    
    puppet apply pp/install_apache_tomcat.pp

### Configure Tomcat for parallel deployment

    puppet apply pp/configure_apache_tomcat.pp
    
### Remove unnecessary files in the Tomcat folder
    
    puppet apply tomcat_tidy.pp