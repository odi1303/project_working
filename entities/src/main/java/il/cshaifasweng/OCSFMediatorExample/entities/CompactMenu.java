package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompactMenu implements Serializable {
    public ArrayList<String> dishes;

    public CompactMenu(List<String> dishes) {
        this.dishes = new ArrayList<>(dishes);
    }
}