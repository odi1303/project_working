package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NaturalId
    @Column(name="name", nullable=false)
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false)
    public UserType type;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    public List<TableOrder> tableOrders = new ArrayList<>();

    @Getter
    @Enumerated(EnumType.STRING)

    @Column(name="MailAddress", nullable=true)
    public String MailAddress;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    public List<Delivery> deliveries = new ArrayList<>();

    public User() {}
    public User(String name, String password, UserType type) {
        this.name = name;
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

}
