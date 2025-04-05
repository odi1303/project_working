package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class BranchesOpeningTimesPopupController implements PopupController<List<String>, Void> {

    @FXML
    private VBox branchesContainer;

    @FXML
    private Button close;

    private boolean submitted = false;

    private String getBranchOpeningTime(String branchName) {
        // Replace this with your actual function
        return getOpeningTime(branchName);
    }

    @Override
    public void reInitialize(List<String> branches) {
        branchesContainer.getChildren().clear();

        for (String branch : branches) {
            String openingTime = getBranchOpeningTime(branch);
            Label branchLabel = new Label(branch + " - Opening Time: " + openingTime);
            branchesContainer.getChildren().add(branchLabel);
        }
    }

    @Override
    public Void getOutput() {
        return null;
    }

    @FXML
    private void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    private String getOpeningTime(String branchName) {
        return "8:00";
    }
}
