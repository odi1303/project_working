package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

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
                HBox hbox = new HBox();
                Button cancelButton = createCancelButton();
                hbox.getChildren().add(cancelButton);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReservationCard.fxml"));
                Node dishNode = fxmlLoader.load();
                ReservationCardController reservationCardController = fxmlLoader.getController();
                reservationCardController.setData(reservation);
                hbox.getChildren().add(dishNode);

                ReservationListContainer.getChildren().add(hbox);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    private Button createCancelButton() {
        Button button = new Button();
        button.setText("Cancel Reservation");
        button.setOnAction(event -> {cancelReservation(button);});
        return button;
    }

    private void cancelReservation(Button button) {
        HBox hbox = (HBox) button.getParent();
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "are you shure you want to cancel the reservation?", (Stage) ReservationListContainer.getScene().getWindow());
            if (isConfirmed) {
                boolean confirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "you will be required to pay ___.", (Stage) ReservationListContainer.getScene().getWindow());
                if (confirmed) {
                    ReservationListContainer.getChildren().remove(hbox);
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}
