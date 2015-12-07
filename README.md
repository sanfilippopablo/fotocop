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


## Build docs

La documentación se crea usando la sintaxis reStructuredText a través Sphinx.

Los archivos de documentación se guardan con extensión .rst dentro de docs/ y deben ser agregados al toctree en index.rst.

Para hacer el build:

1. Entrar a docs y activar el virtual environment.

````
cd docs
source bin/activate
````

2. Hacer el build.

````
make html
````

Esto genera el html dentro de `_build/html`. Para visualizarla, servirla con un servidor HTTP.
````
cd _build/html
python -m SimpleHTTPServer
````
Luego acceder desde el navegador en `http://192:168.33.10:8000`.
