# LXC

This Readme will guide you through setting up the Debian 8 environment for LXC containers, including how to set up a bridged network.

http://lauri.xn--vsandi-pxa.com/cfgmgmt/linux-containers.html

# 

lxc-ls -f

lxc-start -n guest-jessie -d


lxc-attach -n guest-jessie

lxc-stop -n guest-jessie


ssh -nNT -L 3000:localhost:22 root@192.168.72.136

