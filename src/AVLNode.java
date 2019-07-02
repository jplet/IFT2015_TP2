import java.util.List;
import java.util.ArrayList;

public class AVLNode {

    public int key;
    public int height;
    List<MedDescriptor> medGroups = new ArrayList<>();
    public AVLNode left;
    public AVLNode right;

    AVLNode(MedDescriptor meds) {
        key = meds.medicamentID;
        medGroups.add(meds);
        height = 1;
    }

}