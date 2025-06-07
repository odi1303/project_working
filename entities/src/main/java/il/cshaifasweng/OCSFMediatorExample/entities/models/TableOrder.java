package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;

//import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "table_orders")
public class TableOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="start_date")
    public LocalDateTime startDate;

    @Column(name="end_date")
    public LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Restaurant restaurant;

    @Column(name="active")
    public LocalDateTime active;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    public User allocator;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<RestaurantTable> restaurantTables;

    public TableOrder() {}

    public TableOrder(LocalDateTime startDate, LocalDateTime endDate, User allocator, Restaurant restaurant, List<RestaurantTable> restaurantTables) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.allocator = allocator;
        this.restaurant = restaurant;
        this.restaurantTables = restaurantTables;

    }

     public LocalDateTime getStartDate() {
        return startDate;
     }
    public Date getStartDateLegacy() {
        return Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
    }
     public LocalDateTime getEndDate() {
        return endDate;
     }

    public Long getId() {
        return id;
    }
}
