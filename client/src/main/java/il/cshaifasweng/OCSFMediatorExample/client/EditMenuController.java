package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class EditMenuController {

    private MenuClient menu;

    @FXML
    public VBox controlSection;
    @FXML
    public VBox menuContainer;

    @FXML
    public HBox editDishContainer;

    @FXML
    public TextField menuName;

    private MenuController menuController;

    private EditDishController editDishController;

    @FXML
    public void initialize() {
        //dynamic create the sections

        //dynamically create menu section
        try {
            EventBus.getDefault().register(this);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Node node = loader.load();
            menuController = loader.getController();
            menuController.reinitialize(false, false);
            menuController.setMenu(menu);
            menuController.setEdit(this);
            menuContainer.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //dynamically create editDish section
        try {
            EventBus.getDefault().register(this);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDish.fxml"));
            Node node = loader.load();
            editDishController = loader.getController();
            editDishContainer.setVisible(false);
            editDishContainer.setManaged(false);
            editDishContainer.getChildren().clear();
            editDishContainer.getChildren().add(node);
            editDishController.isSubmitted.addListener((observable, oldValue, newValue) -> handleEditDishIsSubmittedChange(oldValue, newValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public void deleteDishPressed(DishClient dish) {
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "are you sure you want to delete the dish?", (Stage) controlSection.getScene().getWindow());
            if (isConfirmed) {
                menu.removeDish(dish);
                menuController.setMenu(menu);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void EditDishPressed(DishClient dish) {
        EditDish(dish);
    }

    private void handleEditDishIsSubmittedChange(boolean oldValue ,boolean newValue) {
        if (newValue && (!oldValue)) {
            editDishContainer.setVisible(false);
            editDishContainer.setManaged(false);
            if(!editDishController.isEdit()) {
                if (!menu.isContainsDish(editDishController.getDish())) {
                    addDishToMenu(editDishController.getDish());
                }else{
                    try {
                        PopupDialogService popupDialogService = new PopupDialogService();
                        popupDialogService.openPopup("InformationWindow.fxml", "This dish is already in the manu.", (Stage) controlSection.getScene().getWindow());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else if (!editDishController.getDish().equals(editDishController.getOriginalDish())) {
                menu.removeDish(editDishController.getOriginalDish());
                addDishToMenu(editDishController.getDish());
            }
        }
    }

    public void setMenu(MenuClient menu) {
        this.menu = menu;
        menuName.setText(menu.getMenuName());
        menuController.setMenu(menu);

    }

    @FXML
    private void addNewDish(){
        editDishController.setDish(new DishClient());
        editDishController.setEdit(false);

        editDishContainer.setVisible(true);
        editDishContainer.setManaged(true);
    }
    @FXML
    private void addDishFromDatabase(){}

    private void EditDish(DishClient dish) {
        editDishController.setDish(dish);
        editDishController.setEdit(true);
        editDishContainer.setVisible(true);
        editDishContainer.setManaged(true);
    }

    @FXML
    private void saveChanges(){
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "are you sure you want to save the changes?", (Stage) controlSection.getScene().getWindow());
            if (isConfirmed) {
                goToHomePage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDishToMenu(DishClient dish) {
        menu.addDish(dish);
        menuController.setMenu(menu);
    }

    @FXML
    public void goToHomePage() throws IOException {
        App.setRoot("home-page");
    }

}
