package il.cshaifasweng.OCSFMediatorExample.client;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class DishSectionInMenuController {

    @FXML private ImageView dishImage;
    @FXML private Label dishName;
    @FXML private Label dishDescription;
    @FXML private Label dishPrice;
    @FXML private VBox availableBranches;
    @FXML private VBox ingredients;

    private DishClient dish;

    public void setDish(DishClient dish) {
        this.dish = dish;
    }

    public void setDishDataInDishSection(){
        setDishImage();
        setDishName();
        setDishDescription();
        setDishPrice();
        initializeAvailableBranches();
        initializeIngredients();
    }

    private void setDishImage(){

    }

    private void setDishName() {
        dishName.setText(dish.getName());
    }

    private void setDishDescription() {
        dishDescription.setText(dish.getDescription());
    }

    private void setDishPrice() {
        dishPrice.setText("Price: " + dish.getPrice());
    }

    private void initializeAvailableBranches() {
        availableBranches.getChildren().clear();
        for (String branch : dish.getAvailableBranches()) {
            Label label = new Label(branch);
            availableBranches.getChildren().add(label);
        }
    }

    private void initializeIngredients() {
        ingredients.getChildren().clear();
        for (String ingredient : dish.getIngredients()) {
            Label label = new Label(ingredient);
            ingredients.getChildren().add(label);
        }
    }

    public DishClient getDishClient() {
        return dish;
    }


}
