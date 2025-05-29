package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.models.*;
import il.cshaifasweng.OCSFMediatorExample.entities.models.LocationInformation;
import il.cshaifasweng.OCSFMediatorExample.entities.models.OrderClient;
import il.cshaifasweng.OCSFMediatorExample.entities.models.PersonalInformation;
import il.cshaifasweng.OCSFMediatorExample.entities.models.CreditInformation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private ArrayList<MenuItem> dishesInOrder;
    private boolean isOrder;
    private boolean isDelete = false;
    private boolean isMain; //used to differentiate between menu used for main menu or used for un inputted menu

    private List<OrderItem> orderDishNodeCountLabelPair = new ArrayList<>();
    private EditMenuController editMenuController;


    @FXML
    public void initialize() {
        try {
////            EventBus.getDefault().register(this);
////            isMain = true;
////            if (fullMenu == null) {
////                try {
////                    System.out.println("requesting menu");
////                    App.sendMessageToServer("send all MenuItems");
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////                /*new Thread(() -> {
////                    try {
////                        System.out.println("requesting menu");
////                        App.sendMessageToServer("send all MenuItems");
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }).start();*/
////            }
            isMain = true;
            isOrder = false;
            if (fullMenu == null) {
                MenuClient response = RequestManager.getInstance().sendAndWait(
                        "get main menu",
                        5000,
                        String.class,
                        MenuClient.class
                );
                fullMenu = response;
            }
            if ((fullMenu) != null){
                System.out.println(fullMenu);
            }
            setMenuInMenuSection(fullMenu, false);
            clearFilter();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    //good
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
    //good
    private boolean getIsDelivery(){
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
    //fine
    public void reinitialize(boolean isOrder, boolean isMain) {
        if (isOrder) {
            this.isOrder = true;
            addOrderSupportToMenu();
        }
        if (!isMain) {
            this.isMain = false;
        }
        setMenuInMenuSection(fullMenu, isOrder);
    }
    //good
    public void setEdit(EditMenuController editMenuController) {
        this.editMenuController = editMenuController;
        isDelete = true;
        isOrder = false;
        clearFilter();
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!
    public void setMenu(MenuClient menu) {
        if (!isMain) {
            fullMenu = menu;
            updateFilter();
            setMenuInMenuSection(fullMenu, isOrder);
        }
    }

    //good
    private void updateFilter() {
        if(fullMenu != null) {
            clearFilter();
            List<String> branches = fullMenu.getAllBranches();
            List<String> ingredients = fullMenu.getAllIngredients();
            putBranchCheckBoxesInFilter(branches);
            putIngredientsCheckBoxesInFilter(ingredients);
        }
    }

    //good
    private void addOrderSupportToMenu() {
        isOrder = true; //should be true after reinitialize, but updated in case a different function will call it.
        setMenuInMenuSection(currentMenu, true);
        showOrderSection();
    }

    //good
    private void showOrderSection() {
        orderSection.setVisible(true);
        orderSection.setManaged(true);

    }

    //probebly good
    private void setMenuInMenuSection(MenuClient menuClient, boolean isOrder) {
//        Platform.runLater(()->menuDishList.getChildren().clear());
        menuDishList.getChildren().clear();
        System.out.println(4);
        if (menuClient != null){
            currentMenu = new MenuClient(menuClient.getMenu());
            System.out.println(3);
            for (MenuItem dish : menuClient.getMenu()) {
                System.out.println(2);
                addDishToMenuSection(dish, isOrder);
                //Platform.runLater(()->addDishToMenuSection(dish, isOrder));
            }
        }else{
            currentMenu = null;
        }

    }

    //good
    private void putBranchCheckBoxesInFilter(List<String> branches){
        branchCheckboxContainerForFilter.setVisible(false);
        branchCheckboxContainerForFilter.setManaged(false);
//        Platform.runLater(()->branchCheckboxContainerForFilter.getChildren().clear());

        branchCheckboxContainerForFilter.getChildren().clear();
        for (String branch : branches){
            CheckBox checkBox = new CheckBox(branch);
            checkBox.setSelected(true);
//            Platform.runLater(()->branchCheckboxContainerForFilter.getChildren().add(checkBox));
            branchCheckboxContainerForFilter.getChildren().add(checkBox);
        }
        branchCheckboxContainerForFilter.setVisible(true);
        branchCheckboxContainerForFilter.setManaged(true);
    }

    //good
    private void putIngredientsCheckBoxesInFilter(List<String> ingredients){
        IngredientsCheckboxContainerForFilter.setVisible(false);
        IngredientsCheckboxContainerForFilter.setManaged(false);
//        Platform.runLater(()->IngredientsCheckboxContainerForFilter.getChildren().clear());
        IngredientsCheckboxContainerForFilter.getChildren().clear();
        for (String branch : ingredients){
            CheckBox checkBox = new CheckBox(branch);
            checkBox.setSelected(true);
//            Platform.runLater(()->IngredientsCheckboxContainerForFilter.getChildren().add(checkBox));
            IngredientsCheckboxContainerForFilter.getChildren().add(checkBox);
        }
        IngredientsCheckboxContainerForFilter.setVisible(true);
        IngredientsCheckboxContainerForFilter.setManaged(true);
    }

    //probebly good
    @FXML
    private void applyFilter(){
        List<String> filteredBranches = getSelectedValuesFromVBox(branchCheckboxContainerForFilter);
        List<String> filteredIngredients = getSelectedValuesFromVBox(IngredientsCheckboxContainerForFilter);
        MenuFilter menuFilter = new MenuFilter(filteredBranches, filteredIngredients);
        MenuClient filteredMenu = new MenuClient();
        if (currentMenu != null) {
            for (MenuItem dish :fullMenu.getMenu()){
                boolean isAtListOneNotFilterAvailableBranch = dish.getAvailableBranches().stream().anyMatch(filteredBranches::contains);
                boolean areAllIngredientsContainedInFilteredIngredients = dish.getIngredients().stream().allMatch(filteredIngredients::contains);
                if (isAtListOneNotFilterAvailableBranch && areAllIngredientsContainedInFilteredIngredients) {
                    filteredMenu.addDish(dish);
                }
            }
        }
        currentMenu = filteredMenu;
        MenuClient newMenu = menuFilter.filterMenu(fullMenu);
        setMenuInMenuSection(newMenu, isOrder);
    }

    //good
    @FXML
    private void clearFilter(){
        selectAllCheckboxesInVBox(branchCheckboxContainerForFilter);
        selectAllCheckboxesInVBox(IngredientsCheckboxContainerForFilter);
        setMenuInMenuSection(fullMenu, isOrder);
    }


    //good
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
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    selectedValues.add(checkBox.getText());
                }
            }
        }
        return selectedValues;
    }




    private void addDishToMenuSection(MenuItem dish, boolean isOrder){
        try {
            if (!isOrder && !isDelete) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DishSectionInMenu.fxml"));
                Node dishNode = fxmlLoader.load();
                DishSectionInMenuController dishSectionInMenuController = fxmlLoader.getController();
                dishSectionInMenuController.setDish(dish);
                dishSectionInMenuController.setDishDataInDishSection();
//                Platform.runLater(()->menuDishList.getChildren().add(dishNode));
                menuDishList.getChildren().add(dishNode);
            }else if (!isDelete) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderableDishSectionInMenu.fxml"));
                Node dishNode = fxmlLoader.load();
                OrderableDishSectionInMenuController orderableDishSectionInMenuController = fxmlLoader.getController();
                orderableDishSectionInMenuController.reinitialize(this);
                orderableDishSectionInMenuController.setDishInDishSection(dish);
                orderableDishSectionInMenuController.setDishDataInDishSection();
//                Platform.runLater(()->menuDishList.getChildren().add(dishNode));
                menuDishList.getChildren().add(dishNode);
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeletableDishSection.fxml"));
                Node dishNode = fxmlLoader.load();
                DeletableDishSectionController DeletableDishSectionController = fxmlLoader.getController();
                DeletableDishSectionController.reinitialize(this);
                DeletableDishSectionController.setDishInDishSection(dish);
                DeletableDishSectionController.setDishDataInDishSection();
//                Platform.runLater(()->menuDishList.getChildren().add(dishNode));
                menuDishList.getChildren().add(dishNode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //good
    public void orderDish(MenuItem dish){
        PopupDialogService popupDialogService = new PopupDialogService();

        try {
            List<String> preferences = popupDialogService.openPopup("PersonalPreferencesPopup.fxml", dish, (Stage) orderSection.getScene().getWindow());

            if (preferences != null && !preferences.isEmpty()) {
                addDishToOrderSection(new MenuItem(dish.getName(), dish.getDescription(), dish.getPrice(), dish.getImageUrl(), dish.getAvailableBranches(), dish.getIngredients(),preferences, dish.getSale()));
            }else{
                addDishToOrderSection(dish);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addDishToOrderSection(MenuItem dish){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderedDishSection.fxml"));
            Node dishNode = fxmlLoader.load();
            OrderedDishSectionController dishSectionInMenuController = fxmlLoader.getController();
            dishSectionInMenuController.setDishInDishSection(dish);
            dishSectionInMenuController.setDishDataInDishSection();
            Platform.runLater(()->{
                orderDishList.getChildren().add(dishNode);
                dishesInOrder.add(dish);
                orderDishNodeCountLabelPair.add(new OrderItem(dish, Integer.parseInt(dishSectionInMenuController.getCountLabel())));
            });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //good
    @FXML
    private void finishOrder(){


        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            if(canTheOrderBeMadeFromASingleBranch(dishesInOrder)){
                Boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "Finish Order?", (Stage) orderSection.getScene().getWindow());
                if (isConfirmed != null && isConfirmed) {
                    boolean isDelivery = getIsDelivery();
                    LocationInformation locationInfo = null;
                    if (isDelivery) {
                        locationInfo = getLocationInformation();
                    }
                    PersonalInformation personalInformation = popupDialogService.openPopup("PersonalInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
                    if (personalInformation != null) {
                        CreditInformation creditInformation = popupDialogService.openPopup("CreditInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
                        if (creditInformation != null) {
                            Boolean Confirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "Total price is:" + String.valueOf(getTotalPrice()) + ". Confirm Order?", (Stage) orderSection.getScene().getWindow());
                            if (Confirmed) {
                                OrderClient order = new OrderClient(getDishesCountPair(), isDelivery, locationInfo, personalInformation, creditInformation);
                                sendOrder(order);
                            }
                        }
                    }
                }
            }else {
                popupDialogService.openPopup("InformationPopupWindow.fxml", "The order cant be made from a single branch", (Stage) orderSection.getScene().getWindow());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //good
    private boolean canTheOrderBeMadeFromASingleBranch(ArrayList<MenuItem> dishesInOrder){
        String commonBranch = getCommonBranch(dishesInOrder);
        if(commonBranch != null){
            return true;
        }else{
            return false;
        }
    }

    private String getCommonBranch(ArrayList<MenuItem> dishes) {  // Changed to ArrayList<DishClient>
        if (dishes == null || dishes.isEmpty()) {
            return null;
        }
        Set<String> commonBranches = new HashSet<>(dishes.get(0).getAvailableBranches());
        for (MenuItem dish : dishes) {
            commonBranches.retainAll(dish.getAvailableBranches());

            if (commonBranches.isEmpty()) {
                return null;
            }
        }
        return commonBranches.iterator().next();
    }

    //good
    private double getTotalPrice(){
        double totalPrice = 0;
        for (MenuItem dish : dishesInOrder) {
            totalPrice += dish.getPrice() *((double) Math.max(100 - dish.getSale(), 0) / 100.0);
        }
        return totalPrice;
    }

    //probebly good
    private ArrayList<OrderItem> getDishesCountPair(){
        ArrayList<OrderItem> pairList = new ArrayList<>();
        for (OrderItem pair : orderDishNodeCountLabelPair){
            pairList.add(new OrderItem(pair.getMenuItem(), pair.getQuantity()));
        }
        return pairList;
    }
/*
    private void getHardcodedDishes() throws IOException {
        App.sendMessageToServer("send all MenuItems");
    }*/
    @Subscribe
    public void on_respond(List<MenuItem> dishes){
        System.out.println("got the menu");
        ArrayList<MenuItem> dishes1= (ArrayList<MenuItem>) dishes;
        fullMenu = new MenuClient(dishes1);
        setMenuInMenuSection(fullMenu, false);
        currentMenu = new MenuClient(new ArrayList<>(dishes));
        updateFilter();

        //for order section
        dishesInOrder = new ArrayList<MenuItem>();
        isOrder = false;
        for (MenuItem item:dishes1)
            Platform.runLater(()->addDishToMenuSection(item,isOrder));
            //addDishToMenuSection(item,isOrder);
        List<String> branches = fullMenu.getAllBranches();
        List<String> ingredients = fullMenu.getAllIngredients();
        putBranchCheckBoxesInFilter(branches);
        putIngredientsCheckBoxesInFilter(ingredients);
    }




    //good
    public void deleteDishPressed(MenuItem dish) {
        editMenuController.deleteDishPressed(dish);
    }

    //good
    public void EditDishPressed(MenuItem dish) {
        editMenuController.EditDishPressed(dish);
    }

    //good
    @FXML
    public void displayBranchesAndTheirOpeningTime() {
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            popupDialogService.openPopup("BranchesOpeningTimesPopup.fxml", fullMenu.getAllBranches(), (Stage) orderSection.getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Subscribe
    public void onEventDummy(Object ignored) {}


    private void sendOrder(OrderClient order) {
        try{
            App.setRoot("home-page");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
