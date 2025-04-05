package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

public class PersonalInformationPopupWindowController implements PopupController<String, PersonalInformation>{

    private PersonalInformation personalInformation = new PersonalInformation();
    private boolean submitted = false;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;


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

    @FXML
    private void submit() {
        personalInformation.setFullName(fullNameField.getText());
        personalInformation.setPhoneNumber(phoneNumberField.getText());
        personalInformation.setEmail(emailField.getText());

        if (!personalInformation.isValid()){
            return;
        }
        submitted = true;
        // Close the pop-up window after submission
        Stage stage = (Stage) (fullNameField.getScene().getWindow());
        stage.close();
    }

    @Override
    public void reInitialize(String input){

    }

    @Override
    public PersonalInformation getOutput(){
        if (submitted){
            return personalInformation;
        }
        return null;
    }
}