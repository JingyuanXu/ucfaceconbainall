package com.uscface.main;



import java.util.*;
import java.io.*;

public class CsvReader {

    public static ArrayList<String[]> read(String fileName) 
            throws FileNotFoundException {
        ArrayList<String[]> rows = new ArrayList<String[]>();
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            String[] fields = line.split(",");
            rows.add(fields);
        }
        scanner.close();
        return rows;
    }
    
}
