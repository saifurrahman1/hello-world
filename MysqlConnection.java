package gui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlConnection {

	public List<String> Connect() {
		
		//arraylist to return the module names
		List<String> data = new ArrayList<String>();
		String str = "";
		try {
			
			//mysql connector jdbc - this is where i need my jar file and mysql-connector-ask dave supervisor for help
			Class.forName("com.mysql.jdbc.Driver");
			
			//connection string for the mysql connection here 3306 is the port, dependencies is the schema, root is the username and "" is the password for mysql

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dependencies?autoReconnect=true&useSSL=false", "root", "p4ssword");
			Statement stmt = con.createStatement();
			
			//get all the tables in the schema
			ResultSet rs1 = stmt.executeQuery(
					"SELECT table_name FROM information_schema.tables WHERE  table_schema='dependencies'");
			
			//storing into an arraylist
			ArrayList<String> tables = new ArrayList<String>();
			
			//moving through the result set of the query and adding the table name to arraylist
			while (rs1.next()) {

				String tablename = rs1.getString(1);
				tablename = "`" + tablename + "`";
				
				//adding to arraylist
				tables.add(tablename);
			}

			//getting each table and finding modules from that table
			for (int i = 0; i < tables.size(); i++) {
				ResultSet rs = stmt.executeQuery("select distinct Module from " + tables.get(i));

				while (rs.next()) {
					
					
					//module name is added to the arraylist
					str = rs.getString(1);
					if(!data.contains(str))
						
					data.add(str);
					

				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return data;
	}

		
	
	//this function connects to the cve db and gets the description against a module
	public ArrayList<String> Connect2(String find) {
		
		//arraylist to store description of the module
		ArrayList<String> data = new ArrayList<String>();
		String str = "";
		try {
			
			//refering to jdbc driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//connection string for the mysql connection here 3306 is the port, cvedb is the schema, root is the username and "" is the password for mysql
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cvedb?autoReconnect=true&useSSL=false", "root", "p4ssword");
			
			//initializing connection statement
			Statement stmt = con.createStatement();
			
			//following query gets all the tables in the schema
			ResultSet rs1 = stmt.executeQuery(
					"SELECT table_name FROM information_schema.tables WHERE  table_schema='cvedb'");
			
			//storing the table names in the arraylist
			ArrayList<String> tables = new ArrayList<String>();
			while (rs1.next()) {

				//getting the first column which is the table name and inserting into arraylist
				String tablename = rs1.getString(1);
				
				tablename = "`" + tablename + "`";
				tables.add(tablename);
			}
			
			//now for each table we get description and name of the column

			for (int i = 0; i < tables.size(); i++) {
				ResultSet rs = stmt.executeQuery("select distinct *  from " + tables.get(i));

			
			while (rs.next()) {
					str = rs.getString(1)+"\t"+rs.getString(3);
					//checking if the module exists in the description
					if (str.toLowerCase().contains(find.toLowerCase())) {
						//add to arraylist
						data.add(str);
					}

				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		//return arraylist
		return data;
	}

	
}
