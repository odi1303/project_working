package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import il.cshaifasweng.OCSFMediatorExample.entities.UsersRepository;
import org.greenrobot.eventbus.EventBus;

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
            throw new IllegalArgumentException("Please enter your username and password");
        }

        String userName = username_field.getText();
        String password = password_field.getText();

        UsersRepository usersRepository = new UsersRepository();
        int user_type = usersRepository.searchUser(userName, password);

        // Use Platform.runLater to handle UI updates and navigation
        Platform.runLater(() -> {
            switch (user_type) {
                case 1:
                    try {
                        App.setRoot("client_personal_page");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        App.setRoot("employee_personal_page");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        App.setRoot("editMenuScreen");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        App.setRoot("reports_view");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        App.setRoot("manager_personal_page");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    wrongDetails.setText("Incorrect username or password");
            }
        });
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void create_an_account(ActionEvent actionEvent) throws IOException {
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                App.setRoot("SignUp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}