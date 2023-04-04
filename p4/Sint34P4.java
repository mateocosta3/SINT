package p4;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.*;

public class Sint34P4 extends HttpServlet {
    
    //Método encargado de inicializar los valores necesarios al producirse la ejecución del servlet
    public void init() {
    	try {	
    		DataModel.init(); //Método que inicializa los valores necesarios en DataModel y crea las listas que contienen los albums y songs de todos los xml encontrados
    	}catch (Exception ex) {
            System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
        }
    }
    
    //Método encargado de comprobar si el parametro p se ha introducido correctamente y de llamar al método doGet apropiado en función del valor de pphase 
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    {
        try {
            
            res.setCharacterEncoding("utf-8");
            res.setContentType("text/html");

            PrintWriter out = res.getWriter();
	    
	    //Variable que contiene el valor del  parámetro pphase
            String fase = req.getParameter("pphase");
            //Variable que contiene el valor del  parámetro p
            String p = req.getParameter("p");
            
            if (p == null) {
               //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro p
            	this.doGetnop(req,res);
            }		
            else if (!p.equals("P4Sint34pw")) {
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
    
    //Método encargado de invocar la respuesta apropiada (HTML o XML) a una solicitud con pphase=01
    public void doGetFase01(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
    	    
    	    //Variable que contiene el valor del  parámetro auto
    	    String auto = reqaux.getParameter("auto");
            
            if (auto == null) FrontEnd.sendHTML01(resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            else if (auto.equals("true")) {
            	FrontEnd.sendXML01(resaux); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            }
            else FrontEnd.sendHTML01(resaux); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	    
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
    
    //Método encargado de invocar la respuesta apropiada (HTML o XML) a una solicitud con pphase=02
    public void doGetFase02(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
	    
	    //Variable que contiene el valor del  parámetro auto
	    String auto = reqaux.getParameter("auto");
            
            if (auto == null) FrontEnd.sendHTML02(resaux,DataModel.FES,DataModel.FEF); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            else if (auto.equals("true")) {
            	FrontEnd.sendXML02(resaux,DataModel.FES,DataModel.FEF); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            }
	    else FrontEnd.sendHTML02(resaux,DataModel.FES,DataModel.FEF); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	    
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
    
    //Método encargado de invocar la respuesta apropiada (HTML o XML) a una solicitud con pphase=11
    public void doGetFase11(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
    	    
    	    //Variable que contiene el valor del  parámetro auto
     	    String auto = reqaux.getParameter("auto");
	    
	    //Variable que contiene la lista de paises disponibles por medio de la llamada del método get correspondiente
	    ArrayList<String> paises = DataModel.getQ1Countries();
	    
	    if (auto == null) FrontEnd.sendHTML11(resaux,paises); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            else if (auto.equals("true")) {
            	FrontEnd.sendXML11(resaux,paises); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            }
            else FrontEnd.sendHTML11(resaux,paises); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	        
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
    
    //Método encargado de comprobar la correcta introduccion del parámetro pcountry y de invocar la respuesta apropiada (HTML o XML) a una solicitud con pphase=12
    public void doGetFase12(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
    	    
    	    //Variable que contiene el valor del  parámetro pcountry
	    String pais = reqaux.getParameter("pcountry");
	    //Variable que contiene el valor del  parámetro auto
	    String auto = reqaux.getParameter("auto");
	    
	    if (pais == null) doGetnoCountry(reqaux,resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro pcountry
	    else {
	        //Variable que contiene la lista de albums disponibles, en función del parámetro pcountry seleccionado, por medio de la llamada del método get correspondiente
	    	ArrayList<Album> albums = DataModel.getQ1Albums(pais); 
	    	
	    	if (auto == null) FrontEnd.sendHTML12(resaux,pais,albums); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            	else if (auto.equals("true")) {
            		FrontEnd.sendXML12(resaux,pais,albums); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            	}
            	else FrontEnd.sendHTML12(resaux,pais,albums); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	    }
	    
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
    
    //Método encargado de comprobar la correcta introduccion de los parámetros pcountry y paid, además de invocar la respuesta apropiada (HTML o XML) a una solicitud con pphase=13
    public void doGetFase13(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
	    //Variable que contiene el valor del  parámetro pcountry
	    String pais = reqaux.getParameter("pcountry");
	    //Variable que contiene el valor del  parámetro paid
	    String idalb = reqaux.getParameter("paid");
	    //Variable que contiene el valor del  parámetro auto
	    String auto = reqaux.getParameter("auto");
	    
	    if (pais == null) doGetnoCountry(reqaux,resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro pcountry
	    else if (idalb == null) doGetnoAid(reqaux,resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro paid
	    else {
	        //Variable que contiene la lista de songs disponibles, en función de los parámetros pcountry y paid seleccionados, por medio de la llamada del método get correspondiente
	    	ArrayList<Song> songs = DataModel.getQ1Songs(pais,idalb); 
	    	
	    	if (auto == null) FrontEnd.sendHTML13(resaux,pais,idalb,songs); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            	else if (auto.equals("true")) {
            		FrontEnd.sendXML13(resaux,pais,idalb,songs); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            	}
            	else FrontEnd.sendHTML13(resaux,pais,idalb,songs); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	    }
	    
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
    
    //Método encargada de invocar la respuesta adecuada ante un parámetro p incorrecto
    public void doGetpWrong(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
	    //Variable que contiene el valor del  parámetro auto
	    String auto = reqaux.getParameter("auto");
            
            if (auto == null) FrontEnd.sendHTMLpw(resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            else if (auto.equals("true")) {
            	FrontEnd.sendXMLpw(resaux); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            }
            else FrontEnd.sendHTMLpw(resaux); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	    
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
    
    //Método encargada de invocar la respuesta adecuada ante la ausencia de parámetro p
    public void doGetnop(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
	    //Variable que contiene el valor del  parámetro auto
	    String auto = reqaux.getParameter("auto");
            
            if (auto == null) FrontEnd.sendHTMLnp(resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            else if (auto.equals("true")) {
            	FrontEnd.sendXMLnp(resaux); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            }
            else FrontEnd.sendHTMLnp(resaux); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	    
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
    
    //Método encargada de invocar la respuesta adecuada ante la ausencia de parámetro pcountry
    public void doGetnoCountry(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
	    //Variable que contiene el valor del  parámetro auto
	    String auto = reqaux.getParameter("auto");
            
            if (auto == null) FrontEnd.sendHTMLnc(resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            else if (auto.equals("true")) {
            	FrontEnd.sendXMLnc(resaux); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            }
            else FrontEnd.sendHTMLnc(resaux); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	    
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
    
    //Método encargada de invocar la respuesta adecuada ante la ausencia de parámetro paid
    public void doGetnoAid(HttpServletRequest reqaux, HttpServletResponse resaux) 
    {
    	try {
	    //Variable que contiene el valor del  parámetro auto
	    String auto = reqaux.getParameter("auto");
            
            if (auto == null) FrontEnd.sendHTMLnid(resaux); //Llamada al método que genera la respuesta correspondiente a la ausencia del parámetro auto (modo browser)
            else if (auto.equals("true")) {
            	FrontEnd.sendXMLnid(resaux); //Llamada al método que genera la respuesta correspondiente al modo auto (auto == true)
            }
            else FrontEnd.sendHTMLnid(resaux); //Llamada al método que genera la respuesta correspondiente al modo browser (auto != true)
	    
	}
	catch (Exception ex) {
	    System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
	}
    }
}
