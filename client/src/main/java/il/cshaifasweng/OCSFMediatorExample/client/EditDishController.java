package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

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

    private DishClient originalDish;
    @FXML
    public AnchorPane editDishPane;

    @FXML
    public Button submitButton;
//    private double submitButtonHDistFromLRCorner = 30;
//    private double submitButtonWDistFromLRCorner = 30;

    //dish properties fields
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField priceTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public TextField imageUrlTextField;

    public final BooleanProperty isSubmitted = new SimpleBooleanProperty(false); //for listeners who want the edited dish.

    private boolean isEdit = false;
    private DishClient dish;

    public void initialize() {
//        positionButtons();
//        editDishPane.widthProperty().addListener((observable, oldValue, newValue) -> positionButtons());
//        editDishPane.heightProperty().addListener((observable, oldValue, newValue) -> positionButtons());
    }

//    private void positionButtons() {
//        double paneWidth = editDishPane.getWidth();
//        double paneHeight = editDishPane.getHeight();
//
//        //submitButton
//        double submitButtonWidth = submitButton.getWidth();
//        double submitButtonHeight = submitButton.getHeight();
//        submitButton.setLayoutX(paneWidth - submitButtonWidth - submitButtonHDistFromLRCorner);
//        submitButton.setLayoutY(paneHeight - submitButtonHeight - submitButtonWDistFromLRCorner);
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
        nameTextField.setText(dish.getName());
        priceTextField.setText(String.valueOf(dish.getPrice()));
        descriptionTextField.setText(dish.getDescription());
        imageUrlTextField.setText(dish.getImageUrl());
        initializeAvailableBranches(dish.getAvailableBranches());
        initializeIngredients(dish.getIngredients());
    }
    public DishClient getDish() {
        return dish;
    }

    @FXML
    public void submitDish() {
        if (checkValidFields() && !isSubmitted.get()) {
            dish = new DishClient(nameTextField.getText(), descriptionTextField.getText(),Float.parseFloat(priceTextField.getText()), imageUrlTextField.getText(), dish.getAvailableBranches(), dish.getIngredients());
            isSubmitted.set(true);
        }
    }
    public boolean isEdit() {
        return isEdit;
    }

    private boolean checkValidFields() {
        return isFloat(priceTextField);
    }

    private boolean isFloat(TextField textField) {
        try {
            Float.parseFloat(textField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;

    }

    private void initializeAvailableBranches(List<String> branches) {
        availableBranches.getChildren().clear();
        for (String branch : branches) {
            Label label = new Label(branch);
            availableBranches.getChildren().add(label);
        }
    }

    private void initializeIngredients(List<String> ingredientsList) {
        ingredients.getChildren().clear();
        for (String ingredient : ingredientsList) {
            Label label = new Label(ingredient);
            ingredients.getChildren().add(label);
        }
    }

    public void updateIngredients(List<String> ingredientsList) {
        dish = new DishClient(dish.getName(), dish.getDescription(), dish.getPrice(), dish.getImageUrl(), dish.getAvailableBranches(), ingredientsList);
        initializeIngredients(ingredientsList);
    }

    public void updateBranches(List<String> branchesList) {
        dish = new DishClient(dish.getName(), dish.getDescription(), dish.getPrice(), dish.getImageUrl(), branchesList, dish.getIngredients());
        initializeAvailableBranches(branchesList);
    }

    public Button getChangeBranchesButton() {
        return changeBranchesButton;
    }
    public Button getChangeIngredientsButton() {
        return changeIngredientsButton;
    }

}
