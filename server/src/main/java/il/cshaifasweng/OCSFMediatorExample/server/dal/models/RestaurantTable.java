package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "tables")
@Data
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Restaurant restaurant;

    @Column(name="size", nullable = false)
    public Long size;

    @Column(name="inside", nullable = false)
    public boolean inside;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<TableOrder> tableOrders;

    public int getSize() {
        return size.intValue();
    }

    public boolean isInside() {
        return inside;
    }

    public List<TableOrder> getTableOrders() {
        return tableOrders;
    }

    public RestaurantTable() {}
}
