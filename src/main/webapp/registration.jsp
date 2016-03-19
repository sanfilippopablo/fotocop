<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Fotocop</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!--Import Google Icon Font-->
      	<link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	 	 <!-- Compiled and minified CSS -->
  		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
		<link rel="stylesheet" href="styles/registration.css">
		<link rel="stylesheet" href="styles/full-height-main.css">
	</head>
	<body>

		<c:import url="partials/header.html" charEncoding="UTF-8"/>

		<main class="grey lighten-3">
			<div class="container">
				<div class="row">
					<hgroup>
						<h3 class="center-align">Registro</h3>
						<h5 class="center-align teal-text">Nuevo usuario</h5>
					</hgroup>
				</div>
				<div class="row">
					<form class="col s12 m6 l6 offset-m3 offset-l3 white" id="register" method="POST" action="/registration">

						<div class="row">
							<div class="input-field col s12">
								<input id="username" name="username" type="text" class="validate" value="<c:out value='${param.username}' />" />
								<label for="username">Usuario</label>
								<div class="error error-username">
									<c:out value="${validationManager.errorForField('username')}"/>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="input-field col s12">
								<input id="email" name="email" type="email" class="validate" value="<c:out value='${param.email}' />" />
								<label for="email">Email</label>
								<div class="error error-email">
									<c:out value="${validationManager.errorForField('email')}"/>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="input-field col s12 m12 l6">
								<input id="password" name="password" type="password" class="validate">
								<label for="password">Contraseña</label>
								<div class="error error-password">
									<c:out value="${validationManager.errorForField('password')}"/>
								</div>
							</div>
							<div class="input-field col s12 m12 l6"	>
								<input id="password2" name="password2" type="password" class="validate">
								<label for="password2">Repita contraseña</label>
								<div class="error error-password2">
									<c:out value="${validationManager.errorForField('password2')}"/>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col s12 center-align">
								<c:out value="${validationManager.errorForField('misc')}"/>
							</div>
						</div>

						<div class="row">
							<button class="waves-effect waves-teal btn btn-large col s12" type="submit" name="action">
								Registrarme
							</button>
						</div>
					</form>
				</div>
			</div>
		</main>

		<!-- Scripts -->

		<!--Import jQuery before materialize.js-->
      	<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
		<!-- Compiled and minified JavaScript -->
  		<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>

  		<script src="scripts/registration.js" type="text/javascript"></script>

	</body>
</html>
