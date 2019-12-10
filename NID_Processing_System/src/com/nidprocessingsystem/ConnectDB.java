package com.nidprocessingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	public static Connection connection=null;
	 
	  public static Connection connect2DB(){
	      try
	      {
	          String username="root";
	          String password=null;
	          String url = "jdbc:mysql://localhost/nid_system";
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          connection=DriverManager.getConnection(url,username,password);
	          System.out.println("Database connection stablished");  
	          //String query = "insert into info_table(,)"+"values(?,?)";
	          //PreparedStatement preparedStatement = connection.prepareStatement(query);
	          return connection;
	      }
	      catch(Exception e)
	      {
	          System.out.println("Can't connect to database+ Exception=>"+e);
	          return null;
	      } 
	  }  
	  
	  public static void disconnect(){
		  try {
			connection.close();
			System.out.println("Connection close");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection close error=>" + e);
			
		}
	  }
}
