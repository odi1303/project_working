package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.CanBeMadeInSameDateRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.SubmitionRequestToNetworkManager;
import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuClient;
import javafx.application.Platform;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ChooseMenuController {
    private MenuClient chosenMenu = null;
    private boolean isCreateCopy = false;
    private boolean isSubmit = false;

    @FXML
    public HBox menuListContainer;

    @FXML
    public void initialize() {
        try {
            @SuppressWarnings("unchecked")
            List<MenuClient> menuList = (List<MenuClient>) RequestManager.getInstance().sendAndWait(
                    "get all menus",
                    5000,
                    String.class,
                    List.class
            );
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
        if (isSubmit){
            submitMenu(menu);
            return;
        }
        else if (isCreateCopy) {
            chosenMenu = new MenuClient(menu);
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

    private void submitMenu(MenuClient menu) {
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "submit menu to network manager?", (Stage) menuListContainer.getScene().getWindow());
            if (isConfirmed) {
                isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "submit?", (Stage) menuListContainer.getScene().getWindow());
                if (isConfirmed) {
                    boolean issubmitted = sendSubmitionRequestToNetworkManager(menu);
                    if (issubmitted) {
                        popupDialogService.openPopup("InformationWindow.fxml", "menu summited", (Stage) menuListContainer.getScene().getWindow());
                        goToHomePage();
                    }else{
                        popupDialogService.openPopup("InformationWindow.fxml", "submission failed", (Stage) menuListContainer.getScene().getWindow());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean sendSubmitionRequestToNetworkManager(MenuClient menu) {
        try {
            Boolean result = RequestManager.getInstance().sendAndWait(
                    new SubmitionRequestToNetworkManager(menu),
                    5000,
                    SubmitionRequestToNetworkManager.class,
                    Boolean.class
            );
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void goToEditMenu(MenuClient menu) {
        // Use Platform.runLater to handle scene navigation
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditMenu.fxml"));
                Parent root = loader.load();
                EditMenuController controller = loader.getController();
                Platform.runLater(() -> controller.setMenu(menu, isCreateCopy));
                Scene currentScene = menuListContainer.getScene();
                currentScene.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void goToHomePage() throws IOException {
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}