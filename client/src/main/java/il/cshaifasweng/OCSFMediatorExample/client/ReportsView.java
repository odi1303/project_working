package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public class ReportsView {

    @FXML
    private Button back_button;
    @FXML private AnchorPane ch;
    @FXML private LineChart<String, Number> chart;
    @FXML private ComboBox<String> report_list;

    private ClientUserInfo currentUser; // Holds the currently logged-in user

    @FXML
    public void initialize() {
        // Register to listen for events from the server
        EventBus.getDefault().register(this);

        // Setup the dropdown report type selector
        report_list.setItems(FXCollections.observableArrayList("Monthly Complaints"));
        report_list.getSelectionModel().selectFirst();

        // Fetch complaints from the server
        fetchComplaints();
    }

    /**
     * Sends a request to the server to fetch all complaints.
     */
    private void fetchComplaints() {
        try {
            App.sendMessageToServer("#getAllComplaints"); // Request complaints from the server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles complaints received from the server.
     * Filters them and updates the chart accordingly.
     *
     * @param complaints List of complaints sent by the server.
     */
    @Subscribe
    public void onComplaintsReceived(ComplaintsListEvent complaintsEvent) {
        List<complaint_to_answer> complaints = complaintsEvent.getComplaints();
        displayComplaintsHistogram(complaints);
    }

    /**
     * Filters complaints for the current user's branch and displays a histogram.
     *
     * @param allComplaints List of all complaints.
     */
    private void displayComplaintsHistogram(List<complaint_to_answer> allComplaints) {
        Platform.runLater(() -> {
            if (currentUser == null || currentUser.getBranchId() == null) {
                chart.setTitle("Please log in as a Branch Manager.");
                return;
            }

            // Filter complaints belonging to the manager's branch
            List<complaint_to_answer> branchComplaints = allComplaints.stream()
                    .filter(complaint -> complaint.getBranch().equals(String.valueOf(currentUser.getBranchId())))
                    .collect(Collectors.toList());

            // Get the current month and initialize the histogram data
            LocalDate now = LocalDate.now();
            int daysInMonth = now.lengthOfMonth();
            int[] complaintCounts = new int[daysInMonth];

            // Parse complaints and count them by day of the current month
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (complaint_to_answer complaint : branchComplaints) {
                try {
                    LocalDate complaintDate = LocalDate.parse(complaint.getDate(), formatter);

                    // Increment count for complaints in the current month and year
                    if (complaintDate.getMonth() == now.getMonth() && complaintDate.getYear() == now.getYear()) {
                        int day = complaintDate.getDayOfMonth() - 1; // Get zero-based index
                        complaintCounts[day]++;
                    }
                } catch (Exception e) {
                    // Handle parsing errors if the date format is invalid
                    e.printStackTrace();
                }
            }

            // Prepare the chart series
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Complaints per Day - " + now.getMonth().toString());

            for (int i = 0; i < daysInMonth; i++) {
                series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), complaintCounts[i]));
            }

            // Update the chart with the histogram data
            chart.getData().clear();
            chart.getData().add(series);
            chart.setTitle("Monthly Complaint Distribution - Branch " + currentUser.getBranchId());
            chart.getXAxis().setLabel("Day of Month");
            chart.getYAxis().setLabel("Number of Complaints");
        });
    }

    /**
     * Handles the selection of a report type in the dropdown.
     *
     * @param event ActionEvent triggered by the dropdown.
     */
    @FXML
    void choosing_report(ActionEvent event) {
        if ("Monthly Complaints".equals(report_list.getValue())) {
            fetchComplaints();
        }
    }

    /**
     * Redirects the user to the manager's personal page.
     *
     * @param event ActionEvent triggered by the back button.
     */
    @FXML
    void to_go_back(ActionEvent event) throws IOException {
        App.setRoot("manager_personal_page");
    }

    /**
     * Sets the current user (BranchManager) details.
     *
     * @param user The current user.
     */
    public void setCurrentUser(ClientUserInfo user) {
        this.currentUser = user;
    }

    /**
     * Unregisters from the EventBus when the view is destroyed.
     */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}