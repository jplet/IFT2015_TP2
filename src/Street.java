/**
 * Jonathan Bhimani-Burrows & JP Letendre
 *
 * Street class for the Carte class
 **/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Street implements Comparable<Street>{

    public String start;
    public String end;
    public int cost;
    public String name;

    public Street(String name, String start, String end, int cost){
        this.name = name;
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<String> getBounds(){
        ArrayList<String> bounds = new ArrayList<String>();
        bounds.add(this.start);
        bounds.add(this.end);
        return bounds;
    }

    public Integer getCost(){
        return this.cost;
    }

    @Override
    public int compareTo(Street o) {
        String selfStart = this.getBounds().get(0);
        String otherStart = o.getBounds().get(0);

        String selfEnd = this.getBounds().get(1);
        String otherEnd = o.getBounds().get(1);

        if(selfStart.compareTo(otherStart) == 0){
            return selfEnd.compareTo(otherEnd);
        }
        else{
            return selfStart.compareTo(otherStart);
        }
    }
}

