package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "table_orders")
public class TableOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="active")
    private Date active;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private User allocator;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<RestaurantTable> restaurantTables;

    public TableOrder() {}

    public TableOrder(Date startDate, Date endDate, User allocator, List<RestaurantTable> restaurantTables) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.allocator = allocator;
        this.restaurantTables = restaurantTables;
    }

     public Date getStartDate() {
        return startDate;
     }
     public Date getEndDate() {
        return endDate;
     }

    public Long getId() {
        return id;
    }
}
