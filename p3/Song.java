package p3;

public class Song {
	
	private String title;
	private String lang;
	private String genre;
	private String composer;
	private String sid;
	private String duration;
	
	public Song (String title,String lang,String genre,String composer,String sid,String duration) 
	{
		this.title = title;
		this.lang = lang;
		this.genre = genre;
		this.composer = composer;
		this.sid = sid;
		this.duration = duration;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLang() {
		return lang;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getComposer() {
		return composer;
	}
	
	public String getSid() {
		return sid;
	}
	
	public String getDuration() {
		return duration;
	}
}
	
	
