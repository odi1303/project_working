package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private HBox menuContainer;

    private MenuController menuController;

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Node node = loader.load();
            menuController = loader.getController();
            menuContainer.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
    
    public void reinitialize(boolean isOrder){
        menuController.reinitialize(isOrder, true);
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}
