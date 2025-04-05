/**
 * Sample Skeleton for 'about-us-page.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AboutUsPage {

    @FXML // fx:id="grand_branch"
    private Button grand_branch; // Value injected by FXMLLoader

    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    @FXML // fx:id="kiryon_branch"
    private Button kiryon_branch; // Value injected by FXMLLoader

    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }

    @FXML
    void show_grand(ActionEvent event) {

    }

    @FXML
    void show_kiryon(ActionEvent event) {

    }

}
