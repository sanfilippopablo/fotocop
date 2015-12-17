# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|

  config.vm.box = "ubuntu/trusty64"

  config.vm.synced_folder "webapps", "/var/lib/tomcat7/webapps",
    mount_options: ["dmode=777,fmode=777"]

  config.vm.network "private_network", ip: "192.168.33.10"

  config.vm.provision "shell", inline: <<-SHELL
    sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password password rootpassword'
    sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password rootpassword'

    sudo apt-get update
    sudo apt-get install -y tomcat7 mysql-server maven python-pip build-essential python-dev libxml2-dev libxslt-dev python-lxml default-jdk python-virtualenv

    # Setup docs
    cd /vagrant/docs
    virtualenv --no-site-packages .
    source bin/activate
    pip install -r requirements.txt

    cd /vagrant

    # Export DB params. Those are the environment variables used by OpenShift,
    # so to be able to use the same Java code to open a connection using them
    # in both environments, I'll set them with the same name here.
    export OPENSHIFT_MYSQL_DB_USERNAME=root
    export OPENSHIFT_MYSQL_DB_PASSWORD=rootpassword
    sudo echo "export OPENSHIFT_MYSQL_DB_USERNAME=root" >> /etc/profile
    sudo echo "export OPENSHIFT_MYSQL_DB_PASSWORD=rootpassword" >> /etc/profile

    # Configure mysqld to listen on all interfaces
    sudo sed -i "s/.*bind-address.*/bind-address = 0.0.0.0/" /etc/mysql/my.cnf
    sudo service mysql restart

    # Allow root to access from outside the host.
    mysql -u$OPENSHIFT_MYSQL_DB_USERNAME -p$OPENSHIFT_MYSQL_DB_PASSWORD < scripts/setup_user.sql

    # Setup DB
    mysql -u$OPENSHIFT_MYSQL_DB_USERNAME -p$OPENSHIFT_MYSQL_DB_PASSWORD < scripts/setup_db.sql

  SHELL

end
