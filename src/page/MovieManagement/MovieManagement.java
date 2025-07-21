package page.MovieManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.Connect;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Cart;
import model.Movie;
import page.adminNavigationBar.AdminNavigationBar;
import page.buyTicket.buyTicketPage;
import page.cartPage.CartPage;
import page.login.LoginPage;
import page.userNavigationBar.userNavigationBar;

public class MovieManagement {
Connect connect;
	
	GridPane gp;
	BorderPane bp;
	VBox vBox;
	VBox vBox2;
	VBox vBox3;
	HBox hBox;
	HBox hBox2;
	ComboBox<String> cb ;
	Alert alert;
	
	
	 String userEmail = "";
	 String userName = "";
	 String selectedId ="";
	 
	
	String movieDescription = "";
	String movieId;
	String updateId;
	private Label tableTitle,movieGenre,durationTitle,stockTitle;
	private MenuBar menuBar;
	private Menu menu;
	private MenuItem homeItem,manageMovieItem,logOutItem;
	private TextField movieTitleTF;
	private TextArea movieDescriptionTA;
	private DatePicker movieReleaseDate;
	
	private Spinner<Integer> movieDuration, movieStock;
	
	private Button insertButton, updateButton, deleteButton;
	
	
	Scene scene;
	Stage stage;
	
	private TableView<Movie> movieListTable;
	private TableColumn<Movie, String>titleColumn;
	private TableColumn<Movie, String>genreColumn;
	private TableColumn<Movie, Integer>durationColumn;
	private TableColumn<Movie, String>releaseDateColumn;
	private TableColumn<Movie, Integer>availableTicketsColumn;
	
	ArrayList<Movie>movieList = new ArrayList<>();
	
	public MovieManagement(Stage stage, String userEmail) {
		this.stage = stage;
		this.userEmail = userEmail;
		initiationComponent();
		setLayout();
		viewMovie();
		handler();
		action();
		
		stage.setScene(scene);
		stage.show();
		
		
		
	}
	public void initiationComponent() {
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		homeItem = new MenuItem("Home");
		manageMovieItem = new MenuItem("Manage Movies");
		logOutItem = new MenuItem("Log Out");
		movieTitleTF = new TextField();
		movieDescriptionTA = new TextArea();
		movieReleaseDate = new DatePicker();
		tableTitle = new Label("Movie List");
		movieGenre = new Label("Genre");
		durationTitle = new Label("Duration");
		stockTitle = new Label("Stock");
		cb = new ComboBox<>();
		movieDuration = new Spinner<>(1,Integer.MAX_VALUE,1);
		movieStock = new Spinner<>(1,Integer.MAX_VALUE,1);
		
		
		insertButton = new Button("Insert");
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");
		
		
		bp = new BorderPane();
		gp = new GridPane();
		vBox = new VBox(15);
		vBox2 = new VBox(15);
		vBox3 = new VBox(15);
		hBox = new HBox(15);
		hBox2 = new HBox(40);
		cb = new ComboBox<String>();
		cb.getItems().addAll("Horror", "Drama", "Action", "Science Fiction", "Comedy");
		cb.setValue("Movie Genre");
//		"Horror", "Drama", "Action", "Science Fiction", "Comedy"
				
		scene = new Scene(bp,1200,700);
		
		
		
		
		
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
		
	}
	public void setLayout() {
		menu.getItems().addAll(homeItem,manageMovieItem,logOutItem);
    	menuBar.getMenus().add(menu);
    	
    	titleColumn.setPrefWidth(100);
		genreColumn.setPrefWidth(100);
		durationColumn.setPrefWidth(100);
		releaseDateColumn.setPrefWidth(100);
		availableTicketsColumn.setPrefWidth(100);
		
		movieListTable.setPrefWidth(503);
		movieListTable.setPrefHeight(500);
		
		gp.add(movieGenre, 0, 0);
		gp.add(cb, 0, 1);
		gp.add(durationTitle, 1, 0);
		gp.add(movieDuration, 1, 1);
		gp.add(stockTitle, 2, 0);
		gp.add(movieStock, 2, 1);
		gp.setHgap(20);
		
		
		
		hBox.getChildren().addAll(insertButton,updateButton,deleteButton);
		
		vBox.getChildren().addAll(gp,hBox);
		vBox.setAlignment(Pos.CENTER_LEFT);
		
		vBox2.getChildren().addAll(movieTitleTF,movieDescriptionTA,movieReleaseDate,vBox);
		vBox2.setAlignment(Pos.CENTER);
		
		vBox3.getChildren().addAll(tableTitle,movieListTable);
		vBox3.setAlignment(Pos.CENTER);
		
		hBox2.getChildren().addAll(vBox3,vBox2);
		hBox2.setAlignment(Pos.CENTER);
		
		movieTitleTF.setPromptText("Movie Title");
		movieDescriptionTA.setPromptText("Movie Description");
		
		
		tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		movieGenre.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		durationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		stockTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		
		
		bp.setCenter(hBox2);
		bp.setTop(menuBar);
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
				movieList.add(movie);
//				movieListTable.getItems().addAll(movieList);
				movieListTable.getItems().add(movie);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void handler() {
	movieListTable.setOnMouseClicked(e->{
		movieTitleTF.setText(movieListTable.getSelectionModel().getSelectedItem().getTitle());
		movieDescriptionTA.setText(movieListTable.getSelectionModel().getSelectedItem().getMovieDescription());
		
		
		});
		insertButton.setOnMouseClicked(e->{
			if (movieTitleTF.getText().isBlank()) {
				alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Insert Error");
				alert.setContentText("Please fill in the title!");
				alert.show();
			} 
			else if (movieDescriptionTA.getText().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Insert Error");
				alert.setContentText("Description can't be empty!");
				alert.show();
				
			} 
			else if (movieReleaseDate.getValue() == null) {
				alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Insert Error");
				alert.setContentText("Release date can't be empty!");
				alert.show();
				
			} 
			else if (!movieReleaseDate.getValue().isBefore(LocalDate.now())) {
				alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Insert Error");
				alert.setContentText("Inputted release date must be before today!");
				alert.show();
				
			} 
			else if (cb.getSelectionModel().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Insert Error");
				alert.setContentText("Choose a movie genre!");
				alert.show();
			
			} else {
			
				insertClicked();
				
			}
		});
		updateButton.setOnMouseClicked(e->{
			Object selectedTable = movieListTable.getSelectionModel().getSelectedItem();
			if(selectedTable == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Update Error");
				alert.setContentText("Please select a movie to update from the table!");
				alert.show();
			}else if (movieTitleTF.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Update Error");
				alert.setContentText("Please fill in the title!");
				alert.show();
			} else if (movieDescriptionTA.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Update Error");
				alert.setContentText("Description can't be empty!");
				alert.show();
			} else if (movieReleaseDate.getValue() == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Update Error");
				alert.setContentText("Release date can't be empty!");
				alert.show();
			} else if (!movieReleaseDate.getValue().isBefore(LocalDate.now())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Update Error");
				alert.setContentText("Inputted release date must be before today!");
				alert.show();
			} else if (cb.getSelectionModel().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Update Error");
				alert.setContentText("Choose a movie genre!");
				alert.show();
				
			} else {
				updateClicked();
			}
		});
		
		deleteButton.setOnMouseClicked(e->{
			Object selectedTable = movieListTable.getSelectionModel().getSelectedItem();
			if(selectedTable == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Delete Error");
				alert.setContentText("Please select a movie to delete from the table!");
				alert.show();
			} else {
				
				deletedClicked();
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText("Delete Success");
				alert.setContentText("Successfully delete!");
				alert.show();
			}
			
		});
			
	}
	private String createMovieId() {
        String query = "SELECT COUNT(*) FROM msmovie";
        Connect connect = Connect.getInstance();
        ResultSet rs = connect.execQuery(query);
        try  {
            rs.next();
            int count = rs.getInt(1) + 1;
            return String.format("MV%03d", count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connect.close();
        }
        return null;
    }
	
	public void insertClicked() {
		 String query = String.format("SELECT * FROM msmovie WHERE MovieName = '%s'", movieTitleTF.getText());
		    connect = Connect.getInstance();
		    ResultSet rs = connect.execQuery(query);
		    try {
		    	if(rs.next()) {
		    		alert.setHeaderText("Insert Error");
					alert.setContentText("Movie already exists!");
					alert.showAndWait();
		    	}else {
		    		alert = new Alert(AlertType.INFORMATION);
		    		alert.setHeaderText("Insert Success");
		    		alert.setContentText("Successfully inserted movie!");
		    		alert.showAndWait();
		    		
		    		String movieId = createMovieId().toString();
					String title  = movieTitleTF.getText();
					String movieDescription = movieDescriptionTA.getText();
					String genre = cb.getSelectionModel().getSelectedItem();
					String duration = movieDuration.getValue().toString();
					String releaseDate = movieReleaseDate.getValue().toString();
					String availableTickets = movieStock.getValue().toString();
					
					connect = Connect.getInstance();
					String query1= String.format("INSERT INTO msmovie VALUES('%S', '%S', '%S', '%S', %S, '%S', %S)", movieId, title, movieDescription, genre , duration, releaseDate, availableTickets);
					
					connect.execUpdate(query1);
		    	}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	public void updateClicked() {	
		 selectedId = movieListTable.getSelectionModel().getSelectedItem().getmovieID();
		
		
		for (int i = 0; i < movieList.size(); i++) {
	        if(movieList.get(i).getTitle().equalsIgnoreCase(movieListTable.getSelectionModel().getSelectedItem().getTitle())) {
	            updateId = movieList.get(i).getmovieID();
	        }
	    }
	    
			    		String movieId = selectedId.toString();
						String title  = movieTitleTF.getText();
						String movieDescription = movieDescriptionTA.getText();
						String genre = cb.getSelectionModel().getSelectedItem();
						String duration = movieDuration.getValue().toString();
						String releaseDate = movieReleaseDate.getValue().toString();
						String availableTickets = movieStock.getValue().toString();
			    		
			    		String query1= String.format("UPDATE msmovie "+ "SET MovieName = '%S', "+ "MovieDescription = '%S', "+ "MovieGenre = '%S', "+ "MovieDuration = %S, "+ "MovieReleaseDate = '%S', "+ "MovieStock = %S "+ "WHERE MovieID = '%S'", title, movieDescription, genre, duration, releaseDate, availableTickets, movieId);
						
			    		connect.execUpdate(query1);
			    		
			    		for (Movie movie : movieList) {
			    	        if (movie.getmovieID().equals(updateId)) {
			    	            movie.setTitle(title);
			    	            movie.setMovieDescription(movieDescription);
			    	            movie.setGenre(genre);
			    	            movie.setDuration(Integer.parseInt(duration));
			    	            movie.setReleaseDate(releaseDate);
			    	            movie.setAvailableTickets(Integer.parseInt(availableTickets));
			    	            break;
			    	        }
			    		
			    		
			    		
		}
			    		alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Success");
						alert.setHeaderText("Update Success");
						alert.setContentText("Successfully update a movie!");
						alert.show();
			    		
		
	}
	public void deletedClicked() {
		connect = Connect.getInstance();
		String movieId = movieListTable.getSelectionModel().getSelectedItem().getmovieID();
		
		String query = String.format("DELETE FROM msmovie WHERE MovieID = '%s'", movieId);
		connect.execUpdate(query);
		
		
		
	}
	public void action() {
		homeItem.setOnAction(e->{
			AdminNavigationBar adminNBV = new AdminNavigationBar(stage, userEmail);
		});
		logOutItem.setOnAction(e->{
			LoginPage loginPage = new LoginPage(stage);
		});
		manageMovieItem.setOnAction(e->{
			MovieManagement movieManagement = new MovieManagement(stage,userEmail);
		});
	}
	

}
	
	
	

