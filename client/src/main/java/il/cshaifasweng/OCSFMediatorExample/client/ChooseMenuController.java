package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ChooseMenuController {
    private MenuClient chosenMenu = null;
    private boolean isCreateCopy = false;
    private boolean isSubmit = false;

    @FXML
    public HBox menuListContainer;

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
            List<MenuClient> menuList = MenuFactory.getMenus();
            // Use Platform.runLater to update menuListContainer
            Platform.runLater(() -> {
                menuListContainer.getChildren().clear();
                for (MenuClient menu : menuList) {
                    addMenuToMenuListContainer(menu);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public void setCreateCopy(boolean createCopy) {
        isCreateCopy = createCopy;
    }

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

    private void addMenuToMenuListContainer(MenuClient menu) {
        if (menu != null) {
            ScrollPane scroller = new ScrollPane();
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            Button nameButton = new Button(menu.getMenuName());
            nameButton.setOnAction(event -> chooseMenu(menu));
            vbox.getChildren().add(nameButton);
            Node node = createMenuNode(menu);
            vbox.getChildren().add(node);
            scroller.setContent(vbox);
            // Use Platform.runLater to add the scroller to menuListContainer
            Platform.runLater(() -> {
                menuListContainer.getChildren().add(scroller);
            });
        }
    }

    private Node createMenuNode(MenuClient menu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Node node = loader.load();
            MenuController menuController = loader.getController();
            menuController.reinitialize(false, false);
            menuController.setMenu(menu);
            return node;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void chooseMenu(MenuClient menu) {
        if (isCreateCopy) {
            chosenMenu = createCopy(menu);
        } else if (!isSubmit) {
            chosenMenu = menu;
        } else {
            // Use Platform.runLater to handle navigation
            Platform.runLater(() -> {
                try {
                    goToHomePage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return;
        }
        goToEditMenu(chosenMenu);
    }

    // Placeholder, should be at server
    private MenuClient createCopy(MenuClient menu) {
        return menu;
    }

    private void goToEditMenu(MenuClient menu) {
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditMenu.fxml"));
                Parent root = loader.load();
                EditMenuController controller = loader.getController();
                controller.setMenu(menu);
                Scene currentScene = menuListContainer.getScene();
                currentScene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void goToHomePage() throws IOException {
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