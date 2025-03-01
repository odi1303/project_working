package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonalInformationInputScreenController {


    @FXML
    private TextField fullNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;


    @FXML
    private void submit() {
        PersonalInformation personalInformation = new PersonalInformation(fullNameField.getText(), phoneNumberField.getText(), emailField.getText());
        if (!personalInformation.isValid()){
            return;
        }


        // Close the pop-up window after submission
        Stage stage = (Stage) (fullNameField.getScene().getWindow());
        stage.close();
    }
}