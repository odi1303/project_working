package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.greenrobot.eventbus.EventBus;

public class EditDishController {

    private DishClient originalDish;
    @FXML
    public Pane editDishPane;

    @FXML
    public Button submitButton;
    private double submitButtonHDistFromLRCorner = 30;
    private double submitButtonWDistFromLRCorner = 30;

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

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    private void positionButtons() {
        double paneWidth = editDishPane.getWidth();
        double paneHeight = editDishPane.getHeight();

        //submitButton
        double submitButtonWidth = submitButton.getWidth();
        double submitButtonHeight = submitButton.getHeight();
        submitButton.setLayoutX(paneWidth - submitButtonWidth - submitButtonHDistFromLRCorner);
        submitButton.setLayoutY(paneHeight - submitButtonHeight - submitButtonWDistFromLRCorner);
    }

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


}
