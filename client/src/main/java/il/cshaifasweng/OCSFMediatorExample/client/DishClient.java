






//there is another version in entities









package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishClient {
    private final String name;
    private final String description;
    private final float price;
    private final String imageUrl;
    private final List<String> availableBranches;
    private final List<String> ingredients;
    private final List<String> personalPreferences;
    private final int sale;

    public DishClient() {
        this.name = "";
        this.description = "";
        this.price = 0;
        this.imageUrl = "";
        this.availableBranches = new ArrayList<String>();
        this.ingredients = new ArrayList<String>();
        personalPreferences = new ArrayList<String>();
        this.sale = 0;
    }

    public DishClient(String name, String description, float price, String imageUrl,
                List<String> availableBranches, List<String> ingredients, List<String> personalPreferences, int sale) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.availableBranches = availableBranches;
        this.ingredients = ingredients;
        this.personalPreferences = new ArrayList<String>(personalPreferences);
        this.sale = sale;
    }

    public DishClient(String name, String description, float price, String imageUrl,
                      List<String> availableBranches, List<String> ingredients, int sale) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.availableBranches = availableBranches;
        this.ingredients = ingredients;
        this.personalPreferences = new ArrayList<String>();
        this.sale = sale;
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

    public List<String> getPersonalPreferences() {
        return personalPreferences;
    }

    public int getSale() {
        return sale;
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
        return name.equals(other.name) && description.equals(other.description) && price == other.price && imageUrl.equals(other.imageUrl) && availableBranches.equals(other.availableBranches) && ingredients.equals(other.ingredients) && personalPreferences.equals(other.personalPreferences) && sale == other.sale;
    }
}
