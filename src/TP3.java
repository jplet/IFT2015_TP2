/**
 * Jonathan Bhimani-Burrows & JP Letendre
 *
 * TP3 class for ARM tree
 **/

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
                    Street street = new Street(streetName, street_infos[1], street_infos[2], Integer.parseInt(street_infos[3]));
                    cityMap.addStreet(street);
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

    public static void main(String[] args) {
        String filename = args[0];

        Carte cityMap = parseInput(filename);
        Set<Street> test = cityMap.primJarnik();
        for(Street street: test){
            System.out.println(street.name + street.getBounds().get(0) + street.getBounds().get(1) + street.getCost());
        }

    }

}
