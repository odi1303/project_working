package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.util.Pair;

public class OrderClient {
    private final List<Pair<DishClient, Integer>> order;

    public OrderClient(List<Pair<DishClient, Integer>> dishes) {
        if (dishes == null) {
            this.order = Collections.unmodifiableList(new ArrayList<>());
        } else {
            this.order = Collections.unmodifiableList(new ArrayList<>(dishes));
        }
    }

    public List<Pair<DishClient, Integer>> getOrderDishesList() {
        return order;
    }
}
