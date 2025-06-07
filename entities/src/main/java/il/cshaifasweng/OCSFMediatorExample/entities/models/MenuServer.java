package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.*;
import java.io.Serializable;

@Entity
@Table(name = "menus")
public class MenuServer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MenuItem> menu;

    public boolean is_main_menu;

    @Column
    @NaturalId
    private String menuName;

    public MenuServer() {
        this.menu = new ArrayList<>();
        menuName = "";
    }
    public MenuServer(MenuClient menuClient) {
        this.menuName = menuClient.getMenuName();
        this.menu = new ArrayList<>(menuClient.getMenu());
    }

    public MenuServer(ArrayList<MenuItem> dishes) {
        if (dishes == null){
            menu = new ArrayList<>();
        }else {
            menu = new ArrayList<>(dishes);
        }
        menuName = "";
    }

    public MenuServer(String menuName, ArrayList<MenuItem> dishes) {
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
        return menu.stream().flatMap(m -> m.getAvailableBranches().stream()).distinct().toList();
        /*Set<String> branchesSet = new HashSet<>(); // Using a Set to avoid duplicates

        for (MenuItem dish : menu) {
            branchesSet.addAll(dish.getAvailableBranches());
        }

        return new ArrayList<>(branchesSet); // Convert Set to List before returning*/
    }

    public List<String> getAllIngredients() {
        return menu.stream().flatMap(m -> m.getIngredients().stream()).distinct().toList();

        /*Set<String> IngredientsSet = new HashSet<>(); // Using a Set to avoid duplicates

        for (MenuItem dish : menu) {
            IngredientsSet.addAll(dish.getIngredients());
        }

        return new ArrayList<>(IngredientsSet); // Convert Set to List before returning*/
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MenuServer other = (MenuServer) obj;
        if (!menuName.equals(other.menuName)) {
            return false;
        }
        return Objects.equals(this.menu, other.menu);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
