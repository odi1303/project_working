package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.BranchManager;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ReportsView {

    @FXML private Button back_button;
    @FXML private AnchorPane ch;
    @FXML private LineChart<String, Number> chart;
    @FXML private ComboBox<String> report_list;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        report_list.setItems(FXCollections.observableArrayList("Monthly Complaints"));
        report_list.getSelectionModel().selectFirst();
        loadComplaints();
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    private void loadComplaints() {
        try {
            App.sendMessageToServer("#getAllComplaints");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void choosing_report(ActionEvent event) {
        if (report_list.getValue().equals("Monthly Complaints")) {
            loadComplaints();
        }
    }

    @FXML
    void to_go_back(ActionEvent event) throws IOException {
        App.setRoot("manager_personal_page");
    }

    @Subscribe
    public void onComplaintsReceived(List<Complain> complaints) {
        Platform.runLater(() -> {
            User currentUser = AppState.getCurrentUser();
            if (complaints != null && currentUser instanceof BranchManager branchManager) {
                List<Complain> branchComplaints = complaints.stream()
                        .filter(c -> c.getBranch_id() != null && c.getBranch_id().equals((long)branchManager.getBranchID()))
                        .toList();
                
                XYChart.Series<String, Number> series = createComplaintHistogram(branchComplaints);
                chart.getData().clear();
                chart.getData().add(series);
                chart.setTitle("Complaints by Day - " + LocalDate.now().getMonth());
            }
        });
    }

    private XYChart.Series<String, Number> createComplaintHistogram(List<Complain> complaints) {
        return null;
    }

    public void chosing_report(ActionEvent actionEvent) {
    }
}