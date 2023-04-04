package p4;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.util.*;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.*;


public class FrontEnd {
	
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con pphase=01
	public static void sendHTML01(HttpServletResponse resaux) 
	{
	    try {
	    
	    	resaux.setCharacterEncoding("utf-8");
	    	resaux.setContentType("text/html");
	    	
	    	PrintWriter out = resaux.getWriter();
	    
	    	out.println("<!DOCTYPE html>");
	    	out.println("<html lang='en'>");
	    	out.println("<head>");
	    	out.println("<title>Consulta Musical</title>");
	    	out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    	out.println("</head>");
	    	out.println("<body>");
	    	out.println("<h1>Servicio de consulta de información musical</h1>");
            	out.println("<h2>Bienvenido a este servicio</h2>");
            	out.println("<h3>Selecciona una consulta: </h3>");

	    	out.println("<ul>");
	    	out.println("<li><a href='?pphase=02&p=P4Sint34pw'>Ver los ficheros erróneos</a>"); //Creamos un enlace a la pphase=02
	    	out.println("<li><a href='?pphase=11&p=P4Sint34pw'>Consulta 1: canciones pop de un Album de un Country</a>"); //Creamos un enlace a la pphase=11
	    	out.println("</ul>");

	    	out.println("<form>");
	    	out.println("<input type='hidden' name='p' value='P4Sint34pw'>");
	    	out.println("<input type='hidden' name='pphase' value='01'>");
	    	out.println("</form>");
	    
	    	out.println("<footer>");
	    	out.println("<div class='linea'></div>");
	    	out.println("Alejandro Mateo Costa de Dios");
	    	out.println("</footer>");

	    	out.println("</body>");
	    	out.println("</html>");
	    	
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con pphase=01
	public static void sendXML01(HttpServletResponse resaux)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<service>");
	    	out.println("<status>OK</status>");
	    	out.println("</service>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con pphase=02
	public static void sendHTML02(HttpServletResponse resaux,ArrayList<String> FES,ArrayList<String> FEF) 
	{
	    try {
	    
	    resaux.setCharacterEncoding("utf-8");
	    resaux.setContentType("text/html");

	    PrintWriter out = resaux.getWriter();

	    out.println("<!DOCTYPE html>");
	    out.println("<html lang='en'>");
	    out.println("<head>");
	    out.println("<title>Consulta Musical</title>");
	    out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Servicio de consulta de información musical</h1>");
            out.println("<h2>Ficheros con errores: " +FES.size()+ "</h2>"); //Mostramos el número de errores simples a partir del tamaño de la lista correspondiente recibida como parámetro
	    out.println("<ul>");
	    
	    for(int i = 0; i < FES.size(); i++) { //Recorremos dicha lista y mostramos la URL absoluta de todos los ficheros que presentan ese tipo de errores
	    	String URL = FES.get(i);	
	    	out.println("<li><a>"+URL+"</a>");		
	    }
	    
	    out.println("</ul>");
	    out.println("<h2>Ficheros con errores fatales: " +FEF.size()+ "</h2>"); //Mostramos el número de errores fatales a partir del tamaño de la lista correspondiente recibida como parámetro
	    out.println("<ul>");
	    
	    for(int j = 0; j < FEF.size(); j++) { //Recorremos dicha lista y mostramos la URL absoluta de todos los ficheros que presentan ese tipo de errores
	    	String URL = FEF.get(j);	
	    	out.println("<li><a>"+URL+"</a>");		
	    }
	    
	    out.println("</ul>");
	    out.println("<form>");
	    out.println("<a href='?pphase=01&p=P4Sint34pw'>"); //Creamos un enlace a la pphase=01
	    out.println("<input type='button' name='Atrás' value='Atrás'>"); //Asignamos dicho enlace a un botón
	    out.println("</a>");
	    out.println("<input type='hidden' name='p' value='P4Sint34pw'>");
	    out.println("</form>");
	    
	    out.println("<footer>");
	    out.println("<div class='linea'></div>");
	    out.println("Alejandro Mateo Costa de Dios");
	    out.println("</footer>");

	    out.println("</body>");
	    out.println("</html>");
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con pphase=02
	public static void sendXML02(HttpServletResponse resaux,ArrayList<String> FES,ArrayList<String> FEF)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<wrongDocs>");
	    	out.println("<errors>");
	    	
	    	for(int i = 0; i < FES.size(); i++) { //Recorremos la lista de URLs de ficheros con errores simples
	    		String URL = FES.get(i);
	    		StringBuffer url = new StringBuffer(URL);
	    		int ind = URL.lastIndexOf('/'); //Localizamos el últimos caracter / de la URL
	    		String XML = url.replace(0,ind+1,"").toString(); //Conservamos únicamente el nombre del .xml
	    		out.println("<error><file>"+XML+"</file></error>"); //Lo mostramos
	    	}
	    	out.println("</errors>");
	    	out.println("<fatalerrors>");
	    	
	    	for(int i = 0; i < FEF.size(); i++) { //Recorremos la lista de URLs de ficheros con errores fatales
	    		String URL = FEF.get(i);
	    		StringBuffer url = new StringBuffer(URL);
	    		int ind = URL.lastIndexOf('/'); //Localizamos el últimos caracter / de la URL
	    		String XML = url.replace(0,ind+1,"").toString(); //Conservamos únicamente el nombre del .xml	
	    		out.println("<fatalerror><file>"+XML+"</file></fatalerror>"); //Lo mostramos
	    	}
	    	out.println("</fatalerrors>");
	    	out.println("</wrongDocs>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con pphase=11
	public static void sendHTML11(HttpServletResponse resaux,ArrayList<String> paises) 
	{
	    try {
	    
	    resaux.setCharacterEncoding("utf-8");
	    resaux.setContentType("text/html");

	    PrintWriter out = resaux.getWriter();

	    out.println("<!DOCTYPE html>");
	    out.println("<html lang='en'>");
	    out.println("<head>");
	    out.println("<title>Consulta Musical</title>");
	    out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Servicio de consulta de información musical</h1>");
            out.println("<h2>Consulta 1: Fase 1</h2>");
	    out.println("<h2>Selecciona un Country: </h2>");
	    out.println("<form>");
	    out.println("<input type='hidden' name='p' value='P4Sint34pw'>");
	    out.println("<input type='hidden' name='pphase' value='12'>");
	    out.println("<ol>");
	    
	    for (int i = 0; i < paises.size(); i++) { //Recorremos la lista de países recibida como parámetro
	    	    String pais = paises.get(i);
		    out.println("<li><a href='?pphase=12&p=P4Sint34pw&pcountry="+pais+"'>"+pais+"</a>"); //Creamos un enlace a la pphase=12 con el pcountry del país seleccionado
	    }

	    out.println("</ol>");
	    out.println("<a href='?pphase=01&p=P4Sint34pw'>"); //Creamos un enlace a la pphase=01
	    out.println("<input type='button' name='Inicio' value='Inicio'>"); //Asignamos dicho enlace a un botón
	    out.println("</a>");
	    out.println("</form>");
	    
	    out.println("<footer>");
	    out.println("<div class='linea'></div>");
	    out.println("Alejandro Mateo Costa de Dios");
	    out.println("</footer>");

	    out.println("</body>");
	    out.println("</html>");
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con pphase=11
	public static void sendXML11(HttpServletResponse resaux,ArrayList<String> paises)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<countries>");
	    	for (int i = 0; i < paises.size(); i++) { //Recorremos la lista de países recibida como parámetro
	    	    String pais = paises.get(i);
		    out.println("<country>"+pais+"</country>"); //Mostramos cada país
	    	}
	    	out.println("</countries>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con pphase=12
	public static void sendHTML12(HttpServletResponse resaux,String pais,ArrayList<Album> albums) 
	{
	    try {
	    
	    resaux.setCharacterEncoding("utf-8");
	    resaux.setContentType("text/html");

	    PrintWriter out = resaux.getWriter();
	  
	    out.println("<!DOCTYPE html>");
	    out.println("<html lang='en'>");
	    out.println("<head>");
	    out.println("<title>Consulta Musical</title>");
	    out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Servicio de consulta de información musical</h1>");
            out.println("<h2>Consulta 1: Fase 2 (Country = " + pais + ")</h2>"); //Mostramos el pcountry recibido como parámetro
	    out.println("<h2>Selecciona un Album: </h2>");
	    out.println("<form>");
	    out.println("<input type='hidden' name='p' value='P4Sint34pw'>");
	    out.println("<input type='hidden' name='pphase' value='12'>");
	    out.println("<input type='hidden' name='pcountry' value='"+pais+"'>");
	    out.println("<ol>");
	    
	    for (int i = 0; i < albums.size(); i++) { //Recorremos la lista de albums recibida como parámetro
	    	    Album album = albums.get(i);
	    	    //Creamos un enlace a la pphase=13 con los pcountry y paid seleccionados en función del elemento Name del album que hemos clickado
	    	    out.println("<li><a href='?pphase=13&p=P4Sint34pw&pcountry="+pais+"&paid="+album.getAid()+"'>Álbum = '"+album.getName()+"'</a>");
	    	    //Mostramos el resto de elementos del album
	    	    out.println("<h> --- Año = '"+album.getYear()+"' --- Intérprete = '"+album.getSinger()+"' --- Review = '"+album.getReview()+"'</h>");	    
	    }
	    
	    out.println("</ol>");
	    out.println("<a href='?pphase=01&p=P4Sint34pw'>"); //Creamos un enlace a la pphase=01
	    out.println("<input type='button' name='Inicio' value='Inicio'>"); //Asignamos dicho enlace a un botón
	    out.println("</a>");
	    out.println("<a href='?pphase=11&p=P4Sint34pw'>"); //Creamos un enlace a la pphase=11
	    out.println("<input type='button' name='Atrás' value='Atrás'>"); //Asignamos dicho enlace a un botón
	    out.println("</a>");
	    out.println("</form>");
	    
	    out.println("<footer>");
	    out.println("<div class='linea'></div>");
	    out.println("Alejandro Mateo Costa de Dios");
	    out.println("</footer>");

	    out.println("</body>");
	    out.println("</html>");
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con pphase=12
	public static void sendXML12(HttpServletResponse resaux,String pais,ArrayList<Album> albums)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<albums>");
	    	for (int i = 0; i < albums.size(); i++) { //Recorremos la lista de albums recibida como parámetro
	    	    Album album = albums.get(i);
	    	    //Mostramos los elementos del album
	    	    out.println("<album year="+"'"+album.getYear()+"'"+" performer="+"'"+album.getSinger()+"'"+" review="+"'"+album.getReview()+"'"+">"+album.getName()+"</album>");	    
	    	}
	    	out.println("</albums>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con pphase=13
	public static void sendHTML13(HttpServletResponse resaux,String pais,String idalb,ArrayList<Song> songs) 
	{
	    try {
	    
	    resaux.setCharacterEncoding("utf-8");
	    resaux.setContentType("text/html");

	    PrintWriter out = resaux.getWriter();
	  
	    out.println("<!DOCTYPE html>");
	    out.println("<html lang='en'>");
	    out.println("<head>");
	    out.println("<title>Consulta Musical</title>");
	    out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Servicio de consulta de información musical</h1>");
            out.println("<h2>Consulta 1: Fase 3 (Country = " + pais + ", Album = " + idalb + ")</h2>"); //Mostramos el pcountry y paid recibidos como parámetros
	    out.println("<h2>Este es el resultado de la consulta: </h2>");
	    out.println("<form>");
	    out.println("<input type='hidden' name='p' value='P4Sint34pw'>");
	    out.println("<input type='hidden' name='pphase' value='12'>");
	    out.println("<input type='hidden' name='pcountry' value='"+pais+"'>");
	    out.println("<input type='hidden' name='paid' value='"+idalb+"'>");
	    
	    out.println("<ol>");
	    for (int i = 0; i < songs.size(); i++) { //Recorremos la lista de songs recibida como parámetro
	    	    Song song = songs.get(i);
	    	    //Mostramos los elementos y atributos de la song
	    	    out.println("<li><a>Título = '"+song.getTitle()+"'--- Idioma = '"+song.getLang()+"' --- Géneros = '"+song.getGenre()+"' --- Compositor = '"+song.getComposer()+"'</a>");	    
	    }
	    out.println("</ol>");

	    out.println("<a href='?pphase=01&p=P4Sint34pw'>"); //Creamos un enlace a la pphase=01
	    out.println("<input type='button' name='Inicio' value='Inicio'>"); //Asignamos dicho enlace a un botón
	    out.println("</a>");
	    
	    out.println("<a href='?pphase=12&p=P4Sint34pw&pcountry="+pais+"'>"); //Creamos un enlace a la pphase=12 con el pcountry recibido como parámetro
	    out.println("<input type='button' name='Atrás' value='Atrás'>"); //Asignamos dicho enlace a un botón
	    out.println("</a>");
	    out.println("</form>");
	    
	    out.println("<footer>");
	    out.println("<div class='linea'></div>");
	    out.println("Alejandro Mateo Costa de Dios");
	    out.println("</footer>");

	    out.println("</body>");
	    out.println("</html>");
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con pphase=13
	public static void sendXML13(HttpServletResponse resaux,String pais,String idalb,ArrayList<Song> songs)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<songs>");
	    	for (int i = 0; i < songs.size(); i++) { //Recorremos la lista de songs recibida como parámetro
	    	    Song song = songs.get(i);
	    	    //Mostramos los elementos y atributos de la song
	    	    out.println("<song lang="+"'"+song.getLang()+"'"+" genres="+"'"+song.getGenre()+"'"+" composer="+"'"+song.getComposer()+"'"+">"+song.getTitle()+"</song>");	    
	    	}
	    	out.println("</songs>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con un parámetro p incorrecto
	public static void sendHTMLpw(HttpServletResponse resaux) 
	{
	    try {
	    
	    	resaux.setCharacterEncoding("utf-8");
	    	resaux.setContentType("text/html");
	    	
	    	PrintWriter out = resaux.getWriter();
	    
	    	out.println("<!DOCTYPE html>");
	    	out.println("<html lang='en'>");
	    	out.println("<head>");
	    	out.println("<title>Consulta Musical</title>");
	    	out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    	out.println("</head>");
	    	out.println("<body>");
	    	out.println("<h1>Contraseña introducida incorrecta</h1>");
	    
	    	out.println("<footer>");
	    	out.println("<div class='linea'></div>");
	    	out.println("Alejandro Mateo Costa de Dios");
	    	out.println("</footer>");

	    	out.println("</body>");
	    	out.println("</html>");
	    	
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con un parámetro p incorrecto
	public static void sendXMLpw(HttpServletResponse resaux)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<wrongRequest>bad passwd</wrongRequest>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con ausencia de parámetro p
	public static void sendHTMLnp(HttpServletResponse resaux) 
	{
	    try {
	    
	    	resaux.setCharacterEncoding("utf-8");
	    	resaux.setContentType("text/html");
	    	
	    	PrintWriter out = resaux.getWriter();
	    
	    	out.println("<!DOCTYPE html>");
	    	out.println("<html lang='en'>");
	    	out.println("<head>");
	    	out.println("<title>Consulta Musical</title>");
	    	out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    	out.println("</head>");
	    	out.println("<body>");
	    	out.println("<h1>Contraseña no introducida</h1>");
	    
	    	out.println("<footer>");
	    	out.println("<div class='linea'></div>");
	    	out.println("Alejandro Mateo Costa de Dios");
	    	out.println("</footer>");

	    	out.println("</body>");
	    	out.println("</html>");
	    	
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con ausencia de parámetro p
	public static void sendXMLnp(HttpServletResponse resaux)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<wrongRequest>no passwd</wrongRequest>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con ausencia de parámetro pcountry
	public static void sendHTMLnc(HttpServletResponse resaux) 
	{
	    try {
	    
	    	resaux.setCharacterEncoding("utf-8");
	    	resaux.setContentType("text/html");
	    	
	    	PrintWriter out = resaux.getWriter();
	    
	    	out.println("<!DOCTYPE html>");
	    	out.println("<html lang='en'>");
	    	out.println("<head>");
	    	out.println("<title>Consulta Musical</title>");
	    	out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    	out.println("</head>");
	    	out.println("<body>");
	    	out.println("<h1>pcountry no introducido</h1>");
	    
	    	out.println("<footer>");
	    	out.println("<div class='linea'></div>");
	    	out.println("Alejandro Mateo Costa de Dios");
	    	out.println("</footer>");

	    	out.println("</body>");
	    	out.println("</html>");
	    	
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con ausencia de parámetro pcountry
	public static void sendXMLnc(HttpServletResponse resaux)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<wrongRequest>no param:pcountry</wrongRequest>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
	
	//Método encargado de generar la respuesta HTML correspondiente a una solicitud con ausencia de parámetro paid
	public static void sendHTMLnid(HttpServletResponse resaux) 
	{
	    try {
	    
	    	resaux.setCharacterEncoding("utf-8");
	    	resaux.setContentType("text/html");
	    	
	    	PrintWriter out = resaux.getWriter();
	    
	    	out.println("<!DOCTYPE html>");
	    	out.println("<html lang='en'>");
	    	out.println("<head>");
	    	out.println("<title>Consulta Musical</title>");
	    	out.println("<link rel='stylesheet' type='text/css' href='p4/p4.css'>"); //Enlazamos el HTML con el CSS
	    	out.println("</head>");
	    	out.println("<body>");
	    	out.println("<h1>paid no introducido</h1>");
	    
	    	out.println("<footer>");
	    	out.println("<div class='linea'></div>");
	    	out.println("Alejandro Mateo Costa de Dios");
	    	out.println("</footer>");

	    	out.println("</body>");
	    	out.println("</html>");
	    	
	    }catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    }
	}
	
	//Método encargado de generar la respuesta XML correspondiente a una solicitud con ausencia de parámetro paid
	public static void sendXMLnid(HttpServletResponse resaux)
	{
		try {
		
		resaux.setContentType("text/xml");
	    	
	    	PrintWriter out = resaux.getWriter();
	    	
	    	out.println("<?xml version='1.0' encoding='utf-8'?>");
	    	out.println("<wrongRequest>no param:paid</wrongRequest>");
	    	
	    	}catch(Exception ex) {
	    	System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	    	}
	}
}
