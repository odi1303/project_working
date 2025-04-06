package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.server.dal.models.BranchManager;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Delivery;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.TableOrder;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        report_list.setItems(FXCollections.observableArrayList("Complaints", "Deliveries", "Reservations"));
        report_list.getSelectionModel().selectFirst();
        loadReportData(report_list.getValue());
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    private void loadReportData(String reportType) {
        try {
            switch (reportType) {
                case "Complaints":
                    App.sendMessageToServer("#getAllComplaints");
                    break;
                case "Deliveries":
                    App.sendMessageToServer("#getAllDeliveries");
                    break;
                case "Reservations":
                    App.sendMessageToServer("#getAllReservations");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void choosing_report(ActionEvent event) {
        String selectedReport = report_list.getValue();
        if (selectedReport != null) {
            loadReportData(selectedReport);
        }
    }

    @FXML
    void to_go_back(ActionEvent event) throws IOException {
        App.setRoot("manager_personal_page");
    }

    @Subscribe
    public void onComplaintsReceived(List<Complain> complaints) {
        handleReportData(complaints, "Complaints", Complain::getRegisteredAt, Complain::getRestaurantId);
    }

    @Subscribe
    public void onDeliveriesReceived(List<Delivery> deliveries) {
        handleReportData(deliveries, "Deliveries", Delivery::getArravilDate, delivery -> delivery.restaurant.getId());
    }

    @Subscribe
    public void onReservationsReceived(List<TableOrder> tableOrders) {
        handleReportData(tableOrders, "Reservations", TableOrder::getStartDate, tableOrder -> tableOrder.restaurant.getId());
    }

    private <T> void handleReportData(List<T> data, String reportType,
                                      java.util.function.Function<T, Date> dateExtractor,
                                      java.util.function.Function<T, Long> branchIdExtractor) {
        Platform.runLater(() -> {
            il.cshaifasweng.OCSFMediatorExample.entities.User currentUser = AppState.getCurrentUser();
            if (data != null && currentUser instanceof BranchManager branchManager) {
                List<T> branchData = data.stream()
                        .filter(d -> branchIdExtractor.apply(d) != null &&
                                branchIdExtractor.apply(d).equals((long)branchManager.getBranchID()))
                        .toList();

                XYChart.Series<String, Number> series = createHistogram(branchData, dateExtractor, reportType);
                chart.getData().clear();
                chart.getData().add(series);
                chart.setTitle(reportType + " by Day - " + LocalDate.now().getMonth());
            }
        });
    }

    private <T> XYChart.Series<String, Number> createHistogram(List<T> data,
                                                               java.util.function.Function<T, Date> dateExtractor,
                                                               String reportType) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(reportType);

        LocalDate now = LocalDate.now();
        int daysInMonth = now.lengthOfMonth();
        int[] counts = new int[daysInMonth];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        for (T item : data) {
            Date date = dateExtractor.apply(item);
            if (date != null) {
                LocalDate itemDate = date.toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();

                if (itemDate.getMonth() == now.getMonth() && itemDate.getYear() == now.getYear()) {
                    int day = itemDate.getDayOfMonth() - 1;
                    counts[day]++;
                }
            }
        }

        for (int i = 0; i < daysInMonth; i++) {
            String day = String.valueOf(i + 1);
            series.getData().add(new XYChart.Data<>(day, counts[i]));
        }

        return series;
    }
}