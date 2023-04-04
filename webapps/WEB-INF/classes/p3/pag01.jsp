<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang='en'>

	<head>
		<title>Consulta Musical</title>
		<link rel='stylesheet' type='text/css' href='p3/p3.css'> <!--Enlazamos el HTML con el CSS-->
	</head>

	<body>
		<h1>Servicio de consulta de información musical</h1>
		<h2>Bienvenido a este servicio</h2>
		<h3>Selecciona una consulta: </h3>

		<ul>
			<li><a href='?pphase=02&p=P3Sint34pw'>Ver los ficheros erróneos</a> <!--Creamos un enlace a la pphase=02-->
			<li><a href='?pphase=11&p=P3Sint34pw'>Consulta 1: canciones pop de un Album de un Country</a> <!-- Creamos un enlace a la pphase=11 -->
		</ul>

		<form>
			<input type='hidden' name='p' value='P3Sint34pw'>
			<input type='hidden' name='pphase' value='01'>
		</form>
		    
		<footer>
			<div class='linea'></div>
			Alejandro Mateo Costa de Dios
		</footer>

	</body>
</html>
