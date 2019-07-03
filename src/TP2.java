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

    public static String ReadPharmacyOrder(String filename) {
        /*
        Function to read order for pharmacy; takes in the filename to read from as argument
         */

        List<ArrayList<Double>> data = new ArrayList<>();

        System.out.println("Reading from file "+filename+"\n");


        try {
            Path filePath = Paths.get(filename);
            Scanner scanner = new Scanner(filePath);
            while (scanner.hasNext()) {
                String line = scanner.next();
                System.out.println(line);

            }
            scanner.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return "Hi";
    }


    public static void main(String[] args) {
//        stockTree stock = new stockTree();
//        stock.add(5);
//        System.out.println(stock.x);
//        isValidDate("2004-11-30");
//        ReadPharmacyOrder(args[0]);
    }

}