package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class EditDishController {

    @FXML
    public VBox availableBranches;
    @FXML
    public VBox ingredients;

    @FXML
    public Button changeBranchesButton;
    @FXML
    public Button changeIngredientsButton;
    @FXML
    public TextField SaleTextField;

    private DishClient originalDish;
    @FXML
    public AnchorPane editDishPane;

    @FXML
    public Button submitButton;

    @FXML
    public TextField nameTextField;
    @FXML
    public TextField priceTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public TextField imageUrlTextField;

    public final BooleanProperty isSubmitted = new SimpleBooleanProperty(false);

    private boolean isEdit = false;
    private DishClient dish;

    // Define the distances for button positioning
    private final double submitButtonHDistFromLRCorner = 10.0; // Horizontal distance from the right corner
    private final double submitButtonWDistFromLRCorner = 10.0; // Vertical distance from the bottom corner

    @FXML
    public void initialize() {
        try {
//            EventBus.getDefault().register(this);
            // Ensure initial positioning of buttons
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//    }



    public void setDish(DishClient dish) {
        this.dish = dish;
        isSubmitted.set(false);
        updateVisibleDishProperties();
        originalDish = dish;
    }

    public DishClient getOriginalDish() {
        return originalDish;
    }

    private void updateVisibleDishProperties() {
        // Use Platform.runLater to update text fields
        Platform.runLater(() -> {
            nameTextField.setText(dish.getName());
            priceTextField.setText(String.valueOf(dish.getPrice()));
            descriptionTextField.setText(dish.getDescription());
            imageUrlTextField.setText(dish.getImageUrl());
            SaleTextField.setText(String.valueOf(dish.getSale()));
        });

        // Update available branches and ingredients separately
        initializeAvailableBranches(dish.getAvailableBranches());
        initializeIngredients(dish.getIngredients());
    }

    public DishClient getDish() {
        return dish;
    }

    @FXML
    public void submitDish() {
        if (checkValidFields() && !isSubmitted.get()) {
            dish = new DishClient(
                    nameTextField.getText(),
                    descriptionTextField.getText(),
                    Float.parseFloat(priceTextField.getText()),
                    imageUrlTextField.getText(),
                    dish.getAvailableBranches(),
                    dish.getIngredients(),
                    Integer.parseInt(SaleTextField.getText())
            );
            isSubmitted.set(true);
        }
    }

    public boolean isEdit() {
        return isEdit;
    }

    private boolean checkValidFields() {
        return isFloat(priceTextField) && isInteger(SaleTextField);
    }

    private boolean isFloat(TextField textField) {
        try {
            Float.parseFloat(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(TextField textField) {
        try {
            Integer.parseInt(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    private void initializeAvailableBranches(List<String> branches) {
        // Use Platform.runLater to update the availableBranches VBox
        Platform.runLater(() -> {
            availableBranches.getChildren().clear();
            for (String branch : branches) {
                Label label = new Label(branch);
                availableBranches.getChildren().add(label);
            }
        });
    }

    private void initializeIngredients(List<String> ingredientsList) {
        // Use Platform.runLater to update the ingredients VBox
        Platform.runLater(() -> {
            ingredients.getChildren().clear();
            for (String ingredient : ingredientsList) {
                Label label = new Label(ingredient);
                ingredients.getChildren().add(label);
            }
        });
    }

    public void updateIngredients(List<String> ingredientsList) {
        dish = new DishClient(
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getImageUrl(),
                dish.getAvailableBranches(),
                ingredientsList,
                dish.getSale()
        );
        initializeIngredients(ingredientsList);
    }

    public void updateBranches(List<String> branchesList) {
        dish = new DishClient(
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getImageUrl(),
                branchesList,
                dish.getIngredients(),
                dish.getSale()
        );
        initializeAvailableBranches(branchesList);
    }

    public Button getChangeBranchesButton() {
        return changeBranchesButton;
    }

    public Button getChangeIngredientsButton() {
        return changeIngredientsButton;
    }
}