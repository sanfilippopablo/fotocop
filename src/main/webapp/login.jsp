<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Fotocop</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	 	<link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.indigo-pink.min.css">
	 	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	 	<link rel="stylesheet" href="/estilos/login.css">
	</head>
	<body>
		<!-- Always shows a header, even in smaller screens. -->
		<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
			
			<!-- Menu -->
			<c:import url="templates/menu.html" charEncoding="UTF-8"/>

			<main class="mdl-layout__content">
			    <div class="page-content">
			    <!-- Your content goes here -->
					
					<hgroup>
						<h2>Ingreso</h2>

						<!-- Error general -->
						<c:if test="${(! validationManager.isValid()) && (validationManager.errorForField('username') == '') && (validationManager.errorForField('password') == '') }">
							<c:forEach items="${validationManager.getErrors()}" var="errorGeneral">
								<p class="errorField">
									<c:out value="${errorGeneral.value}"/>
								</p>
							</c:forEach>
						</c:if>
					</hgroup>


			    	<!-- Formulario de Login -->
			        <form action="login" method="post">
			        
							<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label group">
						    	<input class="mdl-textfield__input" type="text" name="username" value="<c:out value='${param.username}' />" />
						    	<label class="mdl-textfield__label" for="username">Usuario</label>
						  	</div>
						  	<p class="errorField">
						  		<c:out value="${validationManager.errorForField('username')}"/>
						  	</p>

							<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label group">
						    	<input class="mdl-textfield__input" type="password" name="password" value="<c:out value='${param.password}' />" />
						    	<label class="mdl-textfield__label" for="password">Contraseña</label>
						  	</div>  	
							<p class="errorField">
								<c:out value="${validationManager.errorForField('password')}"/>
							</p>

							<button type="submit" name="submitLogin" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored button">Ingresar</button>

					</form>
				</div>
			</main>
		</div>

		<!-- Scripts -->

		<script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>
	
	</body>
</html>