package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private HBox menuContainer;

    private MenuController menuController;

    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Node node = loader.load();
            menuController = loader.getController();
            menuContainer.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void reinitialize(boolean isOrder){
        menuController.reinitialize(isOrder);
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}
