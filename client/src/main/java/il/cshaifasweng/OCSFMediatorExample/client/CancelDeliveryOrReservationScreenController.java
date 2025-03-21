package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class CancelDeliveryOrReservationScreenController {

    @FXML
    private void CancelDelivery() throws IOException {
        App.setRoot("CancelDeliveryScreen");
    }

    @FXML
    private void CancelReservation() throws IOException{
        App.setRoot("CancelReservationScreen");
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}
