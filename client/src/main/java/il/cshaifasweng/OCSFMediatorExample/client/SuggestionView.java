/**
 * Sample Skeleton for 'suggestion-view.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class SuggestionView {

    @FXML // fx:id="back_button"
    private Button back_button; // Value injected by FXMLLoader

    @FXML // fx:id="status_col"
    private TableColumn<?, ?> status_col; // Value injected by FXMLLoader

    @FXML // fx:id="suggestion_table"
    private TableView<?> suggestion_table; // Value injected by FXMLLoader

    @FXML
    void to_go_back(ActionEvent event) throws IOException {
        App.setRoot("manager_personal_page");
    }

}
