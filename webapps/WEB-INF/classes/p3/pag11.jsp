<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!--Hacemos uso de la librería JSTL para emplear el lenguajes de expresiones (EL)-->

<!DOCTYPE html>
<html lang='en'>
	<head>
		<title>Consulta Musical</title>
		<link rel='stylesheet' type='text/css' href='p3/p3.css'> <!--Enlazamos el HTML con el CSS-->
	</head>
	
	<body>
		<h1>Servicio de consulta de información musical</h1>
		<h2>Consulta 1: Fase 1</h2>
		<h2>Selecciona un Country: </h2>
		<form>
			<input type='hidden' name='p' value='P3Sint34pw'>
			<input type='hidden' name='pphase' value='11'>
			
			<ol>
				<c:forEach var="item" items="${countriesBean.countries}"> <!--Recorremos la lista de países perteneciente al Bean correspondiente-->
					<li><a href='?pphase=12&p=P3Sint34pw&pcountry=${item}'>${item}</a> <!--Creamos un enlace a la pphase=12 con el pcountry del país seleccionado-->
				</c:forEach>
			</ol>
			
			<a href='?pphase=01&p=P3Sint34pw'> <!--Creamos un enlace a la pphase=01-->
			<input type='button' name='Inicio' value='Inicio'> <!--Asignamos dicho enlace a un botón-->
			</a>
		</form>
		
		<footer>
			<div class='linea'></div>
			Alejandro Mateo Costa de Dios
		</footer>
	</body>
</html>
