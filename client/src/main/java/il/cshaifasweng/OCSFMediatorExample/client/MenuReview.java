package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.CompactMenu;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class MenuReview {
    @FXML
    public void initialize() {
        try {
            System.out.println("Initializing Secondary Controller");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    CompactMenu compactMenu = (CompactMenu) App.menu;

    @FXML
    private ComboBox<String> MenuList;

    @FXML
    private Label statusLabel;

    @FXML
    void ChooseDish(ActionEvent event) {
        String choice = MenuList.getSelectionModel().getSelectedItem();
        boolean sent = false;
        while (!sent) {
            try {
                SimpleClient.getClient().sendToServer("GetDishInfo:" + choice);
                sent = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(() -> {
            statusLabel.setText("loading " + choice);
        });
    }

    @Subscribe
    public void onEvent(Object event) {
        if (event instanceof CompactMenu) {
            compactMenu = (CompactMenu) event;
            var list = FXCollections.observableList(compactMenu.dishes);
            System.out.println("the length of list is :" + list.size());
            Platform.runLater(() -> {
                MenuList.setItems(list);
            });
        }
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}