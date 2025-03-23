package il.cshaifasweng.OCSFMediatorExample.client;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.Stage;


public class InformationWindowController implements PopupController{
    private String output;

    @FXML
    private Label informationLabel;


    @FXML
    public void closeWindow() {
        output= "Abort";
        Stage stage = (Stage)informationLabel.getScene().getWindow();
        stage.close(); //if the controller has a reference it can steel be called.
    }


    @Override
    public void reInitialize(String input) {
        informationLabel.setText(input);
        output = "Abort";
    }

    @Override
    public String getOutput() {
        return output;
    }
}
