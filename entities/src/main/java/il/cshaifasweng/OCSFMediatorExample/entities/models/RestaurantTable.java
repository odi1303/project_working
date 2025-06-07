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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEnt branch;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "inside", nullable = false)
    private boolean inside;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<TableOrder> tableOrders;

    public RestaurantTable() {}

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