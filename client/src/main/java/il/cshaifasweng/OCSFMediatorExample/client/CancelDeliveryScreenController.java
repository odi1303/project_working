package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.OrderCancellationFeeRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuItem;
import il.cshaifasweng.OCSFMediatorExample.entities.models.OrderClient;
import il.cshaifasweng.OCSFMediatorExample.entities.models.OrderItem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class CancelDeliveryScreenController {

    @FXML
    private VBox orderTable;

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
            App.sendMessageToServer("send all orders");
            //ArrayList<OrderClient> orders = HardcodedOrders.createHardcodedOrders();
            // Use Platform.runLater to add orders to orderTable
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @Subscribe
    public void on_respond(List<OrderClient> orders) {
        Platform.runLater(() -> {
            for (OrderClient order : orders) {
                addOrderToVBox(order);
            }
        });

    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
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

    private void addOrderToVBox(OrderClient order) {
        ScrollPane scrollPane = new ScrollPane();
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        Button cancelButton = new Button("Cancel order");
        cancelButton.setOnAction(event -> removeOrder(order, scrollPane));
        hBox.getChildren().add(cancelButton);

        for (OrderItem pair : order.getOrderItems()) {
            MenuItem dish = pair.getMenuItem();
            int count = pair.getQuantity();
            Node node = createDishCountFxmlNode(dish, count);
            hBox.getChildren().add(node);
        }

        scrollPane.setContent(hBox);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Use Platform.runLater to add the scrollPane to orderTable
        Platform.runLater(() -> {
            orderTable.getChildren().add(scrollPane);
        });
    }

    private Node createDishCountFxmlNode(MenuItem dish, int count) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Label dishCountLabel = new Label("count: " + count);

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

        hBox.getChildren().add(dishCountLabel);
        return hBox;
    }

    private void removeOrder(OrderClient order, ScrollPane scrollPane) {
        PopupDialogService popupDialogService = new PopupDialogService();
        // Use Platform.runLater to handle popup and UI updates
        Platform.runLater(() -> {
            try {
                boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "are you sure you want to delete the order?", (Stage) orderTable.getScene().getWindow());
                if (isConfirmed) {
                    boolean confirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "you will be required to pay: " + String.valueOf(getRequiredOrderCancellationFee(order)), (Stage) orderTable.getScene().getWindow());
                    if (confirmed) {
                        orderTable.getChildren().remove(scrollPane);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private double getRequiredOrderCancellationFee(OrderClient order){
        try {
            Double result = RequestManager.getInstance().sendAndWait(
                    new OrderCancellationFeeRequest(order),
                    5000,
                    OrderCancellationFeeRequest.class,
                    Double.class
            );
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}