/**
 * Sample Skeleton for 'request-report.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class RequestReport {

    @FXML // fx:id="send_button"
    private Button send_button; // Value injected by FXMLLoader

    @FXML
    void send_report(ActionEvent event) throws IOException {
       //write here message to send the server so it will send the request
        App.setRoot("manager_personal_page");
    }
    @FXML
    void to_go_back(ActionEvent event) throws IOException {
        App.setRoot("manager_personal_page");
    }


}
