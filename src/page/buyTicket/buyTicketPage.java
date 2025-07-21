package page.buyTicket;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.Connect;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;
import model.Movie;
import page.cartPage.CartPage;
import page.login.LoginPage;
import page.userNavigationBar.userNavigationBar;

public class buyTicketPage {
	Connect connect;
	
	GridPane gp;
	BorderPane bp;
	VBox vBox;
	VBox vBox2;
	HBox hBox;
	HBox hBox2;
	ComboBox<String> cb;
	DatePicker datePicker;
	Button btn ; 
	Window window;
	Alert alert;
	
	
	
	
	String movieDescription = "";
	private Integer totalPrice = 0;
	private String userEmail;
	private String userId;
	private String movieId;
	private MenuBar menuBar;
	private Menu menu;
	private MenuItem homeItem,buyItem,cartItem,logOutItem;
	private Spinner<Integer> spinner;
	private Integer totalQuantity;
	Integer cinemaTypePrice = 0;
	Scene scene;
	Stage stage;
	
	
	private Label movieNameLabel;
	private Label descriptionLabel;
	private Label ticketQuantityTitle;
	private Label reservationDateTitle;
	private Label totalPriceLabel;
	private Label cinemaTypeTitle;
	
	private TableView<Movie> movieListTable;
	private TableColumn<Movie, String>titleColumn;
	private TableColumn<Movie, String>genreColumn;
	private TableColumn<Movie, Integer>durationColumn;
	private TableColumn<Movie, String>releaseDateColumn;
	private TableColumn<Movie, Integer>availableTicketsColumn;
	
	ArrayList<Movie>ticketList = new ArrayList<>();
	
	public buyTicketPage(Stage stage,String userEmail) {
		this.stage = stage;
		this.userEmail = userEmail;
		initiationComponent();
		setLayout();
		viewMovie();
		buyTicket();
		totalPrice();
		action1();
		validation();
		stage.setScene(scene);
		stage.show();
		
		
	}

	public void initiationComponent() {
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		homeItem = new MenuItem("Home");
		buyItem = new MenuItem("Buy a Ticket");
		cartItem = new MenuItem("Cart");
		logOutItem = new MenuItem("Log Out");
		btn = new Button("Add to Cart");
		bp = new BorderPane();
		gp = new GridPane();
		window = new Window("Buy a Ticket");
		vBox = new VBox(20);
		vBox2 = new VBox(10);
		cb = new ComboBox<>();
		datePicker = new DatePicker();
		hBox = new HBox(20);
		hBox2 = new HBox(20);
		scene = new Scene(bp,1200,700);
		spinner = new Spinner<Integer>(1,10,1);
		
		
		movieNameLabel = new Label("Movie Name");
		descriptionLabel = new Label("Description");
		ticketQuantityTitle = new Label("Quantity");
		reservationDateTitle = new Label("Reservation Date");
		totalPriceLabel = new Label("Total Price : " + totalPrice);
		cinemaTypeTitle = new Label("Cinema Type");
		
		
		
		
		movieListTable = new TableView<Movie>();
		titleColumn = new TableColumn<>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<Movie,String>("title"));
		genreColumn = new TableColumn<>("Genre");
		genreColumn.setCellValueFactory(new PropertyValueFactory<Movie,String>("genre"));
		durationColumn = new TableColumn<>("Duration");
		durationColumn.setCellValueFactory(new PropertyValueFactory<Movie,Integer>("duration"));
		releaseDateColumn = new TableColumn<>("Release Date");
		releaseDateColumn.setCellValueFactory(new PropertyValueFactory<Movie,String>("releaseDate"));
		availableTicketsColumn = new TableColumn<>("Available Tickets");
		availableTicketsColumn.setCellValueFactory(new PropertyValueFactory<Movie,Integer>("availableTickets"));
		
		movieListTable.getColumns().addAll(titleColumn,genreColumn,durationColumn,releaseDateColumn,availableTicketsColumn);
		
		titleColumn.setPrefWidth(200);
		
		
	}
	
	public void setLayout() {
		
		menu.getItems().addAll(homeItem,buyItem,cartItem,logOutItem);
    	menuBar.getMenus().add(menu);
		
		titleColumn.setPrefWidth(200);
		genreColumn.setPrefWidth(200);
		durationColumn.setPrefWidth(200);
		releaseDateColumn.setPrefWidth(200);
		availableTicketsColumn.setPrefWidth(200);
		
		
		
		gp.add(ticketQuantityTitle, 0, 0);
		gp.add(spinner, 0, 1);
		gp.add(reservationDateTitle, 1, 0);
		gp.add(datePicker, 1, 1);
		gp.add(cinemaTypeTitle, 2, 0);
		gp.add(cb, 2, 1);
		
		gp.setHgap(20);
		
		hBox.getChildren().addAll(movieNameLabel,gp);
		hBox.setAlignment(Pos.CENTER);
		hBox2.getChildren().addAll(totalPriceLabel,btn);
		hBox2.setAlignment(Pos.CENTER);
		
		vBox.getChildren().addAll(descriptionLabel,hBox,hBox2);
		vBox.setAlignment(Pos.CENTER);
		window.getContentPane().getChildren().add(vBox);
		
		vBox2.getChildren().addAll(movieListTable,window);
		vBox2.setAlignment(Pos.CENTER);
		
		cb.getItems().add("Regular");
		cb.getItems().add("Premium");
		cb.setValue("Regular");
		
		bp.setTop(menuBar);
		bp.setCenter(vBox2);
		
		
		datePicker.getEditor().textProperty().addListener((obs, oldValue, newValue) ->{
			if(newValue.isEmpty()) {
				datePicker.setValue(null);
			}
		});
		
		
	
	}
	
	public void buyTicket() {
		movieListTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			
			
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				description(movieListTable.getSelectionModel().getSelectedItem().getTitle());
				
				
				movieNameLabel.setText(movieListTable.getSelectionModel().getSelectedItem().getTitle());
				descriptionLabel.setText(movieDescription);
			}
			
		});
		
		
		
		
		
		
	}
	
	public void totalPrice() {
		totalQuantity = (Integer) spinner.getValue();
		
		cb.setOnMouseClicked(e -> {
			if (cb.getValue().equals("Regular")) {
		        cinemaTypePrice = 50000;
		    } else {
		        cinemaTypePrice = 70000;
		    }
		    totalPrice = totalQuantity * cinemaTypePrice;
		    totalPriceLabel.setText("Total Price : " + totalPrice);
		});
		
		cb.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
		    if (cb.getValue().equals("Regular")) {
		        cinemaTypePrice = 50000;
		    } else {
		        cinemaTypePrice = 70000;
		    }
		    totalPrice = totalQuantity * cinemaTypePrice;
		    totalPriceLabel.setText("Total Price : " + totalPrice);
		});
		
		spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			totalQuantity = newValue;
			totalPrice = totalQuantity * cinemaTypePrice;
		    totalPriceLabel.setText("Total Price : " + totalPrice);
		});
	}
	
	public void description (String movieName) {
		connect = Connect.getInstance();
		String query = "SELECT * FROM msmovie WHERE MovieName = '"+ movieName + "'";
		
		connect.rs = connect.execQuery(query);
		try {
			while (connect.rs.next()) {
				movieDescription = connect.rs.getString("MovieDescription");
				movieId = connect.rs.getString("MovieID");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void viewMovie() {
		
		connect = Connect.getInstance();
		String query = "SELECT * FROM msmovie";
		
		connect.rs = connect.execQuery(query);
		try {
			while (connect.rs.next()) {
				String movieId = connect.rs.getString("MovieID");
				String movieTitle = connect.rs.getString("MovieName");
				String movieDescription = connect.rs.getString("MovieDescription");
				String movieGenre = connect.rs.getString("MovieGenre");
				Integer movieDuration = connect.rs.getInt("MovieDuration");
				String movieDate = connect.rs.getString("MovieReleaseDate");
				Integer movieAvailableTickets = connect.rs.getInt("MovieStock");
				
				Movie movie = new Movie(movieId,movieTitle,movieDescription, movieGenre, movieDuration, movieDate, movieAvailableTickets);
				ticketList.add(movie);
				movieListTable.getItems().add(movie);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void action() {
		homeItem.setOnAction(e ->{
			userNavigationBar uNBPage = new userNavigationBar(stage,userEmail);
		});
	}
	public void addToCart() {
		LocalDate reservationDate = datePicker.getValue();
		
		
		if(cb.getValue().equals("Regular")) {
			
			connect = Connect.getInstance();
			
			String query = String.format("INSERT INTO cart VALUES ('%s','%s',%s,'%s','%s')", userId,movieId,totalQuantity,"CT001",reservationDate);
			
			connect.execUpdate(query);
		}else {
connect = Connect.getInstance();
			
			String query = String.format("INSERT INTO cart VALUES ('%s','%s',%s,'%s','%s')", userId,movieId,totalQuantity,"CT002",reservationDate);
			
			connect.execUpdate(query);
		}
		
	
	}
	
	public void validation() {
		
		btn.setOnMouseClicked(e->{
			//validation 1
			Object selectedTable = movieListTable.getSelectionModel().getSelectedItem();
			LocalDate localDate = datePicker.getValue();
			        
			if (selectedTable == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Transaction Error");
				alert.setContentText("Please select a movie from the table! ");
	            alert.showAndWait();
	        	
	            
            //Validation 2
            }else if(datePicker.getValue() == null) {
            	Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Transaction Error");
				alert.setContentText("Please select a reservation date! ");
	            alert.showAndWait();
	            

	            //validation 3
            }else if(localDate.isBefore(LocalDate.now())) {
            	
            	
    	            	Alert alert = new Alert(AlertType.ERROR);
    					alert.setTitle("Error");
    					alert.setHeaderText("Transaction Error");
    					alert.setContentText("Please select a date that is greater than today!");
    		            alert.showAndWait();
    		            
    		            //validation 4
            }else if (totalQuantity > movieListTable.getSelectionModel().getSelectedItem().getAvailableTickets()) {
            	Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Transaction Error");
				alert.setContentText("The quantity must not exceed the ticket stock!");
	            alert.showAndWait();
	            
	           //validation 5
            }
            else {
            	selectedUserId();
        		
            	connect = Connect.getInstance();
        		String query = String.format("SELECT * FROM cart WHERE UserID = '%s' AND MovieID = '%s'", userId,movieId);
        		
        		connect.rs = connect.execQuery(query);
        		try {
        			if(connect.rs.next()) {
        				Alert alert = new Alert(AlertType.ERROR);
        				alert.setTitle("Error");
        				alert.setHeaderText("Transaction Error");
        				alert.setContentText("You have already added this movie to your cart!");
        	            alert.showAndWait();
        			}else {
        				addToCart();
        				Alert alert = new Alert(AlertType.INFORMATION);
        				alert.setTitle("Message");
        				alert.setHeaderText("Transaction Success");
        				alert.setContentText("Successfully added to cart!");
        	            alert.showAndWait();
        	            
        	            
        			}
        		} catch (SQLException ex) {
        			// TODO Auto-generated catch block
        			ex.printStackTrace();
        		}
            	
            }
			
			
			
			
			
			
			
		});
		
	}
	public void selectedUserId() {
		connect = Connect.getInstance();
		String query = "SELECT * FROM msuser WHERE UserEmail = '" + userEmail +"'" ;
		
		connect.rs = connect.execQuery(query);
		try {
			while (connect.rs.next()) {
				userId = connect.rs.getString("UserID");
				
	
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void action1 () {
		homeItem.setOnAction(e ->{
			userNavigationBar userNB = new userNavigationBar(stage, userEmail);
		});
		cartItem.setOnAction(e ->{
			CartPage cartPage = new CartPage(stage, userEmail);
			
		});
		logOutItem.setOnAction(e->{
			LoginPage loginPage = new LoginPage(stage);
		});
	}
	
	
	
	
}