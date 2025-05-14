package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.server.dal.models.*;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.LocationInformation;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.OrderClient;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.CreditInformation;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.PersonalInformation;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private PersonalInformation personalInformation;
    private ArrayList<MenuItem> dishesInOrder;
    private boolean isOrder;
    private boolean isDelete = false;
    private boolean isMain; //used to differentiate between menu used for main menu or used for un inputted menu

    private List<OrderItem> orderDishNodeCountLabelPair = new ArrayList<>();
    private EditMenuController editMenuController;


    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
            isMain = true;
            if (fullMenu == null) {
                try {
                    dishesInOrder = new ArrayList<>();
                    System.out.println("requesting menu");
                    App.sendMessageToServer("send all MenuItems");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*new Thread(() -> {
                    try {
                        System.out.println("requesting menu");
                        App.sendMessageToServer("send all MenuItems");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


    private LocationInformation getLocationInformation() {
        LocationInformation locationInformation;
        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            locationInformation = popupDialogService.openPopup("LocationInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
            //App.sendMessageToServer(locationInformation);
            return locationInformation;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
        if(fullMenu != null) {
            List<String> branches = fullMenu.getAllBranches();
            List<String> ingredients = fullMenu.getAllIngredients();
            putBranchCheckBoxesInFilter(branches);
            putIngredientsCheckBoxesInFilter(ingredients);
            clearFilter();
        }
    }

    private void addOrderSupportToMenu() {
        isOrder = true; //should be true after reinitialize, but updated in case a different function will call it.
        setMenuInMenuSection(currentMenu, true);
        showOrderSection();
    }

    private void showOrderSection() {
        orderSection.setVisible(true);
        orderSection.setManaged(true);

    }
    private void setMenuInMenuSection(MenuClient menuClient, boolean isOrder) {
        System.out.println("hello from set menu");
        Platform.runLater(()->{
            menuDishList.getChildren().clear();
            System.out.println("check if got in and deleted the items"+menuDishList.getChildren().size());

        //menuDishList.getChildren().clear();
        if (menuClient != null){
            int i=0;
            for (MenuItem dish : menuClient.getMenu()) {
                System.out.println(i);
                addDishToMenuSection(dish, isOrder);
                i++;
                //Platform.runLater(()->addDishToMenuSection(dish, isOrder));
            }
            currentMenu = new MenuClient(menuClient.getMenu());
        }else{
            currentMenu = null;
        }
    });
    }

    private void putBranchCheckBoxesInFilter(List<String> branches){
        branchCheckboxContainerForFilter.setVisible(false);
        branchCheckboxContainerForFilter.setManaged(false);
        Platform.runLater(()->branchCheckboxContainerForFilter.getChildren().clear());

        //   branchCheckboxContainerForFilter.getChildren().clear();
        for (String branch : branches){
            CheckBox checkBox = new CheckBox(branch);
            checkBox.setSelected(true);
            Platform.runLater(()->branchCheckboxContainerForFilter.getChildren().add(checkBox));
            //branchCheckboxContainerForFilter.getChildren().add(checkBox);
        }
        branchCheckboxContainerForFilter.setVisible(true);
        branchCheckboxContainerForFilter.setManaged(true);
    }

    private void putIngredientsCheckBoxesInFilter(List<String> ingredients){
        IngredientsCheckboxContainerForFilter.setVisible(false);
        IngredientsCheckboxContainerForFilter.setManaged(false);
        Platform.runLater(()->IngredientsCheckboxContainerForFilter.getChildren().clear());
        ;
        for (String branch : ingredients){
            CheckBox checkBox = new CheckBox(branch);
            checkBox.setSelected(true);
            Platform.runLater(()->IngredientsCheckboxContainerForFilter.getChildren().add(checkBox));
            ;
        }
        IngredientsCheckboxContainerForFilter.setVisible(true);
        IngredientsCheckboxContainerForFilter.setManaged(true);
    }

    @FXML
    private void applyFilter(){
        List<String> filteredBranches = getSelectedValuesFromVBox(branchCheckboxContainerForFilter);
        List<String> filteredIngredients = getSelectedValuesFromVBox(IngredientsCheckboxContainerForFilter);
        MenuFilter menuFilter = new MenuFilter(filteredBranches, filteredIngredients);
        MenuClient filteredMenu = new MenuClient();
        if (filteredBranches != null && filteredIngredients != null && currentMenu != null) {
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

    @FXML
    private void clearFilter(){
        selectAllCheckboxesInVBox(branchCheckboxContainerForFilter);
        selectAllCheckboxesInVBox(IngredientsCheckboxContainerForFilter);
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
                Platform.runLater(()->menuDishList.getChildren().add(dishNode));
                //menuDishList.getChildren().add(dishNode);
            }else if (!isDelete) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderableDishSectionInMenu.fxml"));
                Node dishNode = fxmlLoader.load();
                OrderableDishSectionInMenuController orderableDishSectionInMenuController = fxmlLoader.getController();
                orderableDishSectionInMenuController.reinitialize(this);
                orderableDishSectionInMenuController.setDishInDishSection(dish);
                orderableDishSectionInMenuController.setDishDataInDishSection();
                Platform.runLater(()->menuDishList.getChildren().add(dishNode));
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeletableDishSection.fxml"));
                Node dishNode = fxmlLoader.load();
                DeletableDishSectionController DeletableDishSectionController = fxmlLoader.getController();
                DeletableDishSectionController.reinitialize(this);
                DeletableDishSectionController.setDishInDishSection(dish);
                DeletableDishSectionController.setDishDataInDishSection();
                Platform.runLater(()->menuDishList.getChildren().add(dishNode));


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


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
    @FXML
    private void finishOrder(){


        PopupDialogService popupDialogService = new PopupDialogService();
        try {
            if(canTheOrderBeMadeFromASingleBranch(dishesInOrder)){
                Boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "Finish Order?", (Stage) orderSection.getScene().getWindow());
                if (isConfirmed != null && isConfirmed) {
                    LocationInformation locationInfo = getLocationInformation();
                    boolean isDelivery = getIsDelivery();
                    personalInformation = popupDialogService.openPopup("PersonalInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
                    /*ExecutorService executor = Executors.newFixedThreadPool(1);
                    executor.submit(() -> {
                        try {
                            App.sendMessageToServer(personalInformation);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    executor.shutdown();*/
                    if (personalInformation != null) {
                        CreditInformation creditInformation = popupDialogService.openPopup("CreditInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
                        //App.sendMessageToServer(creditInformation);
                        if (creditInformation != null) {
                            Boolean Confirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "Total price is:" + String.valueOf(getTotalPrice()) + ". Confirm Order?", (Stage) orderSection.getScene().getWindow());
                            if (Confirmed != null && Confirmed) {
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

    private double getTotalPrice(){
        double totalPrice = 0;
        for (MenuItem dish : dishesInOrder) {
            totalPrice += dish.getPrice() *((double) Math.max(100 - dish.getSale(), 0) / 100.0);
        }
        return totalPrice;
    }

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
    public void on_respond(List<?> dishes)
    {
        if (dishes.isEmpty()) return;
        if (dishes.getFirst() instanceof MenuItem) {
            dishes = (List<MenuItem>) dishes;
            System.out.println("got the menu");
            ArrayList<MenuItem> dishes1 = (ArrayList<MenuItem>) dishes;
            fullMenu = new MenuClient(dishes1);
            //setMenuInMenuSection(fullMenu, false);
            currentMenu = new MenuClient((ArrayList<MenuItem>) new ArrayList<>(dishes));
            updateFilter();

            //for order section
        /*dishesInOrder = new ArrayList<MenuItem>();
        isOrder = false;
        for (MenuItem item:dishes1)
            Platform.runLater(()->addDishToMenuSection(item,isOrder));*/
            //addDishToMenuSection(item,isOrder);
       /* List<String> branches = fullMenu.getAllBranches();
        List<String> ingredients = fullMenu.getAllIngredients();
        putBranchCheckBoxesInFilter(branches);
        putIngredientsCheckBoxesInFilter(ingredients);*/
        }
    }
    @Subscribe
    public void on_respond(OrderClient order) throws IOException {
        System.out.println("got into on respond for order");
        if (order != null){
            EmailSender emailSender=new EmailSender();
            emailSender.send_email_respond(personalInformation.getEmail(),"Your Order from Mama's Restaurant no."+order.id,order.toString());
            App.setRoot("home-page");
        }
        else {
            System.out.println("the order is empty, it didn't work");
        }
    }




    public void deleteDishPressed(MenuItem dish) {
        editMenuController.deleteDishPressed(dish);
    }
    public void EditDishPressed(MenuItem dish) {
        editMenuController.EditDishPressed(dish);
    }

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
            System.out.println("is the order null?"+order==null);
            App.sendMessageToServer(order);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
