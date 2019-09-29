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

		$("#formulario").validate({

			rules : {
				nom : {
					required : true
				},
				cant : {
					number : true,
					required : true,
				},
				dia : {
					required : true
				},
				mes : {
					required : true
				},
				any : {
					required : true
				}

			},

			messages : {

				nom : "Seleccione un articulo por favor",
				cant : {
					number : "El valor introducido debe ser numerico",
					required : "La cantidad es obligatoria",
				},
				dia : "Seleccione el dia",
				mes : "Seleccione el mes",
				any : "Seleccione el año"

			}

		});

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
				<th class="zoom" style="text-align: center"><label><a style="text-decoration: none; color: white;"
						href="${datospersonales}"> Datos Personales</a></label></th>
				<th class="zoom" style="text-align: center"><label><a style="text-decoration: none; color: white;"
						href="${nuevoPedido}"> Nuevo Pedido</a></label></th>
				<th class="zoom" style="text-align: center"><label><a style="text-decoration: none; color: white;"
						href="${estadoPedido}"> Estado Pedido</a></label></th>
				<th class="nombre" style="text-align: center"><label>${dist.nombre}</label><br>
					<label><a href="index.jsp"> Salir</a></label></th>
			</tr>

		</table>

		<h1 id="titulo">Nuevo Pedido</h1>

		<form id="formulario" method="post" action="ControllerNuevoPedido">
			<table id="pedir">
				<tr>
					<td width="57" height="26"><label for="usuario">Nombre</label></td>
					<td width="232"><select name="nom" id="nom">
							<option value="" selected disabled hidden>Selec. Artículo</option>
							<c:forEach var="temprod" items="${listaArt}">
								<option value="${temprod.id_articulo}">${temprod.nombre}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><label for="cant">Cantidad</label></td>
					<td><label for="textfield2"></label> <input type="text"
						name="cant" id="cant" style="width: 150px; text-align: center;"></td>
				</tr>
				<tr>

					<td></td>
					<td><input type="submit" id="enviar" value="Añadir" /></td>
				</tr>
			</table>
			<label for="select"></label>

		</form>



		<p class="adicionados">Elementos en la Tabla: ${numero}
		<p>
		<table id="listaTitulo" width="750" align="center" border="1">
			<tr>
				<th width="350">NOMBRE</th>
				<th width="150">CANTIDAD</th>
				<th width="250"></th>
			</tr>
		</table>

		<div id="scrol">
			<table id="lista" width="750" align="center">
				<c:forEach var="temprod" items="${seleccionados}">

					<!-- Link para EMILIMNAR cada producto con su campo clave -->
					<c:url var="linktemp" value="ControllerNuevoPedido">

						<c:param name="instruccion" value="eliminar"></c:param>
						<c:param name="codigo" value="${temprod.articulo.id_articulo}"></c:param>
					</c:url>

					<tr>

						<td width="350">${temprod.articulo.nombre}</td>
						<td width="150">${temprod.cant}</td>
						<td width="250"><a id="del" href="${linktemp}">Eliminar</a></td>
					</tr>

				</c:forEach>


			</table>
		</div>

		<div style="width: 75%">
			<form action="" method="get">

				<input type="hidden" name="instruccion" value="formaliza">

				<table width="75%" border="0" align="center">
					<tr>
						<td width="32%"><label class="F_envio"
							style="font-size: 20px; font-style: italic; font-weight: bold; color: #006;">
								Fecha de Envio </label></td>
						<td width="68%"><select name="dia" id="dia"
							style="width: 50px; text-align: center;">
								<option value="" selected disabled hidden>Dia</option>
								<OPTION VALUE="01">1</OPTION>
								<OPTION VALUE="02">2</OPTION>
								<OPTION VALUE="03">3</OPTION>
								<OPTION VALUE="04">4</OPTION>
								<OPTION VALUE="05">5</OPTION>
								<OPTION VALUE="06">6</OPTION>
								<OPTION VALUE="07">7</OPTION>
								<OPTION VALUE="08">8</OPTION>
								<OPTION VALUE="09">9</OPTION>
								<OPTION VALUE="10">10</OPTION>
								<OPTION VALUE="11">11</OPTION>
								<OPTION VALUE="12">12</OPTION>
								<OPTION VALUE="13">13</OPTION>
								<OPTION VALUE="14">14</OPTION>
								<OPTION VALUE="15">15</OPTION>
								<OPTION VALUE="16">16</OPTION>
								<OPTION VALUE="17">17</OPTION>
								<OPTION VALUE="18">18</OPTION>
								<OPTION VALUE="19">19</OPTION>
								<OPTION VALUE="20">20</OPTION>
								<OPTION VALUE="21">21</OPTION>
								<OPTION VALUE="22">22</OPTION>
								<OPTION VALUE="23">23</OPTION>
								<OPTION VALUE="24">24</OPTION>
								<OPTION VALUE="25">25</OPTION>
								<OPTION VALUE="26">26</OPTION>
								<OPTION VALUE="27">27</OPTION>
								<OPTION VALUE="28">28</OPTION>
								<OPTION VALUE="29">29</OPTION>
								<OPTION VALUE="30">30</OPTION>
								<OPTION VALUE="31">31</OPTION>

						</select> / <select name="mes" id="mes"
							style="width: 60px; text-align: center;">
								<option value="" selected disabled hidden>Mes</option>
								<OPTION VALUE="01">Ene</OPTION>
								<OPTION VALUE="02">Feb</OPTION>
								<OPTION VALUE="03">Mar</OPTION>
								<OPTION VALUE="04">Abr</OPTION>
								<OPTION VALUE="05">May</OPTION>
								<OPTION VALUE="06">Jun</OPTION>
								<OPTION VALUE="07">Jul</OPTION>
								<OPTION VALUE="08">Agos</OPTION>
								<OPTION VALUE="09">Sept</OPTION>
								<OPTION VALUE="10">Oct</OPTION>
								<OPTION VALUE="11">Nov</OPTION>
								<OPTION VALUE="12">Dic</OPTION>
						</select> / <select name="any" id="any"
							style="width: 85px; text-align: center;">
								<option value="" selected disabled hidden>Año</option>
								     <c:forEach var = "i" begin = "${inicio}" end = "${inicio+5}">
								     <OPTION VALUE="${i}">${i}</OPTION>
     								 </c:forEach>
								
						</select></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="submit" value="FOMALIZAR PEDIDO"  /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td></td>
					</tr>
				</table>


			</form>

		</div>

	</section>

	<footer>
		<p style="color: white;">Jorge Villalba Ruiz -
		PROYECTO FIN DE GRADO</p>
	</footer>

</body>
</html>