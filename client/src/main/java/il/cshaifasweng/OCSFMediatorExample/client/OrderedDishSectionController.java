package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class OrderedDishSectionController {

    @FXML private Label countLabel;

    @FXML
    private HBox includedDishSection;

    private static final int MAX_COUNT = Integer.MAX_VALUE;
    private int count;

    private DishSectionInMenuController dishSectionController;

    @FXML
    public void initialize() {
        count = 1;
        countLabel.setText(String.valueOf(count));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DishSectionInMenu.fxml"));
            Node node = loader.load();
            dishSectionController = loader.getController(); // Properly access the controller
            includedDishSection.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void increasCount() {
        if (count < MAX_COUNT) {
            count++;
            countLabel.setText(String.valueOf(count));
        }
    }

    @FXML
    public void decreaseCount() {
        if (count > 0) {
            count--;
            countLabel.setText(String.valueOf(count));
        }
    }

    public void setDishInDishSection(DishClient dish){
        dishSectionController.setDish(dish);
    }
    public void setDishDataInDishSection() {
        dishSectionController.setDishDataInDishSection();
    }
}
