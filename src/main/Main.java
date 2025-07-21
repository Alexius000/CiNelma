package main;

import javafx.application.Application;
import javafx.stage.Stage;
import page.adminNavigationBar.AdminNavigationBar;
import page.buyTicket.buyTicketPage;
import page.cartPage.CartPage;
import page.login.LoginPage;
import page.register.RegisterPage;
import page.userNavigationBar.userNavigationBar;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primaryStage.setTitle("Cinelma Page");
		LoginPage loginPage = new LoginPage(primaryStage);
//		RegisterPage registerPage = new RegisterPage(primaryStage);
//		userNavigationBar userNB = new userNavigationBar(primaryStage);
//		AdminNavigationBar adminNB = new AdminNavigationBar(primaryStage);
//		HomePage homePage = new HomePage (primaryStage);
//		buyTicketPage buyTicket = new buyTicketPage(primaryStage);
		primaryStage.show();
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}


}
