<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
  	<meta charset="utf-8">
  	<title>Fotocop</title>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">

  	<link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">
      <link rel="stylesheet" href="/styles/full-height-main.css">
  	<link rel="stylesheet" href="/styles/jobs.css">
  </head>

  <body>

    <c:import url="partials/header.html" charEncoding="UTF-8"/>

  	<main class="grey lighten-3">
  		<nav>
  			<div class="nav-wrapper teal lighten-3">
  				<div class="container">
  					<div class="col s12">
  						<a href="works_user.html" class="breadcrumb">Trabajos</a>
  					</div>
  				</div>
  			</div>
  		</nav>
  		<div class="container">
  			<hgroup>
  				<div class="row">
  					<div class="title">
  						<div class="item item-primary">
  							<h4>Trabajos</h4>
  						</div>
  						<a href="work_user.html" class="waves-effect waves-light red btn-large add"><i class="material-icons left">add</i> Agregar</a>
  					</div>
  				</div>
  			</hgroup>
  			<hr />
  			<br />
  			<div>
          <c:forEach items="${jobs}" var="job">

            <c:if test='${job.getStatus() == "Abierto"}'>
              <div class="col s12 m6 l4">
      					<div class="card" style="border-left: 5px solid blue;">
      						<div class="card-content flex">
      							<div class="card-principal-contenido">
      								<div class="flex">
      									<i class="material-icons circle blue icon-card">schedule</i>
      									<h4 class="card-title center black-text text-darken-2"><c:out value='${job.getId()}' /></h4>
      								</div>
      								<p class="center flow-text blue-text block">Todavía no enviado.</p>
      							</div>
      							<a href="#" class="waves-effect waves-light btn">Detalles</a>
      						</div>
      					</div>
      				</div>
            </c:if>

            <c:if test='${job.getStatus() == "Enviado"}'>
              <div class="col s12 m6 l4">
      					<div class="card" style="border-left: 5px solid green">
      						<div class="card-content flex">
      							<div class="card-principal-contenido">
      								<div class="flex">
      									<i class="material-icons circle green icon-card">done</i>
      									<h4 class="card-title center black-text text-darken-2"><c:out value='${job.getId()}' /></h4>
      								</div>
      								<p class="center flow-text green-text block">TODO Acá va la ETA</p>
      							</div>
      							<a href="#" class="waves-effect waves-light btn">Detalles</a>
      						</div>
      					</div>
      				</div>
            </c:if>

            <c:if test='${job.getStatus() == "Listo"}'>
              <div class="col s12 m6 l4">
      					<div class="card" style="border-left: 5px solid red;">
      						<div class="card-content flex">
      							<div class="card-principal-contenido">
      								<div class="flex">
      									<i class="material-icons circle red icon-card">done_all</i>
      									<h4 class="card-title center black-text text-darken-2"><c:out value='${job.getId()}' /></h4>
      								</div>
      								<p class="center flow-text red-text block">Listo para retirar por fotocopiadora.</p>
      							</div>
      							<a href="#" class="waves-effect waves-light btn">Detalles</a>
      						</div>
      					</div>
      				</div>
            </c:if>

          </c:forEach>

        </div>
      </div>
  	</main>

  	<!-- Scripts -->
  	<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
  	<script src="scripts/jobs.js" type="text/javascript"></script>

  </body>

</html>
