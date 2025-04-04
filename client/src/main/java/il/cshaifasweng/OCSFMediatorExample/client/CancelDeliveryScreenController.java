package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class CancelDeliveryScreenController {
    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().unregister(this);
        } catch(Exception e) {
            throw new RuntimeException();
        }
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("HomePageScreen");
    }
}
