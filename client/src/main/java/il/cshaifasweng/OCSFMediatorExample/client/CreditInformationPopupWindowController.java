package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreditInformationPopupWindowController implements PopupController<String, CreditInformation> {

    private CreditInformation creditInformation = new CreditInformation();
    private boolean submitted = false;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expirationDateField;

    @FXML
    private TextField cvvField;

    @FXML
    private void submit() {
        creditInformation.setCardNumber(cardNumberField.getText());
        creditInformation.setExpirationDate(expirationDateField.getText());
        creditInformation.setCvv(cvvField.getText());

        if (!creditInformation.isValid()) {
            return;
        }

        submitted = true;
        Stage stage = (Stage) cardNumberField.getScene().getWindow();
        stage.close();
    }

    @Override
    public void reInitialize(String input) {
    }

    @Override
    public CreditInformation getOutput(){
        if (submitted){
            return creditInformation;
        }
        return null;
    }
}
