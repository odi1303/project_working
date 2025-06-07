package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "branch_reports")
public class BranchReportEnt implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Restaurant branch;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "month", nullable = false)
    private int month;

    @Column(name = "failed_orders")
    private int failedOrders;

    @Column(name = "complaints_handled_automatically")
    private int complaintsHandledAutomatically;

    @Column(name = "total_orders_income")
    private double totalOrdersIncome;

    @Column(name = "total_complaints_refunded")
    private double totalComplaintsRefund;

    @ElementCollection
    @CollectionTable(name = "branch_report_orders_per_day", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "orders")
    private List<Integer> ordersPerDay;

    @ElementCollection
    @CollectionTable(name = "branch_report_diners_per_day", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "diners")
    private List<Integer> dinersPerDay;

    @ElementCollection
    @CollectionTable(name = "branch_report_complaints_per_day", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "complaints")
    private List<Integer> complaintsPerDay;

    public BranchReportEnt() {}

    public BranchReportEnt(Restaurant branch, int year, int month, int failedOrders, double totalComplaintsRefund,
                           double totalOrdersIncome, int complaintsHandledAutomatically,
                           List<Integer> ordersPerDay, List<Integer> dinersPerDay, List<Integer> complaintsPerDay) {
        this.branch = branch;
        this.year = year;
        this.month = month;
        this.failedOrders = failedOrders;
        this.totalComplaintsRefund = totalComplaintsRefund;
        this.totalOrdersIncome = totalOrdersIncome;
        this.complaintsHandledAutomatically = complaintsHandledAutomatically;
        this.ordersPerDay = ordersPerDay;
        this.dinersPerDay = dinersPerDay;
        this.complaintsPerDay = complaintsPerDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getBranch() {
        return branch;
    }

    public void setBranch(Restaurant branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getFailedOrders() {
        return failedOrders;
    }

    public void setFailedOrders(int failedOrders) {
        this.failedOrders = failedOrders;
    }

    public int getComplaintsHandledAutomatically() {
        return complaintsHandledAutomatically;
    }

    public void setComplaintsHandledAutomatically(int complaintsHandledAutomatically) {
        this.complaintsHandledAutomatically = complaintsHandledAutomatically;
    }

    public double getTotalOrdersIncome() {
        return totalOrdersIncome;
    }

    public void setTotalOrdersIncome(double totalOrdersIncome) {
        this.totalOrdersIncome = totalOrdersIncome;
    }

    public double getTotalComplaintsRefund() {
        return totalComplaintsRefund;
    }

    public void setTotalComplaintsRefund(double totalComplaintsRefund) {
        this.totalComplaintsRefund = totalComplaintsRefund;
    }

    public List<Integer> getOrdersPerDay() {
        return ordersPerDay;
    }

    public void setOrdersPerDay(List<Integer> ordersPerDay) {
        this.ordersPerDay = ordersPerDay;
    }

    public List<Integer> getDinersPerDay() {
        return dinersPerDay;
    }

    public void setDinersPerDay(List<Integer> dinersPerDay) {
        this.dinersPerDay = dinersPerDay;
    }

    public List<Integer> getComplaintsPerDay() {
        return complaintsPerDay;
    }

    public void setComplaintsPerDay(List<Integer> complaintsPerDay) {
        this.complaintsPerDay = complaintsPerDay;
    }
}