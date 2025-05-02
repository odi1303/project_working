package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Reservation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class CancelReservationScreenController {

    @FXML
    private VBox ReservationListContainer;

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
            App.sendMessageToServer("send all reservations");
            //List<Reservation> reservations = HardcodedReservations.getSampleReservations();
            // Use Platform.runLater to update ReservationListContainer
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Subscribe
    public void on_respond(List<Reservation> reservations) {
        Platform.runLater(() -> {
            ReservationListContainer.getChildren().clear();
            for (Reservation reservation : reservations) {
                HBox hbox = new HBox();
                Button cancelButton = createCancelButton(reservation);
                hbox.getChildren().add(cancelButton);

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReservationCard.fxml"));
                    Node dishNode = fxmlLoader.load();
                    ReservationCardController reservationCardController = fxmlLoader.getController();
                    reservationCardController.setData(reservation);
                    hbox.getChildren().add(dishNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ReservationListContainer.getChildren().add(hbox);
            }
        });
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    private Button createCancelButton(Reservation reservation) {
        Button button = new Button();
        button.setText("Cancel Reservation");
        button.setOnAction(event -> {
            cancelReservation(reservation, button);
        });
        return button;
    }

    private void cancelReservation(Reservation reservation, Button button) {
        HBox hbox = (HBox) button.getParent();
        PopupDialogService popupDialogService = new PopupDialogService();
        // Use Platform.runLater to handle popup and UI updates
        Platform.runLater(() -> {
            try {
                boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "are you sure you want to cancel the reservation?", (Stage) ReservationListContainer.getScene().getWindow());
                if (isConfirmed) {
                    boolean confirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "you will be required to pay: " + String.valueOf(getRequiredReservationCancelationFee(reservation)), (Stage) ReservationListContainer.getScene().getWindow());
                    if (confirmed) {
                        ReservationListContainer.getChildren().remove(hbox);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private double getRequiredReservationCancelationFee(Reservation reservations) {
        return 10.0;
    }
}