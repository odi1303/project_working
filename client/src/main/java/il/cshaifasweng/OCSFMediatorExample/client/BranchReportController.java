package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.models.BranchReportEnt;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class BranchReportController {

    @FXML
    private BarChart<String, Number> orderChart;

    @FXML
    private Label headlineText;

    @FXML
    private BarChart<String, Number> peopleChart;

    @FXML
    private BarChart<String, Number> complaintsChart;

    @FXML
    private Label orderSummaryLabel;

    @FXML
    private Label peopleSummaryLabel;

    @FXML
    private Label complaintsSummaryLabel;

    public void loadBranchReport(BranchReportEnt report) {
        if (report == null) {
            Platform.runLater(() -> {
                orderSummaryLabel.setText("No report found for the selected branch and period.");
                peopleSummaryLabel.setText("");
                complaintsSummaryLabel.setText("");
            });
            return;
        }

        orderChart.getData().clear();
        peopleChart.getData().clear();
        complaintsChart.getData().clear();

        XYChart.Series<String, Number> ordersSeries = prepareHistogramData(report.getOrdersPerDay(), "Orders per Day");
        XYChart.Series<String, Number> peopleSeries = prepareHistogramData(report.getDinersPerDay(), "People per Day");
        XYChart.Series<String, Number> complaintsSeries = prepareHistogramData(report.getComplaintsPerDay(), "Complaints per Day");

        orderChart.getData().add(ordersSeries);
        peopleChart.getData().add(peopleSeries);
        complaintsChart.getData().add(complaintsSeries);

        int totalOrders = report.getOrdersPerDay().stream().mapToInt(Integer::intValue).sum();
        int totalPeople = report.getDinersPerDay().stream().mapToInt(Integer::intValue).sum();
        int totalComplaints = report.getComplaintsPerDay().stream().mapToInt(Integer::intValue).sum();

        orderSummaryLabel.setText("Total Orders: " + totalOrders + " | Failed Orders: " + report.getFailedOrders() + " | Total income: " + report.getTotalOrdersIncome());
        peopleSummaryLabel.setText("Total Diners: " + totalPeople);
        complaintsSummaryLabel.setText("Total Complaints: " + totalComplaints + " | Complaints handled automatically: " + report.getComplaintsHandledAutomatically());
    }

    private XYChart.Series<String, Number> prepareHistogramData(List<Integer> dataList, String seriesName) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);

        for (int i = 0; i < dataList.size(); i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), dataList.get(i)));
        }

        return series;
    }

    @Subscribe
    public void onBranchReportEvent(BranchReportEvent event) {
        Platform.runLater(() -> {
            System.out.println("Received BranchReportEvent");
            loadBranchReport(event.getBranchReport());
        });
    }

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        headlineText.setText("Branch Report For Branch: " + SelectReportController.getSelectedBranchName() +
                " For Month: " + SelectReportController.getSelectedMonth() + "/" +
                SelectReportController.getSelectedYear());
    }

    @FXML
    void goBack(ActionEvent event) throws Exception {
        App.setRoot("pickReport");
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}