package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private boolean isMain; //used to differentiate between menu used for main menu or used for un inputted menu

    private EditMenuController editMenuController;

    @FXML
    public void initialize() {
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

        //for order section
        dishesInOrder = new ArrayList<DishClient>();
        isOrder = false;
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
        List<String> branches = fullMenu.getAllBranches();
        List<String> ingredients = fullMenu.getAllIngredients();
        putBranchCheckBoxesInFilter(branches);
        putIngredientsCheckBoxesInFilter(ingredients);
        clearFilter();
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
        if (menuClient != null){
            for (DishClient dish : menuClient.getMenu()) {
                addDishToMenuSection(dish, isOrder);
            }
            currentMenu = new MenuClient(menuClient.getMenu());
        }else{
            currentMenu = null;
        }

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
            if (!isOrder && !isDelete) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DishSectionInMenu.fxml"));
                Node dishNode = fxmlLoader.load();
                DishSectionInMenuController dishSectionInMenuController = fxmlLoader.getController();
                dishSectionInMenuController.setDish(dish);
                dishSectionInMenuController.setDishDataInDishSection();

                menuDishList.getChildren().add(dishNode);
            }else if (!isDelete) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderableDishSectionInMenu.fxml"));
                Node dishNode = fxmlLoader.load();
                OrderableDishSectionInMenuController orderableDishSectionInMenuController = fxmlLoader.getController();
                orderableDishSectionInMenuController.reinitialize(this);
                orderableDishSectionInMenuController.setDishInDishSection(dish);
                orderableDishSectionInMenuController.setDishDataInDishSection();
                menuDishList.getChildren().add(dishNode);
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeletableDishSection.fxml"));
                Node dishNode = fxmlLoader.load();
                DeletableDishSectionController DeletableDishSectionController = fxmlLoader.getController();
                DeletableDishSectionController.reinitialize(this);
                DeletableDishSectionController.setDishInDishSection(dish);
                DeletableDishSectionController.setDishDataInDishSection();
                menuDishList.getChildren().add(dishNode);


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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


    private List<DishClient> getHardcodedDishes() {
        DishClient pizza = new DishClient(
                "Pizza Margherita",
                "Classic pizza with tomato, cheese, and olives.",
                35.0f,
                "https://example.com/images/pizza.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Cheese", "Tomato", "Olives")
        );

        DishClient burger = new DishClient(
                "Beef Burger",
                "Juicy beef patty with lettuce, tomato, and cheese.",
                42.5f,
                "https://example.com/images/burger.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Beef", "Lettuce", "Tomato", "Cheese")
        );

        DishClient salad = new DishClient(
                "Greek Salad",
                "Fresh vegetables with feta cheese and olives.",
                28.0f,
                "https://example.com/images/salad.jpg",
                List.of("Haifa", "Jerusalem"),
                List.of("Cucumber", "Tomato", "Feta", "Olives")
        );

        DishClient hummusPlate = new DishClient(
                "Hummus Plate",
                "Creamy hummus served with vegetables and pita.",
                25.0f,
                "https://example.com/images/hummus.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Hummus", "Tomato", "Onion", "Olives")
        );

        DishClient falafelPlate = new DishClient(
                "Falafel Plate",
                "Delicious falafel balls served with hummus and salad.",
                22.0f,
                "https://example.com/images/falafel.jpg",
                List.of("Tel Aviv", "Haifa", "Jerusalem"),
                List.of("Falafel", "Hummus", "Lettuce", "Tomato")
        );

        DishClient veggieBurger = new DishClient(
                "Veggie Burger",
                "A healthy burger with tomato, lettuce, and cheese.",
                38.0f,
                "https://example.com/images/veggie_burger.jpg",
                List.of("Tel Aviv", "Haifa"),
                List.of("Lettuce", "Tomato", "Cheese", "Onion")
        );

        DishClient cheeseSandwich = new DishClient(
                "Cheese Sandwich",
                "A simple cheese sandwich with tomato and lettuce.",
                20.0f,
                "https://example.com/images/cheese_sandwich.jpg",
                List.of("Haifa", "Jerusalem"),
                List.of("Cheese", "Tomato", "Lettuce")
        );

        DishClient beefSalad = new DishClient(
                "Beef Salad",
                "Salad with grilled beef, lettuce, tomato, and cucumber.",
                40.0f,
                "https://example.com/images/beef_salad.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Beef", "Lettuce", "Tomato", "Cucumber")
        );

        DishClient falafelWrap = new DishClient(
                "Falafel Wrap",
                "Falafel served in pita bread with lettuce and hummus.",
                24.0f,
                "https://example.com/images/falafel_wrap.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Falafel", "Hummus", "Lettuce")
        );

        DishClient mixedPlatter = new DishClient(
                "Mixed Platter",
                "Combination of falafel, hummus, tomato, and olives.",
                30.0f,
                "https://example.com/images/mixed_platter.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Falafel", "Hummus", "Tomato", "Olives")
        );

        return List.of(pizza, burger, salad, hummusPlate, falafelPlate, veggieBurger, cheeseSandwich, beefSalad, falafelWrap, mixedPlatter);
    }


    public void deleteDishPressed(DishClient dish) {
        editMenuController.deleteDishPressed(dish);
    }
    public void EditDishPressed(DishClient dish) {
        editMenuController.EditDishPressed(dish);
    }
}
