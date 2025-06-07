package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tables")
@Data
public class RestaurantTable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = true)
    private Restaurant branch;

    @Column(name = "size", nullable = true)
    private Long size;

    @Column(name = "inside", nullable = true)
    private boolean inside;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<TableOrder> tableOrders;

    public RestaurantTable() {}

    public RestaurantTable(long size, boolean inside, List<TableOrder> tableOrders) {
        this.size = size;
        this.inside = inside;
        this.tableOrders = tableOrders;
    }
    public int getSize() {
        return size.intValue();
    }

    public boolean isInside() {
        return inside;
    }

    public List<TableOrder> getTableOrders() {
        return tableOrders;
    }
}