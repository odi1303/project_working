package il.cshaifasweng.OCSFMediatorExample.entities.models;

//import javafx.fxml.FXML;

import jakarta.persistence.*;

import java.util.*;
import java.io.Serializable;


public class MenuClient implements Serializable {
    private ArrayList<MenuItem> menu;
    private String menuName;

//    @FXML
//    public void initialize() {
//        try {
////            EventBus.getDefault().register(this);
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//    }

//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//    }

    public MenuClient() {
        this.menu = new ArrayList<>();
        menuName = "";
    }
    public MenuClient(MenuClient menuClient) {
        this.menuName = menuClient.menuName;
        this.menu = new ArrayList<>();
        for (MenuItem item : menuClient.menu) {
            this.menu.add(MenuItem.deepCopyMenuItem(item));
//            if (item.getPersonalPreferences() != null){
//                this.menu.add(new MenuItem(item.getName(), item.getDescription(), item.getPrice(), item.getImageUrl(), item.getAvailableBranches(),item.getIngredients(), item.getPersonalPreferences(), item.getSale()));
//            }else{
//                this.menu.add(MenuItem.deepCopyMenuItem(item));
//            }
        }
    }
    public MenuClient(MenuServer menuClient) {
        this.menuName = menuClient.getMenuName();
        this.menu = menuClient.getMenu();
        /*for (MenuItem item : menuClient.menu) {
            this.menu.add(MenuItem.deepCopyMenuItem(item));
//            if (item.getPersonalPreferences() != null){
//                this.menu.add(new MenuItem(item.getName(), item.getDescription(), item.getPrice(), item.getImageUrl(), item.getAvailableBranches(),item.getIngredients(), item.getPersonalPreferences(), item.getSale()));
//            }else{
//                this.menu.add(MenuItem.deepCopyMenuItem(item));
//            }
        }*/
    }

    public MenuClient(ArrayList<MenuItem> dishes) {
        if (dishes == null){
            menu = new ArrayList<>();
        }else {
            menu = dishes;
        }
        menuName = "";
    }

    public MenuClient(String menuName, ArrayList<MenuItem> dishes) {
        if (dishes == null){
            menu = new ArrayList<>();
        }else {
            menu = dishes;
        }
        this.menuName = menuName;
    }

    public void changeName(String newName) {
        menuName = newName;
    }
    public String getMenuName() {
        return menuName;
    }

    public ArrayList<MenuItem> getMenu() {
        if (menu == null){
            return new ArrayList<>();
        }else {
            return new ArrayList<>(menu);
        }
    }

    public void addDish(MenuItem dish) {
        menu.add(dish);
    }
    //public void removeDish(DishClient dish) {
//        menu.remove(dish);
//    }

    public boolean removeDish(MenuItem dish) {
        if (menu.contains(dish)){
            menu.remove(dish);
        }else{
            for (MenuItem dishInMenu : menu){
                if (dishInMenu.equals(dish)){
                    menu.remove(dishInMenu);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isContainsDish(MenuItem dish) {
        for (MenuItem dishInMenu : menu){
            if (dishInMenu.equals(dish)){
                return true;
            }
        }
        return false;
    }

    public List<String> getAllBranches() {
        Set<String> branchesSet = new HashSet<>(); // Using a Set to avoid duplicates

        for (MenuItem dish : menu) {
            branchesSet.addAll(dish.getAvailableBranches());
        }

        return new ArrayList<>(branchesSet); // Convert Set to List before returning
    }

    public List<String> getAllIngredients() {
        Set<String> IngredientsSet = new HashSet<>(); // Using a Set to avoid duplicates

        for (MenuItem dish : menu) {
            IngredientsSet.addAll(dish.getIngredients());
        }

        return new ArrayList<>(IngredientsSet); // Convert Set to List before returning
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MenuClient other = (MenuClient) obj;
        if (!menuName.equals(other.menuName)) {
            return false;
        }
        return Objects.equals(this.menu, other.menu);
    }
}
