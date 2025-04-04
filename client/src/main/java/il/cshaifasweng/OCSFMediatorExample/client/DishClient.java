






//there is another version in entities









package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;
import java.util.List;

public class DishClient {
    private final String name;
    private final String description;
    private final float price;
    private final String imageUrl;
    private final List<String> availableBranches;
    private final List<String> ingredients;


    public DishClient() {
        this.name = "";
        this.description = "";
        this.price = 0;
        this.imageUrl = "";
        this.availableBranches = new ArrayList<String>();
        this.ingredients = new ArrayList<String>();
    }

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

    public boolean isEmpty() {
        return name.isEmpty() && description.isEmpty() && price == 0 && imageUrl.isEmpty() && availableBranches.isEmpty() && ingredients.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DishClient other = (DishClient) obj;
        return name.equals(other.name) && description.equals(other.description) && price == other.price && imageUrl.equals(other.imageUrl) && availableBranches.equals(other.availableBranches) && ingredients.equals(other.ingredients);
    }
}
