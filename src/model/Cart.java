package model;

public class Cart {
	
	private String movieName;
	private Integer quantity;
	private String cinemaType;
	private String reservationDate;
	public Cart(String movieName, Integer quantity, String cinemaType, String reservationDate) {
		super();
		this.movieName = movieName;
		this.quantity = quantity;
		this.cinemaType = cinemaType;
		this.reservationDate = reservationDate;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getCinemaType() {
		return cinemaType;
	}
	public void setCinemaType(String cinemaType) {
		this.cinemaType = cinemaType;
	}
	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

}
