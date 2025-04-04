package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EditMenuScreenController {

    @FXML
    private void createNewMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditMenu.fxml"));
            Parent root = loader.load();
            EditMenuController controller = loader.getController();
            controller.setMenu(new MenuClient());
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createMenuFromCopy(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseMenu.fxml"));
            Parent root = loader.load();
            ChooseMenuController controller = loader.getController();
            controller.setCreateCopy(true);
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editExistingMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseMenu.fxml"));
            Parent root = loader.load();
            ChooseMenuController controller = loader.getController();
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void submitMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseMenu.fxml"));
            Parent root = loader.load();
            ChooseMenuController controller = loader.getController();
            controller.setSubmit(true);
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}
