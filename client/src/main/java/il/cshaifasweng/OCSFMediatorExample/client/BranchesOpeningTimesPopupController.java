package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.GetBranchOpeningTimes;
import il.cshaifasweng.OCSFMediatorExample.entities.OpeningTimes;
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

    @FXML
    public void initialize() {
    }

    private String getOpeningTime(String branchName) {
        try {
            OpeningTimes response = RequestManager.getInstance().sendAndWait(
                    new GetBranchOpeningTimes(branchName),
                    5000,
                    GetBranchOpeningTimes.class,
                    OpeningTimes.class
            );
            return response.getOpeningTime();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unavailable";
        }
    }

    @Override
    public void reInitialize(List<String> branches) {
        branchesContainer.getChildren().clear();

        for (String branch : branches) {
            String openingTime = getOpeningTime(branch);
            System.out.println(openingTime);
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
}
