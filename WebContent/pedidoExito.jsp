<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PFG Jorge Villalba Ruiz</title>
<link rel="stylesheet" type="text/css" href="css/cuentaUsuario.css" />
<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script>
	$(document).ready(function() {
		//aqui va el codigo de javascript

		$("#check").hide().fadeIn(3000);
	});
</script>
</head>

<body>

	<div class=" contenedorTitulo">
		<img class="imagenSuperior" src="imagenes/estepa.png" alt="" />
	</div>

	<div class="cabecera">
		<div id="separacionIqz">
			<img src="imagenes/logo_uned.png" alt="" width="150" height="150" />
		</div>
		<div id="separacionDer">
			<img src="imagenes/logo_uned.png" alt="" width="150" height="150" />
		</div>
		<img class="imagenConsejo" src="imagenes/imagen_consejo.png"
			width="650" height="85" />
	</div>

	<section>

		<table id="tabla" width="86%" align="center" border="0">

			<c:url var="estadoPedido" value="ControllerEstadoPedido">
			</c:url>
			<c:url var="nuevoPedido" value="ControllerNuevoPedido">
			</c:url>
			<c:url var="datospersonales" value="ControllerDatosPersonales">
			</c:url>
			<tr>
				<th class="zoom" style="text-align: center"><label><a
						href="${datospersonales}"> Datos Personales</a></label></th>
				<th class="zoom" style="text-align: center"><label><a
						href="${nuevoPedido}"> Nuevo Pedido</a></label></th>
				<th class="zoom" style="text-align: center"><label><a
						href="${estadoPedido}"> Estado Pedido</a></label></th>
				<th class="nombre" style="text-align: center"><label>${dist.nombre}</label><br>
					<label><a href="index.jsp"> Salir</a></label></th>
			</tr>

		</table>
		<div style="text-align: center">
			<img id="check" src="imagenes/check.png" alt="" width="150"
				height="150" />
			<p id="pedido">Su pedido ha sido registrado correctamente!
				Gracias.</p>

		</div>
	</section>

	<footer>
		<p>Copyright (C) Jorge Villalba Ruiz</p>
	</footer>

</body>
</html>