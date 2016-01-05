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
mvn compile war:war
````

La máquina está configurada para crear una red privada con IP `192.168.33.10`. Tomcat está configurado para correr en el puerto 8080. Por lo tanto, en la máquina host se puede ver el sitio en `http://192.168.33.10:8080`.

> Nota: Luego de un cambio al Vagrantfile, es necesario reaprovisionar la máquina. Para eso ejecutar `vagrant provision` o `vagrant destroy && vagrant halt` para evitar conflictos.

## Database

La DB es MySQL corriendo dentro de la máquina virtual escuchando en el puerto por defecto, 3306. Está configurada para escuchar en todas las interfaces (0.0.0.0), así que se puede acceder desde afuera usando la dirección de la máquina como host: `192.168.33.10`, útil para administrarla con Workbench o esa onda.

El usuario para acceder es `root` y el password es `rootpassword`. Estos valores están disponibles dentro de la máquina en las variables del entorno `OPENSHIFT_MYSQL_DB_USERNAME` y `OPENSHIFT_MYSQL_DB_PASSWORD`. Estos valores también están seteados dentro del entorno de openshift con los valores correspondientes para acceder a la DB seteada ahí adentro. Por eso, en el código hay que usar estas variables.

Dentro de /scripts está el script `setup_db.sql` que contiene el schema de la DB. Se ejecuta al crearse la máquina. Siempre que se haga un cambio en el schema de la DB, hacer un export de la nueva DB a este archivo.

## Docs

La documentación se crea usando la sintaxis reStructuredText a través Sphinx. Documentación básica del lenguaje [acá](http://sphinx-doc.org/rest.html).

Los archivos de documentación se guardan con extensión .rst dentro de docs/ y deben ser agregados al toctree en index.rst.

Para hacer el build:

1. Entrar a docs y activar el virtual environment.

````
cd docs
source ~/docsvenv/bin/activate
````

2. Hacer el build.

````
make html
````

Esto genera el html dentro de `_build/html`. Hay un servidor nginx configurado automáticamente para servir esa carpeta en el puerto 8000. Por lo tanto, se puede acceder desde el navegador en `http://192.168.33.10:8000`.

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

## Tests

Los tests tienen que estar dentro de src/test/java/fotocop. Los unit tests tienen que tener el nombre a lo NombreTest.java. Los test de integración deben ser a lo NombreIT.java.

Para correr todos los tests:
````
mvn verify
````

Para correr sólo los tests unitarios:
````
mvn test
````

Para que en los tests de integración se resetee la DB antes de cada Test, extender la clase del test de testutils.IntegrationBase.

## Notas adicionales

Avisar siempre que haya un cambio al entorno (cambios en el Vagrantfile o en el scripts/setup_db.sql) para que todos actualicemos nuestros respectivos entornos con `vagrant provision`.
