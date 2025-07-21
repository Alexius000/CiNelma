package model;

public class Movie {
	private String movieID;
	private String title;
	private String movieDescription;
	private String genre;
	private Integer duration;
	private String releaseDate;
	private Integer availableTickets;
	
	public Movie(String movieID,String title,String movieDescription, String genre, Integer duration, String releaseDate, Integer availableTickets) {
		super();
		this.movieID = movieID;
		this.title = title;
		this.movieDescription = movieDescription;
		this.genre = genre;
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.availableTickets = availableTickets;
	}
	public String getmovieID() {
		return movieID;
	}
	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMovieDescription() {
		return movieDescription;
	}
	public void setMovieDescription(String movieDescription) {
		
		this.movieDescription = movieDescription;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getAvailableTickets() {
		return availableTickets;
	}
	public void setAvailableTickets(Integer availableTickets) {
		this.availableTickets = availableTickets;
	}

}
