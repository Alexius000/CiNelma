package page.adminNavigationBar;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import page.MovieManagement.MovieManagement;
import model.User;
import page.login.LoginPage;


public class AdminNavigationBar {
	
	private MenuBar menuBar;
	private Menu menu;
	private MenuItem homeItem;
	private MenuItem manageMoviesItem;
	private MenuItem logOutItem;
	private Label welcomeMessage;
	private Label welcomeName;
	

	VBox vBox;
	Scene scene;
	BorderPane bp;
	Stage stage;
	String userEmail ="";
	String userName = "";
	
	
	public AdminNavigationBar(Stage stage, String userEmail) {
		this.stage = stage;
		this.userEmail = userEmail;
		initiationComponent();
		setComponent();
		action();
		
		stage.setTitle("Admin User Navigation Bar");
		stage.setScene(scene);
	}
	
	
	public void initiationComponent() {
		menuBar = new MenuBar();
		menu = new Menu("Menu");
		homeItem = new MenuItem("Home");
		manageMoviesItem = new MenuItem("Manage Movies");
		logOutItem = new MenuItem("Log Out");
		
		userName = userEmail.substring(0, userEmail.indexOf('@'));
		welcomeName = new Label("Hi, "+ userName + "!");
		welcomeMessage = new Label("Please select your desired menu from the navigation bar");
		
		vBox = new VBox();
		
		bp = new BorderPane();
		scene = new Scene(bp,500,500);
		
	}
	
	public void setComponent() {
		vBox.getChildren().addAll(welcomeName,welcomeMessage);
		vBox.setAlignment(Pos.CENTER);
		menu.getItems().addAll(homeItem,manageMoviesItem,logOutItem);
		menuBar.getMenus().add(menu);
		welcomeName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		bp.setTop(menuBar);
		bp.setCenter(vBox);
	}
	
	public void action() {
    	manageMoviesItem.setOnAction(e ->{
    		MovieManagement movieManagement =  new MovieManagement(stage,userEmail);
    		
    	});
    	logOutItem.setOnAction(e->{
			LoginPage loginPage = new LoginPage(stage);
		});
    	
    	homeItem.setOnAction(e->{
    		AdminNavigationBar adminNBV = new AdminNavigationBar(stage, userEmail);
    	});
    
    }
  

	
 

	}
	

	


	
