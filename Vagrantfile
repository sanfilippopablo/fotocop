# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|

  config.vm.box = "ubuntu/trusty64"

  config.vm.synced_folder "webapps", "/var/lib/tomcat7/webapps",
    mount_options: ["dmode=777,fmode=777"]

    config.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

  config.vm.network "private_network", ip: "192.168.33.10"

  config.vm.provision "shell", privileged: false, inline: <<-SHELL
    cd /vagrant

    sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password password rootpassword'
    sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password rootpassword'

    sudo apt-get update
    sudo apt-get install -y tomcat7 mysql-server maven python-pip build-essential nginx firefox xvfb python-dev zlib1g-dev libxml2-dev libxslt-dev python-lxml default-jdk python-virtualenv

    # Export DB params. Those are the environment variables used by OpenShift,
    # so to be able to use the same Java code to open a connection using them
    # in both environments, I'll set them with the same name here.
    sudo cp utils/db_vars.sh /etc/profile
    sudo cp utils/db_vars.sh /usr/share/tomcat7/bin/setenv.sh

    source /etc/profile
    sudo service tomcat7 restart

    # Configure mysqld to listen on all interfaces
    sudo sed -i "s/.*bind-address.*/bind-address = 0.0.0.0/" /etc/mysql/my.cnf
    sudo service mysql restart

    # Allow root to access from outside the host.
    mysql -u$OPENSHIFT_MYSQL_DB_USERNAME -p$OPENSHIFT_MYSQL_DB_PASSWORD < scripts/setup_user.sql

    # Setup DB
    mysql -u$OPENSHIFT_MYSQL_DB_USERNAME -p$OPENSHIFT_MYSQL_DB_PASSWORD < scripts/setup_db.sql


    # DOCS
    virtualenv --no-site-packages ~/docsvenv
    source ~/docsvenv/bin/activate
    pip install -r docs/requirements.txt

    # Setup nginx to serve docs/_build/html
    sudo cp utils/docs.conf /etc/nginx/sites-available/docs
    sudo ln -s /etc/nginx/sites-available/docs /etc/nginx/sites-enabled
    sudo service nginx restart

  SHELL

end
