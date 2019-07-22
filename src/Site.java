/**
 * Jonathan Bhimani-Burrows & JP Letendre
 *
 * Site class for the Carte class
 **/
import java.util.ArrayList;
import java.util.List;

public class Site {

    public String name;
    public List<Street> incidentStreets = new ArrayList<>();

    public Site(String name){
        this.name = name;
    }

    public void addStreet(Street street){
        this.incidentStreets.add(street);
    }

    public String getName(){
        return this.name;
    }

    public List<Street> getIncidentStreets(){
        return this.incidentStreets;
    }
}
