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
		<h2>Consulta 1: Fase 2 (Country = ${albumsBean.country})</h2> <!--Mostramos el pcountry perteneciente al Bean correspondiente-->
		<h2>Selecciona un Album: </h2>
		<form>
			<input type='hidden' name='p' value='P3Sint34pw'>
			<input type='hidden' name='pphase' value='12'>
			<input type='hidden' name='pcountry' value='${albumsBean.country}'>
			
			<ol>
				<c:forEach var="item" items="${albumsBean.albums}"> <!--Recorremos la lista de albums perteneciente al Bean correspondiente-->
				
					<!--Creamos un enlace a la pphase=13 con los pcountry y paid seleccionados en función del elemento Name del album que hemos clickado-->
					<li><a href='?pphase=13&p=P3Sint34pw&pcountry=${albumsBean.country}&paid=${item.aid}'>Álbum = '${item.name}'</a>
					<!--Mostramos el resto de elementos del album-->
					<h> --- Año = '${item.year}' --- Intérprete = '${item.singer}' --- Review = '${item.review}'</h> 
				</c:forEach>
			</ol>
			
			<a href='?pphase=01&p=P3Sint34pw'> <!--Creamos un enlace a la pphase=01-->
			<input type='button' name='Inicio' value='Inicio'> <!--Asignamos dicho enlace a un botón-->
			</a>
			<a href='?pphase=11&p=P3Sint34pw'> <!--Creamos un enlace a la pphase=11-->
			<input type='button' name='Atrás' value='Atrás'> <!--Asignamos dicho enlace a un botón-->
			</a>
		</form>
		
		<footer>
			<div class='linea'></div>
			Alejandro Mateo Costa de Dios
		</footer>
	</body>
</html>
