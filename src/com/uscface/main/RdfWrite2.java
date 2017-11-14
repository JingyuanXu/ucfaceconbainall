package com.uscface.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property; 
import org.apache.jena.rdf.model.Resource;  
import org.apache.jena.vocabulary.VCARD;  
  
public class RdfWrite2 {  
 //   static String personURI    = "http://somewhere/JohnSmith";  
 //   static String fullName     = "John Smith";  
  
    public static void write(String filename, ArrayList<String[]> rows) throws IOException{  
    	//get first line, each field's name
    	String[] fields = rows.get(0);
        for (int i = 0; i < fields.length; ++i) {
            fields[i] = fields[i].replace("\"", "");//NO. ,TIME, AIRPASSENGER
        }
        // create an empty Model  
        Model model = ModelFactory.createDefaultModel();  
  
        // create the resource 
        for (int i = 1; i < rows.size(); ++i) {//loop each row
        //  String resourceURI = "http://testcase/" + rows.get(i)[0].replace("\"", "");
        	String resourceURI = "http://testcase/" +rows.get(i)[0].replace("\"", "")+"/"+i+"#"+ rows.get(i)[0].replace("\"", "");
        Resource johnSmith = model.createResource(resourceURI);  
  
        // add the property  
        for (int j = 1; j < rows.get(i).length; ++j) {//in that row
        	String vcards=VCARD.getURI();
            Property property = model.createProperty(vcards+ fields[j]);
            johnSmith.addProperty(property, rows.get(i)[j]);
        }
        
    }
        FileWriter writer = new FileWriter(filename);
        model.write(writer);
        writer.close();
    }  
}  
