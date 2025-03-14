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
    private PasswordField password; // Value injected by FXMLLoader

    @FXML // fx:id="username"
    private TextField username; // Value injected by FXMLLoader

    @FXML // fx:id="welcomeText"
    private Label welcomeText; // Value injected by FXMLLoader

    @FXML // fx:id="wrongDetails"
    private Label wrongDetails; // Value injected by FXMLLoader


    @FXML
    void create_an_account(ActionEvent event) {

    }

    @FXML
        //check if the details are correct, if they not show an error
    void onConnectButtonClick(ActionEvent event) throws IOException {
        int user = -1;
        try {
            user = Integer.parseInt(username.getText());
        } catch (NumberFormatException e) {
            wrongDetails.setText("Invalid username, the user name should only contain numbers");
        }
        if(user==1){
            if (password.getText().equals("manager1"))
                App.setRoot("manager_personal_page");
        }
        else if(user==2){
            if (password.getText().equals("manager2"))
                App.setRoot("manager_personal_page");
        }
        else if(user==-1||password.getText().isEmpty()){
            wrongDetails.setText("Please fill all the fields");
        } else if (user==3) {
            if (password.getText().equals("client3"))
                App.setRoot("client_personal_page");

        } else {
            wrongDetails.setText("One or more fields are wrong, please try again");
        }




    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }

}
/*App.sendMessageToServer("exist? "+username.getText());
        if (user == -1 || password.getText().isEmpty()) {
            wrongDetails.setText("Please fill all the fields");
        } else if (!usersRepository.userExists(user)) {
            wrongDetails.setText("User not found, you can create a new account");
        } else if (usersRepository.correctPassword(user, password.getText())) {
            if (usersRepository.getUserType(user, password.getText()) == UserType.valueOf("Admin")) {
                HelloApplication.setRoot("manager_personal_page.fxml");
            }
        } else {
            HelloApplication.setRoot("manager_personal_page.fxml");
            wrongDetails.setText("One or more fields are wrong, please try again");
        }*/