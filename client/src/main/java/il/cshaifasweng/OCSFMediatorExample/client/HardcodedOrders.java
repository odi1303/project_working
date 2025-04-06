package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.util.Pair;

public class HardcodedOrders {

    public static ArrayList<OrderClient> createHardcodedOrders() {
        ArrayList<OrderClient> orders = new ArrayList<>();

        DishClient pizza = new DishClient(
                "Pizza",
                "Cheese pizza with tomato sauce",
                35.0f,
                "images/pizza.jpg",
                Arrays.asList("Branch A", "Branch B", "Branch C"),
                Arrays.asList("Cheese", "Tomato Sauce", "Dough"),
                new ArrayList<>(),
                10
        );

        DishClient burger = new DishClient(
                "Burger",
                "Beef burger with lettuce and tomato",
                40.0f,
                "images/burger.jpg",
                Arrays.asList("Branch A", "Branch D"),
                Arrays.asList("Beef Patty", "Lettuce", "Tomato", "Bun"),
                new ArrayList<>(),
                0
        );

        DishClient pasta = new DishClient(
                "Pasta",
                "Spaghetti with meatballs",
                30.0f,
                "images/pasta.jpg",
                Arrays.asList("Branch B", "Branch C"),
                Arrays.asList("Spaghetti", "Meatballs", "Tomato Sauce"),
                new ArrayList<>(),
                5
        );

        DishClient salad = new DishClient(
                "Salad",
                "Fresh vegetable salad",
                25.0f,
                "images/salad.jpg",
                Arrays.asList("Branch A", "Branch C"),
                Arrays.asList("Lettuce", "Tomato", "Cucumber", "Dressing"),
                new ArrayList<>(),
                15
        );

        DishClient sushi = new DishClient(
                "Sushi",
                "Assorted sushi platter",
                55.0f,
                "images/sushi.jpg",
                Arrays.asList("Branch D"),
                Arrays.asList("Rice", "Fish", "Seaweed", "Vegetables"),
                new ArrayList<>(),
                0
        );

        LocationInformation location1 = new LocationInformation("New York", "Broadway", "123");
        LocationInformation location2 = new LocationInformation("Los Angeles", "Sunset Boulevard", "456");
        LocationInformation location3 = new LocationInformation("Chicago", "Michigan Avenue", "789");

        PersonalInformation personalInfo = new PersonalInformation("John Doe", "john@example.com", "1234567890");
        CreditInformation creditInfo = new CreditInformation("1234-5678-9012-3456", "12/27", "123");

        OrderClient order1 = new OrderClient(List.of(
                new Pair<>(pizza, 2),
                new Pair<>(burger, 1)
        ), true, location1, personalInfo, creditInfo);

        OrderClient order2 = new OrderClient(List.of(
                new Pair<>(pasta, 1),
                new Pair<>(salad, 3)
        ), false, location2, personalInfo, creditInfo);

        OrderClient order3 = new OrderClient(List.of(
                new Pair<>(sushi, 2),
                new Pair<>(pizza, 1),
                new Pair<>(salad, 1),
                new Pair<>(pasta, 3),
                new Pair<>(burger, 1),
                new Pair<>(salad, 3)
        ), true, location3, personalInfo, creditInfo);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        return orders;
    }
}
