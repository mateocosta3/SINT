package p3;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.*;

public class Sint34P3 extends HttpServlet {
	
	/*VARIABLES DE CLASE PERSISTENTES*/
	
	Bean02 b02; //Variable perteneciente al Bean correspondiente a pphase=02
	Bean11 b11; //Variable perteneciente al Bean correspondiente a pphase=11
	Bean12 b12; //Variable perteneciente al Bean correspondiente a pphase=12
	Bean13 b13; //Variable perteneciente al Bean correspondiente a pphase=13
	
	/*********************************/
	
	//Método encargado de inicializar los valores necesarios al producirse la ejecución del servlet
	public void init() {
		try {	
    			DataModel.init(); //Método que inicializa los valores necesarios en DataModel y crea los árboles DOM de los documentos al iniciar el servlet
    		}catch (Exception ex) {
            		System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
        	}
	}
	
	//Método encargado de comprobar si el parametro p se ha introducido correctamente y de llamar al método doGet apropiado en función del valor de pphase
	public void doGet(HttpServletRequest req, HttpServletResponse res)
    	{	
        	try {
        		//Variable que contiene el valor del  parámetro pphase
        		String fase = req.getParameter("pphase");
        		//Variable que contiene el valor del  parámetro p
        		String p = req.getParameter("p");
        		
        		if (p == null) {
               		//Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro p
            			this.doGetnop(req,res);
            		}		
            		else if (!p.equals("P3Sint34pw")) {
               		//Llamada al método que genera la respuesta correspondiente a la introducción incorrecta del parámetro p
            			this.doGetpWrong(req,res);
            		}
            		else {
            
		    		if (fase == null) {
		    		fase = "01";
		    		}
		    
		    		//Llamada al doGet correspondiente al valor de pphase
		    		switch(fase) {
		    	
		    			case "01":
				    		this.doGetFase01(req,res);
				    		break;
				    		    
					case "02": 
						this.doGetFase02(req,res);
				    		break;
				    		    
					case "11": 
						this.doGetFase11(req,res);
				    		break;
				    		    
					case "12": 
						this.doGetFase12(req,res);
				    		break;
				    		    
			     		case "13": 
			     			this.doGetFase13(req,res);
				    		break;
				    }
			}     	
			
        	}
        	catch (Exception ex) {
            		System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
        	}
    	}
    	
    	//Método encargado de invocar la página JSP correspondiente a una solicitud con pphase=01
    	public void doGetFase01(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
	    	try {
		    
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pag01.jsp");
		    rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada
		    
		}
		catch (Exception ex) {
		    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
    	
    	//Método encargado de invocar la página JSP correspondiente a una solicitud con pphase=02
    	public void doGetFase02(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
	    	try {
	    		
	    	    b02 = new Bean02(); //Inicializamos el Bean
	    	    
	    	    //Modificamos los valores de las variables del Bean con los datos obtenidos de analizar todos los documentos encontrados
	    	    b02.setFes(DataModel.FES); 
	    	    b02.setFef(DataModel.FEF);
	    	    
	    	    reqaux.setAttribute("errorBean",b02); //Introducimos la Bean como atributo del objeto reqaux
		    
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pag02.jsp");
		    rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada
		    
		}
		catch (Exception ex) {
		    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
    	
    	//Método encargado de invocar la página JSP correspondiente a una solicitud con pphase=11
    	public void doGetFase11(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
	    	try {
	    	
	    	    ArrayList<String> paises = DataModel.getQ1Countries();
	    		
	    	    b11 = new Bean11(); //Inicializamos el Bean
	    	    b11.setCountries(paises); //Modificamos los valores de las variables del Bean con los datos obtenidos de analizar todos los documentos encontrados
	    	    reqaux.setAttribute("countriesBean",b11); //Introducimos la Bean como atributo del objeto reqaux
		    
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pag11.jsp");
		    rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada
		    
		}
		catch (Exception ex) {
		    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
    	
    	//Método encargado de comprobar la correcta introduccion del parámetro pcountry y de invocar la página JSP correspondiente a una solicitud con pphase=12
    	public void doGetFase12(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
		try {
	    	    //Variable que contiene el valor del  parámetro pcountry
		    String pais = reqaux.getParameter("pcountry");
		    
		    if (pais == null) doGetnoCountry(reqaux,resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro pcountry
		    else {
			//Variable que contiene la lista de albums disponibles, en función del parámetro pcountry seleccionado, por medio de la llamada del método get correspondiente
		    	ArrayList<Album> albums = DataModel.getQ1Albums(pais); 
		    	
		    	b12 = new Bean12(); //Inicializamos el Bean
		    	
		    	//Modificamos los valores de las variables del Bean con los datos obtenidos de analizar todos los documentos encontrados
		    	b12.setAlbums(albums);
		    	b12.setCountry(pais);
		    	
		    	reqaux.setAttribute("albumsBean",b12); //Introducimos la Bean como atributo del objeto reqaux
		    
		    	ServletContext sc = getServletContext();
		    	RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pag12.jsp");
		    	rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada  	
		    }
		    
		}
		catch (Exception ex) {
		    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
    	
    	//Método encargado de comprobar la correcta introduccion de los parámetros pcountry y paid, además de invocar la página JSP correspondiente a una solicitud con pphase=13
    	public void doGetFase13(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
		try {
	    	    //Variable que contiene el valor del  parámetro pcountry
		    String pais = reqaux.getParameter("pcountry");
		    //Variable que contiene el valor del  parámetro paid
		    String idalb = reqaux.getParameter("paid");
		    
		    if (pais == null) doGetnoCountry(reqaux,resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro pcountry
		    else if (idalb == null) doGetnoAid(reqaux,resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro paid
		    else {
			//Variable que contiene la lista de songs disponibles, en función de los parámetros pcountry y paid seleccionados, por medio de la llamada del método get correspondiente
		    	ArrayList<Song> songs = DataModel.getQ1Songs(pais,idalb); 
		    	
		    	b13 = new Bean13(); //Inicializamos el Bean
		    	
		    	//Modificamos los valores de las variables del Bean con los datos obtenidos de analizar todos los documentos encontrados
		    	b13.setSongs(songs);
		    	b13.setCountry(pais);
		    	b13.setId(idalb);
		    	
		    	reqaux.setAttribute("songsBean",b13); //Introducimos la Bean como atributo del objeto reqaux
		    
		    	ServletContext sc = getServletContext();
		    	RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pag13.jsp");
		    	rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada
		    
			}
		}catch (Exception ex) {
		    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
    	
    	//Método encargada de invocar la página JSP adecuada ante un parámetro p incorrecto
    	public void doGetpWrong(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
    		try {
    		
            	    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pagPw.jsp");
		    rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada
	    
		}
		catch (Exception ex) {
	    		System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
    
    	//Método encargada de invocar la página JSP adecuada ante la ausencia de parámetro p
    	public void doGetnop(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
    		try {
	    	
	    	    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pagNp.jsp");
		    rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada
	   
		}
		catch (Exception ex) {
	    		System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
    
    	//Método encargada de invocar la página JSP adecuada ante la ausencia de parámetro pcountry
    	public void doGetnoCountry(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
    		try {
	    
	    	    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pagNc.jsp");
		    rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada
	    
		}
		catch (Exception ex) {
	    		System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
    
    	//Método encargada de invocar la página JSP adecuada ante la ausencia de parámetro paid
    	public void doGetnoAid(HttpServletRequest reqaux, HttpServletResponse resaux) 
    	{
    		try {
	    
	    	    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/classes/p3/pagNa.jsp");
		    rd.forward(reqaux,resaux); //Transferimos el control a la página JSP llamada
	    
		}
		catch (Exception ex) {
	    		System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
    	}
}
