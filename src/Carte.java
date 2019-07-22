/**
 * Jonathan Bhimani-Burrows & JP Letendre
 *
 * Carte class for TP3 // follows the linked list representation of a graph (found in lectures)
 **/

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Carte {

    public TreeMap<String, Site> citySites = new TreeMap<>();

    public Carte(){}

    public void addSite(Site site){
        String siteName = site.getName();
        this.citySites.put(siteName, site);
    }

    public void removeSite(Site site){
        String siteName = site.getName();
        this.citySites.remove(siteName);
    }

    public void addStreet(Street street){
        ArrayList<String> bounds = street.getBounds();
        Site test = this.citySites.get(bounds.get(0));
        this.citySites.get(bounds.get(0)).addStreet(street);
    }

//    public void ARM(){
//
//    }
}
