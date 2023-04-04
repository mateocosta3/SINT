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
		<h2>Consulta 1: Fase 3 (Country = ${songsBean.country}, Album = ${songsBean.id})</h2> <!--Mostramos el pcountry y paid pertenecientes al Bean correspondiente-->
		<h2>Este es el resultado de la consulta: </h2>
		<form>
			<input type='hidden' name='p' value='P3Sint34pw'>
			<input type='hidden' name='pphase' value='13'>
			<input type='hidden' name='pcountry' value='${songsBean.country}'>
			<input type='hidden' name='paid' value='${songsBean.id}'>
			
			<ol>
				<c:forEach var="item" items="${songsBean.songs}"> <!--Recorremos la lista de songs perteneciente al Bean correspondiente-->
				
					<!--Mostramos los elementos y atributos de la song-->
					<li><a>Título = '${item.title}'--- Idioma = '${item.lang}' --- Géneros = '${item.genre}' --- Compositor = '${item.composer}'</a>
				</c:forEach>
			</ol>
			
			<a href='?pphase=01&p=P3Sint34pw'> <!--Creamos un enlace a la pphase=01-->
			<input type='button' name='Inicio' value='Inicio'> <!--Asignamos dicho enlace a un botón-->
			</a>
			<a href='?pphase=12&p=P3Sint34pw&pcountry=${songsBean.country}'> <!--Creamos un enlace a la pphase=12 con el pcountry perteneciente al Bean correspondiente-->
			<input type='button' name='Atrás' value='Atrás'> <!--Asignamos dicho enlace a un botón-->
			</a>
		</form>
		
		<footer>
			<div class='linea'></div>
			Alejandro Mateo Costa de Dios
		</footer>
	</body>
</html>
