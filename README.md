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

> Nota: Luego de un cambio al Vagrantfile, es necesario reaprovisionar la máquina. Para eso ejecutar `vagrant provision` o `vagrant destroy && vagrant halt` para evitar conflictos.

## Build docs

La documentación se crea usando la sintaxis reStructuredText a través Sphinx. Documentación básica del lenguaje [acá](http://sphinx-doc.org/rest.html).

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
Luego acceder desde el navegador en `http://192.168.33.10:8000`.

### Documentación en el código

Se debe usar la sintaxis de Javadoc para documentar el código. Idealmente, todas las clases y métodos deberían estar documentados.
La referencia completa de la sintaxis Javadoc puede encontrarse [acá](http://www.oracle.com/technetwork/articles/java/index-137868.html), sin embargo, con el siguiente ejemplo basta:

````java
package main;

/**
* Permite hacer algunos cálculos básicos.
*/
public class A {

  /**
  * Suma dos números.
  *
  * @param a un entero
  * @param b otro entero
  * @return La suma de los dos
  */
  public static int sum (int a, int b) {
    return a + b;
  }

  /**
  * Resta dos números.
  *
  * @param a un entero
  * @param b otro entero
  * @return La resta de los dos
  */
  public static int diff (int a, int b) {
    return a - b;
  }
}
````

El build system de los docs está configurado para incluir los docs del código automáticamente en la documentación generada por Sphinx.
