package com.uscface.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class RdfWriter {

  public static void main(String[] args) throws IOException{
  
  //Introduction
  String inputFileName = "/Users/xujing/Documents/workspace/csv2/src/riotcmdx/workbook1.nt";
  Model model = ModelFactory.createDefaultModel();
  InputStream inputStream = FileManager.get().open( inputFileName );  
  model.read(inputStream, null, "N-TRIPLES") ;
  
  //Model write
  model.write(System.out);
  System.out.println();
  model.write(System.out, "RDF/XML-ABBREV");
  System.out.println();
  FileWriter writer = new FileWriter("/Users/xujing/Documents/workspace/csv2/src/riotcmdx/workbook1.rdf");
  model.write(writer);
  writer.close();

 // model.write(System.out, "N-TRIPLE");
}

}
