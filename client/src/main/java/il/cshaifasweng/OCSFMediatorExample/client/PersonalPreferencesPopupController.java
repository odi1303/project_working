package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PersonalPreferencesPopupController implements PopupController<MenuItem, List<String>> {

    @FXML
    private Label dishNameLabel;

    @FXML
    private VBox preferencesContainer;

    private MenuItem dish;
    private List<String> selectedPreferences = new ArrayList<>();
    private boolean submitted = false;

    @FXML
    private void submitPreferences() {
        selectedPreferences.clear();

        for (var node : preferencesContainer.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                for (var checkBoxNode : hbox.getChildren()) {
                    if (checkBoxNode instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) checkBoxNode;
                        if (checkBox.isSelected()) {
                            selectedPreferences.add(checkBox.getText());
                        }
                    }
                }
            }
        }
        submitted = true;
        closeWindow();
    }

    @FXML
    private void cancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) dishNameLabel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void reInitialize(MenuItem input) {
        this.dish = input;
        dishNameLabel.setText("Preferences for: " + dish.getName());

        preferencesContainer.getChildren().clear();

        for (String ingredient : dish.getIngredients()) {
            HBox ingredientBox = new HBox(10);

            Label ingredientLabel = new Label(ingredient + ": ");
            CheckBox noCheckBox = new CheckBox("No " + ingredient);
            CheckBox moreCheckBox = new CheckBox("More " + ingredient);

            // Mutual exclusion logic
            noCheckBox.setOnAction(event -> {
                if (noCheckBox.isSelected()) {
                    moreCheckBox.setSelected(false);
                }
            });

            moreCheckBox.setOnAction(event -> {
                if (moreCheckBox.isSelected()) {
                    noCheckBox.setSelected(false);
                }
            });

            ingredientBox.getChildren().addAll(ingredientLabel, noCheckBox, moreCheckBox);
            preferencesContainer.getChildren().add(ingredientBox);
        }
    }


    @Override
    public List<String> getOutput() {
        return submitted ? selectedPreferences : null;
    }
}
