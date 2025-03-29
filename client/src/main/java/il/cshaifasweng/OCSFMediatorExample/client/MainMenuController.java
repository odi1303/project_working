package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MainMenuController {

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
    boolean isOrder;

    @FXML
    public void initialize() {
        List<String> branches = List.of("Haifa", "Tel Aviv", "Jerusalem");
        List<String> ingredients = List.of("Cheese", "Tomato", "Olives", "Onion");
        putBranchCheckBoxesInFilter(branches);
        putIngredientsCheckBoxesInFilter(ingredients);

        List<DishClient> dishes = getHardcodedDishes();
        fullMenu = new MenuClient(new ArrayList<>(dishes));
        setMenuInMenuSection(fullMenu, false);
        currentMenu = new MenuClient(new ArrayList<>(dishes));

        //for order section
        dishesInOrder = new ArrayList<DishClient>();
        isOrder = false;
        reinitialize(true);
    }

    public void reinitialize(boolean isOrder) {
        if (isOrder) {
            this.isOrder = true;
            addOrderSupportToMenu();
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
        menuDishList.getChildren().clear();
        for (DishClient dish : menuClient.getMenu()) {
            addDishToMenuSection(dish, isOrder);
        }
        currentMenu = new MenuClient(menuClient.getMenu());
    }

    private void putBranchCheckBoxesInFilter(List<String> branches){
        branchCheckboxContainerForFilter.setVisible(false);
        branchCheckboxContainerForFilter.setManaged(false);

        branchCheckboxContainerForFilter.getChildren().clear();
        for (String branch : branches){
            CheckBox checkBox = new CheckBox(branch);
            checkBox.setSelected(true);
            branchCheckboxContainerForFilter.getChildren().add(checkBox);
        }
        branchCheckboxContainerForFilter.setVisible(true);
        branchCheckboxContainerForFilter.setManaged(true);
    }

    private void putIngredientsCheckBoxesInFilter(List<String> ingredients){
        IngredientsCheckboxContainerForFilter.setVisible(false);
        IngredientsCheckboxContainerForFilter.setManaged(false);

        IngredientsCheckboxContainerForFilter.getChildren().clear();
        for (String branch : ingredients){
            CheckBox checkBox = new CheckBox(branch);
            checkBox.setSelected(true);
            IngredientsCheckboxContainerForFilter.getChildren().add(checkBox);
        }
        IngredientsCheckboxContainerForFilter.setVisible(true);
        IngredientsCheckboxContainerForFilter.setManaged(true);
    }

    @FXML
    private void applyFilter(){
        List<String> filteredBranches = getSelectedValuesFromVBox(branchCheckboxContainerForFilter);
        List<String> filteredIngredients = getSelectedValuesFromVBox(IngredientsCheckboxContainerForFilter);
        MenuFilter menuFilter = new MenuFilter(filteredBranches, filteredIngredients);
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




    private void addDishToMenuSection(DishClient dish, boolean isOrder){
        try {
            if (!isOrder) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DishSectionInMenu.fxml"));
                Node dishNode = fxmlLoader.load();
                DishSectionInMenuController dishSectionInMenuController = fxmlLoader.getController();
                dishSectionInMenuController.setDish(dish);
                dishSectionInMenuController.setDishDataInDishSection();

                menuDishList.getChildren().add(dishNode);
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderableDishSectionInMenu.fxml"));
                Node dishNode = fxmlLoader.load();
                OrderableDishSectionInMenuController orderableDishSectionInMenuController = fxmlLoader.getController();
                orderableDishSectionInMenuController.reinitialize(this);
                orderableDishSectionInMenuController.setDishInDishSection(dish);
                orderableDishSectionInMenuController.setDishDataInDishSection();



                menuDishList.getChildren().add(dishNode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<DishClient> getHardcodedDishes() {
        DishClient pizza = new DishClient(
                "Pizza Margherita",
                "Classic pizza with tomato, mozzarella, and basil.",
                35.0f,
                "https://example.com/images/pizza.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Tomato", "Cheese", "Basil")
        );

        DishClient burger = new DishClient(
                "Beef Burger",
                "Juicy beef patty with lettuce, tomato, and cheese.",
                42.5f,
                "https://example.com/images/burger.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Beef", "Lettuce", "Tomato", "Cheese", "Bun")
        );

        DishClient salad = new DishClient(
                "Greek Salad",
                "Fresh vegetables with feta cheese and olives.",
                28.0f,
                "https://example.com/images/salad.jpg",
                List.of("Haifa", "Jerusalem"),
                List.of("Cucumber", "Tomato", "Feta", "Olives")
        );

        return List.of(pizza, burger, salad);
    }

    public void orderDish(DishClient dish){
        addDishToOrderSection(dish);
    }

    private void addDishToOrderSection(DishClient dish){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderedDishSection.fxml"));
            Node dishNode = fxmlLoader.load();
            OrderedDishSectionController dishSectionInMenuController = fxmlLoader.getController();
            dishSectionInMenuController.setDishInDishSection(dish);
            dishSectionInMenuController.setDishDataInDishSection();

            orderDishList.getChildren().add(dishNode);
            dishesInOrder.add(dish);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
