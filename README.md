Fotocop
=======

## Corriendo el entorno de desarrollo

Clonar este repo.

````
git clone git@gitlab.com:sanfilippopablo/fotocop.git
cd fotocop
````

Levantar la máquina virtual.

````
vagrant up
````

Entrar en la máquina virtual a través de SSH.

````
vagrant ssh
cd /vagrant
````

Compilar.

````
mvn war:war
````

La máquina está configurada para crear una red privada con IP `192.168.33.10`. Tomcat está configurado para correr en el puerto 8080. Por lo tanto, en la máquina host se puede ver el sitio en `http://192.168.33.10:8080`.