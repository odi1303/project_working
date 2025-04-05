package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import org.greenrobot.eventbus.EventBus;

public class TableAllocController {
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
}
