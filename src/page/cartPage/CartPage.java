package page.cartPage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.Connect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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
import page.login.LoginPage;
import page.userNavigationBar.userNavigationBar;

public class CartPage {
Connect connect;
	
	GridPane gp;
	BorderPane bp;
	VBox vBox;
	HBox hBox;
	Button checkOutBtn ; 
	Button deleteItemBtn ; 
	Alert alert;

	
	
	private MenuBar menuBar;
	private Menu menu;
	private MenuItem homeItem,buyItem,cartItem,logOutItem;
	
	
	Scene scene;
	Stage stage;
	
	private Label cartNameLabel;
	 String userName;
	 String userEmail;
	 String userId;
	 String movieId;
	 String cinemaTypeId;
	 Integer availableStock;

	
	private TableView<Cart> cartListTable;
	private TableColumn<Cart, String>movieNameColumn;
	private TableColumn<Cart, Integer>quantityColumn;
	private TableColumn<Cart, String>cinemaTypeColumn;
	private TableColumn<Cart, String>reservationDateColumn;
	
	ArrayList<Cart>cartList = new ArrayList<>();
	
	public CartPage(Stage stage, String userEmail) {
		this.stage = stage;
		this.userEmail = userEmail;
		
		initiationComponent();
		setLayout();
		viewCart();
		deleteClicked();
		action();
		checkOutClicked();
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void initiationComponent() {
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		homeItem = new MenuItem("Home");
		buyItem = new MenuItem("Buy a Ticket");
		cartItem = new MenuItem("Add to Cart");
		logOutItem = new MenuItem("Log Out");
		checkOutBtn = new Button("Checkout");
		deleteItemBtn = new Button("Delete Item");
		hBox = new HBox(20);
		vBox = new VBox(20);
		bp = new BorderPane();
		gp = new GridPane();
		scene = new Scene(bp,1200,700);
		
		userName = userEmail.substring(0, userEmail.indexOf('@'));
		cartNameLabel = new Label(userName +"'s Cart");
		
		
		
		cartListTable = new TableView<Cart>();
		movieNameColumn = new TableColumn<>("Movie Name");
		movieNameColumn.setCellValueFactory(new PropertyValueFactory<Cart,String>("movieName"));
		quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Cart,Integer>("quantity"));
		cinemaTypeColumn = new TableColumn<>("Cinema Type");
		cinemaTypeColumn.setCellValueFactory(new PropertyValueFactory<Cart,String>("cinemaType"));
		reservationDateColumn = new TableColumn<>("Reservation Date");
		reservationDateColumn.setCellValueFactory(new PropertyValueFactory<Cart,String>("reservationDate"));
		
		cartListTable.getColumns().addAll(movieNameColumn,quantityColumn,cinemaTypeColumn,reservationDateColumn);
		
//		movieNameColumn.setPrefWidth(200);
	}
	
	public void setLayout() {
		menu.getItems().addAll(homeItem,buyItem,cartItem,logOutItem);
    	menuBar.getMenus().add(menu);
    	
    	cartNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	
    	movieNameColumn.setPrefWidth(200);
		quantityColumn.setPrefWidth(200);
		cinemaTypeColumn.setPrefWidth(200);
		reservationDateColumn.setPrefWidth(200);
		cartListTable.setMaxWidth(802);
		
		hBox.getChildren().addAll(checkOutBtn,deleteItemBtn);
		hBox.setAlignment(Pos.CENTER);
		
		vBox.getChildren().addAll(cartNameLabel,cartListTable,hBox);
		vBox.setAlignment(Pos.CENTER);
		
		bp.setTop(menuBar);
		bp.setCenter(vBox);
	
	}
	public void viewCart() {
		connect = Connect.getInstance();
			String query = "SELECT * FROM msuser mu  JOIN cart c on mu.UserID = c.UserID JOIN msmovie mm on c.MovieID = mm.MovieID JOIN mscinematype mct on c.CinemaTypeID = mct.CinemaTypeID WHERE userEmail = '" + userEmail.replace("'", "''") + "'";
		

		connect.rs = connect.execQuery(query);
		try {
			while (connect.rs.next()) {
				String movieNameTitle = connect.rs.getString("MovieName");
				Integer quantityTitle = connect.rs.getInt("Quantity");
				String cinemaTypeTitle = connect.rs.getString("CinemaType");
				String reservationDateTitle = connect.rs.getString("ReservationDate");
				
				Cart cart = new Cart(movieNameTitle, quantityTitle, cinemaTypeTitle, reservationDateTitle);
				cartList.add(cart);
				cartListTable.getItems().add(cart);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteClicked() {
		
		//validation 1
		
		
		deleteItemBtn.setOnAction(e->{
			Object selectedTable = cartListTable.getSelectionModel().getSelectedItem();
			if(selectedTable == null) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Deletion Error");
				alert.setContentText("Please select a movie to delete from cart!");
				alert.show();
				} else {
					selectUserID(userEmail);
					selectMovieID(cartListTable.getSelectionModel().getSelectedItem().getMovieName());
					
					connect = Connect.getInstance();
//					String query = "DELETE * FROM cart WHERE UserID = '" + userId + "'" + " AND MovieID = '" + movieId + "'"; 			
					
					String query = String.format("DELETE FROM cart WHERE UserID = '%s' AND MovieID = '%s'", userId, movieId);
					connect.execUpdate(query);
					
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("Delete Success");
					alert.setContentText("Successfully delete!");
					alert.show();
				}
			
		
		});
		}
	
	public void checkOutClicked() {
		checkOutBtn.setOnAction(e->{
			Object selectedTable = cartListTable.getSelectionModel().getSelectedItem();
			if(selectedTable == null) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Checkout Error");
				alert.setContentText("Please select a movie to checkout from cart!");
				alert.show();
				
			}else if(cartListTable.getItems().isEmpty()) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Checkout Error");
				alert.setContentText("There is no item inside your cart!");
				alert.show();
			}else {
				
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Checkout Confirmation");
				alert.setContentText("Are you sure you want to checkout?");
				
				if(alert.showAndWait().get() == ButtonType.OK) {
					connect = Connect.getInstance();
					selectUserID(userEmail);
					
					for (int i = 0; i < cartList.size(); i++) {
						String transactionId = getTransactionID();
						selectMovieID(cartList.get(i).getMovieName());
						Integer quantity = cartList.get(i).getQuantity();
						cinemaTypeID(cartList.get(i).getCinemaType());
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate reservationDateFormat = LocalDate.parse(cartList.get(i).getReservationDate(),formatter);				
						Integer movieStock = availableStock - cartList.get(i).getQuantity();

						//Insert
						
						String query = String.format("INSERT INTO transactionheader VALUES ('%s','%s','%s')", transactionId,LocalDate.now(),userId);	
						connect.execUpdate(query);
						
						//Insert
						String query1 = String.format("INSERT INTO transactiondetail VALUES ('%s','%s',%s,'%s','%s')",transactionId,movieId,quantity,cinemaTypeId,reservationDateFormat);
						connect.execUpdate(query1);
						
						//update
						String query2 = String.format("UPDATE msmovie SET MovieStock = %s WHERE MovieID = '%s'",movieStock,movieId);
						connect.execUpdate(query2);
						
						//delete
						String query3 = String.format("DELETE FROM cart WHERE UserID = '%s' AND MovieID = '%s'", userId, movieId);
						connect.execUpdate(query3);
				
					}
				
					
					
					
					
					
					
					
					
					
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Message");
					alert.setHeaderText("Checkout Success");
					alert.setContentText("Successfully checked out!");
					alert.show();
				}
				
				
				
			}
			
		});
	}
	
	
	
	
	
	
	
	public String getTransactionID() {
		String query = "SELECT COUNT(*) FROM transactionheader ";
		 Connect connect = Connect.getInstance();
		 try {
			connect.rs =  connect.execQuery(query);
			connect.rs.next();
			Integer count = connect.rs.getInt(1) + 1;
			return String.format("TR%03d", count);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void cinemaTypeID(String cinemaType) {
		String query = "SELECT * FROM mscinematype WHERE CinemaType = '" + cinemaType + "'";
		 Connect connect = Connect.getInstance();
		 ResultSet rs = connect.execQuery(query);
		 try {
			 while (rs.next()) {
						cinemaTypeId = connect.rs.getString("CinemaTypeID");	            	
			 }
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	public void selectUserID(String userEmail) {
		 String query = "SELECT * FROM msuser WHERE UserEmail = '" + userEmail + "'";
		 Connect connect = Connect.getInstance();
		 ResultSet rs = connect.execQuery(query);
		 try {
			 while (rs.next()) {
						userId = connect.rs.getString("UserID");	            	
			 }
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void selectMovieID(String movieName) {
		String query = "SELECT * FROM msmovie WHERE MovieName = '" + movieName + "'";
		 Connect connect = Connect.getInstance();
		 ResultSet rs = connect.execQuery(query);
		 try {
			 while (rs.next()) {
						movieId = connect.rs.getString("MovieID");	 
						availableStock = connect.rs.getInt("MovieStock");
			 }
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void action() {
		homeItem.setOnAction(e->{
			userNavigationBar userNB = new userNavigationBar(stage, userEmail);
		});
		cartItem.setOnAction(e->{
    		CartPage cartPage = new CartPage(stage, userEmail);
    	});
		buyItem.setOnAction(e ->{
    		buyTicketPage bTPage =  new buyTicketPage(stage, userEmail);
    		
    	});
		logOutItem.setOnAction(e->{
			LoginPage loginPage = new LoginPage(stage);
		});
		
	}
	
	
	
	
	
	
	
	
}
