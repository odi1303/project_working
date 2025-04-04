package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.GetUserType;
import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import il.cshaifasweng.OCSFMediatorExample.entities.VerifyPassword;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import il.cshaifasweng.OCSFMediatorExample.entities.UsersRepository;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Optional;

public class HelloController {
    @FXML // fx:id="connectButton"
    private Button connectButton; // Value injected by FXMLLoader

    @FXML // fx:id="create_accont_button"
    private Hyperlink create_accont_button; // Value injected by FXMLLoader

    @FXML // fx:id="password"
    private PasswordField password_field; // Value injected by FXMLLoader

    @FXML // fx:id="username"
    private TextField username_field; // Value injected by FXMLLoader

    @FXML // fx:id="welcomeText"
    private Label welcomeText; // Value injected by FXMLLoader

    @FXML // fx:id="wrongDetails"
    private Label wrongDetails; // Value injected by FXMLLoader



    @FXML
        //check if the details are correct, if they not show an error
    void onConnectButtonClick(ActionEvent event) throws IOException {
        if (username_field.getText().isEmpty() || password_field.getText().isEmpty()) {
            throw new IllegalArgumentException("Please enter your username and password");
        }

        String userName = username_field.getText();
        String password = password_field.getText();

        boolean sent = false;
        while (!sent) {
            try {
                SimpleClient.getClient().sendToServer(new GetUserType(userName, password));
                sent = true;
                System.out.println("sent to server");
            } catch (Exception ignored) {
            }
        }
    }

    @Subscribe
    public void onUserType(UserType userType) {
        EventBus.getDefault().unregister(this);
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
        EventBus.getDefault().unregister(this);
    }

    public void create_an_account(ActionEvent actionEvent) throws IOException {
        App.setRoot("SignUp");
        EventBus.getDefault().unregister(this);
    }

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
    }
}