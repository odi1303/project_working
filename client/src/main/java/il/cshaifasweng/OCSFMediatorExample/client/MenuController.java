package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.max;

public class MenuController {

    @FXML
    private VBox orderSection;

    @FXML
    private HBox orderDishList;

    @FXML
    private VBox menuDishList;

    @FXML
    private VBox branchCheckboxContainerForFilter;

    @FXML
    private VBox IngredientsCheckboxContainerForFilter;

    private MenuClient fullMenu;

    private MenuClient currentMenu;

    private ArrayList<DishClient> dishesInOrder;
    private boolean isOrder;
    private boolean isDelete = false;
    private boolean isMain; // used to differentiate between menu used for main menu or used for un-inputted menu

    private List<Pair<DishClient, Label>> orderDishNodeCountLabelPair = new ArrayList<>();
    private EditMenuController editMenuController;

    @FXML
    public void initialize() {
        try {
//            EventBus.getDefault().register(this);
            isMain = true;
            fullMenu = new MenuClient();
            List<String> branches = fullMenu.getAllBranches();
            List<String> ingredients = fullMenu.getAllIngredients();
            putBranchCheckBoxesInFilter(branches);
            putIngredientsCheckBoxesInFilter(ingredients);

            List<DishClient> dishes = getHardcodedDishes();
            fullMenu = new MenuClient(new ArrayList<>(dishes));
            setMenuInMenuSection(fullMenu, false);
            currentMenu = new MenuClient(new ArrayList<>(dishes));
            updateFilter();

            // for order section
            dishesInOrder = new ArrayList<DishClient>();
            isOrder = false;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//    }

    private LocationInformation getLocationInformation() {
        LocationInformation locationInformation;
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            locationInformation = popupDialogService.openPopup("LocationInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
            return locationInformation;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean getIsDelivery() {
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            Boolean isDelivery = popupDialogService.openPopup("ConfirmationWindow.fxml", "Would you rather self pickup", (Stage) orderSection.getScene().getWindow());
            if (isDelivery == null) {
                return false;
            }
            return !isDelivery;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reinitialize(boolean isOrder, boolean isMain) {
        if (isOrder) {
            this.isOrder = true;
            addOrderSupportToMenu();
        }
        if (!isMain) {
            this.isMain = false;
        }
    }

    public void setEdit(EditMenuController editMenuController) {
        this.editMenuController = editMenuController;
        isDelete = true;
        isOrder = false;
        clearFilter();
    }

    public void setMenu(MenuClient menu) {
        if (!isMain) {
            fullMenu = menu;
            updateFilter();
        }
    }

    private void updateFilter() {
        if (fullMenu != null) {
            List<String> branches = fullMenu.getAllBranches();
            List<String> ingredients = fullMenu.getAllIngredients();
            putBranchCheckBoxesInFilter(branches);
            putIngredientsCheckBoxesInFilter(ingredients);
            clearFilter();
        }
    }

    private void addOrderSupportToMenu() {
        isOrder = true; // should be true after reinitialize, but updated in case a different function will call it.
        setMenuInMenuSection(currentMenu, true);
        showOrderSection();
    }

    private void showOrderSection() {
        // Use Platform.runLater to update visibility
        Platform.runLater(() -> {
            orderSection.setVisible(true);
            orderSection.setManaged(true);
        });
    }

    private void setMenuInMenuSection(MenuClient menuClient, boolean isOrder) {
        // Use Platform.runLater to update the menuDishList
        Platform.runLater(() -> {
            menuDishList.getChildren().clear();
            if (menuClient != null) {
                for (DishClient dish : menuClient.getMenu()) {
                    addDishToMenuSection(dish, isOrder);
                }
                currentMenu = new MenuClient(menuClient.getMenu());
            } else {
                currentMenu = null;
            }
        });
    }

    private void putBranchCheckBoxesInFilter(List<String> branches) {
        // Use Platform.runLater to update the branchCheckboxContainerForFilter
        Platform.runLater(() -> {
            branchCheckboxContainerForFilter.setVisible(false);
            branchCheckboxContainerForFilter.setManaged(false);

            branchCheckboxContainerForFilter.getChildren().clear();
            for (String branch : branches) {
                CheckBox checkBox = new CheckBox(branch);
                checkBox.setSelected(true);
                branchCheckboxContainerForFilter.getChildren().add(checkBox);
            }
            branchCheckboxContainerForFilter.setVisible(true);
            branchCheckboxContainerForFilter.setManaged(true);
        });
    }

    private void putIngredientsCheckBoxesInFilter(List<String> ingredients) {
        // Use Platform.runLater to update the IngredientsCheckboxContainerForFilter
        Platform.runLater(() -> {
            IngredientsCheckboxContainerForFilter.setVisible(false);
            IngredientsCheckboxContainerForFilter.setManaged(false);

            IngredientsCheckboxContainerForFilter.getChildren().clear();
            for (String ingredient : ingredients) {
                CheckBox checkBox = new CheckBox(ingredient);
                checkBox.setSelected(true);
                IngredientsCheckboxContainerForFilter.getChildren().add(checkBox);
            }
            IngredientsCheckboxContainerForFilter.setVisible(true);
            IngredientsCheckboxContainerForFilter.setManaged(true);
        });
    }

    @FXML
    private void applyFilter() {
        List<String> filteredBranches = getSelectedValuesFromVBox(branchCheckboxContainerForFilter);
        List<String> filteredIngredients = getSelectedValuesFromVBox(IngredientsCheckboxContainerForFilter);
        MenuFilter menuFilter = new MenuFilter(filteredBranches, filteredIngredients);
        MenuClient newMenu = menuFilter.filterMenu(fullMenu);
        // Use setMenuInMenuSection which already includes Platform.runLater
        setMenuInMenuSection(newMenu, isOrder);
    }

    @FXML
    private void clearFilter() {
        // Use Platform.runLater to update the checkboxes
        Platform.runLater(() -> {
            selectAllCheckboxesInVBox(branchCheckboxContainerForFilter);
            selectAllCheckboxesInVBox(IngredientsCheckboxContainerForFilter);
        });
        // Use setMenuInMenuSection which already includes Platform.runLater
        setMenuInMenuSection(fullMenu, isOrder);
    }

    private void selectAllCheckboxesInVBox(VBox container) {
        for (Node node : container.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                checkBox.setSelected(true);
            }
        }
    }

    private List<String> getSelectedValuesFromVBox(VBox container) {
        List<String> selectedValues = new ArrayList<>();

        for (Node node : container.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                if (checkBox.isSelected()) {
                    selectedValues.add(checkBox.getText());
                }
            }
        }
        return selectedValues;
    }

    private void addDishToMenuSection(DishClient dish, boolean isOrder) {
        try {
            Node dishNode;
            if (!isOrder && !isDelete) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DishSectionInMenu.fxml"));
                dishNode = fxmlLoader.load();
                DishSectionInMenuController dishSectionInMenuController = fxmlLoader.getController();
                dishSectionInMenuController.setDish(dish);
                dishSectionInMenuController.setDishDataInDishSection();
            } else if (!isDelete) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderableDishSectionInMenu.fxml"));
                dishNode = fxmlLoader.load();
                OrderableDishSectionInMenuController orderableDishSectionInMenuController = fxmlLoader.getController();
                orderableDishSectionInMenuController.reinitialize(this);
                orderableDishSectionInMenuController.setDishInDishSection(dish);
                orderableDishSectionInMenuController.setDishDataInDishSection();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeletableDishSection.fxml"));
                dishNode = fxmlLoader.load();
                DeletableDishSectionController deletableDishSectionController = fxmlLoader.getController();
                deletableDishSectionController.reinitialize(this);
                deletableDishSectionController.setDishInDishSection(dish);
                deletableDishSectionController.setDishDataInDishSection();
            }

            // Use Platform.runLater to add the dish node to the menuDishList
            Node finalDishNode = dishNode;
            Platform.runLater(() -> {
                menuDishList.getChildren().add(finalDishNode);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void orderDish(DishClient dish) {
        PopupDialogService popupDialogService = new PopupDialogService();
        // Use Platform.runLater to handle the popup and subsequent UI updates
        Platform.runLater(() -> {
            try {
                List<String> preferences = popupDialogService.openPopup("PersonalPreferencesPopup.fxml", dish, (Stage) orderSection.getScene().getWindow());

                if (preferences != null && !preferences.isEmpty()) {
                    addDishToOrderSection(new DishClient(dish.getName(), dish.getDescription(), dish.getPrice(), dish.getImageUrl(), dish.getAvailableBranches(), dish.getIngredients(), preferences, dish.getSale()));
                } else {
                    addDishToOrderSection(dish);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addDishToOrderSection(DishClient dish) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderedDishSection.fxml"));
            Node dishNode = fxmlLoader.load();
            OrderedDishSectionController dishSectionInMenuController = fxmlLoader.getController();
            dishSectionInMenuController.setDishInDishSection(dish);
            dishSectionInMenuController.setDishDataInDishSection();

            // Use Platform.runLater to add the dish node to the orderDishList
            Platform.runLater(() -> {
                orderDishList.getChildren().add(dishNode);
                dishesInOrder.add(dish);
                orderDishNodeCountLabelPair.add(new Pair<>(dish, dishSectionInMenuController.getCountLabel()));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void finishOrder() {
        PopupDialogService popupDialogService = new PopupDialogService();
        // Use Platform.runLater to handle the entire order finishing process
        Platform.runLater(() -> {
            try {
                if (canTheOrderBeMadeFromASingleBranch(dishesInOrder)) {
                    Boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "Finish Order?", (Stage) orderSection.getScene().getWindow());
                    if (isConfirmed != null && isConfirmed) {
                        LocationInformation locationInfo = getLocationInformation();
                        boolean isDelivery = getIsDelivery();
                        PersonalInformation personalInformation = popupDialogService.openPopup("PersonalInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
                        if (personalInformation != null) {
                            CreditInformation creditInformation = popupDialogService.openPopup("CreditInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
                            if (creditInformation != null) {
                                Boolean confirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "Total price is:" + String.valueOf(getTotalPrice()) + ". Confirm Order?", (Stage) orderSection.getScene().getWindow());
                                if (confirmed != null && confirmed) {
                                    OrderClient order = new OrderClient(getDishesCountPair(), isDelivery, locationInfo, personalInformation, creditInformation);
                                    sendOrder(order);
                                }
                            }
                        }
                    }
                } else {
                    popupDialogService.openPopup("InformationPopupWindow.fxml", "The order can't be made from a single branch", (Stage) orderSection.getScene().getWindow());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean canTheOrderBeMadeFromASingleBranch(ArrayList<DishClient> dishesInOrder) {
        String commonBranch = getCommonBranch(dishesInOrder);
        return commonBranch != null;
    }

    private String getCommonBranch(ArrayList<DishClient> dishes) {
        if (dishes == null || dishes.isEmpty()) {
            return null;
        }
        Set<String> commonBranches = new HashSet<>(dishes.get(0).getAvailableBranches());
        for (DishClient dish : dishes) {
            commonBranches.retainAll(dish.getAvailableBranches());
            if (commonBranches.isEmpty()) {
                return null;
            }
        }
        return commonBranches.iterator().next();
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        for (DishClient dish : dishesInOrder) {
            totalPrice += dish.getPrice() * ((double) Math.max(100 - dish.getSale(), 0) / 100.0);
        }
        return totalPrice;
    }

    private ArrayList<Pair<DishClient, Integer>> getDishesCountPair() {
        ArrayList<Pair<DishClient, Integer>> pairList = new ArrayList<>();
        for (Pair<DishClient, Label> pair : orderDishNodeCountLabelPair) {
            pairList.add(new Pair<>(pair.getKey(), Integer.parseInt(pair.getValue().getText())));
        }
        return pairList;
    }

    private List<DishClient> getHardcodedDishes() {
        DishClient pizza = new DishClient(
                "Pizza Margherita",
                "Classic pizza with tomato, cheese, and olives.",
                35.0f,
                "https://example.com/images/pizza.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Cheese", "Tomato", "Olives"),
                10 // Sale: 10% off
        );

        DishClient burger = new DishClient(
                "Beef Burger",
                "Juicy beef patty with lettuce, tomato, and cheese.",
                42.5f,
                "https://example.com/images/burger.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Beef", "Lettuce", "Tomato", "Cheese"),
                0 // No Sale
        );

        DishClient salad = new DishClient(
                "Greek Salad",
                "Fresh vegetables with feta cheese and olives.",
                28.0f,
                "https://example.com/images/salad.jpg",
                List.of("Haifa", "Jerusalem"),
                List.of("Cucumber", "Tomato", "Feta", "Olives"),
                5 // Sale: 5% off
        );

        DishClient hummusPlate = new DishClient(
                "Hummus Plate",
                "Creamy hummus served with vegetables and pita.",
                25.0f,
                "https://example.com/images/hummus.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Hummus", "Tomato", "Onion", "Olives"),
                0 // No Sale
        );

        DishClient falafelPlate = new DishClient(
                "Falafel Plate",
                "Delicious falafel balls served with hummus and salad.",
                22.0f,
                "https://example.com/images/falafel.jpg",
                List.of("Tel Aviv", "Haifa", "Jerusalem"),
                List.of("Falafel", "Hummus", "Lettuce", "Tomato"),
                15 // Sale: 15% off
        );

        DishClient veggieBurger = new DishClient(
                "Veggie Burger",
                "A healthy burger with tomato, lettuce, and cheese.",
                38.0f,
                "https://example.com/images/veggie_burger.jpg",
                List.of("Tel Aviv", "Haifa"),
                List.of("Lettuce", "Tomato", "Cheese", "Onion"),
                0 // No Sale
        );

        DishClient cheeseSandwich = new DishClient(
                "Cheese Sandwich",
                "A simple cheese sandwich with tomato and lettuce.",
                20.0f,
                "https://example.com/images/cheese_sandwich.jpg",
                List.of("Haifa", "Jerusalem"),
                List.of("Cheese", "Tomato", "Lettuce"),
                0 // No Sale
        );

        DishClient beefSalad = new DishClient(
                "Beef Salad",
                "Salad with grilled beef, lettuce, tomato, and cucumber.",
                40.0f,
                "https://example.com/images/beef_salad.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Beef", "Lettuce", "Tomato", "Cucumber"),
                20 // Sale: 20% off
        );

        DishClient falafelWrap = new DishClient(
                "Falafel Wrap",
                "Falafel served in pita bread with lettuce and hummus.",
                24.0f,
                "https://example.com/images/falafel_wrap.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Falafel", "Hummus", "Lettuce"),
                0 // No Sale
        );

        DishClient mixedPlatter = new DishClient(
                "Mixed Platter",
                "Combination of falafel, hummus, tomato, and olives.",
                30.0f,
                "https://example.com/images/mixed_platter.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Falafel", "Hummus", "Tomato", "Olives"),
                0 // No Sale
        );

        return List.of(pizza, burger, salad, hummusPlate, falafelPlate, veggieBurger, cheeseSandwich, beefSalad, falafelWrap, mixedPlatter);
    }

    public void deleteDishPressed(DishClient dish) {
        editMenuController.deleteDishPressed(dish);
    }

    public void EditDishPressed(DishClient dish) {
        editMenuController.EditDishPressed(dish);
    }

    @FXML
    public void displayBranchesAndTheirOpeningTime() {
        PopupDialogService popupDialogService = new PopupDialogService();
        // Use Platform.runLater to handle the popup
        Platform.runLater(() -> {
            try {
                popupDialogService.openPopup("BranchesOpeningTimesPopup.fxml", fullMenu.getAllBranches(), (Stage) orderSection.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendOrder(OrderClient order) {
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}