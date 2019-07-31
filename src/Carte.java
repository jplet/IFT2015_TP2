/**
 * Jonathan Bhimani-Burrows & JP Letendre
 *
 * Carte class for TP3 // follows the linked list representation of a graph (found in lectures)
 **/

import java.util.*;

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

    public Street minimumCost(List<Street> streets){
        int min = Integer.MAX_VALUE;
        Street minStreet = new Street("null", "null", "null", Integer.MAX_VALUE);
        for(Street street : streets){
            if(min > street.getCost()){
                minStreet = new Street(street.name, street.getBounds().get(0), street.getBounds().get(1), street.getCost());
                min = street.getCost();
            }
        }
        return minStreet;
    }

    // doesn't consider links to be non-oriented (in the Carte object, the way Streets are stored and read)
    public Set<Street> primJarnik(){
        Set<String> visited = new TreeSet<>();
        Set<String> old_nodes = new TreeSet<>(citySites.keySet());
        Set<Street> arm = new TreeSet<Street>();

        visited.add(old_nodes.stream().findFirst().get());
        old_nodes.remove((old_nodes.stream().findFirst().get()));
        while(!citySites.keySet().equals(visited)){
            List<Street> incidentVertices = new ArrayList<>();
            for(String node : visited) {
                for(Street street : citySites.get(node).getIncidentStreets()){
                    if (visited.contains(street.getBounds().get(1))) {;}
                    else {
                        incidentVertices.add(street);
                    }
                }

            }

            Street bestStreet = minimumCost(incidentVertices);
            arm.add(bestStreet);
            visited.add(bestStreet.getBounds().get(1));
            old_nodes.remove(bestStreet.getBounds().get(1));
        }
        return arm;
    }
}
