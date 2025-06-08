package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.models.Restaurant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelectReportController {

    @FXML
    private Button getReportButton;

    @FXML
    private ComboBox<Restaurant> branchComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    // Static variables to store the selected year and month
    private static int selectedYear;
    private static int selectedMonth;
    private static Long selectedBranchId;
    private static String selectedBranchName;

    @FXML
    public void initialize() {
        // Initially disable confirm button
        getReportButton.setDisable(true);

        // Set "Choose Branch" as placeholder text
        branchComboBox.setPromptText("Choose Branch");

        // Customize branchComboBox display format
        branchComboBox.setCellFactory(lv -> new ListCell<Restaurant>() {
            protected void updateItem(Restaurant item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getId() + " - " + item.getBranchName());
            }
        });
        branchComboBox.setButtonCell(new ListCell<Restaurant>() {
            @Override
            protected void updateItem(Restaurant item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getId() + " - " + item.getBranchName());
            }
        });

        // Populate the branchComboBox with only the branches the user has access to
        List<Restaurant> filteredBranches = SimpleClient.BranchList.stream()
                .filter(branch -> SimpleClient.userBranchesIdList.contains(branch.getId()))
                .collect(Collectors.toList()).reversed();


        branchComboBox.setItems(FXCollections.observableArrayList(filteredBranches));

        // Populate yearComboBox with years from 2024 to the current year
        int currentYear = LocalDate.now().getYear();
        yearComboBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(2024, currentYear).boxed().toList()));

        // Listener for year selection
        yearComboBox.valueProperty().addListener((obs, oldYear, newYear) -> {
            if (newYear != null) {
                selectedYear = newYear;
                updateMonthComboBox(); // Refresh month list based on selected year
                validateSelection(); // Check if confirm button should be enabled
            }
        });

        // Listener for month selection
        monthComboBox.valueProperty().addListener((obs, oldMonth, newMonth) -> {
            if (newMonth != null) {
                selectedMonth = monthComboBox.getItems().indexOf(newMonth) + 1;
                validateSelection(); // Check if confirm button should be enabled
            }
        });

        // Listener for branch selection (only if enabled)
        branchComboBox.valueProperty().addListener((obs, oldBranch, newBranch) -> {
            selectedBranchId = newBranch.getId();
            selectedBranchName = newBranch.getBranchName();
            validateSelection(); // Check if confirm button should be enabled
        });
    }


    // Show months up to the current month (if this year was choosen
    private void updateMonthComboBox() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        List<String> months = List.of(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        if (selectedYear == currentYear) {
            // Restrict to months up to the current month
            monthComboBox.setItems(FXCollections.observableArrayList(months.subList(0, currentMonth)));
        } else {
            // Show all 12 months for previous years
            monthComboBox.setItems(FXCollections.observableArrayList(months));
        }

        monthComboBox.getSelectionModel().clearSelection();
    }

    // Enable get report button of year month and branch were choosen
    private void validateSelection() {
        boolean isYearSelected = yearComboBox.getValue() != null;
        boolean isMonthSelected = monthComboBox.getValue() != null;
        boolean isBranchSelected = (branchComboBox.isDisabled() || branchComboBox.getValue() != null);

        getReportButton.setDisable(!(isYearSelected && isMonthSelected && isBranchSelected));
    }

    // Getter methods for selected values
    public static int getSelectedYear() {
        return selectedYear;
    }

    public static int getSelectedMonth() {
        return selectedMonth;
    }

    public static Long getSelectedBranchId() {
        return selectedBranchId;
    }

    public static String getSelectedBranchName() { return selectedBranchName;}

    @FXML
    void getReport(ActionEvent event) throws Exception {
        App.setRoot("branchReport");
    }


    @FXML
    void goBack(ActionEvent event) throws Exception {
        App.setRoot("primary");
    }
}