package com.zilker.utility;


import java.sql.*;

public class DatabaseConnection {
	
	Connection connection;
	public Connection getConnection(){
		
		
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zbuy","root","root");
			
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return connection;
	}


}
