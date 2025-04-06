package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import il.cshaifasweng.OCSFMediatorExample.entities.GetUserType;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button connectButton;

    @FXML
    private Hyperlink create_accont_button;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField username_field;

    @FXML
    private Label welcomeText;

    @FXML
    private Label wrongDetails;

    public void initialize() {
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @FXML
    void onConnectButtonClick(ActionEvent event) throws IOException {
        if (username_field.getText().isEmpty() || password_field.getText().isEmpty()) {
            wrongDetails.setText("Please enter your username and password");
            return;
        }

        String userName = username_field.getText();
        String password = password_field.getText();

        // Send authentication request to server
        GetUserType authRequest = new GetUserType(userName, password);
        try {
            App.sendMessageToServer(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
            wrongDetails.setText("Connection error");
        }
    }

    @Subscribe
    public void onUserTypeReceived(UserType userType) {
        Platform.runLater(() -> {
            if (userType == UserType.Empty) {
                wrongDetails.setText("Incorrect username or password");
                return;
            }

            // Fetch full user details (you'll need to implement this in SimpleServer)
            try {
                App.sendMessageToServer("#getUserDetails:" + username_field.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Subscribe
    public void onUserReceived(User user) {
        Platform.runLater(() -> {
            if (user == null) {
                wrongDetails.setText("Error fetching user details");
                return;
            }

            // Store the current user
            AppState.setCurrentUser(user);

            // Navigate based on user type
            try {
                switch (user.getType()) {
                    case Employee:
                        App.setRoot("employee_personal_page");
                        break;
                    case Admin:
                        App.setRoot("editMenuScreen");
                        break;
                    case BranchManager:
                        App.setRoot("reports_view");
                        break;
                    default:
                        wrongDetails.setText("Unknown user type");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void create_an_account(ActionEvent actionEvent) throws IOException {
        Platform.runLater(() -> {
            try {
                App.setRoot("SignUp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}