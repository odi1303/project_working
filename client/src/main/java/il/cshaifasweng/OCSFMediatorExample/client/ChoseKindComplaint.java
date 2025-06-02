/**
 * Sample Skeleton for 'chose-kind-complaint.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ChoseKindComplaint {

    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    @FXML // fx:id="late_button"
    private Button late_button; // Value injected by FXMLLoader

    @FXML // fx:id="regular_button"
    private Button regular_button; // Value injected by FXMLLoader

    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }

    @FXML
    void open_delivery_complaint(ActionEvent event) throws IOException {
        App.setRoot("file-delivery-complaint");
    }

    @FXML
    void open_regular_complaint(ActionEvent event) throws IOException {
        App.setRoot("file-a-complaint");
    }

}
