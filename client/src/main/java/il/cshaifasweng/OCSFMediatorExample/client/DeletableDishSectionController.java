package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class DeletableDishSectionController {
    private MenuController menuController;

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

    public void reinitialize(MenuController menuController) {
        setMainMenuController(menuController);
    }

    private void setMainMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @FXML
    private void deleteDish(){
        if (menuController != null && dishSectionController != null) {
            menuController.deleteDishPressed(dishSectionController.getDishClient());
        }
    }

    @FXML
    public void EditDish(){
        if (menuController != null && dishSectionController != null) {
            menuController.EditDishPressed(dishSectionController.getDishClient());
        }
    }

    public void setDishInDishSection(DishClient dish){
        dishSectionController.setDish(dish);
    }
    public void setDishDataInDishSection(){
        dishSectionController.setDishDataInDishSection();
    }
}
