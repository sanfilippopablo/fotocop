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
  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css" />
    <link rel="stylesheet" href="/styles/full-height-main.css" />
		<link rel="stylesheet" href="/styles/job.css" />
	</head>
	<body>

    <c:import url="partials/header.html" charEncoding="UTF-8"/>

		<main class="grey lighten-3">
			<nav>
				<div class="nav-wrapper teal lighten-3">
					<div class="container">
						<div class="col s12">
							<a href="works_user.html" class="breadcrumb">Trabajos</a>
							<a href="#!" class="breadcrumb">Matemática Superior</a>
						</div>
					</div>
				</div>
			</nav>
        <div class="container">
          <hgroup>
					  <div class="flex flex-text-buttom">
              <h4 class="title-work">Trabajo <strong><c:out value='${job.getId()}'/></strong></h4>
              <p class="state-work">Estado <strong><c:out value='${job.getEstado()}' /></strong></p>
              <a class="waves-effect waves-light modal-trigger red btn-large add" href="#modal1"><i class="material-icons left">add</i> Agregar</a>
					  </div>
				  </hgroup>
          <hr />

          <c:forEach items="${jobs.getJobLines()}" var="jobLine">
  				  <div class="col s12 m6 l4">
  				    <div class="card flex">
                <h5 class="name-file"><c:out value='${jobLine.getFile().getName()}'/></h5>
                <div>
                    <p>Cantidad: <strong><c:out value='${jobLine.getQuantity()}'/></strong></p>
                    <p>Doble Faz: <strong><c:out value='${jobLine.isDobleFaz()}'/></strong></p>
                    <p>Abrochado: <strong><c:out value='${jobLine.isAbrochado()}'/></strong></p>
                    <p>Anillado: <strong><c:out value='${jobLine.isAnillado()}'/></strong></p>
                </div>
  			        <a class="waves-effect waves-light btn modal-trigger button-file" href="#modal1"><i class="material-icons">edit</i></a>
              </div>
    				</div>
          </c:forEach>

          <div class="flex flex-button">
              <a class="waves-effect waves-light blue btn-large send">Enviar</a>
              <a class="waves-effect waves-light red btn-large cancelar">Cancelar</a>
          </div>

          <!-- Modal Structure -->
          <div id="modal1" class="modal">
              <div class="modal-content">
                  <h4>Añadir archivo</h4>
                  <tr />
                  <br />
                  <form action="#">
                      <div class="file-field input-field">
                          <div class="btn">
                              <span>Archivo</span>
                              <input type="file">
                          </div>
                          <div class="file-path-wrapper">
                              <input class="file-path validate" type="text">
                          </div>
                      </div>
                      <div class="flex">
                          <div class="input-field">
                              <p>
                                <input type="checkbox" id="doble-faz" />
                                <label for="doble-faz">Doble faz</label>
                              </p>
                              <p>
                                <input type="checkbox" id="anillado" />
                                <label for="anillado">Anillado</label>
                              </p>
                              <p>
                                <input type="checkbox" id="abrochado" />
                                <label for="abrochado">Abrochado</label>
                              </p>
                          </div>
                          <br />
                          <div class="input-field">
                              <input id="copias" type="number" class="validate">
                              <label for="copias">Cantidad de copias</label>
                          </div>
                      </div>
                  </form>
              </div>
              <div class="modal-footer">
                  <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat ">Cancelar</a>
                  <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat ">Agregar</a>
              </div>
          </div>
      </div>
		</main>

		<!-- Scripts -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
		<script src="scripts/job.js" type="text/javascript"></script>

	</body>
</html>
