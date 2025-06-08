package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class HomePage {
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
    private Button viewReports;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        Platform.runLater(this::toggleButtons); // Set initial button visibility
    }

    private void toggleButtons() {
        UserType userType = App.userType != null ? App.userType : UserType.Empty;
        // Default buttons
        aboutButton.setVisible(true);
        connectButton.setVisible(true);
        reserveButton.setVisible(true);
        showMenu.setVisible(true);
        EditMenu.setVisible(false);
        EditMenu.setManaged(false);
        orderDelivery.setVisible(true);
        cancelDeliveryOrReservation.setVisible(true);
        fileComplaint.setVisible(true);
        watchComplaints.setVisible(false);
        watchComplaints.setManaged(false);
        watchBranchesCapacity.setVisible(false);
        watchBranchesCapacity.setManaged(false);
        viewReports.setVisible(false);
        viewReports.setManaged(false);

        switch (userType) {
            case Employee:
                watchBranchesCapacity.setVisible(true);
                watchBranchesCapacity.setManaged(true);
                break;
            case Dietitian:
                EditMenu.setVisible(true);
                EditMenu.setManaged(true);
                watchBranchesCapacity.setVisible(true);
                watchBranchesCapacity.setManaged(true);
                break;
            case BranchManager:
                watchBranchesCapacity.setVisible(true);
                watchBranchesCapacity.setManaged(true);
                viewReports.setVisible(true);
                viewReports.setManaged(true);
                break;
            case ChainManager:
                watchBranchesCapacity.setVisible(true);
                watchBranchesCapacity.setManaged(true);
                viewReports.setVisible(false);
                viewReports.setManaged(true);
                break;
            case CustomerServiceWorker:
                watchBranchesCapacity.setVisible(true);
                watchBranchesCapacity.setManaged(true);
                watchComplaints.setVisible(true);
                watchComplaints.setManaged(true);
                break;
            case Empty:
            default:
                connectButton.setVisible(true); // Show login button if not logged in
                connectButton.setManaged(true);
                break;
        }
    }

    @Subscribe
    public void onUserTypeUpdated(UserType type) {
        Platform.runLater(this::toggleButtons); // Update buttons on user type change
    }

    @FXML
    void showAbout(ActionEvent event) throws IOException {
        App.setRoot("about-us-page");
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
        App.setRoot("MainMenu");
    }

    @FXML
    void toConnect(ActionEvent event) throws IOException {
        App.setRoot("hello-view");
    }

    @FXML
    void toReserveAtable(ActionEvent event) throws IOException {
        App.setRoot("TableOrderScreen");
    }

    @FXML
    void editMenu(ActionEvent event) throws IOException {
        App.setRoot("EditMenuScreen");
    }

    @FXML
    void orderDelivery(ActionEvent event) throws IOException {
        MainMenuController controller = App.setRootAndGetController("MainMenu");
        controller.reinitialize(true);
    }

    @FXML
    void cancelDeliveryOrReservation(ActionEvent event) throws IOException {
        App.setRoot("cancel-delivey");
    }

    @FXML
    void fileComplaint(ActionEvent event) throws IOException {
        App.setRoot("chose-kind-complaint");
    }

    @FXML
    void watchComplaints(ActionEvent event) throws IOException {
        App.setRoot("view-complaints");
    }

    @FXML
    void watchBranchesCapacity(ActionEvent event) throws IOException {
        App.setRoot("WatchBranchesCapacityScreen");
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public void EditMenu(ActionEvent actionEvent) throws IOException {
        App.setRoot("EditMenu");
    }

    public void goToReports(ActionEvent actionEvent) throws IOException {
        App.setRoot("pickReport");
    }
}