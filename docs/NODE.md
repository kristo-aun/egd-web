# Install Node.js in Debian Wheezy

Authenthicate as root
====
	su -


Get the latest Node.js archive
====
	wget http://nodejs.org/dist/node-latest.tar.gz


Unpack
====
	tar -xf node-latest.tar.gz


Move inside the folder
====
	cd node-v*



Install dependencies
====
	apt-get install python g++ wget libssl-dev checkinstall


Configure for your machine
====
	./configure


Compile. This will take about 10 minutes.
====
	make


Install via package manager
====
	checkinstall --type=debian --install=yes
