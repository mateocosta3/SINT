package p4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class DataModel {

	/*VARIABLES GLOBALES*/
	
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
	//Lista que contiene el conjunto de elementos Year encontrados en los diversos MuML
	static ArrayList<Music> años;
	//Lista que contiene el conjunto de elementos Album encontrados en los diversos MuML
	static ArrayList<Album> albums;
	//Lista que contiene el conjunto de elementos Song encontrados en los diversos MuML
	static ArrayList<Song> songs;
	//Variable que contiene el nombre del documento padre del que se está generando (sirve para asignar el prefijo correspondiente en caso de que la URL encontrada fuese relativa)
	static String padre;
	
	static MyHandler handler;
	static SAXParser saxParser;

	/*********************/
	
	//Método encargado de inicializar todas las variables necesarias y de llamar al método encargado de encontrar,generar y clasificar todas las listas
	public static void init() {	
		
		try {	
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			saxParser = saxParserFactory.newSAXParser();
			handler = new MyHandler();
			
			// Variable que contiene el URL del fichero inicial de la práctica
			String URL = "http://alberto.gil.webs.uvigo.es/SINT/22-23/muml2001.xml";
			
			mapRoots = new HashMap<String,String>();
			FV = new ArrayList<String> ();
			FES = new ArrayList<String> ();
			FEF = new ArrayList<String> ();
			LMUML = new ArrayList<String> ();
			años = new ArrayList<Music>();
			albums = new ArrayList<Album>();
			songs = new ArrayList<Song>();
			
			LMUML.add(URL);
			
			Buscar(URL); // Llamada al método encargado de encontrar,generar y clasificar todos los MuML
			
		}catch (Exception ex) {
            		System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
        	}
	}
	
	//Método get encargado de devolver la lista que contenga a todos los paises encontrados en los distintos MuML
	public static ArrayList<String> getQ1Countries() throws ParserConfigurationException,SAXException,IOException
	{	
		
		//Inicializamos la lista de paises que vamos a devolver
		ArrayList<String> listaPaises = new ArrayList<String> ();
		
		for (Album alb : albums) { //Recorremos la lista de albums
			listaPaises.add(alb.getCountry()); //Añadimos el country de cada album a la lista de paises
		}
		
		//Este fragmento de código simplemente crea otra lista idéntica a la original pero eliminando los países duplicados
		ArrayList<String> listaPaises2 = new ArrayList<String> ();
		for (String pais : listaPaises) {
			if (!listaPaises2.contains(pais)) {
				listaPaises2.add(pais);
			}
		}
		
		
		Comparator<String> comparador = Collections.reverseOrder(); //Establecemos como criterio de ordenación el alfabético inverso
		Collections.sort(listaPaises2,comparador); //Ordenamos bajo ese criterio la lista a devolver
			
		return listaPaises2; //Devolvemos la lista ordenada
	}
	
	//Método get encargado de devolver la lista que contenga a todos los albums de un determinado país encontrados en los distintos MuML
	public static ArrayList<Album> getQ1Albums (String country) throws ParserConfigurationException,SAXException,IOException
	{	
		
		//Inicializamos la lista de albums que vamos a devolver
		ArrayList<Album> listaAlb = new ArrayList<Album> ();
		
		for (Album alb : albums) { //Recorremos la lista de albums
			if (alb.getCountry().equals(country)) { //Encontramos los albums con un elemento country igual al recibido como parámetro
				listaAlb.add(alb); //Añadimos el album a la lista de albums
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
			
		return listaAlb; //Devolvemos la lista ordenada
	}
	
	//Método get encargado de devolver la lista que contenga a todas las songs pertenecientes a un album, de un determinado país e id, encontradas en los distintos MuML
	public static ArrayList<Song> getQ1Songs (String country,String album) throws ParserConfigurationException,SAXException,IOException
	{
		//Inicializamos la lista de songs que vamos a devolver
		ArrayList<Song> listaSongs = new ArrayList<Song> ();
		
		for (Song sng : songs) { //Recorremos la lista de songs
			if ((sng.getGenre().contains("Pop")) && (sng.getAid().equals(album))) { //Encontramos las song que pertenezcan al género Pop y al album con el aid recibido como parámetro
				listaSongs.add(sng); //Añadimos la song a la lista de songs
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
		
		return listaSongs; //Devolvemos la lista ordenada
		
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
	
	//Método encargado de encontrar,generar y clasificar todos los MuML
	public static void Buscar(String fichero) throws ParserConfigurationException,SAXException,IOException
	{
		//Variable que contiene el indice de inicio de la subcadena http:// y que sirve para saber si la URL recibida como parámetro es relativa o absoluta
		int rel_abs = fichero.indexOf("http://");
		//Inicializamos la variable que contendrá la URL absoluta que utilizaremos para generar el documento
		String URL="";
		
		if (rel_abs == 0) { //Si vale 0 significa que la URL contiene la subcadena y que además está al inicio (URL absoluta)
		
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
		
		try{
			saxParser.parse(URL,handler);
		}catch(SAXException e) {
			FEF.add(URL); //Lo añadimos a la lista de documentos con errores fatales si se detecta la excepción que indica que no es well-formed
			
			int contador_alb = handler.getContadorAlb(); //Obtenemos el contador de los albums encontrados en un MuML no well-formed
			int contador_song = handler.getContadorSong(); //Obtenemos el contador de los songs encontrados en un MuML no well-formed
			
			albums = handler.getAlbumList(); //Obtenemos la lista que contiene todos los elementos Album de los MuML examinados
			songs = handler.getSongList(); //Obtenemos la lista que contiene todos los elementos Song de los MuML examinados
			
			//En este fragmento eliminamos los albums añadidos a la lista encontrados en un MuML no well-formed
			while (contador_alb > 0) {
				albums.remove(albums.size()-1);
				contador_alb--;
			}
			
			//En este fragmento eliminamos los songs añadidos a la lista encontrados en un MuML no well-formed
			while (contador_song > 0) {
				songs.remove(songs.size()-1);
				contador_song--;
			}
			
			return;
		}
		
		años = handler.getXmlList(); //Obtenemos la lista que contiene todos los elementos Year de los MuML examinados
			
		//En este fragmento comparamos el valor obtenido con el rango numérico permitido
		int year_int = Integer.parseInt(años.get(años.size()-1).getYear());
		if (year_int < 1980 || year_int > 2021) {
			FES.add(URL); //Lo añadimos a la lista de documentos con errores simples si se detecta que el año está fuera de rango
			return;
		}
			
		albums = handler.getAlbumList(); //Obtenemos la lista que contiene todos los elementos Album de los MuML examinados
		songs = handler.getSongList(); //Obtenemos la lista que contiene todos los elementos Song de los MuML examinados
		
		//En este fragmento de código obtenmos el valor de los diferentes elementos MuML del documento
		for (Song sng : songs) {
			if (!LMUML.contains(sng.getMuml()) && sng.getMuml() != null) {
				LMUML.add(sng.getMuml());
			}
		}
		
		//Este bucle recorre toda la lista de elementos MuML y comprueba si los ficheros a los que se asocian ya has sido visitados	
		for (String MUML: LMUML)  {
			if (!FV.contains(MUML)) { //Si no se ha visitado el fichero
				Buscar(MUML); //Se llama recursivamente al método inicial para repetir todo el proceso desde el inicio
			}
		}
		
		return;	
	}

}
