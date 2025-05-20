package il.cshaifasweng.OCSFMediatorExample.client;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuItem;

public class DishSectionInMenuController {

    @FXML private ImageView dishImage;
    @FXML private Label dishName;
    @FXML private Label dishDescription;
    @FXML private Label dishPrice;
    @FXML private Label dishSale;
    @FXML private VBox availableBranches;
    @FXML private VBox ingredients;

    private MenuItem dish;

    @FXML
    public void initialize() {
        try {
//            EventBus.getDefault().register(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//    }

    public void setDish(MenuItem dish) {
        this.dish = dish;
    }

    public void setDishDataInDishSection(){
        setDishImage();
        setDishName();
        setDishDescription();
        setDishPrice();
        setDishSale();
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
    private void setDishSale() {
        if (dish.getSale() == 0) {
            dishSale.setVisible(false);
            dishSale.setManaged(false);
        }else{
            dishSale.setText("Sale: " + dish.getSale() + "%");
        }
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

    public MenuItem getDishClient() {
        return dish;
    }


}
