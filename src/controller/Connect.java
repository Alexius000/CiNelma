package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class Connect {

	

	
	    private static Connect controller;

	    private final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	    private final String USERNAME = "root";
	    private final String PASSWORD = "";
	    private final String DATABASE = "cinelma";
	    private final String HOST = "localhost:3306";
	    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	    private Connection con;
	    private Statement st;
	    public ResultSet rs;

	    private Connect() {
	        try {
	            Class.forName(CLASS_NAME);
	            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
	            st = con.createStatement();
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static Connect getInstance() {
	        if (controller == null) return new Connect();
	        return controller;

	    }

	    public ResultSet execQuery(String query) {
	        try {
	            rs = st.executeQuery(query);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return rs;
	    }

	    public void execUpdate(String query) {
	        try {
	            st.executeUpdate(query);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public void close() {
	        try {
	            if(rs != null) rs.close();
	            if(st != null) st.close();
	            if(con != null) con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	}

