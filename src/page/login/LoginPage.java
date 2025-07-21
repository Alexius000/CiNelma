package page.login;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Connect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.User;
import page.adminNavigationBar.AdminNavigationBar;
import page.register.RegisterPage;
import page.userNavigationBar.userNavigationBar;

public class LoginPage  {
	
	private Label loginTitle;
	private Label emailTitle;
	private Label passwordTitle;
	private Label clickHere;
	
	private TextField emailInput;
	private PasswordField passwordInput;
	private Button loginButton;
	private Alert alert;
	private Connect connect;
	
	User user;
	String userRole = "";
	String userEmail = "";
	String email;
		String password;
	
	BorderPane bp;
	Scene scene;
	VBox vb;
	Stage stage;
	
	public  LoginPage(Stage stage) {
		this.stage = stage;
		initiationComponent();
		setComponent();
		setUpComponent();
		onClickedRegister();
		onLoginButtonClicked();
		onPressClicked();
		stage.setTitle("Login Page");
		stage.setScene(scene);
		stage.show();
		
		
	}

	
	public void initiationComponent() {
		Connect.getInstance();
		
		loginTitle = new Label("Login");
		emailTitle = new Label("Email: ");
		passwordTitle = new Label("Password: ");
		clickHere = new Label("Don't have an account yet? Click here to register");
		
		emailInput = new TextField();
		passwordInput = new PasswordField();
		loginButton = new Button("Login");
		bp = new BorderPane();
		scene = new Scene(bp,500,500);
		
		
		vb = new VBox(10);
		
	}
	public void setComponent() {
		vb.getChildren().addAll(loginTitle,emailTitle,emailInput,passwordTitle,passwordInput,loginButton,clickHere);
		
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(vb);
		
	}
	
	public void setUpComponent() {
		emailInput.setMaxWidth(300);
		passwordInput.setMaxWidth(300);
		loginTitle.setFont(Font.font(null, FontWeight.BOLD,20));
		clickHere.setTextFill(Color.STEELBLUE);
		emailInput.setPromptText("Input email");
		passwordInput.setPromptText("Input password");
	}
	
	public void onClickedRegister() {
		
		    clickHere.setOnMouseClicked(e-> {
		        RegisterPage register = new RegisterPage(stage);
		        register.getClass();
		    });
		
	}
	 public void onLoginButtonClicked() {
		 	loginButton.setOnMouseClicked(e -> {
		 		
		 		email = emailInput.getText();
		 		password = passwordInput.getText();
		 		
		 		 if (email.isEmpty()) {
		             Alert alertEmail = new Alert(AlertType.ERROR);
		             alertEmail.setContentText("Please fill out your email");
		             alertEmail.showAndWait();
		             
		         } else if (password.isEmpty()) {
		             Alert alertPassword = new Alert(AlertType.ERROR);
		             alertPassword.setContentText("Please fill out your password");
		             alertPassword.showAndWait();
		             
		         } else {
		           validateAccount(email, password, stage);
		           
		         }
		 		
		 	});
	 }
	 
	 public void validateAccount(String userEmail, String password, Stage stage) {
		 
		 String query = "SELECT * FROM msuser WHERE UserEmail = '" + userEmail + "' AND UserPassword = '" + password + "'";
		 Connect connect = Connect.getInstance();
		 ResultSet rs = connect.execQuery(query);
		 try {
			 if (rs.next()) {
				 String userId =rs.getString("userId");
	            	userEmail = rs.getString("userEmail");
	            	userRole = rs.getString("userRole");
	            	if (userRole.contains("Admin") ) {
		                 AdminNavigationBar adminNB = new AdminNavigationBar(stage,userEmail);
		             } else {
		                 userNavigationBar userNB = new userNavigationBar(stage, userEmail);
		             }
			} else {
				Alert alertEmail = new Alert(AlertType.ERROR);
	             alertEmail.setContentText("Invalid Email and Password! ");
	             alertEmail.showAndWait();
				
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 
	 }
	 public void loginPress() {
		  email = emailInput.getText();
		  password = passwordInput.getText();
		  if (email.isEmpty()) {
	             Alert alertEmail = new Alert(AlertType.ERROR);
	             alertEmail.setContentText("Please fill out your email");
	             alertEmail.showAndWait();
	         } else if (password.isEmpty()) {
	             Alert alertPassword = new Alert(AlertType.ERROR);
	             alertPassword.setContentText("Please fill out your password");
	             alertPassword.showAndWait();
	         } else {
		           validateAccount(email, password, stage);
		         }
	 }
	 public void onPressClicked() {
		 emailInput.setOnAction(e->{
			 loginPress();
		 });
		 passwordInput.setOnAction(e->{
			 loginPress();
		 });
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 	
//	 String email = "";
//	private boolean validateAccount( email, String password) {
//		 String query = "SELECT * FROM msuser WHERE UserEmail = '" + email + "' AND UserPassword = '" + password + "'";
//	        Connect connect = Connect.getInstance();
//	        ResultSet rs = connect.execQuery(query);
//
//	        try {
//	        	
//	         if (rs.next()) {
//	            	String userId =rs.getString("userId");
//	            	userEmail = rs.getString("userEmail");
//	            	String userPassword = rs.getString("userPassword");
//	            	String userDOB = rs.getString("userDOB");
//	            	userRole = rs.getString("userRole");
//	            	
//	            	user = new User(userId, userEmail, userPassword, userDOB, userRole);
//	                return true;
//	            }else {
//	            	Alert alertEmail = new Alert(AlertType.ERROR);
//		             alertEmail.setContentText("Invalid Email and Password! ");
//		             alertEmail.showAndWait();
//		             return false;
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        } finally {
//	            connect.close();
//	        }
//
//	        return false;
//	    }


	 }
	 

