package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import il.cshaifasweng.OCSFMediatorExample.entities.dal.models.LocationInformation;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

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
    public void initialize() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @FXML
    private void submit() {
        locationInformation = new LocationInformation(
                cityField.getText(),
                streetField.getText(),
                houseNumberField.getText()
        );
        new Thread(()->{
            try {
                App.sendMessageToServer(locationInformation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
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
    @Subscribe
    public void on_respond(LocationInformation locationInformation) throws IOException {
        this.locationInformation=locationInformation;
        System.out.println("got the location from server");
    }
    @Override
    public LocationInformation getOutput() {
        if (submitted) {
            return locationInformation;
        }
        return null;
    }
}
