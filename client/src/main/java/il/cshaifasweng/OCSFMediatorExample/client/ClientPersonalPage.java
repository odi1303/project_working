package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ClientPersonalPage {

    @FXML
    private TextField address;

    @FXML
    private ImageView cart;

    @FXML
    private Button complaint;

    @FXML
    private Button complaint_status;

    @FXML
    private ImageView home_icon;

    @FXML
    private ImageView menu;

    @FXML
    private Button my_orders;

    @FXML
    private Button my_reservations;

    @FXML
    void edit_address(ActionEvent event) {
        // Placeholder method; no UI updates currently
    }

    @FXML
    void home_page(MouseEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void new_complaint(ActionEvent event) {
        // Placeholder method; no UI updates currently
    }

    @FXML
    void show_cart(MouseEvent event) {
        // Placeholder method; no UI updates currently
    }

    @FXML
    void show_menu(MouseEvent event) throws IOException {
        boolean sent = false;
        while (!sent) {
            try {
                SimpleClient.getClient().sendToServer("GetDishNames");
                sent = true;
            } catch (Exception ignored) {
            }
        }
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                App.setRoot("menu-review");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void view_complaint_status(ActionEvent event) {
        // Placeholder method; no UI updates currently
    }

    @FXML
    void view_orders(ActionEvent event) {
        // Placeholder method; no UI updates currently
    }

    @FXML
    void view_reservations(ActionEvent event) {
        // Placeholder method; no UI updates currently
    }
}