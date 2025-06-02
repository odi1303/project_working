package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuClient;
import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuItem;
import javafx.fxml.FXML;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuFactory {

    @FXML
    public void initialize() {
        try {
//            EventBus.getDefault().register(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public static List<MenuClient> getMenus() {
        List<MenuClient> menus = new ArrayList<>();

        MenuClient italianMenu = new MenuClient("Italian Menu", new ArrayList<>(Arrays.asList(
                new MenuItem("Margherita Pizza", "Classic pizza with tomato sauce, mozzarella cheese, and fresh basil",
                        25, "images/margherita.jpg",
                        Arrays.asList("Rome", "Naples", "Florence"),
                        Arrays.asList("Tomato Sauce", "Mozzarella Cheese", "Basil"),
                        new ArrayList<>(),
                        10),

                new MenuItem("Pasta Carbonara", "Creamy pasta with pancetta, parmesan cheese, and egg",
                        20, "images/carbonara.jpg",
                        Arrays.asList("Rome", "Milan", "Venice"),
                        Arrays.asList("Pasta", "Pancetta", "Parmesan Cheese", "Egg", "Black Pepper"),
                        new ArrayList<>(),
                        5)
        )));
        menus.add(italianMenu);

        MenuClient americanMenu = new MenuClient("American Menu", new ArrayList<>(Arrays.asList(
                new MenuItem("Cheeseburger", "Juicy beef patty with cheddar cheese, lettuce, tomato, and pickles",
                        18, "images/cheeseburger.jpg",
                        Arrays.asList("New York", "Chicago", "Dallas"),
                        Arrays.asList("Beef Patty", "Cheddar Cheese", "Lettuce", "Tomato", "Pickles"),
                        new ArrayList<>(),
                        0),

                new MenuItem("Fried Chicken", "Crispy fried chicken with a side of fries",
                        15, "images/fried_chicken.jpg",
                        Arrays.asList("Nashville", "Houston", "Atlanta"),
                        Arrays.asList("Chicken", "Flour", "Spices", "Fries"),
                        new ArrayList<>(),
                        20)
        )));
        menus.add(americanMenu);

        MenuClient japaneseMenu = new MenuClient("Japanese Menu", new ArrayList<>(Arrays.asList(
                new MenuItem("Sushi Platter", "An assortment of fresh sushi including nigiri, sashimi, and maki rolls",
                        35, "images/sushi.jpg",
                        Arrays.asList("Tokyo", "Osaka", "Kyoto"),
                        Arrays.asList("Salmon", "Tuna", "Shrimp", "Rice", "Seaweed", "Avocado"),
                        new ArrayList<>(),
                        15),

                new MenuItem("Ramen", "Hot noodle soup with pork, egg, and vegetables",
                        12, "images/ramen.jpg",
                        Arrays.asList("Tokyo", "Fukuoka", "Sapporo"),
                        Arrays.asList("Noodles", "Pork", "Egg", "Vegetables", "Broth"),
                        new ArrayList<>(),
                        0)
        )));
        menus.add(japaneseMenu);

        return menus;
    }
}
