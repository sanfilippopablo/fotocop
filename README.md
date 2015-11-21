Fotocop
=======

## Corriendo el entorno de desarrollo

1. Clonar este repo.

````
git clone git@gitlab.com:sanfilippopablo/fotocop.git
cd fotocop
````

2. Levantar la máquina virtual.

````
vagrant up
````

3. Entrar en la máquina virtual a través de SSH.

````
vagrant ssh
cd /vagrant
````

4. Compilar.

````
mvn war:war
````

La máquina está configurada para crear una red privada con IP `192.168.33.10`. Tomcat está configurado para correr en el puerto 8080. Por lo tanto, en la máquina host se puede ver el sitio en `http://192.168.33.10:8080`.