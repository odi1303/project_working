package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;
import lombok.Data;
import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NaturalId
    public String name;

    @Column(name="password", nullable=false)
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false)
    public UserType type;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    public List<TableOrder> tableOrders = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    public List<Delivery> deliveries = new ArrayList<>();

    public User() {}
    public User(String name, String password, UserType type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }
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

    public UserType getType() {
        return type;
    }
}
