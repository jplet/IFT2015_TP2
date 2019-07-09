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
    	String filename = new String("D:/JP/Documents/Maitrise/Stage_2019/IFT2015 Data Structure/IFT2015/tests/exemple2.txt");
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

                    // TODO: Add to ID's medTree if ID already there
                    if (medications.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                        medications.get(Integer.parseInt(pieces[0].replace("Medicament", ""))).AddMedicament(Integer.parseInt(pieces[1]), formatter1.parse(pieces[2]));
                    }
                    else {
                        MedDescriptor placeHolder = new MedDescriptor();
                        placeHolder.AddMedicament(Integer.parseInt(pieces[1]), formatter1.parse(pieces[2]));
                        medications.put(Integer.parseInt(pieces[0].replace("Medicament", "")), placeHolder);
                    }
                	}
                	System.out.println("APPROV OK");
                }
                else if(line.contains("DATE")) {
                	currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(line.replace("DATE ", ""));
                	if (commands.isEmpty()) {
                        System.out.println(formatter1.format(currentDate) + " OK");
                    }
                	else if (!commands.isEmpty()) {
                        System.out.println(formatter1.format(currentDate) + " COMMANDES :");
                        for(SortedMap.Entry<Integer, Integer> entry: commands.entrySet()){
                            System.out.println("Medicament"+entry.getKey() +"  "+ entry.getValue());
                        }
                        commands.clear();
                    }
                }
                else if(line.contains("STOCK")) {
                	//print the stock
                	//not sure how to resolve currentDate well
//                	System.out.println("STOCK"+currentDate);
                	System.out.println("STOCK " + formatter1.format(currentDate));
                	for(SortedMap.Entry<Integer, MedDescriptor> entry: medications.entrySet()) {
                        for (SortedMap.Entry<Date, Integer> entry2 : entry.getValue().medTree.entrySet()) {
                            System.out.println("Medicament" + entry.getKey() + "  " + entry2.getValue() + "  " + formatter1.format(entry2.getKey()));
                        }
                    }
                }
                else if(line.contains("PRESCRIPTION")) {
                	prescriptionCount+=1;
                	System.out.println("PRESCRIPTION  " + prescriptionCount);
                	line=scanner.nextLine();
                	//there is here as a temp only //
//                	String dummy = "2013-08-27";
//                	Date currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(dummy.replace("DATE ", ""));
                	//remove what is above
                	while(!line.equals(";")) {
                    	String[] pieces = line.split("\\s+");
                    	int medAmount = Integer.parseInt(pieces[1]) * Integer.parseInt(pieces[2]);
                    	int ID = Integer.parseInt(pieces[0].replace("Medicament", ""));

                    	// CHECK IF ID IS AVAILABLE IN STOCK
                    	if(!medications.containsKey(ID)){
                    		System.out.println(line +"   COMMANDE");
                    		if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                    		    Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
                    		    Integer newCommand = prevCommand + medAmount;
                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
                                line=scanner.nextLine();
                                continue;
                            }
                    		else {
                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                                line=scanner.nextLine();
                                continue;
                            }
                    		// If Med is not available
                    		//leave this for reference if necessary
//                    		System.out.println(medications.get(Integer.parseInt(pieces[0])) + " "+ Integer.parseInt(pieces[1]) + " "+ Integer.parseInt(pieces[2]) + "COMMANDE");
                    	}
                    	// CHECK IF AVAILABLE STOCK IS NOT EXPIRED OR WONT BE EXPIRED
                        // first "else if" checks if there's any ceiling key over our currentDate
                    	else if(medications.containsKey(ID) && medications.get(ID).medTree.ceilingKey(currentDate) == null){
                            //order more
                            System.out.println(line + "   COMMANDE");
                            //remove node
                            medications.remove(ID);
                            if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                                Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
                                Integer newCommand = prevCommand + medAmount;
                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
                                line=scanner.nextLine();
                                continue;
                            }
                            else {
                                commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                                line=scanner.nextLine();
                                continue;
                            }
                        }
                    	// because of the previous else if, we know for sure there's at least one key
                        Integer size = medications.get(ID).medTree.size();
                    	boolean prescriptDone = false;
                        for (Date entry : medications.get(ID).medTree.keySet()){
                            // technically this condition is verified in previous else if
//                            if(medications.containsKey(ID) && currentDate.compareTo(entry.getKey())<0){
                            // check if end-of-treatment pills are still good

                            // size is a safety variable to avoid getting NullPointerException when going through our pointers list
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
                                    prescriptDone = true;
                                }
                                else if(medAmount > medications.get(ID).medTree.get(entry)) {
                                    System.out.println(line + "   COMMANDE");
                                    // We'll check that later
                                    // medications.remove(ID);
                                    if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", ""))) & size == 0) {
                                        Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
                                        Integer newCommand = prevCommand + medAmount;
                                        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
//                                        if (size == 0 & )
                                    }
                                    else {
                                        commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                                    }
                                }
                                else {
                                    System.out.println("Another Error Occurred ");
                                }
                            }

                            if (size == 0 & !prescriptDone){
                                System.out.println(line + "   COMMANDE");
                                if (commands.containsKey(Integer.parseInt(pieces[0].replace("Medicament", "")))) {
                                    Integer prevCommand = commands.get(Integer.parseInt(pieces[0].replace("Medicament", "")));
                                    Integer newCommand = prevCommand + medAmount;
                                    commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), newCommand);
                                }
                                else {
                                    commands.put(Integer.parseInt(pieces[0].replace("Medicament", "")), medAmount);
                                }
                                break;
                            } else if (prescriptDone){
                                break;
                            }

//                            }
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
