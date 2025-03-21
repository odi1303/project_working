package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUp {
    public void onGoBackButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    private ChoiceBox<String> workerTypeChoiceBox;
    @FXML
    private TextField branchIdField;
    @FXML
    private Button doneButton;
    @FXML
    private Button homePageButton;


    private void initialize() {
        workerTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {workerTypeChoiceBox.setVisible("Branch Manager".equals(newValue));
        });
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}