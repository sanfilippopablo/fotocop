# Allows root to access from outside the host.
CREATE USER root@'%' IDENTIFIED BY 'rootpassword';
GRANT ALL PRIVILEGES on *.* TO root@'%' WITH GRANT OPTION;
