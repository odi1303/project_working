package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "table_orders")
public class TableOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="start_date")
    public Date startDate;

    @Column(name="end_date")
    public Date endDate;

    @Column(name="active")
    public Date active;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    public User allocator;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<RestaurantTable> restaurantTables;

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
