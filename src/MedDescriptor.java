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

//    public Set<SortedMap.Entry<Date, Integer>> getAllMedicamentAmount() {
//        return medTree.entrySet();
//    }

    public void setMedicamentAmount(Date expDate, Integer medAmount) {
        medTree.put(expDate, medAmount);
    }
}

//import java.util.Date;
//
//public class MedDescriptor {
//
//    public Date expirationDate;
////    public int medicamentID;
//    public int medicamentAmount;
//
////    MedDescriptor(int medID, int medAmount, Date expDate){
//    MedDescriptor(int medAmount, Date expDate){
////        medicamentID = medID;
//        medicamentAmount = medAmount;
//        expirationDate = expDate;
//    }
//
//    public Date getExpirationDate() {
//    	return this.expirationDate;
//    }
////    public int getMedicamentID() {
////    	return this.medicamentID;
////    }
//    public int getMedicamentAmount() {
//    	return this.medicamentAmount;
//    }
//
//    public void setMedicamentAmount(int amount) {
//    	this.medicamentAmount= amount;
//
//    }
//}
