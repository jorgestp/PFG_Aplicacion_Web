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

		<h1 id="titulo">Nuevo Pedido</h1>

		<form id="formulario" method="post" action="ControllerNuevoPedido">
			<table width="305">
				<tr>
					<td width="57" height="26"><label for="usuario">Nombre</label></td>
					<td width="232"><label for="textfield"> <select
							name="nom" id="nom">
								<option value="" selected disabled hidden>Choose here</option>
								<c:forEach var="temprod" items="${listaArt}">
									<option value="${temprod.id_articulo}">${temprod.nombre}</option>
								</c:forEach>
						</select>
					</label></td>
				</tr>
				<tr>
					<td><label for="cant">Cantidad</label></td>
					<td><label for="textfield2"></label> <input type="text"
						name="cant" id="cant"></td>
				</tr>
				<tr>
					<td height="39">
						<!--<button id="adicionar"  type="button">Añadir</button>--> <input
						type="submit" id="enviar" value="Añadir" />
					</td>
				</tr>
			</table>
			<label for="select"></label>

		</form>

		<p>Elementos en la Tabla:
		<div id="adicionados">${numero}</div>
		</p>
		<table width="547" align="center" id="mytable">
			<tr>
				<th>Nobmre</th>
				<th>Cantidad</th>
				<th></th>
			</tr>
			<c:forEach var="temprod" items="${seleccionados}">

				<!-- Link para EMILIMNAR cada producto con su campo clave -->
				<c:url var="linktemp" value="ControllerNuevoPedido">

					<c:param name="instruccion" value="eliminar"></c:param>
					<c:param name="codigo" value="${temprod.articulo.id_articulo}"></c:param>
				</c:url>

				<tr>

					<td class="filas">${temprod.articulo.nombre}</td>
					<td class="filas">${temprod.cant}</td>
					<td class="filas"><a id="del" href="${linktemp}">Eliminar</a></td>
				</tr>

			</c:forEach>
		</table>

		<c:url var="linkformalizar" value="ControllerNuevoPedido">

			<c:param name="instruccion" value="formaliza"></c:param>
			<c:param name="lista" value="${seleccionados }"></c:param>
		</c:url>
				<a id="fomalizar" href="${linkformalizar}">FOMALIZAR</a>
	</section>

	<footer>
		<p>Copyright (C) Jorge Villalba Ruiz</p>
	</footer>

</body>
</html>