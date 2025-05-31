package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menu_items")
public class MenuItem implements Serializable
// when trying to send objects over a channel, make sure that they are serializable!!4
// otherwise, sending them over a channel will silently fail, making it a pain in the ass to debug
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    public String name;

    @Column(name = "description", nullable=false)
    public String description;

    @Convert(converter = StringListConverter.class)
    @Column(name = "preferences")
    public List<String> personalPreferences;;

    @Column(name = "price", nullable=false)
    public long price;

    public String imageUrl;
    @Convert(converter = StringListConverter.class)
    @Column(name = "available branches")
    public List<String> availableBranches;
    @Convert(converter = StringListConverter.class)
    @Column(name = "ingredients")
    public List<String> ingredients;
    //@JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Restaurant restaurant;
/*
    @Column(name = "restaurant_id")
    public Long restaurantId;*/

    @Column(name = "available_for_takeout")
    public boolean availableForTakeout;

    @Column(name = "deleted_At")
    public Date deletedAt;
    @Column(name="sale")
    public int sale;

    public MenuItem() {

    }
    public MenuItem(String description, long price) {
        this.description = description;
        this.price = price;
    }
    public MenuItem(String name, String description, long price, String imageUrl,
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

    public MenuItem(String name, String description, long price, String imageUrl,
                    List<String> availableBranches, List<String> ingredients, int sale) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.availableBranches = availableBranches;
        this.ingredients = ingredients;
        this.personalPreferences = new ArrayList<>();
        this.sale = sale;
    }

    public Long getId() {
        return id;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRestaurantId() {
        return restaurant.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getPersonalPreferences() {
        return personalPreferences;
    }

    public void setPersonalPreferences(List<String> personalPreferences) {
        this.personalPreferences = personalPreferences;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getAvailableBranches() {
        return availableBranches;
    }

    public void setAvailableBranches(List<String> availableBranches) {
        this.availableBranches = availableBranches;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isAvailableForTakeout() {
        return availableForTakeout;
    }

    public void setAvailableForTakeout(boolean availableForTakeout) {
        this.availableForTakeout = availableForTakeout;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                (description == null || description.isEmpty()) &&
                price == 0 &&
                (imageUrl == null || imageUrl.isEmpty()) &&
                (availableBranches == null || availableBranches.isEmpty()) &&
                (ingredients == null || ingredients.isEmpty());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MenuItem other = (MenuItem) obj;
        return name.equals(other.name)
                && description.equals(other.description)
                && price == (other.price)
                && imageUrl.equals(other.imageUrl)
                && availableBranches.equals(other.availableBranches)
                && ingredients.equals(other.ingredients)
                && personalPreferences.equals(other.personalPreferences)
                && sale == other.sale;
    }

}
