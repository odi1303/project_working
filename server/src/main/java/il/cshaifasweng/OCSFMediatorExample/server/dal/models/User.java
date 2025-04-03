package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;
import lombok.Data;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UserType;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@Column(name="type", nullable=false)
     private String s;
     */

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false)
    private UserType type;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<TableOrder> tableOrders;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<Delivery> deliveries;

    public List<TableOrder> getTableOrders() {
        return tableOrders;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public boolean isAdmin() {
        return type == UserType.Admin;
    }

    public boolean isDietitian() {
        return type == UserType.Dietitian;
    }
}
