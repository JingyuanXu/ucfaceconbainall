package com.uscface.main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.*;
import java.util.List;


public class face2 {
    
    static JFrame frame = new JFrame();

    static JButton queryBtn = new JButton("Query");

    static String queryRqFile = "query.rq";

    static TextField fileTextField = new TextField();
    static JLabel queryLabel = new JLabel("Query:");
    static TextArea queryTextArea = new TextArea();
    static JLabel resultLabel = new JLabel("Result:");
    static JLabel addressLabel = new JLabel("file address:");
    static TextArea queryResultTextArea = new TextArea();
    static TextArea addressTextArea = new TextArea();
    
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
    
    public static void bindBtnAction() {
    	
        
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
              String address=  addressTextArea.getText();
           //     String commandStr = "./jena/bin/sparql --data=" + "/Users/xujing/Documents/workspace/csv2/test.rdf " + " --query=" + queryRqFile;
                String commandStr = "./jena/bin/sparql --data=" + address + " --query=" + queryRqFile;
                
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
    
    public static void main(String[] args) throws FileNotFoundException {
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
      
        addressLabel.setPreferredSize(new Dimension(80, 100));
        frame.add(addressLabel);
        addressTextArea.setPreferredSize(new Dimension(500, 50));
        frame.add(addressTextArea);
        
        queryLabel.setPreferredSize(new Dimension(80, 100));
        frame.add(queryLabel);
        
        queryTextArea.setPreferredSize(new Dimension(500, 100));
     //   queryResultTextArea.setFont(new Font("Consolas", Font.BOLD, 12));
        frame.add(queryTextArea);
        
       
        
        resultLabel.setPreferredSize(new Dimension(80, 100));
        frame.add(resultLabel);
        
        queryResultTextArea.setEditable(false);
        queryResultTextArea.setPreferredSize(new Dimension(500, 300));
        queryResultTextArea.setFont(new Font("Consolas", Font.BOLD, 12));
        frame.add(queryResultTextArea);
        
      //  frame.add(chooseFileBtn);
        
        frame.add(queryBtn);
        
        frame.setVisible(true);
        bindBtnAction();
    }
    
}
