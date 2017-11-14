package com.uscface.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class testSql {

	public static void main(String[] a)
		      throws Exception {
		   Class.forName("org.h2.Driver");
		   
		   Connection conn = DriverManager.
		       getConnection("jdbc:h2:mem:test", "sa", "");
		  // jdbc:h2:mem:
		 //  jdbc:h2:tcp://localhost/server~/dbname
		   //jdbc:h2:~/mem:test 
		   // add application code here
		   Statement stmt = conn.createStatement();
		 //  Boolean crea = stmt.execute("Create table test(ID int, Name char(50))"); 
		 // int inser=stmt.executeUpdate("insert into test (ID, Name) values (1,'sarah')");
		   ResultSet  rs = stmt.executeQuery("SELECT * FROM CSVREAD('./db/homeless2.csv')"); 
		   ResultSetMetaData meta=rs.getMetaData();
		      while(rs.next()) {	
		    	  for (int i = 0,j=meta.getColumnCount(); i <j; i++) {
						System.out.println(meta.getColumnLabel(i+1)+" : "+rs.getString(i+1));
					}
		      // System.out.println(rs.getInt("ID")+","+rs.getString("Name"));
		      }
		   conn.close();
		 }


}
