package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;

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
    public void initialize() {

        //________temporary for tests__________sets all visable__________
        setAllVisableForDevelopment();
        //________temporary for tests__________sets all visable__________

        aboutButton.setVisible(true);
        aboutButton.setManaged(true);

        reserveButton.setVisible(true);
        reserveButton.setManaged(true);

        showMenu.setVisible(true);
        showMenu.setManaged(true);

        orderDelivery.setVisible(true);
        orderDelivery.setManaged(true);

        //retrive user premitions
        //tmporery implementation, can be switched when premitions are implemented
        String userType = "User";

        //those ifs wont work if the call to setAllVisableForDevelopment() above is in place.
        if (isChainManager(userType)) {
            //missing view filed menus Buttons.
        }
        if (isBranchManager(userType)) {

        }
        if (isChainDietitian(userType)) {
            EditMenu.setVisible(true);
            EditMenu.setManaged(true);
        }
        if (isCustomerServiceWorker(userType)) {
            watchComplaints.setVisible(true);
            watchComplaints.setManaged(true);
        }
        if (isWorker(userType)) {
            watchBranchesCapacity.setVisible(true);
            watchBranchesCapacity.setManaged(true);
        }

        if (isUser(userType)) {
            cancelDeliveryOrReservation.setVisible(true);
            cancelDeliveryOrReservation.setManaged(true);

            fileComplaint.setVisible(true);
            fileComplaint.setManaged(true);
        } else {
            connectButton.setVisible(true);
            connectButton.setManaged(true);
        }



    }
    private void setAllVisableForDevelopment() {
        aboutButton.setVisible(true);
        aboutButton.setManaged(true);
        connectButton.setVisible(true);
        connectButton.setManaged(true);
        reserveButton.setVisible(true);
        reserveButton.setManaged(true);
        showMenu.setVisible(true);
        showMenu.setManaged(true);
        EditMenu.setVisible(true);
        EditMenu.setManaged(true);
        orderDelivery.setVisible(true);
        orderDelivery.setManaged(true);
        cancelDeliveryOrReservation.setVisible(true);
        cancelDeliveryOrReservation.setManaged(true);
        fileComplaint.setVisible(true);
        fileComplaint.setManaged(true);
        watchComplaints.setVisible(true);
        watchComplaints.setManaged(true);
        watchBranchesCapacity.setVisible(true);
        watchBranchesCapacity.setManaged(true);
    }



    //temporary function, should be at server.
    private boolean isUser(String userType) {
        return Objects.equals(userType, "User") || isWorker(userType);
    }

    //temporary function, should be at server.
    private boolean isChainManager(String userType) {
        return Objects.equals(userType, "Chain Manager");
    }

    //temporary function, should be at server.
    private boolean isBranchManager(String userType) {
        return Objects.equals(userType, "Branch Manager");
    }

    //temporary function, should be at server.
    private boolean isChainDietitian(String userType) {
        return Objects.equals(userType, "Chain Dietitian");
    }

    //temporary function, should be at server.
    private boolean isCustomerServiceWorker(String userType) {
        return Objects.equals(userType, "Customer Service Worker");
    }

    //temporary function, should be at server, can be generalized by checking if there is a father worker class.
    private boolean isWorker(String userType) {
        return Objects.equals(userType, "Hostess") || Objects.equals(userType, "Worker") || Objects.equals(userType, "Chain Dietitian") || Objects.equals(userType, "Customer Service Worker") || Objects.equals(userType, "Chain Manager") || Objects.equals(userType, "Branch Manager");
    }


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
