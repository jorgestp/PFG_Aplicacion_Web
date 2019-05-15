<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PFG Jorge Villalba Ruiz</title>
<link rel="stylesheet" type="text/css" href="css/cuentaUsuario.css" />
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


		<p id="pedSist">PEDIDOS EN EL SISTEMA</p>

		<table id="pedidosSistema" width="750" align="center" border="1">
			<tr>
				<th width="150">ID</th>
				<th width="200">FECHA REALIZAZO</th>
				<th width="200">FECHA ENVIO</th>
				<th width="200">ESTADO</th>

			</tr>
		</table>

		<div id="scrol">
			<table id="lista" width="750" align="center">

				<c:forEach var="temprod" items="${pedido}">


					<tr>

						<td width="150">${temprod.id_pedido}</td>
						<td width="200">${temprod.fecha_entrada}</td>
						<td width="200">${temprod.fecha_envio}</td>
						<td width="200">${temprod.estado}</td>

					</tr>

				</c:forEach>



			</table>
		</div>
	</section>

	<footer>
		<p>Copyright (C) Jorge Villalba Ruiz</p>
	</footer>

</body>
</html>
