package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.GetBranchOpeningTimes;
import il.cshaifasweng.OCSFMediatorExample.entities.OpeningTimes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class BranchesOpeningTimesPopupController implements PopupController<List<String>, Void> {

    @FXML
    private VBox branchesContainer;

    @FXML
    private Button close;

    private String openingTimeResponse = null;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
    }

    private String getOpeningTime(String branchName) {
        boolean sent = false;
        openingTimeResponse = null;

        while (!sent) {
            try {
                SimpleClient.getClient().sendToServer(new GetBranchOpeningTimes(branchName));
                sent = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        synchronized (this) {
            while (openingTimeResponse == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return openingTimeResponse;
    }

    @Subscribe
    public void reciveOpeningTimes(OpeningTimes openingTimes) {
        if (openingTimes != null) {
            openingTimeResponse = openingTimes.getOpeningTime();
            synchronized (this) {
                notify();
            }
        }
    }

    @Override
    public void reInitialize(List<String> branches) {
        branchesContainer.getChildren().clear();

        for (String branch : branches) {
            String openingTime = getOpeningTime(branch);
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
