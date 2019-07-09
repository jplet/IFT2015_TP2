import java.util.*;

public class MedDescriptor {

    public NavigableMap<Date, Integer> medTree = new TreeMap<>();

    MedDescriptor(){

    }

    public void AddMedicament(int medAmount, Date expDate){
        if (medTree.containsKey(expDate)) {
            Integer oldAmount = medTree.get(expDate);
            Integer newAmount = oldAmount + medAmount;
            medTree.put(expDate, newAmount);
        }
        else {
            medTree.put(expDate, medAmount);
        }
    }

    public Set<Date> getExpirationDate() {
        return medTree.keySet();
    }

    public int getMedicamentAmount(Date expDate) {
        return medTree.get(expDate);
    }

    public void setMedicamentAmount(Date expDate, Integer medAmount) {
        medTree.put(expDate, medAmount);
    }
}

