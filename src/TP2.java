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
	
    
    public static void main(String[] args) {
//    	ReadPharmacyOrder(args[0]);

//    	remove as necessary
    	String filename = new String("C:/Users/jonat/Documents/School/Data_Structures/A2/tests/exemple2.txt");
    	SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
    	SortedMap<Integer, MedDescriptor> medications = new TreeMap<>();
    	
        stockTree stock = new stockTree();
        int count = 0;
        
        // start parsing here 
        System.out.println("Reading from file "+filename+"\n");
        try {
            Path filePath = Paths.get(filename);
            Scanner scanner = new Scanner(filePath);
            
            while (scanner.hasNext()) {
            	String line = scanner.nextLine();
                if(line.contains("APPROV :")) {
            		line=scanner.nextLine();
                	while(line.equals(";") == false) {
                    	String[] pieces = line.split("\\s+");
//                    	System.out.println(pieces[0]);
//                    	System.out.println(pieces[1]);
//                    	System.out.println(pieces[2]);
                		line=scanner.nextLine();
//                	leaving this here for reference only
//                	MedDescriptor placeHolder = new MedDescriptor(Integer.parseInt(pieces[0].replace("Medicament", "")), Integer.parseInt(pieces[1]), formatter1.parse(pieces[2]));
//                	stock.addMedicament(placeHolder);
                		
                   MedDescriptor placeHolder = new MedDescriptor(Integer.parseInt(pieces[1]), formatter1.parse(pieces[2]));
                	medications.put(Integer.parseInt(pieces[0].replace("Medicament", "")),placeHolder);
//                	System.out.println(placeHolder);
                	
                	}
                	System.out.println("APPROV OK");
                }
                else if(line.contains("DATE")) {
                	Date currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(line.replace("DATE ", ""));
                	System.out.println(currentDate + " OK");
                }
                else if(line.contains("STOCK")) {
                	//print the stock
                	System.out.println("Stock is");
                }
                else if(line.contains("PRESCRIPTION")) {
                	line=scanner.nextLine();
                	while(line.equals(";") == false) {
                    	String[] pieces = line.split("\\s+");
//                    	System.out.println(pieces[0]);
//                    	System.out.println(pieces[1]);
//                    	System.out.println(pieces[2]);
                    	int medAmount = Integer.parseInt(pieces[1]) * Integer.parseInt(pieces[2]);
                		line=scanner.nextLine();
                	PrescriptionRequest placeHolder = new PrescriptionRequest(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);

                	System.out.println(placeHolder);
                	}
                	System.out.println("PRE OK");
                }
                else {
                	System.out.println("Unrecognized character");
                }
                

            }
            scanner.close();
        }
        catch(IOException | NumberFormatException | ParseException e){
            e.printStackTrace();
        }
    }
        
        
        
      
       
        	}
        

