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
		<h2>Ficheros con errores: ${errorBean.fes.size()}</h2> <!--Mostramos el número de errores simples a partir del tamaño de la lista perteneciente al Bean correspondiente-->
		
		<ul>
			<c:forEach var="item" items="${errorBean.fes}"> <!--Recorremos dicha lista y mostramos la URL absoluta de todos los ficheros que presentan ese tipo de errores-->
				<li><a>${item}</a></li>
			</c:forEach>
		</ul>
		
		<h2>Ficheros con errores fatales: ${errorBean.fef.size()}</h2> <!--Mostramos el número de errores fatales a partir del tamaño de la lista perteneciente al Bean correspondiente-->
		
		<ul>
			<c:forEach var="item" items="${errorBean.fef}"> <!--Recorremos dicha lista y mostramos la URL absoluta de todos los ficheros que presentan ese tipo de errores-->
				<li><a>${item}</a></li>
			</c:forEach>
		</ul>
		
		<form>
			<a href='?pphase=01&p=P3Sint34pw'> <!--Creamos un enlace a la pphase=01-->
			<input type='button' name='Atrás' value='Atrás'> <!--Asignamos dicho enlace a un botón-->
			</a>
			<input type='hidden' name='p' value='P3Sint34pw'>
		</form>
		
		<footer>
			<div class='linea'></div>
			Alejandro Mateo Costa de Dios
		</footer>
	</body>
</html>
