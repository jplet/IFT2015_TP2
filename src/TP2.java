/**
 * Jonathan Bhimani-Burrows & JP Letendre
 *
 * TP2 class for pharmacy pill management
 **/

import java.nio.file.*;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TreeMap;

public class TP2 {
	
    
    public static void main(String[] args) {
//    	ReadPharmacyOrder(args[0]);

//    	remove as necessary
    	String filename = new String("D:/JP/Documents/Maitrise/Stage_2019/IFT2015 Data Structure/IFT2015/tests/exemple3.txt");
    	SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
    	SortedMap<Integer, MedDescriptor> medications = new TreeMap<>();
    	int prescriptionCount = 0;
    	SortedMap<Integer, Integer> commands = new TreeMap<>();

        // start parsing here 
        System.out.println("Reading from file "+filename+"\n");
        
        try {
            Path filePath = Paths.get(filename);
            Scanner scanner = new Scanner(filePath);
            Date currentDate = new Date();
            while (scanner.hasNext()) {
            	
            	String line = scanner.nextLine();

            	// APPROVISIONNEMENT command
                if(line.contains("APPROV :")) {
            		line=scanner.nextLine();
                	while(line.equals(";") == false) {
                    	String[] pieces = line.split("\\s+");
                		line=scanner.nextLine();

                    // We have already have the medicamentID
                    if (medications.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                        medications.get(Integer.parseInt(pieces[0].replace("Medicament", ""))).AddMedicament(Integer.parseInt(pieces[1]), formatter1.parse(pieces[2]));
                    }

                    // We don't have the medicamentID
                    else {
                        MedDescriptor placeHolder = new MedDescriptor();
                        placeHolder.AddMedicament(Integer.parseInt(pieces[1]), formatter1.parse(pieces[2]));
                        medications.put(Integer.parseInt(pieces[0].replace("Medicament", "")), placeHolder);
                    }
                	}
                	System.out.println("APPROV OK");
                }

                // DATE command
                else if(line.contains("DATE")) {
                	currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(line.replace("DATE ", ""));
                	if (commands.isEmpty()) {
                        System.out.println(formatter1.format(currentDate) + " OK");
                    }
                	else if (!commands.isEmpty()) {
                	    // TODO: Remove expired content
                        System.out.println(formatter1.format(currentDate) + " COMMANDES :");
                        for(SortedMap.Entry<Integer, Integer> entry: commands.entrySet()){
                            System.out.println("Medicament"+entry.getKey() +"  "+ entry.getValue());
                        }
                        commands.clear();
                    }
                }

                // STOCK command
                else if(line.contains("STOCK")) {
                	System.out.println("STOCK " + formatter1.format(currentDate));
                	for(SortedMap.Entry<Integer, MedDescriptor> entry: medications.entrySet()) {
                        for (SortedMap.Entry<Date, Integer> entry2 : entry.getValue().medTree.entrySet()) {
                            System.out.println("Medicament" + entry.getKey() + "  " + entry2.getValue() + "  " + formatter1.format(entry2.getKey()));
                        }
                    }
                }

                // PRESCRIPTION command
                else if(line.contains("PRESCRIPTION")) {

                	prescriptionCount+=1;
                	System.out.println("PRESCRIPTION  " + prescriptionCount);
                	line=scanner.nextLine();

                	// While we're in the PRESCRIPTION section, with delimiter ';'
                	while(!line.equals(";")) {
                    	String[] pieces = line.split("\\s+");
                    	int medAmount = Integer.parseInt(pieces[1]) * Integer.parseInt(pieces[2]);
                    	int ID = Integer.parseInt(pieces[0].replace("Medicament", ""));

                    	// We check if ID is available in stock, if not we order it
                    	if(!medications.containsKey(ID)){
                    		System.out.println(line +"   COMMANDE");

                    		// If medicament already ordered, just add to the current value
                    		if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                    		    Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
                    		    Integer newCommand = prevCommand + medAmount;
                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
                                line=scanner.nextLine();
                                continue;
                            }

                    		// If medicament not in the order, instantiate the node in the tree
                    		else {
                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                                line=scanner.nextLine();
                                continue;
                            }
                    	}

                    	// We check if any of the expirationDates for a certain ID is even over our currentDate
                        // If not (ceilingKey returns null), we command the medicament
                    	else if(medications.containsKey(ID) && medications.get(ID).medTree.ceilingKey(currentDate) == null){
                    	    System.out.println(line + "   COMMANDE");

                    	    // if medicament already ordered
                            if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                                Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
                                Integer newCommand = prevCommand + medAmount;
                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
                                line=scanner.nextLine();
                                continue;
                            }

                            // if medicament not ordered
                            else {
                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                                line=scanner.nextLine();
                                continue;
                            }
                        }
                    	// If we get here, we know we have the medicamentID AND expirationDates over our currentDate
                        // We know check gradually (with for loop) if our expDate - medAmount pairs are sufficient for
                        // the prescription
                        else {
                            Integer size = medications.get(ID).medTree.size();

                            // We loop through all our expirationDates available
                            for (Date entry : medications.get(ID).medTree.keySet()){
                                size -= 1;
                                Calendar endOfTreatmentCal = Calendar.getInstance();
                                endOfTreatmentCal.setTime(currentDate);
                                endOfTreatmentCal.add(Calendar.DAY_OF_MONTH, medAmount);
                                // Date after adding the days to the given date
                                Date endOfTreatmentDate = endOfTreatmentCal.getTime();
                                Date expirationDate = entry;
                                // Check if current expirationDate is smaller than endOfTreatmentDate
                                if(endOfTreatmentDate.compareTo(expirationDate)>0) {}
                                else if(endOfTreatmentDate.compareTo(expirationDate)<0){
                                    // check if enough
                                    // GOLDEN CONDITION :: if we reach it we're done searching through expiration dates
                                    if(medAmount < medications.get(ID).medTree.get(entry)) {
                                        // set new amount of meds
                                        int currentAmount = medications.get(ID).medTree.get(entry);
                                        int newAmount = currentAmount - medAmount;
                                        medications.get(ID).medTree.put(entry, newAmount);
                                        System.out.println(line +"   OK ");
                                        System.out.println("GREEN1");
                                        break;
                                    }
                                    else if(medAmount > medications.get(ID).medTree.get(entry)) {

                                        //
                                        if (size == 0){
                                            System.out.println(line + "   COMMANDE");
                                            System.out.println("GREEN2");
                                            // We'll check that later
                                            // medications.remove(ID);
                                            if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                                                Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
                                                Integer newCommand = prevCommand + medAmount;
                                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
                                                break;
                                            }
                                            else {
                                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                                                break;
                                            }
                                        }
                                        else {

                                        }
                                    }
                                    else {
                                        System.out.println("Another Error Occurred ");
                                    }
                                }

                                if (size == 0){ // & !prescriptDone){
                                    System.out.println(line + "   COMMANDE");
                                    System.out.println("GREEN-FINAL");
                                    if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                                        Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
                                        Integer newCommand = prevCommand + medAmount;
                                        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
                                    }
                                    else {
                                        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                                    }
                                    break;
                                }

                                else { //if (prescriptDone){
                                }
                            }
                        }


//                    	else if(medications.containsKey(ID) && currentDate.compareTo(medications.get(ID).getExpirationDate())>0){
//                    		//order more
//                    		System.out.println(line + "   COMMANDE");
//                    		//remove node
//                    		medications.remove(ID);
//                            if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
//                                Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
//                                Integer newCommand = prevCommand + medAmount;
//                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
//                            }
//                            else {
//                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
//                            }
//                        }
                    	
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
        catch(IOException | NumberFormatException | ParseException | ClassCastException | NullPointerException e){
            e.printStackTrace();
        }
    }

       
        	}
        
//        else if(medications.containsKey(ID) && currentDate.compareTo(medications.get(ID).medTree.ceilingKey(currentDate))<0){
//        // check if end-of-treatment pills are still good
//        Calendar endOfTreatmentCal = Calendar.getInstance();
//        endOfTreatmentCal.setTime(currentDate);
//        endOfTreatmentCal.add(Calendar.DAY_OF_MONTH, medAmount);
//        // Date after adding the days to the given date
//        Date endOfTreatmentDate = endOfTreatmentCal.getTime();
//        if(endOfTreatmentDate.compareTo(medications.get(ID).medTree.ceilingKey(endOfTreatmentDate))>0) {
//        System.out.println(line + "   COMMANDE");
//        if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
//        Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
//        Integer newCommand = prevCommand + medAmount;
//        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
//        }
//        else {
//        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
//        }
//        }
//        // check if enough
//        if(medAmount < medications.get(ID).getMedicamentAmount()) {
//        // set new amount of meds
//        int currentAmount = medications.get(ID).getMedicamentAmount();
//        int newAmount = currentAmount - medAmount;
//        medications.get(ID).setMedicamentAmount(newAmount);
//        System.out.println(line +"   OK ");
//        }
//        else if(medAmount > medications.get(ID).getMedicamentAmount()) {
//        System.out.println(line + "   COMMANDE");
//        medications.remove(ID);
//        if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
//        Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
//        Integer newCommand = prevCommand + medAmount;
//        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
//        }
//        else {
//        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
//        }
//        }
//        else {
//        System.out.println("Another Error Occurred ");
//        }
//        }
//        else if(medications.containsKey(ID) && currentDate.compareTo(medications.get(ID).getExpirationDate())>0){
//        //order more
//        System.out.println(line + "   COMMANDE");
//        //remove node
//        medications.remove(ID);
//        if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
//        Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
//        Integer newCommand = prevCommand + medAmount;
//        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
//        }
//        else {
//        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
//        }
//        }
//
//        line=scanner.nextLine();
//
//        }
//        System.out.println("PRE OK");
//        }
//        else {
//        System.out.println("Unrecognized character");
//        }
//
//
//
//        }
//        scanner.close();
//        }
//        catch(IOException | NumberFormatException | ParseException | ClassCastException | NullPointerException e){
//        e.printStackTrace();
//        }
//        }
//
//
//        }
