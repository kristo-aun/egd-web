# Install Node.js in Debian Wheezy

Authorize as root
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


Compile. This took about 20 minutes with Intel® Atom™ 1.86 GHz Dual-core.
====
	make


Install via package manager
====
	checkinstall --type=debian --install=yes

- in the dialog, remove v from before version number (field no. 3)

Install bower via npm
====
	npm install -g bower


Install grunt via npm
====
	npm install -g grunt-cli
	

To solve an imagemin during compile time
====
    npm install grunt-contrib-imagemin --save-dev
    npm install -save-dev

    
