package page.register;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import controller.Connect;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import page.login.LoginPage;

public class RegisterPage  {
	
	 Label registerTitle;
	 Label emailTitle;
	 Label passwordTitle;
	 Label dateOfBirth;
	 TextField emailInput;
	 PasswordField passwordInput;
	 Label clickHere;
	 Button registerButton;
	 DatePicker dOB;
	 
	 

	BorderPane bp;
	Scene scene;
	VBox vb;
	 Stage stage;
	 
	 String errorMessage;
	 String email;
	 String password;
	 LocalDate birthDate;

	
	
	
	public RegisterPage(Stage stage) {
		this.stage = stage;
		initiationComponent();
		setComponent();
		setUpComponent();
		onClickedLogin();
		registerButtonClicked();
		onPressClicked();
		stage.setTitle("Register Page");
		stage.setScene(scene);
		stage.show();
	}
	
	public  void initiationComponent() {
	// TODO Auto-generated method stub
		
		registerTitle = new Label("Register");
		emailTitle = new Label("Email: ");
		passwordTitle = new Label("Password: ");
		clickHere = new Label("Already have an account? Click here to log in");
		emailInput = new TextField();
		passwordInput = new PasswordField();
		registerButton = new Button("Register");
		dOB = new DatePicker();
		dateOfBirth = new Label("Date of Birth");
		bp = new BorderPane();
		scene = new Scene(bp,500,500);
		vb = new VBox(10);
		
		
	

}
	 public void setComponent() {
		// TODO Auto-generated method stub
		 vb.getChildren().addAll(registerTitle,emailTitle,emailInput,passwordTitle,passwordInput,dateOfBirth,dOB,registerButton,clickHere);
		 vb.setAlignment(Pos.CENTER);
		 bp.setCenter(vb);

	}
	 public void setUpComponent() {
		 
		 emailInput.setMaxWidth(300);
		 passwordInput.setMaxWidth(300);
		 registerTitle.setFont(Font.font(null, FontWeight.BOLD,20));
		 emailInput.setPromptText("Input email");
		 passwordInput.setPromptText("Input password");
		 dOB.setPromptText("Input birthdate");
		 clickHere.setTextFill(Color.STEELBLUE);
		 
	 }
	 public void onClickedLogin() {
			
		    clickHere.setOnMouseClicked(e-> {
		    	
		        LoginPage login = new LoginPage(stage);
		   
		      
		    });
	 }
		    public void registerButtonClicked() {
		    	
		    	registerButton.setOnMouseClicked(e->{
		    		 email = emailInput.getText();
			         password = passwordInput.getText();
			         birthDate = dOB.getValue();
		    		//Empty email error
			        if (email.isEmpty()) {
			            Alert alert = new Alert(AlertType.ERROR);
				        alert.setTitle("Error");
				        alert.setHeaderText("Register Error");
				        alert.setContentText("Please fill out your email!");
				        alert.showAndWait();
				        return ;
			        }
			        if (password.isEmpty()) {
			            Alert alert = new Alert(AlertType.ERROR);
				        alert.setTitle("Error");
				        alert.setHeaderText("Register Error");
				        alert.setContentText("Please fill out your password!");
				        alert.showAndWait();
				        return ;
			        }
			        if (birthDate == null) {
			            Alert alert = new Alert(AlertType.ERROR);
				        alert.setTitle("Error");
				        alert.setHeaderText("Register Error");
				        alert.setContentText("Please fill out your Birthdate!");
				        alert.showAndWait();
				        return ;
			        }
			        //invalid format email
			        if (!email.contains("@")||!email.contains(".")||email.startsWith("@") ||email.endsWith(".")||email.contains(" ") || !specialCharacter(email)) {
			        	Alert alert = new Alert(AlertType.ERROR);
			        	alert.setTitle("Error");
			            alert.setContentText("Register Error");
			            alert.setContentText("Email is not valid!");
			            alert.showAndWait();
			            return;
			        }
			 
			        
			        if(password.length()<8||password.length()>20) {
			        	Alert alert1 = new Alert(AlertType.ERROR);
			        	alert1.setTitle("Error");
			            alert1.setContentText("Register Error");
			            alert1.setContentText("Password must be between 8 and 20 characters inclusively!");
			            alert1.showAndWait();       
			            return;
			        }
			       
			 	        // Password not alphanumeric error

			        if(!hasAlphabet(password)||!hasNumber(password)) {
			        
			        	Alert alert1 = new Alert(AlertType.ERROR);
			            alert1.setTitle("Error");
			            alert1.setContentText("Register Error");
			            alert1.setContentText("Password must be alphanumeric!");
			            alert1.showAndWait();       
			            return;
			        }
			        // if Password must have a length of 8-20 characters inclusively 
			        
//if email already registered
			        
			        if(!validateAccount(email)) {
			        	Alert alert1 = new Alert(AlertType.ERROR);
			        	alert1.setTitle("Error");
			            alert1.setContentText("Register Error");
			            alert1.setContentText("Email is already registered!");
			            alert1.showAndWait();
			            return;
			        }else { 
				        Alert success = new Alert(AlertType.INFORMATION);
				        success.setTitle("Message");
				        success.setHeaderText("Register Success");
				        success.setContentText("New user successfully registered!");
				        success.showAndWait();
				        
				        
				        registerUser(email, password, birthDate);
				        LoginPage loginPage = new LoginPage(stage);
			        	
			        }
			        
			        
		    	});
		    }
		    
		    private boolean validateAccount(String email) {
		 String query = "SELECT * FROM msuser WHERE UserEmail = '" + email +"'" ;
	        Connect connect = Connect.getInstance();
	        ResultSet rs = connect.execQuery(query);

	        try {
	            if (rs.next()) {
	                return false;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            connect.close();
	        }

	        return true;
	    }
	 
	 private boolean hasAlphabet(String password) {
		 for (char a : password.toCharArray()) {
			 if(Character.isLowerCase(a)) {
				 return true;
			 }
			
		}
		return false;
	 }
	 
	 private boolean hasNumber(String password) {
		 for (char a : password.toCharArray()) {
			 if(Character.isDigit(a)) {
				 return true;
			 }
			
		}
		return false;
	 }
	 private boolean specialCharacter(String email) {
		 for (char b : email.toCharArray()) {
			 if(!Character.isLetterOrDigit(b)&& b!= '@' && b != '.' && b != '_') {
				 return false;
			 }
			
		}
		return true;
	 }
	 
	 
	 
	 private  void registerUser(String email, String password, LocalDate birthdate) {

	        String role = email.contains("admin") ? "Admin" : "User";
	        String userID = userId();

	        String query = "INSERT INTO msuser (UserID, UserEmail, UserPassword, UserDOB, UserRole) "
	                + "VALUES ('"+ userID +"', '"+ email +"', '"+ password +"', '"+ birthdate +"', '"+ role +"')";

	        Connect connect = Connect.getInstance();
	        connect.execUpdate(query);

	    }
	 private String userId() {
	        String query = "SELECT COUNT(*) FROM msuser";
	        Connect connect = Connect.getInstance();
	        ResultSet rs = connect.execQuery(query);
	        try  {
	            rs.next();
	            int count = rs.getInt(1) + 1;
	            return String.format("US%03d", count);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            connect.close();
	        }
	        return null;
	    }
	 
	 public void registerPress() {
		 email = emailInput.getText();
		  password = passwordInput.getText();
		 birthDate = dOB.getValue();
		 
		//Empty email error
	        if (email.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Register Error");
		        alert.setContentText("Please fill out your email!");
		        alert.showAndWait();
		        return ;
	        }
	        if (password.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Register Error");
		        alert.setContentText("Please fill out your password!");
		        alert.showAndWait();
		        return ;
	        }
	        if (birthDate == null) {
	            Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Register Error");
		        alert.setContentText("Please fill out your Birthdate!");
		        alert.showAndWait();
		        return ;
	        }
	        //invalid format email
	        if (!email.contains("@")||!email.contains(".")||email.startsWith("@") ||email.endsWith(".")||email.contains(" ") || !specialCharacter(email)) {
	        	Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	            alert.setContentText("Register Error");
	            alert.setContentText("Email is not valid!");
	            alert.showAndWait();
	            return;
	            
	        
	        }
	        
	        if(password.length()<8||password.length()>20) {
	        	Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	            alert.setContentText("Register Error");
	            alert.setContentText("Password must be between 8 and 20 characters inclusively!");
	            alert.showAndWait();       
	            return;
	        }
	       
	 	        // Password not alphanumeric error
	        if (!hasAlphabet(password)||!hasNumber(password)) {
	        	Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("Register Error");
	            alert.setContentText("Password must be alphanumeric!");
	            alert.showAndWait();       
	            return;
	        }
	        // if Password must have a length of 8-20 characters inclusively 
	        
//if email already registered
	        
	        if(!validateAccount(email)) {
	        	Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	            alert.setContentText("Register Error");
	            alert.setContentText("Email is already registered!");
	            alert.showAndWait();
	            return;
	        }else { 
		        Alert success = new Alert(AlertType.INFORMATION);
		        success.setTitle("Message");
		        success.setContentText("Register Success");
		        success.setContentText("New user successfully registered!");
		        success.showAndWait();
		        
		        
		        registerUser(email, password, birthDate);
		        LoginPage loginPage = new LoginPage(stage);
	        	
	        }
	        
		 
	 }
	 public void onPressClicked() {
		 emailInput.setOnAction(e->{
			 registerPress();
		 });
		 passwordInput.setOnAction(e->{
			 registerPress();
		 });
	
	 

	    }
}
	
