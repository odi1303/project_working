package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuItem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import javafx.collections.ListChangeListener;

public class EditMenuController {

    @FXML
    public ScrollPane ChangeBranchesScrollPaneContainer;
    @FXML
    public ScrollPane ChangeIngredientsScrollPaneContainer;
    @FXML
    public VBox ChangeBranchesContainer;
    @FXML
    public VBox ChangeIngredientsContainer;

    private MenuClient menu;

    @FXML
    public VBox controlSection;
    @FXML
    public VBox menuContainer;

    @FXML
    public VBox editDishContainer;

    @FXML
    public TextField menuName;

    private MenuController menuController;

    private EditDishController editDishController;

    private ChangeBranchesController changeBranchesController;
    private ChangeIngredientsController changeIngredientsController;

    @FXML
    public void initialize() {
        try {
//            EventBus.getDefault().register(this);

            // Dynamically create menu section
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Node menuNode = menuLoader.load();
            menuController = menuLoader.getController();
            menuController.reinitialize(false, false);
            menuController.setMenu(menu);
            menuController.setEdit(this);
            // Use Platform.runLater to add the menu node to menuContainer
            Platform.runLater(() -> {
                menuContainer.getChildren().add(menuNode);
            });

            // Dynamically create editDish section
            FXMLLoader editDishLoader = new FXMLLoader(getClass().getResource("EditDish.fxml"));
            Node editDishNode = editDishLoader.load();
            editDishController = editDishLoader.getController();
            // Use Platform.runLater to update editDishContainer
            Platform.runLater(() -> {
                editDishContainer.setVisible(false);
                editDishContainer.setManaged(false);
                editDishContainer.getChildren().clear();
                editDishContainer.getChildren().add(editDishNode);
                editDishContainer.requestLayout();
            });
            editDishController.isSubmitted.addListener((observable, oldValue, newValue) -> handleEditDishIsSubmittedChange(oldValue, newValue));
            editDishController.getChangeBranchesButton().setOnAction(event -> changeBranches());
            editDishController.getChangeIngredientsButton().setOnAction(event -> changeIngredients());

            // Dynamically create ChangeBranches section
            FXMLLoader changeBranchesLoader = new FXMLLoader(getClass().getResource("ChangeBranches.fxml"));
            Node changeBranchesNode = changeBranchesLoader.load();
            changeBranchesController = changeBranchesLoader.getController();
            // Use Platform.runLater to update ChangeBranchesContainer
            Platform.runLater(() -> {
                ChangeBranchesScrollPaneContainer.setVisible(false);
                ChangeBranchesScrollPaneContainer.setManaged(false);
                ChangeBranchesContainer.getChildren().clear();
                ChangeBranchesContainer.getChildren().add(changeBranchesNode);
            });
            changeBranchesController.getSubmittedBranches().addListener((ListChangeListener<String>) change -> {
                handelSubmittedBranches();
            });
            changeBranchesController.getCancelButton().setOnAction(event -> hideChangeBranches());

            // Dynamically create ChangeIngredients section
            FXMLLoader changeIngredientsLoader = new FXMLLoader(getClass().getResource("ChangeIngredients.fxml"));
            Node changeIngredientsNode = changeIngredientsLoader.load();
            changeIngredientsController = changeIngredientsLoader.getController();
            // Use Platform.runLater to update ChangeIngredientsContainer
            Platform.runLater(() -> {
                ChangeIngredientsScrollPaneContainer.setVisible(false);
                ChangeIngredientsScrollPaneContainer.setManaged(false);
                ChangeIngredientsContainer.getChildren().clear();
                ChangeIngredientsContainer.getChildren().add(changeIngredientsNode);
            });
            changeIngredientsController.getSubmittedIngredients().addListener((ListChangeListener<String>) change -> {
                handleSubmittedIngredients();
            });
            changeIngredientsController.getCancelButton().setOnAction(event -> hideChangeIngredients());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void hideChangeBranches() {
        // Use Platform.runLater to update visibility
        Platform.runLater(() -> {
            ChangeBranchesScrollPaneContainer.setVisible(false);
            ChangeBranchesScrollPaneContainer.setManaged(false);
        });
    }

    private void changeBranches() {
        // Use Platform.runLater to update UI
        Platform.runLater(() -> {
            changeBranchesController.checkBranches(editDishController.getDish().getAvailableBranches());
            ChangeBranchesScrollPaneContainer.setVisible(true);
            ChangeBranchesScrollPaneContainer.setManaged(true);
        });
    }

    private void handelSubmittedBranches() {
        List<String> submittedBranches = changeBranchesController.getSubmittedBranches();
        // Use Platform.runLater to update UI
        Platform.runLater(() -> {
            editDishController.updateBranches(submittedBranches);
            ChangeBranchesScrollPaneContainer.setVisible(false);
            ChangeBranchesScrollPaneContainer.setManaged(false);
        });
    }

    private void hideChangeIngredients() {
        // Use Platform.runLater to update visibility
        Platform.runLater(() -> {
            ChangeIngredientsScrollPaneContainer.setVisible(false);
            ChangeIngredientsScrollPaneContainer.setManaged(false);
        });
    }

    private void changeIngredients() {
        // Use Platform.runLater to update UI
        Platform.runLater(() -> {
            changeIngredientsController.checkIngredients(editDishController.getDish().getIngredients());
            ChangeIngredientsScrollPaneContainer.setVisible(true);
            ChangeIngredientsScrollPaneContainer.setManaged(true);
        });
    }

    private void handleSubmittedIngredients() {
        List<String> submittedIngredients = changeIngredientsController.getSubmittedIngredients();
        // Use Platform.runLater to update UI
        Platform.runLater(() -> {
            editDishController.updateIngredients(submittedIngredients);
            ChangeIngredientsScrollPaneContainer.setVisible(false);
            ChangeIngredientsScrollPaneContainer.setManaged(false);
        });
    }

//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//    }

    public void deleteDishPressed(MenuItem dish) {
        PopupDialogService popupDialogService = new PopupDialogService();
        // Use Platform.runLater to handle popup and UI updates
        Platform.runLater(() -> {
            try {
                boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "are you sure you want to delete the dish?", (Stage) controlSection.getScene().getWindow());
                if (isConfirmed) {
                    menu.removeDish(dish);
                    menuController.setMenu(menu);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void EditDishPressed(MenuItem dish) {
        EditDish(dish);
    }

    private void handleEditDishIsSubmittedChange(boolean oldValue, boolean newValue) {
        if (newValue && (!oldValue)) {
            // Use Platform.runLater to update UI
            Platform.runLater(() -> {
                editDishContainer.setVisible(false);
                editDishContainer.setManaged(false);
                if (!editDishController.isEdit()) {
                    if (!menu.isContainsDish(editDishController.getDish())) {
                        addDishToMenu(editDishController.getDish());
                    } else {
                        try {
                            PopupDialogService popupDialogService = new PopupDialogService();
                            popupDialogService.openPopup("InformationWindow.fxml", "This dish is already in the menu.", (Stage) controlSection.getScene().getWindow());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (!editDishController.getDish().equals(editDishController.getOriginalDish())) {
                    menu.removeDish(editDishController.getOriginalDish());
                    addDishToMenu(editDishController.getDish());
                }
            });
        }
    }

    public void setMenu(MenuClient menu) {
        this.menu = menu;
        // Use Platform.runLater to update UI
        Platform.runLater(() -> {
            menuName.setText(menu.getMenuName());
            menuController.setMenu(menu);
        });
    }

    @FXML
    private void addNewDish() {
        // Use Platform.runLater to update UI
        Platform.runLater(() -> {
            editDishController.setDish(new MenuItem());
            editDishController.setEdit(false);
            editDishContainer.setVisible(true);
            editDishContainer.setManaged(true);
        });
    }

    @FXML
    private void addDishFromDatabase() {
        // Placeholder method; no UI updates currently
    }

    private void EditDish(MenuItem dish) {
        // Use Platform.runLater to update UI
        Platform.runLater(() -> {
            editDishController.setDish(dish);
            editDishController.setEdit(true);
            editDishContainer.setVisible(true);
            editDishContainer.setManaged(true);
        });
    }

    @FXML
    private void saveChanges() {
        PopupDialogService popupDialogService = new PopupDialogService();
        // Use Platform.runLater to handle popup and navigation
        Platform.runLater(() -> {
            try {
                boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml", "are you sure you want to save the changes?", (Stage) controlSection.getScene().getWindow());
                if (isConfirmed) {
                    goToHomePage();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addDishToMenu(MenuItem dish) {
        menu.addDish(dish);
        menuController.setMenu(menu);
    }

    @FXML
    public void goToHomePage() throws IOException {
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}