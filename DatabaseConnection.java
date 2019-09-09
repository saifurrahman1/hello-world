
package tds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	
	public static void main(String [] args){
		new DatabaseConnection().getDatabaseConnection();
	}
	
	//Connection Properties - with mysql configuration attributes
	
	 private static final String SERVER="jdbc:mysql://localhost";        
	 private static final String USER = "root"; 
	 private static final String PASSWORD = "p4ssword";
	 
	//Method to return Connection
	 
	public Connection getDatabaseConnection(){
		Connection connection=null;
		try{
			
			//Need to ensure the timezone - caused error before
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cvedb?useTimezone=true&serverTimezone=UTC", "root", "p4ssword");
			
			Statement state=connection.createStatement();
            state.execute("USE cvedb");
            
		}
		
		//Catch an exception if there is an error connecting to the database
		
		catch(SQLException ex){
			System.out.println("Error in connecting to the Database"+ex.getMessage());
		}
		return connection;
		
	}

}
