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
    public Long id;

    @Column(name="password", nullable=false)
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false)
    public UserType type;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    public List<TableOrder> tableOrders;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false)
    public String MailAddress;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    public List<Delivery> deliveries;

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
