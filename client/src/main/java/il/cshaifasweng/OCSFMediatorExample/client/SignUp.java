package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUp {
    public void onGoBackButtonClick(ActionEvent actionEvent) {
    }

    private ChoiceBox<String> workerTypeChoiceBox;
    private TextField branchIDField;

    private void initialize() {
        workerTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {workerTypeChoiceBox.setVisible("Branch Manager".equals(newValue));
        });
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}