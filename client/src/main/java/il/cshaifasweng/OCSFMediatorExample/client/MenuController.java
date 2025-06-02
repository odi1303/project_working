package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.models.*;
import il.cshaifasweng.OCSFMediatorExample.entities.models.LocationInformation;
import il.cshaifasweng.OCSFMediatorExample.entities.models.OrderClient;
import il.cshaifasweng.OCSFMediatorExample.entities.models.PersonalInformation;
import il.cshaifasweng.OCSFMediatorExample.entities.models.CreditInformation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
    private ArrayList<MenuItem> dishesInOrder = new ArrayList<>();
    private boolean isOrder;
    private boolean isDelete = false;
    private boolean isMain; //used to differentiate between menu used for main menu or used for un inputted menu

    private List<Pair<OrderedDishSectionController, Label>> orderDishNodeControllerCountLabelPair = new ArrayList<>();
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
            updateFilter();


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
            //App.sendMessageToServer(locationInformation);
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
        System.out.println("hello from set menu");
//        Platform.runLater(()->menuDishList.getChildren().clear());
        menuDishList.getChildren().clear();
        if (menuClient != null){
            currentMenu = new MenuClient(menuClient.getMenu());
            for (MenuItem dish : menuClient.getMenu()) {
                addDishToMenuSection(dish, isOrder);
                //Platform.runLater(()->addDishToMenuSection(dish, isOrder));
            }
        }else{
            currentMenu = null;
        }
    //});
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
                orderDishNodeControllerCountLabelPair.add(new Pair<>(dishSectionInMenuController, (dishSectionInMenuController.getCountLabel())));
            });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //good
    @FXML
    private void finishOrder() throws IOException {
        PopupDialogService popupDialogService = new PopupDialogService();
        if (IsOrderEmpty()){
            System.out.println("Your cart is empty!");
            popupDialogService.openPopup("InformationWindow.fxml", "Your cart is empty!", (Stage) orderSection.getScene().getWindow());
            return;
        }

        try {
            if(canTheOrderBeMadeFromASingleBranch(dishesInOrder)){
                Boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "Finish Order?", (Stage) orderSection.getScene().getWindow());
                if (isConfirmed != null && isConfirmed) {
                    boolean isDelivery = getIsDelivery();
                    LocationInformation locationInfo = null;
                    if (isDelivery) {
                        locationInfo = getLocationInformation();
                    }
                    personalInformation = popupDialogService.openPopup("PersonalInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
                    if (personalInformation != null) {
                        CreditInformation creditInformation = popupDialogService.openPopup("CreditInformationPopupWindow.fxml", null, (Stage) orderSection.getScene().getWindow());
                        //App.sendMessageToServer(creditInformation);
                        if (creditInformation != null) {
                            Boolean Confirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "Total price is:" + String.valueOf(getTotalPrice()) + ". Confirm Order?", (Stage) orderSection.getScene().getWindow());
                            if (Confirmed != null && Confirmed) {
                                OrderClient order = createOrderClient(isDelivery, locationInfo, personalInformation, creditInformation);
                                sendOrder(order);
                            }
                        }
                    }
                }
            }else {
                System.out.println("The order cant be made from a single branch");
                popupDialogService.openPopup("InformationWindow.fxml", "The order cant be made from a single branch", (Stage) orderSection.getScene().getWindow());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean IsOrderEmpty(){
        for (Pair<OrderedDishSectionController, Label> pair : orderDishNodeControllerCountLabelPair) {
            Label countLabel = pair.getValue();
            String text = countLabel.getText();
            int quantity = Integer.parseInt(text.trim());
            if (quantity > 0) {
                return false;
            }
        }
        return true;
    }

    private OrderClient createOrderClient(boolean isDelivery,LocationInformation locationInfo,PersonalInformation personalInformation,CreditInformation creditInformation) {
        List<OrderItem> itemList = new ArrayList<>();
        for (Pair<OrderedDishSectionController, Label> pair : orderDishNodeControllerCountLabelPair) {
            Label countLabel = pair.getValue();
            String text = countLabel.getText();
            int quantity = Integer.parseInt(text.trim());
            if (quantity > 0) {
                MenuItem dish = pair.getKey().getDishSectionController().getDishClient();
                itemList.add(new OrderItem(dish, quantity));
            }
        }
        return new OrderClient(itemList, isDelivery, locationInfo, personalInformation, creditInformation);
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
        for (Pair<OrderedDishSectionController, Label> pair : orderDishNodeControllerCountLabelPair) {
            Label countLabel = pair.getValue();
            String text = countLabel.getText();
            int quantity = Integer.parseInt(text.trim());
            if (quantity > 0) {
                MenuItem dish = pair.getKey().getDishSectionController().getDishClient();
                totalPrice += quantity*dish.getPrice() *((double) Math.max(100 - dish.getSale(), 0) / 100.0);
            }
        }
        return totalPrice;
    }

//    //probebly good
//    private ArrayList<OrderItem> getDishesCountPair(){
//        ArrayList<OrderItem> pairList = new ArrayList<>();
//        for (OrderItem pair : orderDishNodeControllerCountLabelPair){
//            if(pair.getQuantity()>0)
//                pairList.add(new OrderItem(pair.getMenuItem(), pair.getQuantity()));
//            else{
//                Platform.runLater(()->{
//                    orderSection.getChildren().remove(pair);
//                    orderDishList.getChildren().remove(pair);
//                    dishesInOrder.remove(pair.getMenuItem());
//                    orderDishNodeControllerCountLabelPair.remove(pair);
//                });
//            }
//
//        }
//        return pairList;
//    }
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
            System.out.println(order.toString());
            App.setRoot("home-page");
        }
        else {
            System.out.println("the order is empty, it didn't work");
        }
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
        System.out.println("sending the order to saving");
        try{
            System.out.println("is the order null?"+order==null);
            App.sendMessageToServer(order);
            App.setRoot("home-page");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
