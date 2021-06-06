package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.*;

public class HelloWorld {

	public static void main(String[] args) {
		
		String data = null;
		Connection conn = null;
		ArrayList<String> listOfTitles = new ArrayList<String>();

		// 1. Read the JSON file
		try {
	      File myObj = new File("movies.json");
	      Scanner myReader = new Scanner(myObj);
	      
	      while (myReader.hasNextLine()) {
	    	  
	    	if (data == null) {
	    		data = "";
	    	}
	    	  
	    	data += myReader.nextLine();
	      }
	      
	      myReader.close();
	      
	    } catch (FileNotFoundException e) {
	    	
	      System.out.println("FILE NOT FOUND");
	      e.printStackTrace();
	      
	    }
		
		// 2. Verify if its correct by printing it
//		if (data != null) {
//			System.out.println(data);
//		}
		
		// 3. Parse it as JSON object
		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONArray("movies");
			
			for(int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				String title = obj.getString("title");
				System.out.println(title);
				listOfTitles.add(title);
			}
		
		} catch (Exception e) {
			System.out.println("Unable to read JSON");
			e.printStackTrace();
		}
		
		// 4. Insert into DB
		try {
			String url = "jdbc:mysql://localhost:3306/tamilboomi"; 
			conn = DriverManager.getConnection(url,"root","");
			
			for (int i = 0; i < listOfTitles.size(); i++) {
				Statement st = conn.createStatement(); 
				st.executeUpdate("INSERT INTO titles " + "VALUES ('"+ listOfTitles.get(i) + "')" );
			}

			conn.close(); 
			
		} catch (Exception e) {
			System.out.println("Unable to connect to Databse");
			e.printStackTrace();
		}

	}
}
