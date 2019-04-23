<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PFG Jorge Villalba Ruiz</title>
	<link rel="stylesheet" type="text/css" href="css/tcal.css" />
	<script type="text/javascript" src="js/tcal.js"></script> 
    <script src="js/jquery-1.7.2.min.js"></script>
	<script src="js/jquery.validate.min.js"></script>
    
    <script>
	
	$(document).ready(function() {
		
	jQuery.validator.addMethod("tele", function(value, element){
			if(/^[0-9]{9}/.test(value)){
				return true;	
			}else{
				return false;
			};
		
	}, "Debe introducir los digitos correctos");
	
	
		jQuery.validator.addMethod("cp", function(value, element){
			if(/^[0-9]{5}/.test(value)){
				return true;	
			}else{
				return false;
			};
		
	}, "Introduzca los 5 digitos correctamente");
	
	$("#form_registro").validate({
		
		rules:{
			email:{
				required:true,
				email:true	
			},
			telefono:{
				tele:true
			},
			cp:{
				cp:true	
			},
			date:{
				required:true
			},
			emailconf:{
				required:true,
				email:true,
				equalTo:"#email"
				
			}
			
		},
		
		
		messages:{
			email:{
				required:"El email es obligatorio",
				email:"Especifice formato: nombre@dominio"	
			}, 
			
			date:{
				required:"La fecha es obligatoria"	
			},
			
			emailconf:{
				required:"Es obligatorio repetir el email",
				equalTo:"No coinciden los email",
				email:"Especifice formato: nombre@dominio"
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
        <img src="imagenes/logo_uned.png" alt="" width="150" height="150"  />
		</div>
      <div id="separacionDer">
        <img src="imagenes/logo_uned.png" alt="" width="150" height="150"  />
        </div>
      <img class="imagenConsejo" src="imagenes/imagen_consejo.png" width="650" height="85" />
    </div>

<section>
    
    <p> Registro en el sistema</p>

	<form method="post" name="form_registro" id="form_registro" action="ControllerDistribuidor" >
	  <table width="93%" height="393" align="center">
	    <tr>
	      <th height="57" class="fila"><label>Nombre de Empresa *</label></th>
	      <th><input type="text" name="nombre" id="nombre" placeholder="Nombre completo..." class="required" Title="El nombre es obligatorio" /></th>
	      <th><label>Domicilio *</label></th>
	      <th><input type="text" name="domicilio" id="domicilio" placeholder="Introduzca numero también..." class="required" Title="El domicilio es obligatorio" /></th>
        </tr>
	    <tr>
	      <td height="40"><label>Email *</label></td>
	      <th><input type="email" name="email" id="email" placeholder="nombre@dominio" /></th>
	      <td><label>Confirmar email *</label></td>
	      <th><input type="email" name="emailconf" id="emailconf" placeholder="Repite el email anterior..." /></th>
        </tr>
	    <tr>
	      <td height="42"><label>Telefono *</label></td>
	      <th><input type="text" name="telefono" id="telefono" placeholder="000000000" /></th>
	      <td><label>Codigo Postal *</label></td>
	      <th><input type="text" name="cp" id="cp"  placeholder="00000"/></th>
        </tr>
	    <tr>
	      <td height="45"><label>Pais *</label></td>
	      <th><input type="text" name="pais" id="pais" /></th>
	      <td><label>Fecha de alta </label></td>
	      <td><input type="text" name="date" id ="date" class="tcal" placeholder="pulse en el icono" /></td>
        </tr>
	    <tr>
	      <td height="21">&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
        </tr>
	    <tr>
	      <td height="51"><label>Usuario *</label></td>
	      <th><input type="text" name="user" id="user" /></th>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
        </tr>
	    <tr>
	      <td height="33"><label>Contraseña *</label></td>
	      <th><input type="password" name="pss" id="pss" /></th>
	      <td><label>Repite contraseña *</label></td>
	      <th><input type="password" name="pss_conf" id="pss_conf" /></th>
        </tr>
        	    <tr>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
        </tr>

        <tr>
	      <td height="40"><input type="submit" value="Enviar"  /></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td><input type="reset" value="Borrar datos"  /></td>
        </tr>
               	<tr>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
        </tr>
               	<tr>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
        </tr>
      </table>
    
    
    </form>
</section>
    
    <footer>
    <p>Copyright (C) Jorge Villalba Ruiz </p>
    </footer>

</body>
</html>
