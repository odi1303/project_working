package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LocationInformationPopupWindowController implements PopupController<String, LocationInformation> {

    private LocationInformation locationInformation;
    private boolean submitted = false;

    @FXML
    private TextField cityField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField houseNumberField;

    @FXML
    private void submit() {
        locationInformation = new LocationInformation(
                cityField.getText(),
                streetField.getText(),
                houseNumberField.getText()
        );

        if (!locationInformation.isValid()) {
            return;
        }
        submitted = true;

        // Close the pop-up window after submission
        Stage stage = (Stage) (cityField.getScene().getWindow());
        stage.close();
    }

    @Override
    public void reInitialize(String input) {
        // Optionally use input to pre-populate fields if needed.
    }

    @Override
    public LocationInformation getOutput() {
        if (submitted) {
            return locationInformation;
        }
        return null;
    }
}
