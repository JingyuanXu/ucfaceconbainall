package com.uscface.main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.*;
import java.util.*;
import java.util.List;


public class face {
    
    static JFrame frame = new JFrame();

    static JButton queryBtn = new JButton("Query");
    static JButton statsBtn=new JButton("Stats");
    static JButton convertBtn = new JButton("Convert");
    static String queryRqFile = "query.rq";

    static JLabel separateLabel=new JLabel("------------Below is for querying an exist RDF file-------------");
    static TextField fileTextField = new TextField();
    static TextField outputField=new TextField();
    static JLabel inputLabel = new JLabel("Input");
    static JLabel outputLabel = new JLabel("Output");
    static JLabel queryLabel = new JLabel("Query:");
    static TextArea queryTextArea = new TextArea();
    static JLabel resultLabel = new JLabel("Result:");
    static JLabel addressLabel = new JLabel("RDF File address:");
    static TextArea queryResultTextArea = new TextArea();
    static TextField addressTextField = new TextField();
    
    public static List<String> runShell(String cmd) throws Exception {
        List<String> strList = new ArrayList<String>();
        Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd}, null, null);
        InputStreamReader reader = new InputStreamReader(p.getInputStream());
        LineNumberReader input = new LineNumberReader(reader);
        String line;
        p.waitFor();
        while ((line = input.readLine()) != null) {
            strList.add(line);
        }
        p.destroy();
        return strList;
    }
    
    public static void ConvertCsv2Rdf(String csvFile, String rdfFile) 
            throws IOException {
        ArrayList<String[]> rows = CsvReader.read(csvFile);
        RdfWrite2.write(rdfFile, rows);
    }
  
    
    public static void bindBtnAction() {
    	
    	statsBtn.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event){
             try {
				SubFrame();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }});
    
        convertBtn.addActionListener(new ActionListener()
        {
        	 public void actionPerformed(ActionEvent e) {
        		String csvFile= fileTextField.getText();
        		String rdfFile=outputField.getText();
        		 try {
					ConvertCsv2Rdf(csvFile, rdfFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(frame,"convert failed");
				}
        		 JOptionPane.showMessageDialog(frame,"convert done");
        	 }
        });
        queryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
     
                File file = new File(queryRqFile);
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(queryTextArea.getText());
                    fileWriter.close();
                    FileReader fr = new FileReader(queryRqFile);
                    int ch = 0;  
                    while((ch = fr.read())!=-1 ){  
                        System.out.print( (char)ch );  
                    } 
                    fr.close();
                } catch (IOException e2) {
                    JOptionPane.showMessageDialog(frame, "fail to do sparql query");
                    System.exit(1);
                }
              //SELECT ?x 
               //	  WHERE {?x  <http://www.w3.org/2001/vcard-rdf/3.0#AirPassengers> "199" }
              String address=  addressTextField.getText();
           //     String commandStr = "./jena/bin/sparql --data=" + "/Users/xujing/Documents/workspace/csv2/test.rdf " + " --query=" + queryRqFile;
             String jenaads="./jena";
              String commandStr = jenaads+"/bin/sparql --data=" + address + " --query=" + queryRqFile;
             //output csv file
              FileInputStream in1;
				try {
					in1 = new FileInputStream(new File(address));
					 Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, null);
		                model.read(in1, null);
		                in1.close();
		                Query query2 = QueryFactory.create(queryTextArea.getText());
		                QueryExecution qe = QueryExecutionFactory.create(query2, model);
		                ResultSet results = qe.execSelect();
		                PrintStream out = new PrintStream(new FileOutputStream("./db/coutput.csv")); 
		                System.setOut(out); 
		                ResultSetFormatter.outputAsCSV(out, results);
		                qe.close();
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                try {
                    String text = "";
                    List<String> result = runShell(commandStr);
                    for (int i = 0; i < result.size(); ++i) {
//                        text += result.get(i).replace("\t", "    ") + "\n";
                        text += result.get(i) + "\n";
                    }
                    queryResultTextArea.setText(text);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(frame, "fail to do sparql query");
                }
            }
        });
    }
    
 protected static void SubFrame() throws Exception {
		// TODO Auto-generated method stub
	 JFrame frame1 = new JFrame();
         /*frame1.setTitle("Statstic");
         frame1.setSize(500, 600);
         frame1.setResizable(false);
         frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         frame1.setLocationByPlatform(true);
         frame1.setVisible(true);
         
         frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    */
	 JFreeChartTest2 chart=new JFreeChartTest2("Statistic");
     chart.pack();//以合适的大小显示
     chart.setVisible(true);
	}



	public static void main(String[] args) throws FileNotFoundException {
        frame.setSize(555, 800);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        inputLabel.setSize(new Dimension(500,12));
        frame.add(inputLabel);
        fileTextField.setPreferredSize(new Dimension(500,40));
        frame.add(fileTextField);
        outputLabel.setSize(new Dimension(500,12));
        frame.add(outputLabel);
        outputField.setPreferredSize(new Dimension(500,40));
        frame.add(outputField);
        frame.add(convertBtn);
        separateLabel.setSize(new Dimension(500,12));
        frame.add(separateLabel);
        
        
        addressLabel.setPreferredSize(new Dimension(120, 12));
        frame.add(addressLabel);
        addressTextField.setPreferredSize(new Dimension(500, 40));
        frame.add(addressTextField);
        
        queryLabel.setPreferredSize(new Dimension(80, 12));
        frame.add(queryLabel);
        
        queryTextArea.setPreferredSize(new Dimension(500, 100));
        frame.add(queryTextArea);
        
       
        
        resultLabel.setPreferredSize(new Dimension(80, 12));
        frame.add(resultLabel);
        
        queryResultTextArea.setEditable(false);
        queryResultTextArea.setPreferredSize(new Dimension(500, 300));
        queryResultTextArea.setFont(new Font("Consolas", Font.BOLD, 12));
        frame.add(queryResultTextArea);
        
        frame.add(queryBtn);
        frame.add(statsBtn);
        frame.setVisible(true);
        bindBtnAction();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
