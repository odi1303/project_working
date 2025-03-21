package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class EditMenuScreenController {

    @FXML
    private void createNewMenu(ActionEvent event) {
        // TODO: Implement logic for creating a new menu
    }

    @FXML
    private void createMenuFromCopy(ActionEvent event) {
        // TODO: Implement logic for creating a menu from a copy
    }

    @FXML
    private void editExistingMenu(ActionEvent event) {
        // TODO: Implement logic for editing an existing menu
    }

    @FXML
    private void submitMenu(ActionEvent event) {
        // TODO: Implement logic for submitting a menu
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}
