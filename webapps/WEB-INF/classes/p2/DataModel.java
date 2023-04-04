package p2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.util.*;
import javax.xml.xpath.*;
import java.text.*;

public class DataModel {
	
	/*VARIABLES GLOBALES*/
	
	//HashMap que asocia el conjunto de documentos encontrados en los diversos elementos MuML con sus respectivos elementos Year
	static HashMap<String,Document> mapDocs;
	//HashMap que asocia los URL absolutos encontrados en los elementos MuML con sus respectivos prefijos
	static HashMap<String,String> mapRoots;
	//Lista que contiene el conjunto de documentos visitados
	static ArrayList<String> FV;
	//Lista que contiene el conjunto de documentos que contienen errores simples (año fuera de rango)
	static ArrayList<String> FES;
	//Lista que contiene el conjunto de documentos que contienen errores fatales (no well-formed)
	static ArrayList<String> FEF;
	//Lista que contiene el conjunto de documentos encontrados en los diversos elementos MuML
	static ArrayList<String> LMUML;
	//Variable necesaria para crear el parse de cada uno de los documentos
	static DocumentBuilder db;
	//Variable que contiene el nombre del documento padre del que se está generando (sirve para asignar el prefijo correspondiente en caso de que la URL encontrada fuese relativa)
	static String padre;
	
	/*********************/
	
	
	//Método encargado de inicializar todas las variables necesarias y de llamar al método encargado de encontrar,generar y clasificar todos los documentos
	public static void init() throws ParserConfigurationException, SAXException, IOException
	{
		try {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		
		mapDocs = new HashMap<String,Document>();
		mapRoots = new HashMap<String,String>();
		FV = new ArrayList<String> ();
		FES = new ArrayList<String> ();
		FEF = new ArrayList<String> ();
		LMUML = new ArrayList<String> ();
		
		// Variable que contiene el URL del fichero inicial de la práctica
		String FI = "http://alberto.gil.webs.uvigo.es/SINT/22-23/muml2001.xml";
		LMUML.add(FI);
		
		Buscar(FI); // Llamada al método encargado de encontrar,generar y clasificar todos los documentos
			
		}catch(Exception ex) {
			System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
		}
	}
			
	//Método get encargado de devolver la lista que contenga a todos los paises encontrados en los distintos árboles DOM generados
	public static ArrayList<String> getQ1Countries() throws XPathExpressionException,ParserConfigurationException,SAXException,IOException
	{	
		/*VARIABLES NECESARIAS PARA CREAR LA NODELIST QUE CONTENGA LOS ELEMENTOS DE CADA DOC*/
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		/**/
		
		String xpathTarget = "/Music/Album"; //Predefinimos un path para evaluar el elemento Country de cada album dentro de un documento 
		
		//Inicializamos la lista de paises que vamos a devolver
		ArrayList<String> listaPaises = new ArrayList<String> ();
		
		//Recorremos el mapa de documentos
		for(String clave:mapDocs.keySet()) {
			Document doc = mapDocs.get(clave);
			NodeList paises = (NodeList)xpath.evaluate(xpathTarget,doc,XPathConstants.NODESET); //Creamos un nodelist de cada documento
			
			for (int i = 0; i < paises.getLength(); i++) { //Recorremos la nodelist y obtenemos el elemento Country de cada album del documento para añadirlo a la lista
			
				Element elempais =(Element)paises.item(i); //Obtenemos el elemento de cada documento que tenga la ruta especificada
				
				//Obtenemos el elemento Country de cada album
				NodeList country = elempais.getElementsByTagName("Country");
				Element elem_country = (Element)country.item(0);
				String nombrepais = elem_country.getTextContent().trim();
				
				listaPaises.add(nombrepais); //Lo añadimos a la lista
			}
		}
		
		//Este fragmento de código simplemente crea otra lista idéntica a la original pero eliminando los países duplicados
		ArrayList<String> listaPaises2 = new ArrayList<String> ();
		for (String pais : listaPaises) {
			if (!listaPaises2.contains(pais)) {
				listaPaises2.add(pais);
			}
		}
		/**/
		
		
		Comparator<String> comparador = Collections.reverseOrder(); //Establecemos como criterio de ordenación el alfabético inverso
		Collections.sort(listaPaises2,comparador); //Ordenamos bajo ese criterio la lista a devolver
			
		return listaPaises2; //Devolvemos la lista ordenada
	}
	
	
	//Método get encargado de devolver la lista que contenga a todos los albums de un determinado país encontrados en los distintos árboles DOM generados
	public static ArrayList<Album> getQ1Albums (String country) throws XPathExpressionException,ParserConfigurationException,SAXException,IOException
	{	
		/*VARIABLES NECESARIAS PARA CREAR LA NODELIST QUE CONTENGA LOS ELEMENTOS DE CADA DOC*/
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		/**/
		
		String xpathTarget = "/Music/Album[Country = '"+country+"']"; //Predefinimos un path para evaluar los elementos de cada album pertenecientes al país recibido como parámetro
		
		//Inicializamos la lista de albums que vamos a devolver
		ArrayList<Album> listaAlb = new ArrayList<Album> ();
		
		//Recorremos el mapa de documentos
		for(String clave:mapDocs.keySet()) {
			Document doc = mapDocs.get(clave);
			NodeList albums = (NodeList)xpath.evaluate(xpathTarget,doc,XPathConstants.NODESET); //Creamos un nodelist de cada documento
		
			for (int i = 0; i < albums.getLength(); i++) { //Recorremos la nodelist y obtenemos los atributos y elementos de cada album para añadirlo a la lista
			
				Element elemalbum =(Element)albums.item(i); //Obtenemos el elemento de cada documento que tenga la ruta especificada
				
				//Obtenemos el elemento Name de cada album
				NodeList name = elemalbum.getElementsByTagName("Name");
				Element elem_name = (Element)name.item(0);
				String nombrealb = elem_name.getTextContent().trim();
				
				//Obtenemos el elemento Singer de cada album
				NodeList singer = elemalbum.getElementsByTagName("Singer");
				Element elem_singer = (Element)singer.item(0);
				String nombresing;
				if (elem_singer != null) { 
					nombresing = elem_singer.getTextContent().trim();
				}
				else { //Si es null significa que el album tiene un elemento Group en vez de Singer
					NodeList group = elemalbum.getElementsByTagName("Group");
					Element elem_group = (Element)group.item(0);
					nombresing = elem_group.getTextContent().trim();
				}
				
				String aid = elemalbum.getAttribute("aid"); //Obtenemos el atributo aid de cada album
				
				String format = elemalbum.getAttribute("format"); //Obtenemos el atributo format de cada album
				
				String review = getComentario(elemalbum); //Para obtener el review llamamos a un método especializado
				
				String year = clave; //El elemento Year lo obtenemos de la lista de claves del HashMap de documentos
				
				Album album = new Album(nombrealb,year,nombresing,review,aid,format); //Creamos cada album a partir de los datos recogidos
				
				listaAlb.add(album); //Lo añadimos a la lista
			}
		}
		
		//En este fragmento definimos los criterios de ordenación de la lista y la ordenamos en función de los mismos
		Collections.sort(listaAlb, new Comparator<Album>() {
			@Override
			public int compare(Album a1, Album a2) { //Comparamos dos album
				Integer int1 = Integer.valueOf(a1.getYear()); //Obtenemos el elemento Year de cada uno
				Integer int2 = Integer.valueOf(a2.getYear());
				int comp = int1.compareTo(int2);
				if (comp == 0) { //Si son iguales se ordenarán alfabéticamente en base al elemento Name de cada uno
					return new String(a1.getName()).compareTo(new String(a2.getName()));
				}
				else return comp; //Si son distintos se ordenarán por año en orden creciente 
			}
		});
		/**/
			
		return listaAlb; //Devolvemos la lista ordenada
	}
	
	
	//Método get encargado de devolver la lista que contenga a todas las songs pertenecientes a un album, de un determinado país e id, encontradas en los distintos árboles DOM generados
	public static ArrayList<Song> getQ1Songs (String country,String album) throws XPathExpressionException,ParserConfigurationException,SAXException,IOException
	{
		/*VARIABLES NECESARIAS PARA CREAR LA NODELIST QUE CONTENGA LOS ELEMENTOS DE CADA DOC*/
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		/**/
		
		//Predefinimos un path para evaluar los elementos de las songs incluidas en el album perteneciente al país y asignado al aid recibidos como parámetros
		String xpathTarget = "/Music/Album[@aid='"+album+"' and Country = '"+country+"']/Song"; 
		
		//Inicializamos la lista de songs que vamos a devolver
		ArrayList<Song> listaSongs = new ArrayList<Song> ();
		
		//Recorremos el mapa de documentos
		for(String clave:mapDocs.keySet()) {
			Document doc = mapDocs.get(clave);
			NodeList songs = (NodeList)xpath.evaluate(xpathTarget,doc,XPathConstants.NODESET); //Creamos un nodelist de cada documento
		
			for (int i = 0; i < songs.getLength(); i++) { //Recorremos la nodelist y obtenemos los atributos y elementos de cada album para añadirlo a la lista
				
				Element elemsong = (Element)songs.item(i); //Obtenemos el elemento de cada documento que tenga la ruta especificada

				NodeList nl_genre = elemsong.getElementsByTagName("Genre");
				String cad_genre = "";
				for (int j = 0; j < nl_genre.getLength(); j++) { //Obtenemos los distintos elementos Genre que puede tener un album y los concatenamos dentro de una misma String
					Element elem_genre = (Element)nl_genre.item(j);
					String genre = elem_genre.getTextContent().trim();
					if (j == 0) cad_genre = genre; //Si es el primer elemento encontrado se añade sin más a la cadena
					else cad_genre = cad_genre + "," + genre; //El resto se añaden precedidos de una ,
				}
				
				if (cad_genre.contains("Pop")) { //Comprobamos que uno de los elementos Genre de la song es Pop
					
					//Obtenemos el elemento Title de cada song
					NodeList title = elemsong.getElementsByTagName("Title");
					Element elem_title = (Element)title.item(0);
					String titlesong = elem_title.getTextContent().trim();
					
					//Obtenemos el elemento Composer de cada song	
					NodeList composer = elemsong.getElementsByTagName("Composer");
					Element elem_composer = (Element)composer.item(0);
					String songcomp = elem_composer.getTextContent().trim();
						
					String sid = elemsong.getAttribute("sid"); //Obtenemos el atributo sid de cada song
						
					String lang = elemsong.getAttribute("lang"); //Obtenemos el atributo lang de cada song
					
					//Obtenemos el elemento Duration de cada song	
					NodeList duration = elemsong.getElementsByTagName("Duration");
					Element elem_dur = (Element)duration.item(0);
					String dursong = elem_dur.getTextContent().trim();
						
					Song song = new Song(titlesong,lang,cad_genre,songcomp,sid,dursong); //Creamos cada song a partir de los datos recogidos
						
					listaSongs.add(song); //Lo añadimos a la lista
				}
				
			}
		}
		
		//En este fragmento definimos los criterios de ordenación de la lista y la ordenamos en función de los mismos
		Collections.sort(listaSongs, new Comparator<Song>() {
			@Override
			public int compare(Song s1, Song s2) { //Comparamos dos songs
				Integer int1 = countChars(',',s1.getGenre()); //Llamamos a una función que cuente el número de , que hay en cada cadena de elemetos Genre para saber cual contiene más
				Integer int2 = countChars(',',s2.getGenre());
				int comp = int1.compareTo(int2);
				if (comp == 0) { //Si ambas songs tienen el mismo número de elementos Genre se ordenarán alfabéticamente en base al elemento Title de cada una
					return new String(s1.getTitle()).compareTo(new String(s2.getTitle()));
				}
				else return comp; //Si no tienen el mismo número de elementos Genre se ordenarán de menor a mayor número
			}
		});
		/**/
		
		return listaSongs; //Devolvemos la lista ordenada
		
	}
				
	//Método encargado de devolver un fragmento de texto sin formato predefinido
	public static String getComentario (Element root) {
	
		NodeList nlHijos = root.getChildNodes(); //Creamos una nodelist que contenga todos los nodos hijo del elemento recibido como parámetro
		String comentario=""; //Inicializamos la variable que contendrá el fragmento de texto
		
		for (int i = 0; i < nlHijos.getLength(); i++) { //Recorremos la nodelist nodo por nodo
			Node hijo = nlHijos.item(i);
			if(hijo.getNodeType() == org.w3c.dom.Node.TEXT_NODE) { //Juntamos todos los nodos que sean identificados como fragmentos de texto sin formato predefinido
				comentario = comentario + hijo.getNodeValue();
			}
		}
		comentario = comentario.trim(); //Eliminamos todos los espacios en blanco que pudiese haber al inicio y al final de la cadena
		
	
		return comentario; //Devolvemos el fragmento de texto sin formato predefinido buscado
	}
	
	//Método encargado de devolver el número de veces que una cadena contiene un caracter específico
	public static int countChars(char caracter,String cadena) {
	
		int total = 0; //Inicializamos la variable que contendrá el entero a devolver
		
		for (int i = 0; i < cadena.length(); i++) { //Recorremos caracter a caracter la cadena recibida como parámetro
			char c = cadena.charAt(i);
			if (c == caracter) total++; //Si el caracter de la cadena coincide con el recibido como parámetro, se incrementa el valor del entero
		}
		
		return total; //Devolvemos el número de caracteres x que contiene la cadena
	}
	
	
	//Método encargado de encontrar,generar y clasificar todos los documentos
	public static void Buscar(String fichero) throws XPathExpressionException,ParserConfigurationException,SAXException,IOException
	{
		//Variable que contiene el indice de inicio de la subcadena http:// y que sirve para saber si la URL recibida como parámetro es relativa o absoluta
		int rel_abs = fichero.indexOf("http://");
		//Inicializamos la variable que contendrá la URL absoluta que utilizaremos para generar el documento
		String URL="";
		
		if (rel_abs == 0) { //Si vale 0 significa que la URL contiene la subcade y que además está al inicio (URL absoluta)
		
			URL = fichero;
			//Variable que contiene el indice del ultimo caracter / de la URL absoluta
	    		int ind = URL.lastIndexOf('/');
	    		//Variable que contiene el prefijo de la URL absoluta
	    		String raiz = URL.substring(0,ind+1); 
	    		mapRoots.put(fichero,raiz);
	    		padre=fichero;
		}
		else { // URL relativa
		
			//Variable que contiene el prefijo de la URL asignada al documento padre del actual
			String root = mapRoots.get(padre);
			URL = root + fichero;	
		}
		
		FV.add(fichero);
		
		Document doc; //Inicializamos el documento que vamos a generar a partir de la URL
			
		try {
			doc = db.parse(URL); /* obtiene el árbol */
		}catch(SAXException e) {
			FEF.add(URL); //Lo añadimos a la lista de documentos con errores fatales si se detecta la excepción que indica que no es well-formed
			return;
		}
			
		Element Music = doc.getDocumentElement(); /* obtiene el elemento raíz del árbol */
		
		//En este fragmento de código obtenemos el valor del elemento Year del documento
		NodeList año = Music.getElementsByTagName("Year");
		Element elem_año = (Element)año.item(0);
		String fecha = elem_año.getTextContent().trim();
		/**/
		
		//En este fragmento comparamos el valor obtenido con el rango numérico permitido
		int year_int = Integer.parseInt(fecha);
		if (year_int < 1980 || year_int > 2021) {
			FES.add(URL); //Lo añadimos a la lista de documentos con errores simples si se detecta que el año está fuera de rango
			return;
		}
		/**/
		
		mapDocs.put(fecha,doc);
		
		//En este fragmento de código obtenmos el valor de los diferentes elementos MuML del documento
		NodeList nl_Mumls = doc.getElementsByTagName("MuML");
		for (int i = 0; i < nl_Mumls.getLength(); i++) {
			Element el_muml = (Element)nl_Mumls.item(i);
			String muml = el_muml.getTextContent().trim();
			LMUML.add(muml);
		}
		/**/
		
		//Este bucle recorre toda la lista de elementos MuML y comprueba si los ficheros a los que se asocian ya has sido visitados	
		for (String MUML: LMUML)  {
			if (!FV.contains(MUML)) { //Si no se ha visitado el fichero
				Buscar(MUML); //Se llama recursivamente al método inicial para repetir todo el proceso desde el inicio
			}
		}
		
		return;	
	}
}
