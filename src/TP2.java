/**
 * Jonathan Bhimani-Burrows & JP Letendre
 *
 * TP2 class for pharmacy pill management
 **/

import java.nio.file.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class TP2 {
	static SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
    public static List ReadPharmacyOrder(String filename) {
        /*
        Function to read order for pharmacy; takes in the filename to read from as argument
         */

        List<Object> data = new ArrayList<>();
        System.out.println("Reading from file "+filename+"\n");


        try {
            Path filePath = Paths.get(filename);
            Scanner scanner = new Scanner(filePath);
            
            while (scanner.hasNext()) {
            	String line = scanner.nextLine();
                if(line.contains("APPROV :")) {
                	data.add(line);
            		line=scanner.nextLine();
                	while(line.equals(";") == false) {
                    	String[] pieces = line.split("\\s+");
//                    	System.out.println(pieces[0]);
//                    	System.out.println(pieces[1]);
//                    	System.out.println(pieces[2]);
                		line=scanner.nextLine();
                	MedDescriptor placeHolder = new MedDescriptor(Integer.parseInt(pieces[0].replace("Medicament", "")), Integer.parseInt(pieces[1]), formatter1.parse(pieces[2]));
                	data.add(placeHolder);
                	System.out.println(placeHolder);
                	}
                	System.out.println("APPROV OK");
                }
                else if(line.contains("DATE")) {
                	Date currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(line.replace("DATE ", ""));
                	data.add(currentDate);
                	System.out.println(currentDate + " OK");
                }
                else if(line.contains("STOCK")) {
                	data.add("STOCK");
                	System.out.println("Stock is");
                }
                else if(line.contains("PRESCRIPTION")) {
                	data.add(line);
                	line=scanner.nextLine();
                	while(line.equals(";") == false) {
                    	String[] pieces = line.split("\\s+");
//                    	System.out.println(pieces[0]);
//                    	System.out.println(pieces[1]);
//                    	System.out.println(pieces[2]);
                    	int medAmount = Integer.parseInt(pieces[1]) * Integer.parseInt(pieces[2]);
                		line=scanner.nextLine();
                	PrescriptionRequest placeHolder = new PrescriptionRequest(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                	data.add(placeHolder);
                	System.out.println(placeHolder);
                	}
                	System.out.println("PRE OK");
                }
                else {
                	System.out.println("Unrecognized character");
                }
                

            }
            scanner.close();
            System.out.println("Finished parsing file");
        }
        catch(IOException | NumberFormatException | ParseException e){
            e.printStackTrace();
        }
        return data;
    }
    
//    public static void PrintStock() {
//    	
//    }
    


    public static void main(String[] args) {
    	List<Object> parseData = new ArrayList<>(); 
//    	ReadPharmacyOrder(args[0]);
    	
//    	remove as necessary
    	String file = new String("C:/Users/jonat/Documents/School/Data_Structures/A2/tests/exemple2.txt");
    	parseData = ReadPharmacyOrder(file);
    	
        stockTree stock = new stockTree();
        for (int i = 0; i < parseData.size(); i++) {
        	System.out.println(parseData.get(i)); 
        	}
        
//        stock.add(5);
//        System.out.println(stock.x);
//        isValidDate("2004-11-30");
        
    }

}