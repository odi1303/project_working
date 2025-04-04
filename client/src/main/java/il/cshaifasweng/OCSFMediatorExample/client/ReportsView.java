/**
 * Sample Skeleton for 'reports-view.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ReportsView {

    @FXML // fx:id="back_button"
    private Button back_button; // Value injected by FXMLLoader

    @FXML // fx:id="ch"
    private AnchorPane ch; // Value injected by FXMLLoader

    @FXML // fx:id="chart"
    private LineChart<?, ?> chart; // Value injected by FXMLLoader

    @FXML // fx:id="report_list"
    private ComboBox<?> report_list; // Value injected by FXMLLoader

    @FXML
    void choosing_report(ActionEvent event) {

    }
    @FXML
    void to_go_back(ActionEvent event) throws IOException {
        App.setRoot("manager_personal_page");
    }

}

