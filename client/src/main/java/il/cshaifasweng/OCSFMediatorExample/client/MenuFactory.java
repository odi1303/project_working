package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.util.Pair;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuFactory {

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public static List<MenuClient> getMenus() {
        List<MenuClient> menus = new ArrayList<>();

        // Italian Menu
        MenuClient italianMenu = new MenuClient("Italian Menu", new ArrayList<>(Arrays.asList(
                new DishClient("Margherita Pizza", "Classic pizza with tomato sauce, mozzarella cheese, and fresh basil",
                        25.0f, "images/margherita.jpg",
                        Arrays.asList("Rome", "Naples", "Florence"),
                        Arrays.asList("Tomato Sauce", "Mozzarella Cheese", "Basil")),

                new DishClient("Pasta Carbonara", "Creamy pasta with pancetta, parmesan cheese, and egg",
                        20.0f, "images/carbonara.jpg",
                        Arrays.asList("Rome", "Milan", "Venice"),
                        Arrays.asList("Pasta", "Pancetta", "Parmesan Cheese", "Egg", "Black Pepper"))
        )));
        menus.add(italianMenu);

        // American Menu
        MenuClient americanMenu = new MenuClient("American Menu", new ArrayList<>(Arrays.asList(
                new DishClient("Cheeseburger", "Juicy beef patty with cheddar cheese, lettuce, tomato, and pickles",
                        18.5f, "images/cheeseburger.jpg",
                        Arrays.asList("New York", "Chicago", "Dallas"),
                        Arrays.asList("Beef Patty", "Cheddar Cheese", "Lettuce", "Tomato", "Pickles")),

                new DishClient("Fried Chicken", "Crispy fried chicken with a side of fries",
                        15.0f, "images/fried_chicken.jpg",
                        Arrays.asList("Nashville", "Houston", "Atlanta"),
                        Arrays.asList("Chicken", "Flour", "Spices", "Fries"))
        )));
        menus.add(americanMenu);

        // Japanese Menu
        MenuClient japaneseMenu = new MenuClient("Japanese Menu", new ArrayList<>(Arrays.asList(
                new DishClient("Sushi Platter", "An assortment of fresh sushi including nigiri, sashimi, and maki rolls",
                        35.0f, "images/sushi.jpg",
                        Arrays.asList("Tokyo", "Osaka", "Kyoto"),
                        Arrays.asList("Salmon", "Tuna", "Shrimp", "Rice", "Seaweed", "Avocado")),

                new DishClient("Ramen", "Hot noodle soup with pork, egg, and vegetables",
                        12.0f, "images/ramen.jpg",
                        Arrays.asList("Tokyo", "Fukuoka", "Sapporo"),
                        Arrays.asList("Noodles", "Pork", "Egg", "Vegetables", "Broth"))
        )));
        menus.add(japaneseMenu);

        return menus;
    }
}
