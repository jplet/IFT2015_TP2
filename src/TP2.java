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
    public static String ReadPharmacyOrder(String filename) {
        /*
        Function to read order for pharmacy; takes in the filename to read from as argument
         */

        List<Object> data = new ArrayList<>();
        System.out.println("Reading from file "+filename+"\n");


        try {
            Path filePath = Paths.get(filename);
            Scanner scanner = new Scanner(filePath);
            
            while (scanner.hasNext()) {
            	String line = scanner.next();
                if(line.equals("APPROV")) {
                	
                	while(!scanner.next().equals(';')) {             	
                	line = scanner.next();
                	String line_med = line.split(" ")[0].replace("Medicament", "");
                	System.out.println(line_med);
                	line = scanner.next();
                	String line_quantity = line;
                	System.out.println(line_quantity);
                	line = scanner.next();
                	String line_exp = line;
                	System.out.println(line_exp);
                	MedDescriptor hi = new MedDescriptor(Integer.parseInt(line_med), Integer.parseInt(line_quantity), formatter1.parse(line_exp));
                	data.add(hi);
                	System.out.println(hi);
                	}
                	System.out.println("APPROV OK");
                }
                else if(line.equals("DATE")) {
                	Date currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(scanner.next());
                	//cant add date or meddescriptor 
                	//how do I dynamically assign names, or do I have to instantiate the classes at all?
                	data.add(currentDate);
                	System.out.println(currentDate + " OK");
                }
                else if(line.equals("STOCK")) {
                	System.out.println("Stock is");
                }
                else if(line.equals("PRESCRIPTION")) {
                	System.out.println("PRE OK");
                }
                else {
                	System.out.println("Unrecognized character");
                }
//                System.out.println(line);

            }
            scanner.close();
        }
        catch(IOException | NumberFormatException | ParseException e){
            e.printStackTrace();
        }
        return "Hi";
    }
    
//    public static void PrintStock() {
//    	
//    }
    


    public static void main(String[] args) {
//    	ReadPharmacyOrder(args[0]);
    	
//    	remove as necessary
    	String file = new String("C:/Users/jonat/Documents/School/Data_Structures/A2/tests/exemple1.txt");
    	ReadPharmacyOrder(file);
//        stockTree stock = new stockTree();
//        stock.add(5);
//        System.out.println(stock.x);
//        isValidDate("2004-11-30");
        
    }

}