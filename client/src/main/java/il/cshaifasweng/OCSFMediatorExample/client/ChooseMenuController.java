package il.cshaifasweng.OCSFMediatorExample.client;

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
        List<MenuClient> menuList = MenuFactory.getMenus();
        menuListContainer.getChildren().clear();
        for (MenuClient menu : menuList) {
            addMenuToMenuListContainer(menu);
        }
    }

    public void setCreateCopy(boolean createCopy) {
        isCreateCopy = createCopy;
    }
    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

    private void addMenuToMenuListContainer(MenuClient menu) {
        if(menu != null) {
            ScrollPane scroller = new ScrollPane();
            //create vbox
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);
            //create button
            Button nameButton = new Button(menu.getMenuName());
            nameButton.setOnAction(event -> chooseMenu(menu));

            vbox.getChildren().add(nameButton);
            //create menu
            Node node = createMenuNode(menu);

            vbox.getChildren().add(node);
            scroller.setContent(vbox);
            menuListContainer.getChildren().add(scroller);
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
        }else if(!isSubmit) {
            chosenMenu = menu;
        }else{
            try {
                goToHomePage();
                return;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        goToEditMenu(chosenMenu);

    }
    //placeholder, should be at server.
    private MenuClient createCopy(MenuClient menu) {
        return menu;
    }

    private void goToEditMenu(MenuClient menu){
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
    }

    @FXML
    private void goToHomePage() throws IOException {
        App.setRoot("home-page");
    }
}
