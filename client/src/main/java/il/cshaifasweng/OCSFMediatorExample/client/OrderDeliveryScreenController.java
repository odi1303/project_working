package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class OrderDeliveryScreenController {

    @FXML
    private ComboBox<String> branchComboBox;

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private void selectCity(ActionEvent event) {
        //add here



        String selectedCity = cityComboBox.getValue();
        if (selectedCity != null && !selectedCity.isEmpty()) {
            branchComboBox.setVisible(true);
        }

    }

    @FXML
    private void selectBranch(ActionEvent event) {

    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }

}
