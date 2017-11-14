package com.uscface.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class testconn {
	public static void main(String[] a) throws Exception
	{
		mconncreateDataset();
	}
	public static void main1(String[] a)
		      throws Exception {
		   Class.forName("org.h2.Driver");
		   
		   Connection conn = DriverManager.
		       getConnection("jdbc:h2:mem:test", "sa", "");
		  // jdbc:h2:mem:
		 //  jdbc:h2:tcp://localhost/server~/dbname
		   //jdbc:h2:~/mem:test 
		   // add application code here
		   Statement stmt = conn.createStatement();
		   Boolean crea = stmt.execute("Create table test(ID int, Name char(50))"); 
		  int inser=stmt.executeUpdate("insert into test (ID, Name) values (1,'sarah')");
		   ResultSet  rs = stmt.executeQuery("SELECT * FROM TEST");   
		      while(rs.next()) {   
		       System.out.println(rs.getInt("ID")+","+rs.getString("NAME"));
		      }
		   conn.close();
		 }
	public static DefaultPieDataset mconncreateDataset() throws Exception
	{//requirement: total have two columns, first column is string, second column is number, 
		//when use sparql, write like this for special rangeS
		/*PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
			PREFIX vcard:<http://www.w3.org/2001/vcard-rdf/3.0#>
			SELECT ?class ?d ?z
			WHERE { ?class vcard:Homeless ?d. 
			              ?class vcard:Area ?z.
			Filter regex (str(?class), '2011')
			}*/
		DefaultPieDataset dataset=new DefaultPieDataset();
           Class.forName("org.h2.Driver"); 
		   Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
		   Statement stmt = conn.createStatement();
		   ResultSet  rscolumn = stmt.executeQuery("SELECT * FROM CSVREAD('./db/coutput.csv')"); 
		   ResultSetMetaData metacolumn=rscolumn.getMetaData();
		   String sqlt="";
		   for (int k=1;k<=metacolumn.getColumnCount();k++)
		   {
			   if (k!=metacolumn.getColumnCount())
			   {
			      sqlt+= metacolumn.getColumnLabel(k)+",";
			   }
			   else
			   {
				   sqlt+=metacolumn.getColumnLabel(k); 
			   }
		   }
		   System.out.print(sqlt);
		   ResultSet rs=stmt.executeQuery("select distinct "+sqlt+" from CSVREAD('./db/coutput.csv')");
		   ResultSetMetaData meta=rs.getMetaData();   
		   while(rs.next()) {	
		    	  for (int i = 1,j=meta.getColumnCount(); i <j; i++) {
		    		 //  ResultSet valuer=stmt.executeQuery("select count ('"+rs.getString(i+1)+"') from CSVREAD('./db/homeless2.csv')");
		    		 //  ResultSetMetaData valuem=valuer.getMetaData(); 
		    		   dataset.setValue(rs.getString(i), Integer.parseInt(rs.getString(i+1)));
						//System.out.println(rs.getString(i)+" : "+rs.getString(i+1));
		    		//   System.out.println(valuem.getColumnLabel(0)+":"+rs.getString(i+1)+":"+ meta.getColumnLabel(i+1));
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
