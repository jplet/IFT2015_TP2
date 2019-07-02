/**
 * Jonathan Birmani-Burrows & JP Letendre
 *
 * stockTree class, to contain stock from a specific date
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


public class stockTree {

    public String stock_date;
    public AVLTree stock_tree;

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            System.out.println(inDate+" is not valid.");
            return false;
        }
        return true;
    }

    public void setDate(String date){
        if (isValidDate(date)){
            stock_date = date;
        }
    }

    public void preOrder(AVLNode node){
        stock_tree.preOrder(node);
    }

    public void addMedicament(MedDescriptor meds){
        stock_tree.root = stock_tree.insert(stock_tree.root, meds);
    }

    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
