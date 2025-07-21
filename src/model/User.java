package model;

public class User {
	
	private String userId;
	private String userEmail;
	private String userPassword;
	private String userDOB;
	private String userRole;
	
	
	public User(String userId, String userEmail, String userPassword, String userDOB, String userRole) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userDOB = userDOB;
		this.userRole = userRole;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public String getUserPassword() {
		return userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getUserDOB() {
		return userDOB;
	}


	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	

}
