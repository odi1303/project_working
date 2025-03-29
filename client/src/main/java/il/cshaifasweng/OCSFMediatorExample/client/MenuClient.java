package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;

public class MenuClient {
    private ArrayList<DishClient> menu;

    public MenuClient() {
        this.menu = new ArrayList<>();
    }

    public MenuClient(ArrayList<DishClient> dishes) {
        if (dishes == null){
            menu = new ArrayList<>();
        }else {
            menu = new ArrayList<>(dishes);
        }
    }

    public ArrayList<DishClient> getMenu() {
        return new ArrayList<>(menu);
    }

    public void addDish(DishClient dish) {
        menu.add(dish);
    }
    public void removeDish(DishClient dish) {
        menu.remove(dish);
    }
}
