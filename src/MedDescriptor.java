import java.util.Date;

public class MedDescriptor {

    public Date expirationDate;
//    public int medicamentID;
    public int medicamentAmount;

//    MedDescriptor(int medID, int medAmount, Date expDate){
    MedDescriptor(int medAmount, Date expDate){
//        medicamentID = medID;
        medicamentAmount = medAmount;
        expirationDate = expDate;
    }
    
    public Date getExpirationDate() {
    	return this.expirationDate;
    }
//    public int getMedicamentID() {
//    	return this.medicamentID;
//    }
    public int getMedicamentAmount() {
    	return this.medicamentAmount;
    }
    
    public void setMedicamentAmount(int amount) {
    	this.medicamentAmount= amount;
    	
    }
}
