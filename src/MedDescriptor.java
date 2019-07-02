import java.util.Date;

public class MedDescriptor {

    public Date expirationDate;
    public int medicamentID;
    public int medicamentAmount;

    MedDescriptor(int medID, int medAmount, Date expDate){
        medicamentID = medID;
        medicamentAmount = medAmount;
        expirationDate = expDate;
    }
}
