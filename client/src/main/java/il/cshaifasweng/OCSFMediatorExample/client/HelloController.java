package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import il.cshaifasweng.OCSFMediatorExample.entities.UsersRepository;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

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
    @FXML // fx:id="userType"
    private ChoiceBox<String> userType=new ChoiceBox<>(); // Value injected by FXMLLoader
    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    public void initialize() {
        userType.getItems().addAll("User", "Admin", "Employee", "Dietitian", "BranchManager", "ChainManager", "CustomerServiceWorker");
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
        //check if the details are correct, if they not show an error
    void onConnectButtonClick(ActionEvent event) throws IOException {
        if (username_field.getText().isEmpty() || password_field.getText().isEmpty()||userType.getValue().isEmpty()) {
            throw new IllegalArgumentException("Please enter your username and password");
        }

        String userName = username_field.getText();
        String password = password_field.getText();
        UserType user_type = UserType.valueOf(userType.getValue());

        User temp_user=new User(userName,password,user_type);
        App.sendMessageToServer(temp_user);
        switch (user_type) {
            case UserType.User:
                App.setRoot("client_personal_page");
                break;
            case UserType.Employee:
                App.setRoot("employee_personal_page");
                break;
            case UserType.Dietitian:
                App.setRoot("editMenuScreen");
                break;
            case UserType.CustomerServiceWorker:
                App.setRoot("reports_view");
                break;
            case UserType.Admin:
                App.setRoot("manager_personal_page");
                break;
            default:
                wrongDetails.setText("Incorrect username or password");
        }
    }

    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }

    public void create_an_account(ActionEvent actionEvent) throws IOException {
        App.setRoot("SignUp");
    }
}