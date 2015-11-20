# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|

  config.vm.box = "ubuntu/trusty64"

  config.vm.synced_folder "webapps", "/var/lib/tomcat7/webapps",
    mount_options: ["dmode=777,fmode=777"]

  config.vm.network "private_network", ip: "192.168.33.10"

  config.vm.provision "shell", inline: <<-SHELL
    sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password password devpassword'
    sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password devpassword'

    sudo apt-get update
    sudo apt-get install -y tomcat7 mysql-server maven

  SHELL

end
