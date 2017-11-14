package com.uscface.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
public class Memoryconn {

	public CategoryDataset mconncreateDataset() throws Exception
	{
		  DefaultCategoryDataset dataset=new DefaultCategoryDataset();
           Class.forName("org.h2.Driver"); 
		   Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
		   Statement stmt = conn.createStatement();
		   ResultSet  rscolumn = stmt.executeQuery("SELECT * FROM CSVREAD('./db/coutput.csv')"); 
		   ResultSetMetaData metacolumn=rscolumn.getMetaData();
		   String sqlt="";
		   for (int k=0;k<metacolumn.getColumnCount();k++)
		   {
			   if (k!=0)
			   {
			      sqlt+=metacolumn.getColumnLabel(k)+", ";
			   }
			   else
			   {
				   sqlt+=metacolumn.getColumnLabel(k); 
			   }
		   }
		   ResultSet rs=stmt.executeQuery("select distinct "+sqlt+" from CSVREAD('./db/coutput.csv')");
		   ResultSetMetaData meta=rs.getMetaData();   
		   while(rs.next()) {	
		    	  for (int i = 0,j=meta.getColumnCount(); i <j; i++) {
		    		   ResultSet value=stmt.executeQuery("select count ('"+rs.getString(i+1)+"') from CSVREAD('./db/coutput.csv')");
		    		 
		    		   dataset.setValue((Number) value, rs.getString(i+1), meta.getColumnLabel(i+1));
					//	System.out.println(meta.getColumnLabel(i+1)+" : "+rs.getString(i+1));
		    	  }
		      }
	       // dataset.setValue(10,"a","管理人员");
	      //  dataset.setValue(20,"b","市场人员");
	      //  dataset.setValue(40,"c","开发人员");
	      //  dataset.setValue(15,"d","其他人员");
	        
		   conn.close();
		   return dataset;
		   
	}
}
