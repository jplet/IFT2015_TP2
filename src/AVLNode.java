import java.nio.file.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

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