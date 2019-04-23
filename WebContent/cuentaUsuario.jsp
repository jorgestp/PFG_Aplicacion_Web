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

/*
 * 
 ,
	submitHandler(function(form) {
		
		var datosFormulario=$("#datospersonales").serialize();
		
			$.ajax({
				type: "POST",
				url: "ControllerDatosPersonales",
				data: datosFormulario,
				success(function(data){
					
					$("#nada").html(data);
					
				}),
				
				error(function(){
					
					
					alert("NO SE PUEDE VER");
					
				});	
					
			});
		return false;
	});//fin submitHandler
 */
	$(document).ready(function() {
		

		jQuery.validator.addMethod("tele", function(value, element) {
			if (/^[0-9]{9}/.test(value)) {
				return true;
			} else {
				return false;
			}
			;

		}, "Debe introducir los digitos correctos");

		jQuery.validator.addMethod("cp", function(value, element) {
			if (/^[0-9]{5}/.test(value)) {
				return true;
			} else {
				return false;
			}
			;

		}, "Introduzca los 5 digitos correctamente");

		$("#datospersonales").validate({

			rules : {
				email : {
					required : true,
					email : true
				},
				telefono : {
					tele : true
				},
				cp : {
					cp : true
				},

				confEmail : {
					required : true,
					email : true,
					equalTo : "#email"

				},

				pss : {
					required : true,
					rangelength : [ 8, 16 ]

				},

				confpss : {
					required : true,
					rangelength : [ 8, 16 ],
					equalTo : "#pss"

				}

			},

			messages : {
				email : {
					required : "El email es obligatorio",
					email : "Especifice formato: nombre@dominio"
				},

				confEmail : {
					required : "Es obligatorio repetir el email",
					equalTo : "No coinciden los email",
					email : "Especifice formato: nombre@dominio"
				},

				pss : {
					required : "La contraseña es obligatoria",
					rangelength : "Longitud entre 8 y 16 caracteres"

				},

				confpss : {
					required : "Es obligatorio repetir la contraseña",
					equalTo : "No coinciden las contraseñas",

				}

			}

		});//fin funcion validate

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
				<th class="zoom" style="text-align: center"><label><a href="${datospersonales}"> Datos Personales</a></label></th>
				<th class="zoom" style="text-align: center"><label><a href="${nuevoPedido}"> Nuevo Pedido</a></label></th>
				<th class="zoom" style="text-align: center"><label><a href="${estadoPedido}"> Estado Pedido</a></label></th>
				<th class="nombre" style="text-align: center"><label>${dist.nombre}</label><br>
				<label><a href="index.jsp"> Salir</a></label></th>
			</tr>
		
		</table>
		<h1 id="titulo">Datos Personales</h1>
		<form action="ControllerDatosPersonales" method="post" id="datospersonales">
			<table width="735" border="0" align="center">
				<tr>
					<td class="fila">Nombre</td>
					<td class="fila"><input type="text" id="nombre" name="nombre"
						class="required" title="El nombre es obligatorio" value="${dist.nombre}" /></td>
					<td class="fila">Domicilio</td>
					<td class="fila"><input type="text" id="domicilio"
						name="domicilio" class="required" title="El nombre es obligatorio" value="${dist.domicilio}" /></td>
				</tr>
				<tr>
					<td class="fila">Email</td>
					<td class="fila"><input type="email" id="email" name="email" value="${dist.email}" /></td>
					<td class="fila">Confirma email</td>
					<td class="fila"><input type="email" id="confEmail"
						name="confEmail" value="${dist.email}" /></td>
				</tr>
				<tr>
					<td class="fila">Telefono</td>
					<td class="fila"><input type="number" id="telefono"
						name="telefono" value="${dist.tfno}" /></td>
					<td class="fila">Codigo Postal</td>
					<td class="fila"><input type="number" id="cp" name="cp" value="${dist.cp}" /></td>
				</tr>
				<tr>
					<td class="fila">Pais</td>
					<td class="fila"><input type="text" id="pais" name="pais" value="${dist.pais}" /></td>
					<td class="fila">Fecha Alta</td>
					<td class="fila"><label>${fecha}</label></td>
				</tr>
				<tr>
					<td class="fila"></td>
					<td class="fila"></td>
					<td class="fila"></td>
					<td class="fila"></td>
				</tr>
				<tr>
					<td class="fila">Contraseña</td>
					<td class="fila"><input type="password" id="pss" name="pss"
						class="required" title="No puede estar vacio" /></td>
					<td class="fila">Confirma contraseña</td>
					<td class="fila"><input type="password" id="confpss"
						name="confpss" class="required" title="No puede estar vacio" /></td>
				</tr>
				<tr>
					<td class="fila">&nbsp;</td>
					<td class="fila">&nbsp;</td>
					<td class="fila">&nbsp;</td>
					<td class="fila">&nbsp;</td>
				</tr>
				<tr>
					<td class="fila">&nbsp;</td>
					<td class="fila">&nbsp;</td>
					<td class="fila">&nbsp;</td>
					<td class="fila"><input type="submit" id="enviar"
						name="enviar" value="Enviar" /></td>
				</tr>
			</table>



		</form>
	</section>

	<footer>
		<p>Copyright (C) Jorge Villalba Ruiz</p>
	</footer>

</body>
</html>