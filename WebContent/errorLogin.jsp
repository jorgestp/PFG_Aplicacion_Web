<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PFG Jorge Villalba Ruiz</title>
	<link rel="stylesheet" type="text/css" href="css/login.css" />
	
    <script src="Jquery/jquery-1.7.2.min.js"></script>
	<script src="Jquery/jquery.validate.min.js"></script>
    
    <script>
	
	$(document).ready(function() {
		
	$("#form_login").validate();

	
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
    
    <p id="identifica"> Identifíquese</p>

	
  <form style="width:70%; margin:0 auto" id="form_login" name="form_login" method="post" action="">
    <table width="72%">
      <tr>
        <td width="24%"><label>Usuario</label></td>
        <td width="76%"><input name="usuario" type="text" class="required" id="usuario" placeholder="Introduzca su nick..." Title="El nombre es obligatorio" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><label>Contraseña</label></td>
        <td><input type="password" name="contra" id="contra" placeholder="Introduzca contaseña..." class="required" Title="No deje el campo vacio" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td></td>
        <td><input name="envio" type="submit" id="envio" value="Enviar"  /></td>
      </tr>
    </table>
  </form>
  
  <p id="error">El usuario o la contraseña no existe...</p>
</section>
    
    <footer>
    <p>Copyright (C) Jorge Villalba Ruiz </p>
    </footer>

</body>
</html>