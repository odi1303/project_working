package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import il.cshaifasweng.OCSFMediatorExample.entities.models.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button connectButton;
    @FXML
    private PasswordField password_field;
    @FXML
    private TextField username_field;
    @FXML
    private Label welcomeText;
    @FXML
    private Label wrongDetails;
    @FXML
    private ChoiceBox<String> userType;
    @FXML
    private ImageView home_icon;

    @FXML
    public void initialize() {
        userType.getItems().setAll("User", "Admin", "Employee", "Dietitian", "BranchManager", "ChainManager", "CustomerServiceWorker");
        EventBus.getDefault().register(this);
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @FXML
    void onConnectButtonClick(ActionEvent event) {
        try {
            if (username_field.getText().isEmpty() || password_field.getText().isEmpty()) {
                wrongDetails.setText("Please enter username and password");
                return;
            }
            String username = username_field.getText().trim();
            String password = password_field.getText().trim();
            User user = new User(username, password, null);
            App.sendMessageToServer(user);
            wrongDetails.setText("Verifying credentials...");
        } catch (IOException e) {
            wrongDetails.setText("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onUserTypeReceived(UserType type) {
        Platform.runLater(() -> {
            if (type == UserType.Empty) {
                wrongDetails.setText("Incorrect username or password");
            } else {
                try {
                    App.saveClientDetails(username_field.getText(), password_field.getText(), type);
                    wrongDetails.setText("Login successful!");
                } catch (IOException e) {
                    wrongDetails.setText("Navigation error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }

    @FXML
    void create_an_account(ActionEvent event) throws IOException {
        App.setRoot("SignUp");
    }
}