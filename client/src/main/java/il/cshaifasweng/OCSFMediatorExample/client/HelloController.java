package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import il.cshaifasweng.OCSFMediatorExample.entities.UsersRepository;

import java.io.IOException;

public class HelloController {
    //private final UsersRepository usersRepository = new UsersRepository();
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
        if (App.type!=null){
            UserType user_type = App.type;
            switch (user_type) {
                case User:
                    App.setRoot("client_personal_page");
                    break;
                case employee:
                    App.setRoot("employee_personal_page");
                    break;
                case dietician:
                    App.setRoot("editMenuScreen");
                    break;
                case chainManager:
                    App.setRoot("reports_view");
                    break;
                case branchManager:
                    App.setRoot("manager_personal_page");
                    break;
                default:
                    wrongDetails.setText("Incorrect username or password");
            }
        }
        if (username_field.getText().isEmpty() || password_field.getText().isEmpty()) {
            wrongDetails.setText("Please enter your username and password");
            return;
        }

        String userName = username_field.getText();
        String password = password_field.getText();
        App.sendMessageToServer("is user exists?("+userName+","+password+")");

        UsersRepository usersRepository=new UsersRepository();






    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }

    public void create_an_account(ActionEvent actionEvent) throws IOException {
        App.setRoot("SignUp");
    }
}