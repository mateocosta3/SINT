package p2;

public class Album {
	
	private String name;
	private String year;
	private String singer;
	private String review;
	private String aid;
	private String format;
	
	public Album (String name,String year,String singer,String review,String aid,String format) 
	{
		this.name = name;
		this.year = year;
		this.singer = singer;
		this.review = review;
		this.aid = aid;
		this.format = format;
	}
	
	public String getName() {
		return name;
	}
	
	public String getYear() {
		return year;
	}
	
	public String getSinger() {
		return singer;
	}
	
	public String getReview() {
		return review;
	}
	
	public String getAid() {
		return aid;
	}
	
	public String getFormat() {
		return format;
	}
}
	
	
