############
Casos de Uso
############

.. contents::
  :depth: 2
  :local:


Registrar un nuevo usuario regular
===================================

Versión 1.0

Camino básico
***************

1. Usuario no registrado selecciona la opción "Nuevo usuario". Sistema muestra el form de nuevo usuario.
2. Usuario no registrado ingresa la información pedida (username, pass con confirmación, mail, nombre, apellido y DNI) y confirma. Sistema se encarga del registro del nuevo usuario e informa al usuario.

Alternativas
**************

2.A Mail no válido.
  2.A.1 Sistema informa situación (sin borrar la información ingresada).
2.B Las contraseñas no coinciden.
  2.B.1 Sistema informa situación (sin borrar la información ingresada).
2.C Username ya existe.
  2.C.1 Sistema informa situación (sin borrar la información ingresada).
2.D Ya hay un usuario asociado a ese mail.
  2.D.1 Sistema informa situación (sin borrar la información ingresada).

Notas
********
No aplica.
