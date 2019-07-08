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
    	int prescriptionCount = 0;
        
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
                	
                	}
                	System.out.println("APPROV OK");
                }
                else if(line.contains("DATE")) {
                	Date currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(line.replace("DATE ", ""));
                	System.out.println(currentDate + " OK");
                }
                else if(line.contains("STOCK")) {
                	//print the stock
                	//not sure how to resolve currentDate well
//                	System.out.println("STOCK"+currentDate);
                	System.out.println("STOCK");
                	for(SortedMap.Entry<Integer, MedDescriptor> entry: medications.entrySet()){
                		System.out.println(entry.getKey() +"  "+ entry.getValue().getMedicamentAmount() + "  " + entry.getValue().getExpirationDate());
                	}

                }
                else if(line.contains("PRESCRIPTION")) {
                	prescriptionCount+=1;
                	System.out.println("PRESCRIPTION  " + prescriptionCount);
                	line=scanner.nextLine();
                	//there is here as a temp only //
                	String dummy = "2013-08-27";
                	Date currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(dummy.replace("DATE ", ""));
                	//remove what is above
                	while(line.equals(";") == false) {
                    	String[] pieces = line.split("\\s+");         
                    	int medAmount = Integer.parseInt(pieces[1]) * Integer.parseInt(pieces[2]);
                    	int ID = Integer.parseInt(pieces[0].replace("Medicament", ""));
                
                    	if(!medications.containsKey(ID)){
                    		System.out.println(line +"   COMMANDE");
                    		//leave this for reference if necessary
//                    		System.out.println(medications.get(Integer.parseInt(pieces[0])) + " "+ Integer.parseInt(pieces[1]) + " "+ Integer.parseInt(pieces[2]) + "COMMANDE");
                    	}
                		

                    	else if(medications.containsKey(ID) && currentDate.compareTo(medications.get(ID).getExpirationDate())<0){
                    		//check if enough 
                    		if(medAmount < medications.get(ID).getMedicamentAmount()) {
                    			//set new amount of meds
                    			int currentAmount = medications.get(ID).getMedicamentAmount();
                    			int newAmount = currentAmount - medAmount;
                    			medications.get(ID).setMedicamentAmount(newAmount);
                    			System.out.println(line +"   OK ");
                    		}
                    		else if(medAmount > medications.get(ID).getMedicamentAmount()) {
                    			System.out.println(line + "   COMMANDE");
                    			medications.remove(ID);
                    		}
                    		else {
                    			System.out.println("Another Error Occurred ");
                    		}
                    	}
                    	else if(medications.containsKey(ID) && currentDate.compareTo(medications.get(ID).getExpirationDate())>0){
                    		//order more
                    		System.out.println(line + "   COMMANDE");
                    		//remove node
                    		medications.remove(ID);
                    	}
                    	
                		line=scanner.nextLine();

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
        

