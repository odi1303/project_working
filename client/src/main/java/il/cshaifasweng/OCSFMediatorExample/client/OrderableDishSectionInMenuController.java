package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class OrderableDishSectionInMenuController {
    private MainMenuController mainMenuController;

    private DishSectionInMenuController dishSectionController;

    @FXML
    private HBox includedDishSection;

    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DishSectionInMenu.fxml"));
            Node dishNode = loader.load();
            dishSectionController = loader.getController(); // Properly access the controller
            includedDishSection.getChildren().add(dishNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reinitialize(MainMenuController mainMenuController) {
        setMainMenuController(mainMenuController);
    }

    private void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    private void orderDish(){
        if (mainMenuController != null && dishSectionController != null) {
            mainMenuController.orderDish(dishSectionController.getDishClient());
        }
    }

    public void setDishInDishSection(DishClient dish){
        dishSectionController.setDish(dish);
    }
    public void setDishDataInDishSection(){
        dishSectionController.setDishDataInDishSection();
    }
}
