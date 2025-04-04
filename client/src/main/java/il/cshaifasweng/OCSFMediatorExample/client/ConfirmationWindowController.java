package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

public class ConfirmationWindowController implements PopupController<String, Boolean> {

    private Boolean output = null;
    private boolean submitted = false;

    @FXML private Label confirmationLabel;

    @FXML
    private void handleYes() {
        output = true;
        submitted = true;
        closeWindow();
    }

    @FXML
    private void handleNo() {
        output = false;
        submitted = true;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmationLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void reInitialize(String message) {
        confirmationLabel.setText(message);
    }

    @Override
    public Boolean getOutput() {
        if (submitted){
            return output;
        }else{
            return null;
        }
    }
}
