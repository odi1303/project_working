package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeBranchesController {

    @FXML
    public VBox branchesContainer;
    @FXML
    public TextField newBranchNameField;
    @FXML
    public Button cancelButton;

    private ObservableList<String> submittedBranches = FXCollections.observableArrayList();

    public ObservableList<String> getSubmittedBranches() {
        return submittedBranches;
    }
    public Button getCancelButton() {
        return cancelButton;
    }

    @FXML
    public void initialize() {
        List<String> branches = getAllPossibleBranches();
        putBranchCheckBoxesInBranchesContainer(branches);
    }

    private void putBranchCheckBoxesInBranchesContainer(List<String> branches){
        branchesContainer.setVisible(false);
        branchesContainer.setManaged(false);

        branchesContainer.getChildren().clear();
        for (String branch : branches){
            CheckBox checkBox = new CheckBox(branch);
            checkBox.setSelected(true);
            branchesContainer.getChildren().add(checkBox);
        }
        branchesContainer.setVisible(true);
        branchesContainer.setManaged(true);
    }

    private List<String> getAllPossibleBranches() {
        try {
            @SuppressWarnings("unchecked")
            List<String> response = (List<String>) RequestManager.getInstance().sendAndWait(
                    "get all branches",
                    5000,
                    String.class,
                    List.class
            );
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
//    private List<String> getAllPossibleBranches() {
//        return Arrays.asList(
//                "Rome", "Naples", "Florence",
//                "Milan", "Venice",
//                "New York", "Chicago", "Dallas",
//                "Nashville", "Houston", "Atlanta",
//                "Tokyo", "Osaka", "Kyoto",
//                "Fukuoka", "Sapporo"
//        );
//    }

    private List<String> getCheckedBranches() {
        List<String> checkedBranches = new ArrayList<>();

        for (var node : branchesContainer.getChildren()) {
            if (node instanceof CheckBox) {  // Ensure the node is a CheckBox
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {  // Check if it is selected
                    checkedBranches.add(checkBox.getText());
                }
            }
        }
        return checkedBranches;
    }

    @FXML
    public void submitBranchesChange(){
        submittedBranches.setAll(getCheckedBranches());
    }


    public void checkBranches(List<String> branchesToCheck) {
        for (var node : branchesContainer.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (branchesToCheck.contains(checkBox.getText())) {
                    checkBox.setSelected(true); // Check the checkbox if it's in the list
                }else{
                    checkBox.setSelected(false);
                }
            }
        }
    }
}
