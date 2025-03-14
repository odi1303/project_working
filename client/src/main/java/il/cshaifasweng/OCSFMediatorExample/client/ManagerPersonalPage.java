
/**
 * Sample Skeleton for 'manager_personal_page.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ManagerPersonalPage {

    @FXML // fx:id="SuggestionsButton"
    private Button SuggestionsButton; // Value injected by FXMLLoader

    @FXML // fx:id="requestReportsButton"
    private Button requestReportsButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewReportsButton"
    private Button viewReportsButton; // Value injected by FXMLLoader

    @FXML
    void OpenSuggestions(ActionEvent event) throws IOException {
        App.setRoot("suggestion-view");
    }

    @FXML
    void requestReports(ActionEvent event) throws IOException {
        App.setRoot("request-report");
    }

    @FXML
    void viewReports(ActionEvent event) throws IOException {
        App.setRoot("reports-view");
    }

}
