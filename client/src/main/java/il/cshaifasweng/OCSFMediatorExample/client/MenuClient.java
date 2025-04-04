package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;

public class MenuClient {
    private ArrayList<DishClient> menu;
    private String menuName;

    public MenuClient() {
        this.menu = new ArrayList<>();
        menuName = "";
    }

    public MenuClient(ArrayList<DishClient> dishes) {
        if (dishes == null){
            menu = new ArrayList<>();
        }else {
            menu = new ArrayList<>(dishes);
        }
        menuName = "";
    }

    public MenuClient(String menuName, ArrayList<DishClient> dishes) {
        if (dishes == null){
            menu = new ArrayList<>();
        }else {
            menu = new ArrayList<>(dishes);
        }
        this.menuName = menuName;
    }

    public void changeName(String newName) {
        menuName = newName;
    }
    public String getMenuName() {
        return menuName;
    }

    public ArrayList<DishClient> getMenu() {
        if (menu == null){
            return new ArrayList<>();
        }else {
            return new ArrayList<>(menu);
        }
    }

    public void addDish(DishClient dish) {
        menu.add(dish);
    }
    //public void removeDish(DishClient dish) {
//        menu.remove(dish);
//    }

    public boolean removeDish(DishClient dish) {
        if (menu.contains(dish)){
            menu.remove(dish);
        }else{
            for (DishClient dishInMenu : menu){
                if (dishInMenu.equals(dish)){
                    menu.remove(dishInMenu);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isContainsDish(DishClient dish) {
        for (DishClient dishInMenu : menu){
            if (dishInMenu.equals(dish)){
                return true;
            }
        }
        return false;
    }
}
