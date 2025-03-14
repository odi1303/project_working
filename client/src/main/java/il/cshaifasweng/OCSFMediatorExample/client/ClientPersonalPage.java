/**
 * Sample Skeleton for 'client_personal_page.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ClientPersonalPage {

    @FXML // fx:id="address"
    private TextField address; // Value injected by FXMLLoader

    @FXML // fx:id="cart"
    private ImageView cart; // Value injected by FXMLLoader

    @FXML // fx:id="complaint"
    private Button complaint; // Value injected by FXMLLoader

    @FXML // fx:id="complaint_status"
    private Button complaint_status; // Value injected by FXMLLoader

    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    @FXML // fx:id="menu"
    private ImageView menu; // Value injected by FXMLLoader

    @FXML // fx:id="my_orders"
    private Button my_orders; // Value injected by FXMLLoader

    @FXML // fx:id="my_reservations"
    private Button my_reservations; // Value injected by FXMLLoader

    @FXML
    void edit_address(ActionEvent event) {

    }

    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }

    @FXML
    void new_complaint(ActionEvent event) {

    }

    @FXML
    void show_cart(MouseEvent event) {

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
        App.setRoot("menu-review");
    }

    @FXML
    void view_complaint_status(ActionEvent event) {

    }

    @FXML
    void view_orders(ActionEvent event) {

    }

    @FXML
    void view_reservations(ActionEvent event) {

    }

}
