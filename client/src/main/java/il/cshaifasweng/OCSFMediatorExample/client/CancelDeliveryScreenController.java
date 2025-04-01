package il.cshaifasweng.OCSFMediatorExample.client;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import java.io.IOException;
import java.util.ArrayList;

public class CancelDeliveryScreenController {

    @FXML
    private VBox orderTable;

    @FXML
    public void initialize() {
        ArrayList<OrderClient> orders = HardcodedOrders.createHardcodedOrders();
        for (OrderClient order : orders) {
            addOrderToVBox(order);
        }
    }

    @FXML
    private void goToHomePage() throws IOException {
        App.setRoot("home-page");
    }

    private void addOrderToVBox(OrderClient order) {
        ScrollPane scrollPane = new ScrollPane();

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        //add cancel button
        Button cancelButton = new Button("Cancel order");
        cancelButton.setOnAction(event -> removeOrder(scrollPane));
        hBox.getChildren().add(cancelButton);

        for (Pair<DishClient, Integer> pair : order.getOrderDishesList()){
            DishClient dish = pair.getKey();
            int count = pair.getValue();
            Node node = createDishCountFxmlNode(dish, count);
            hBox.getChildren().add(node);
        }
//        orderTable.getChildren().add(hBox);


        scrollPane.setContent(hBox);            // Put the HBox inside the ScrollPane
        scrollPane.setFitToWidth(false);         // Make it fill available width
        scrollPane.setFitToHeight(true);        // Make it fill available height
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Add the ScrollPane to the VBox (orderTable)
        orderTable.getChildren().add(scrollPane);
    }

    private Node createDishCountFxmlNode(DishClient dish, int count) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Label dishCountLabel = new Label("count: " + count);

        //add dish section
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DishSectionInMenu.fxml"));
            Node dishNode = fxmlLoader.load();
            DishSectionInMenuController dishSectionController = fxmlLoader.getController();
            dishSectionController.setDish(dish);
            dishSectionController.setDishDataInDishSection();

            hBox.getChildren().add(dishNode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //add count label
        hBox.getChildren().add(dishCountLabel);

        return hBox;
    }

    private void removeOrder(ScrollPane scrollPane) {
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "are you shure you want to delete the order?", (Stage) orderTable.getScene().getWindow());
            if (isConfirmed) {
                boolean confirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "you will be required to pay ___.", (Stage) orderTable.getScene().getWindow());
                if (confirmed) {
                    orderTable.getChildren().remove(scrollPane);
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
