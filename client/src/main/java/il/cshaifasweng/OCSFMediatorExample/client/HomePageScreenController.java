package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomePageScreenController {

//    @FXML
//    private Button MyCartBottun;

    @FXML
    private Button aboutButton;

    @FXML
    private Button connectButton;

    @FXML
    private Button reserveButton;

    @FXML
    private Button showMenu;

    @FXML
    private Button EditMenu;


    @FXML
    private Button orderDelivery;

    @FXML
    private Button cancelDeliveryOrReservation;

    @FXML
    private Button fileComplaint;

    @FXML
    private Button watchComplaints;

    @FXML
    private Button watchBranchesCapacity;

    @FXML
    private Label StatusLabel;

    @FXML
    void showAbout(ActionEvent event) throws IOException {
        App.setRoot("aboutScreen");
    }

//    @FXML
//    void showMyCart(ActionEvent event) throws IOException {
//        App.setRoot("MyCartScreen");
//    }

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

    @FXML
    void EditMenu(ActionEvent event) throws IOException {
        App.setRoot("EditMenuScreen");
    }

    @FXML
    void orderDelivery(ActionEvent event) throws IOException {
        App.setRoot("OrderDeliveryScreen");
    }

    @FXML
    void cancelDeliveryOrReservation(ActionEvent event) throws IOException {
        App.setRoot("cancelDeliveryOrReservationScreen");
    }

    @FXML
    void fileComplaint(ActionEvent event) throws IOException {
        App.setRoot("FileComplaintScreen");
    }

    @FXML
    void watchComplaints(ActionEvent event) throws IOException {
        App.setRoot("WatchComplaintsScreen");
    }

    @FXML
    void watchBranchesCapacity(ActionEvent event) throws IOException {
        App.setRoot("WatchBranchesCapacityScreen");
    }
}
