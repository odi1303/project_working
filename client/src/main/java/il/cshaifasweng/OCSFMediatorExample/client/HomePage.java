package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;

public class HomePage {

    @FXML // fx:id="MyCartBottun"
    private Button MyCartBottun; // Value injected by FXMLLoader

    @FXML // fx:id="aboutButton"
    private Button aboutButton; // Value injected by FXMLLoader

    @FXML // fx:id="connectButton"
    private Button connectButton; // Value injected by FXMLLoader

    @FXML // fx:id="reserveButton"
    private Button reserveButton; // Value injected by FXMLLoader

    @FXML // fx:id="showMenu"
    private Button showMenu; // Value injected by FXMLLoader
    @FXML
    private Label StatusLabel;


    @FXML
    void showAbout(ActionEvent event) {

    }

    @FXML
    void showMyCart(ActionEvent event) {

    }

    @FXML
    void showTheMenu(ActionEvent event) throws IOException {
        boolean sent = false;
        while (!sent) {
            try {
                SimpleClient.getClient().sendToServer("GetDishNames");
                sent = true;
            } catch (Exception ignored) {
            }
        }
        StatusLabel.setText("Loading Menu");
        App.setRoot("menu-review");
    }

    @FXML
    void toConnect(ActionEvent event) throws IOException {
        App.setRoot("hello-view");
    }

    @FXML
    void toResrveAtable(ActionEvent event) throws IOException {
        App.setRoot("TableOrderScreen");
    }

}
