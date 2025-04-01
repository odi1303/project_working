package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class CancelReservationScreenController {

    @FXML
    private VBox ReservationListContainer;

    @FXML
    public void initialize() {
        List<Reservation> reservations = HardcodedReservations.getSampleReservations();
        ReservationListContainer.getChildren().clear();
        try {
            for (Reservation reservation : reservations) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReservationCard.fxml"));
                Node dishNode = fxmlLoader.load();
                ReservationCardController reservationCardController = fxmlLoader.getController();
                reservationCardController.setData(reservation);

                ReservationListContainer.getChildren().add(dishNode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}
