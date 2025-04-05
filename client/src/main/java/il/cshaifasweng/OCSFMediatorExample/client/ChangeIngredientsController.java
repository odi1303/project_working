package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeIngredientsController {

    @FXML
    public VBox ingredientsContainer;
    @FXML
    public TextField newIngredientNameField;
    @FXML
    public Button cancelButton;

    private ObservableList<String> submittedIngredients = FXCollections.observableArrayList();

    public ObservableList<String> getSubmittedIngredients() {
        return submittedIngredients;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    @FXML
    public void initialize() {
        List<String> ingredients = getAllPossibleIngredients();
        putIngredientCheckBoxesInIngredientsContainer(ingredients);
    }

    private void putIngredientCheckBoxesInIngredientsContainer(List<String> ingredients){
        ingredientsContainer.setVisible(false);
        ingredientsContainer.setManaged(false);

        ingredientsContainer.getChildren().clear();
        for (String ingredient : ingredients){
            CheckBox checkBox = new CheckBox(ingredient);
            checkBox.setSelected(true);
            ingredientsContainer.getChildren().add(checkBox);
        }
        ingredientsContainer.setVisible(true);
        ingredientsContainer.setManaged(true);
    }

    private List<String> getAllPossibleIngredients() {
        return Arrays.asList(
                "Tomato Sauce", "Mozzarella Cheese", "Basil",
                "Pancetta", "Parmesan Cheese", "Egg", "Black Pepper",
                "Beef Patty", "Cheddar Cheese", "Lettuce",
                "Tomato", "Pickles", "Chicken",
                "Flour", "Spices", "Fries",
                "Salmon", "Tuna", "Shrimp",
                "Rice", "Seaweed", "Avocado",
                "Noodles", "Pork", "Vegetables", "Broth"
        );
    }

    private List<String> getCheckedIngredients() {
        List<String> checkedIngredients = new ArrayList<>();

        for (var node : ingredientsContainer.getChildren()) {
            if (node instanceof CheckBox) {  // Ensure the node is a CheckBox
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {  // Check if it is selected
                    checkedIngredients.add(checkBox.getText());
                }
            }
        }
        return checkedIngredients;
    }

    @FXML
    public void submitIngredientsChange(){
        submittedIngredients.setAll(getCheckedIngredients());
    }

    public void checkIngredients(List<String> ingredientsToCheck) {
        for (var node : ingredientsContainer.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (ingredientsToCheck.contains(checkBox.getText())) {
                    checkBox.setSelected(true); // Check the checkbox if it's in the list
                } else {
                    checkBox.setSelected(false);
                }
            }
        }
    }
}
