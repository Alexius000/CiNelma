package page.userNavigationBar;

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
import page.buyTicket.buyTicketPage;
import page.cartPage.CartPage;
import page.login.LoginPage;

public class userNavigationBar {
	private MenuBar menuBar;
    private Menu menu;
    private MenuItem homeItem;
    private MenuItem buyTicketItem;
    private MenuItem cartItem;
    private MenuItem logOutItem;
    private Label welcomeMessage;
	private Label welcomeName;
    
    String userEmail;
    String userName;
    Scene scene;
    VBox vBox;
    BorderPane bp;
    Stage stage;
    
    
    
    public userNavigationBar (Stage stage,String userEmail){
    	this.stage = stage;
    	this.userEmail = userEmail;
    	initiationComponent();
    	setComponent();
    	action();
    	
    	stage.setTitle("User Page");
    	stage.setScene(scene);	
    	
    }
    
    public void initiationComponent() {
    	
    	menuBar = new MenuBar();
    	menu = new Menu("Menu");
    	homeItem = new MenuItem("Home");
    	buyTicketItem = new MenuItem("Buy a Ticket");
    	cartItem	 = new MenuItem("Cart");
    	logOutItem = new MenuItem("Log Out");
    	userName = userEmail.substring(0, userEmail.indexOf('@'));
		welcomeName = new Label("Hi, "+ userName + "!");
		welcomeMessage = new Label("Please select your desired menu from the navigation bar");
		
    	
    	vBox = new VBox();
    	bp = new BorderPane();
    	scene = new Scene(bp,500,500);
    	
    	
    	
    	
    
    }
    public void setComponent(){
    	vBox.getChildren().addAll(welcomeName,welcomeMessage);
		vBox.setAlignment(Pos.CENTER);
    	menu.getItems().addAll(homeItem,buyTicketItem,cartItem,logOutItem);
    	menuBar.getMenus().add(menu);
    	welcomeName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    	bp.setTop(menuBar);
    	bp.setCenter(vBox);
    }
    
    public void action() {
    	buyTicketItem.setOnAction(e ->{
    		buyTicketPage bTPage =  new buyTicketPage(stage, userEmail);
    		
    	});
    	cartItem.setOnAction(e->{
    		CartPage cartPage = new CartPage(stage, userEmail);
    	});
    	logOutItem.setOnAction(e->{
			LoginPage loginPage = new LoginPage(stage);
		});
    
    }
  

}
