package p4;

import java.io.*;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {
	
	private Music xml = null;
	private Album album = null;
	private Song song = null;
	private StringBuilder elementValue = new StringBuilder(); //Variable que contiene los datos encontrados en el MuML
	private String genre_cad = null; //Variable que contiene todos los elementos Genre de una song
	
	private ArrayList<Music> xmlList = null; //Variable que contiene la lista de elementos Music
	private ArrayList<Album> albumList = null; //Variable que contiene la lista de elementos Album
	private ArrayList<Song> songList = null; //Variable que contiene la lista de elementos Song
	private int contador_alb; //Variable que contiene el número de albums añadidos a la lista par parte de cada MuML
	private int contador_song; //Variable que contiene el número de songs añadidos a la lista par parte de cada MuML
	
	public ArrayList<Music> getXmlList() {
		return xmlList;
	}
	
	public ArrayList<Album> getAlbumList() {
		return albumList;
	}
	
	public ArrayList<Song> getSongList() {
		return songList;
	}
	
	public int getContadorAlb() {
		return contador_alb;
	}
	
	public int getContadorSong() {
		return contador_song;
	}
	
	boolean bName = false; 
	boolean bYear = false;
	boolean bSinger = false;
	boolean bGroup = false;
	boolean bCountry = false;
	boolean bTitle = false;
	boolean bComposer = false;
	boolean bGenre = false;
	boolean isFirst = true;
	boolean bMuML = false;
	boolean wYear = false;
	
	@Override
	public void startDocument() throws SAXException { //Método ejecutado al iniciar el parser de un fichero
		xml = new Music();
		contador_alb = 0;
		contador_song = 0;
		System.out.println("START\n");
	}
	
	@Override
	public void endDocument() throws SAXException { //Método ejecutado al finalizar el parser de un fichero
		contador_alb = 0;
		contador_song = 0;
		System.out.println("END\n");
	}
	
	@Override
	public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException { //Método ejecutado al encontrar el inicio de un tag
		
		elementValue.setLength(0);
		
		if (qName.equalsIgnoreCase("Year")) { //Si es un elemento Year
		
			bYear = true;
			if (xmlList == null) xmlList = new ArrayList<Music>();
			
		}else if (qName.equalsIgnoreCase("Album") && wYear==false) { //Si es un elemento Album
		
			String aid = attr.getValue("aid");
			album = new Album();
			album.setAid(aid);
			
			String year = xml.getYear();
			album.setYear(year);
			
			if (albumList == null) albumList = new ArrayList<Album>();
			
		}else if (qName.equalsIgnoreCase("Name") && wYear==false) { //Si es un elemento Name
			bName = true;
			
		}else if (qName.equalsIgnoreCase("Country") && wYear==false) { //Si es un elemento Country
			bCountry = true;
			
		}else if (qName.equalsIgnoreCase("Singer") && wYear==false) { //Si es un elemento Singer
			bSinger = true;
			
		}else if (qName.equalsIgnoreCase("Group") && wYear==false) { //Si es un elemento Group
			bGroup = true;
			
		}else if (qName.equalsIgnoreCase("Song") && wYear==false) { //Si es un elemento Song
		
			String lang = attr.getValue("lang");
			song = new Song();
			song.setLang(lang);
			
			String aid = album.getAid();
			song.setAid(aid);
			
			if (songList == null) songList = new ArrayList<Song>();
			
		}else if (qName.equalsIgnoreCase("Title") && wYear==false) { //Si es un elemento Title
			bTitle = true;
			
		}else if (qName.equalsIgnoreCase("Genre") && wYear==false) { //Si es un elemento Genre
			bGenre = true;
			
		}else if (qName.equalsIgnoreCase("Composer") && wYear==false) { //Si es un elemento Composer
			isFirst = true; //Al encontrar un elemento Composer, interpretamos que ya no hay más elementos Genre en la song
			genre_cad = null;
			bComposer = true;
			
		}else if (qName.equalsIgnoreCase("MuML") && wYear==false) { //Si es un elemento MuML
			bMuML = true;
		}
	}
	
	@Override
	public void endElement(String uri, String lName, String qName) throws SAXException { //Método ejecutado al encontrar el cierre de un tag
	
		if (bYear) { //Si el elemento que se cierra es Year
		
			xml.setYear(elementValue.toString()); 
			xmlList.add(xml);
			
			int año = Integer.parseInt(elementValue.toString());
			
			if (año < 1980 || año > 2021) wYear=true; //Comprobamos si el Year pertenece al rango permitido, si no bloqueamos el análisis del resto de elementos para que no modifique nada
			else wYear=false;
			
			bYear = false;
			
		}else if (bName) { //Si el elemento que se cierra es Name
			album.setName(elementValue.toString());
			bName = false;
			
		}else if (bCountry) { //Si el elemento que se cierra es Country
			album.setCountry(elementValue.toString());
			bCountry = false;
			
		}else if (bSinger) { //Si el elemento que se cierra es Singer
			album.setSinger(elementValue.toString());
			bSinger = false;
			
		}else if (bGroup) { //Si el elemento que se cierra es Group
			album.setSinger(elementValue.toString());
			bGroup = false;
			
		}else if (bTitle) { //Si el elemento que se cierra es Title
			song.setTitle(elementValue.toString());
			bTitle = false;
			
		}else if (bGenre) { //Si el elemento que se cierra es Genre
		
			if (isFirst == true) { //Si es el primer elemento Genre de la song
				genre_cad = elementValue.toString();
				song.setGenre(elementValue.toString());
				isFirst = false;
			}
			else { //El resto de elementos Genre se concatenan al primero con ,
				genre_cad = genre_cad + "," + elementValue.toString();
				song.setGenre(genre_cad);
			}
			
			bGenre = false;
			
		}else if (bComposer) { //Si el elemento que se cierra es Composer
			song.setComposer(elementValue.toString());
			bComposer = false;
			
		}else if (bMuML) { //Si el elemento que se cierra es MuML
			song.setMuml(elementValue.toString());
			bMuML = false;
		}
		
		if (qName.equalsIgnoreCase("Album") && wYear==false) { //Si el elemento que se cierra es Album
			albumList.add(album);
			contador_alb++;
			
		}else if (qName.equalsIgnoreCase("Song") && wYear==false) { //Si el elemento que se cierra es Song
			songList.add(song);
			contador_song++;
		}
	}
	
	@Override
	public void characters(char [] ch,int start,int length) throws SAXException { //Método encargado de recopilar el texto de el MuML analizado
		elementValue.append(ch,start,length);
		
		String review = new String(ch,start,length);
		
		if (review.contains(":")) album.setReview(review.trim()); //Si en los characters encontramos : lo identificamos como review
	}
	
}
