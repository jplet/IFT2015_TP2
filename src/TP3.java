/**
 * Jonathan Bhimani-Burrows & JP Letendre
 *
 * TP3 class for ARM tree
 **/

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class TP3 {

    public static Carte parseInput(String filename) {

        Carte cityMap = new Carte();
        try {
            Path filePath = Paths.get(filename);
            Scanner scanner = new Scanner(filePath);
            if (scanner.hasNext()) {
                String line = scanner.nextLine();
                line = line.replaceAll("\\s+", "");

                // Parsing the site names
                while (!line.equals("---")) {
                    Site site = new Site(line);
                    cityMap.addSite(site);
                    line = scanner.nextLine();
                    line = line.replaceAll("\\s+", "");
                }
                line = scanner.nextLine();

                while (!line.equals("---")) {
                    String[] pieces = line.split(":");
                    String streetName = pieces[0].replaceAll("\\s+", "");
                    String[] street_infos = pieces[1].split("\\s+");
                    Street street1 = new Street(streetName, street_infos[1], street_infos[2], Integer.parseInt(street_infos[3]));
                    cityMap.addStreet(street1);
                    // Since the graph is non-oriented, for every street I add the street to it's origin and destination street list
                    Street street2 = new Street(streetName, street_infos[2], street_infos[1], Integer.parseInt(street_infos[3]));
                    cityMap.addStreet(street2);
                    line = scanner.nextLine();

                }
                System.out.println("Parsing over.");
            }
        }

        catch(IOException | NumberFormatException | ClassCastException | NullPointerException e){
            e.printStackTrace();
        }

        return cityMap;
    }

    public static void parseOutput(Set<Street> arm, Set<String> places, String filename){
        try {

            FileWriter outputFile = new FileWriter(filename);
            for(String place : places) {
                outputFile.write(place + "\n");
            }
            int final_cost = 0;
            for(Street street: arm){
                if (street.getBounds().get(0).matches("[sn]([0-9][0-9]|[0-9])")){ // If site string starts with a letter
                    if(Integer.parseInt(street.getBounds().get(0).substring(1)) > Integer.parseInt(street.getBounds().get(1).substring(1))){
                        outputFile.write(street.name + '\t' + street.getBounds().get(1) + '\t' + street.getBounds().get(0) + '\t' + street.getCost() + '\n');
                    }
                    else{
                        outputFile.write(street.name + '\t' + street.getBounds().get(0) + '\t' + street.getBounds().get(1) + '\t' + street.getCost() + '\n');
                    }
                final_cost += street.getCost();
                }
                else{ // if site doesn't start with letter
                    if(street.getBounds().get(0).compareTo(street.getBounds().get(1))>0){
                    outputFile.write(street.name + '\t' + street.getBounds().get(1) + '\t' + street.getBounds().get(0) + '\t' + street.getCost() + '\n');
                }
                else{
                    outputFile.write(street.name + '\t' + street.getBounds().get(0) + '\t' + street.getBounds().get(1) + '\t' + street.getCost() + '\n');
                }
                    final_cost += street.getCost();
                }
            }
            outputFile.write("---"+'\n');
            outputFile.write(Integer.toString(final_cost));
            outputFile.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        String filename = args[0];

        Carte cityMap = parseInput(filename);
        Set<Street> ARM = cityMap.primJarnik();
        parseOutput(ARM, cityMap.citySites.keySet(), args[1]);
//        for(Street street: test){
////            System.out.println(street.name + street.getBounds().get(0) + street.getBounds().get(1) + street.getCost());
////        }

    }

}