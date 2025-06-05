package il.cshaifasweng.OCSFMediatorExample.server.bl;

import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuItem;
import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HardcodedDataProvider {

    public static List<String> getAllIngredients() {
        return List.of(
                "Tomato Sauce", "Mozzarella Cheese", "Basil",
                "Pancetta", "Parmesan Cheese", "Egg", "Black Pepper",
                "Beef Patty", "Cheddar Cheese", "Lettuce",
                "Tomato", "Pickles", "Chicken",
                "Flour", "Spices", "Fries",
                "Salmon", "Tuna", "Shrimp",
                "Rice", "Seaweed", "Avocado",
                "Noodles", "Pork", "Vegetables", "Broth", "Pasta"
        );
    }

    public static List<String> getAllBranches() {
        return List.of(
                "Rome", "Naples", "Florence",
                "Milan", "Venice",
                "New York", "Chicago", "Dallas",
                "Nashville", "Houston", "Atlanta",
                "Tokyo", "Osaka", "Kyoto",
                "Fukuoka", "Sapporo"
        );
    }

    public static List<MenuClient> getAllMenus() {
        List<MenuClient> menus = new ArrayList<>();

        // Hardcoded absolute file URL (Windows style, for your local setup)
        String foodImageUrl = "file:/C:/Users/sharo/Documents/Kiran_workspace/software_enginiring/project_working/client/src/main/resources/il/cshaifasweng/OCSFMediatorExample/client/menuItemPictures/FOOD_image.png";

        MenuClient italianMenu = new MenuClient("Italian Menu", new ArrayList<>(Arrays.asList(
                new MenuItem("Margherita Pizza", "Classic pizza with tomato sauce, mozzarella cheese, and fresh basil",
                        25, foodImageUrl,
                        Arrays.asList("Rome", "Naples", "Florence"),
                        Arrays.asList("Tomato Sauce", "Mozzarella Cheese", "Basil"),
                        new ArrayList<>(), 10),

                new MenuItem("Pasta Carbonara", "Creamy pasta with pancetta, parmesan cheese, and egg",
                        20, foodImageUrl,
                        Arrays.asList("Rome", "Milan", "Venice"),
                        Arrays.asList("Pasta", "Pancetta", "Parmesan Cheese", "Egg", "Black Pepper"),
                        new ArrayList<>(), 5),

                new MenuItem("Lasagna", "Layers of pasta with rich meat sauce, béchamel, and melted cheese",
                        28, foodImageUrl,
                        Arrays.asList("Rome", "Naples"),
                        Arrays.asList("Pasta", "Beef Patty", "Tomato Sauce", "Mozzarella Cheese", "Parmesan Cheese"),
                        new ArrayList<>(), 8),

                new MenuItem("Risotto alla Milanese", "Creamy saffron risotto topped with parmesan",
                        22, foodImageUrl,
                        Arrays.asList("Milan"),
                        Arrays.asList("Rice", "Parmesan Cheese", "Spices"),
                        new ArrayList<>(), 4),

                new MenuItem("Bruschetta", "Grilled bread topped with fresh tomatoes, garlic, and basil",
                        10, foodImageUrl,
                        Arrays.asList("Florence", "Rome"),
                        Arrays.asList("Tomato", "Basil", "Bread", "Garlic", "Olive Oil"),
                        new ArrayList<>(), 12),

                new MenuItem("Tiramisu", "Classic Italian dessert with layers of coffee-soaked ladyfingers and mascarpone cream",
                        14, foodImageUrl,
                        Arrays.asList("Venice", "Rome"),
                        Arrays.asList("Egg", "Cheese", "Coffee", "Sugar", "Cocoa"),
                        new ArrayList<>(), 6)
        )));
        menus.add(italianMenu);

        MenuClient americanMenu = new MenuClient("American Menu", new ArrayList<>(Arrays.asList(
                new MenuItem("Cheeseburger", "Juicy beef patty with cheddar cheese, lettuce, tomato, and pickles",
                        18, foodImageUrl,
                        Arrays.asList("New York", "Chicago", "Dallas"),
                        Arrays.asList("Beef Patty", "Cheddar Cheese", "Lettuce", "Tomato", "Pickles"),
                        new ArrayList<>(), 0),

                new MenuItem("Fried Chicken", "Crispy fried chicken with a side of fries",
                        15, foodImageUrl,
                        Arrays.asList("Nashville", "Houston", "Atlanta"),
                        Arrays.asList("Chicken", "Flour", "Spices", "Fries"),
                        new ArrayList<>(), 20)
        )));
        menus.add(americanMenu);

        MenuClient japaneseMenu = new MenuClient("Japanese Menu", new ArrayList<>(Arrays.asList(
                new MenuItem("Sushi Platter", "An assortment of fresh sushi including nigiri, sashimi, and maki rolls",
                        35, foodImageUrl,
                        Arrays.asList("Tokyo", "Osaka", "Kyoto"),
                        Arrays.asList("Salmon", "Tuna", "Shrimp", "Rice", "Seaweed", "Avocado"),
                        new ArrayList<>(), 15),

                new MenuItem("Ramen", "Hot noodle soup with pork, egg, and vegetables",
                        12, foodImageUrl,
                        Arrays.asList("Tokyo", "Fukuoka", "Sapporo"),
                        Arrays.asList("Noodles", "Pork", "Egg", "Vegetables", "Broth"),
                        new ArrayList<>(), 0)
        )));
        menus.add(japaneseMenu);

        return menus;
    }


    public static MenuClient getMainMenu() {
        List<MenuClient> allMenus = getAllMenus();
        return allMenus.isEmpty() ? null : allMenus.getFirst();
    }
}
