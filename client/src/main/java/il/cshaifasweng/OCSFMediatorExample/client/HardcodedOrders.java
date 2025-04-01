package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.util.Pair;

public class HardcodedOrders {

    public static ArrayList<OrderClient> createHardcodedOrders() {
        ArrayList<OrderClient> orders = new ArrayList<>();

        // Creating DishClients
        DishClient pizza = new DishClient(
                "Pizza",
                "Cheese pizza with tomato sauce",
                35.0f,
                "images/pizza.jpg",
                Arrays.asList("Branch A", "Branch B", "Branch C"),
                Arrays.asList("Cheese", "Tomato Sauce", "Dough")
        );

        DishClient burger = new DishClient(
                "Burger",
                "Beef burger with lettuce and tomato",
                40.0f,
                "images/burger.jpg",
                Arrays.asList("Branch A", "Branch D"),
                Arrays.asList("Beef Patty", "Lettuce", "Tomato", "Bun")
        );

        DishClient pasta = new DishClient(
                "Pasta",
                "Spaghetti with meatballs",
                30.0f,
                "images/pasta.jpg",
                Arrays.asList("Branch B", "Branch C"),
                Arrays.asList("Spaghetti", "Meatballs", "Tomato Sauce")
        );

        DishClient salad = new DishClient(
                "Salad",
                "Fresh vegetable salad",
                25.0f,
                "images/salad.jpg",
                Arrays.asList("Branch A", "Branch C"),
                Arrays.asList("Lettuce", "Tomato", "Cucumber", "Dressing")
        );

        DishClient sushi = new DishClient(
                "Sushi",
                "Assorted sushi platter",
                55.0f,
                "images/sushi.jpg",
                Arrays.asList("Branch D"),
                Arrays.asList("Rice", "Fish", "Seaweed", "Vegetables")
        );

        // Creating Orders (Immutable Lists)
        OrderClient order1 = new OrderClient(List.of(
                new Pair<>(pizza, 2),
                new Pair<>(burger, 1)
        ));

        OrderClient order2 = new OrderClient(List.of(
                new Pair<>(pasta, 1),
                new Pair<>(salad, 3)
        ));

        OrderClient order3 = new OrderClient(List.of(
                new Pair<>(sushi, 2),
                new Pair<>(pizza, 1),
                new Pair<>(salad, 1),
                new Pair<>(pasta, 3),
                new Pair<>(burger, 1),
                new Pair<>(salad, 3)
        ));

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        return orders;
    }
}
