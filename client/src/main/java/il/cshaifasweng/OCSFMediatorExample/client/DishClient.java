






//there is another version in entities









package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

public class DishClient {
    private String name;
    private String description;
    private float price;
    private String imageUrl;
    private List<String> availableBranches;
    private List<String> ingredients;

    public DishClient(String name, String description, float price, String imageUrl,
                List<String> availableBranches, List<String> ingredients) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.availableBranches = availableBranches;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getAvailableBranches() {
        return availableBranches;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
