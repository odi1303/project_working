package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class CancelDeliveryScreenController {


    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("HomePageScreen");
    }
}
