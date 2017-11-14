package com.uscface.main;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

import com.uscface.main.CsvReader;
/**
 * CsvWrite.java
 * 功能:测试H2提供cvs数据写入文件
 * @author Jing
 */
public class testWriteCsv {
	static String address="./db/coutput.csv";
	static String prefix="http://testcase/";
	 
	
	 public static void findcolumn() throws SQLException
	    {
		 SimpleResultSet srs=new SimpleResultSet();
	   	 ArrayList<String[]> rows = null;
	     try {
	         rows = CsvReader.read(address);
	     } catch (FileNotFoundException e2) {
	        // JOptionPane.showMessageDialog(frame, "please enter your findLiner csv path");
	       //  System.exit(1);
	     } 
	     String[] colum = rows.get(0);
	     int cou=0;
	     for (int i = 0; i < rows.size(); ++i) {
	    	 colum = rows.get(i);
	    	 String [] a=colum;
	    	 for(int c=1;c<colum.length;c++)
	    	 {
	    		 if(cou==0){
	    			 srs.addColumn(colum[c], Types.VARCHAR, 255, 0); 
	    			 System.out.println(colum[c]);
	    		 }
	    		
	    	 }
	    	 cou++;
	    	//System.out.println("a:"+a[i]);
	       for (int j = 0; j < colum.length; j++) {
	    	
	    	// if(a[j].toString().equals("2f")){
	    		if(a[j].toString().contains(prefix)){   
	    			String[] aa = a[j].toString().split("#"); 
	    			
	    			//System.out.println(aa[1]);
	    			for(int z=1;z<colum.length;z++)
	    			{
	    				//String[] aaa;
	    				srs.addRow(a[z],a[z+1]);
	    				//System.out.println(z+":"+a[z]+":"+a[z+1]);
	    				
	    				break;
	    				
	    			}
	    			
	    		//	return aa[1];
	    		//	lineclassifer=aa[1];
	    	  // break;
	    	   }
	       }
	     
	     //  break;
	   }
	     try {
				new Csv().write("./db/test.csv", srs,null);
			} catch (SQLException e) {
				e.printStackTrace();}
	     // System.out.println(srs.getRow());
	    }
/**PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX vcard:<http://www.w3.org/2001/vcard-rdf/3.0#>
SELECT *
WHERE { ?class vcard:Homeless ?d. 
              ?class vcard:Area ?z.
}**/
	public void write(){
		SimpleResultSet srs=new SimpleResultSet();
		srs.addColumn("ID", Types.INTEGER, 255, 0);
		srs.addColumn("NAME", Types.VARCHAR, 255, 0);
		srs.addRow(1,"Jing");
		srs.addRow(2,"Jingyuan");
		try {
			new Csv().write("./db/test.csv", srs,null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		new testWriteCsv();
		testWriteCsv.findcolumn();
	}

}
