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

	<section style="
	background-image:url(imagenes/marca_agua_uned.png);
	/*Hace que la imagen no se repita en el contenedor*/
	background-repeat:no-repeat;
	/*Se pociciona en el centro del contenedor*/
	background-position:center;
	/*ANchura del contenedor*/
	width:75%;
	/*altura del contenedor y en px ya que en % no salia*/
	height:750px;
	margin:0 auto;
	margin-top:5px;
	">
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

		<table id="pedidosSistema" width="750" align="center" border="0">
			<tr>
				<th width="150">ID</th>
				<th width="200">FECHA REALIZAZO</th>
				<th width="200">FECHA ENVIO</th>
				<th width="200">ESTADO</th>
				<th width="200">DESGLOSAR</th>

			</tr>
		</table>

		<div id="scrol">
			<table id="lista" width="750" align="center" >

				<c:forEach var="temprod" items="${pedido}">
				
					<c:url var="temp" value="ControllerEstadoPedido">

					<c:param name="instruccion" value="ver"></c:param>
					<c:param name="id_pedido" value="${temprod.id_pedido}"></c:param>
					</c:url>
					
					<tr>

						<td width="150">${temprod.id_pedido}</td>
						<td width="200">${temprod.fecha_entrada}</td>
						<td width="200">${temprod.fecha_envio}</td>
						

						<c:choose>
							<c:when test="${temprod.estado == 'En Tramite'}">
    						<td width="200" style="background-color: #EC7063;">${temprod.estado}</td>
  							</c:when>
  							<c:when test="${temprod.estado == 'Realizado'}">
    						<td width="200" style="background-color: #F1C40F;">${temprod.estado}</td>
  							</c:when>
							<c:when test="${temprod.estado == 'Preparado'}">
    						<td width="200" style="background-color: #C39BD3;">${temprod.estado}</td>
  							</c:when>
							<c:when test="${temprod.estado == 'Terminado'}">
    						<td width="200" style="background-color: 3498DB;">${temprod.estado}</td>
  							</c:when> 
  							<c:when test="${temprod.estado == 'Enviado'}">
    						<td width="200" style="background-color: #2ECC71;">${temprod.estado}</td>
  							</c:when> 													
						</c:choose>
						<td width="200"><a id="del" href="${temp}">Ver</a></td>	

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
