package org.example.finalproject.dal.models;

import jakarta.persistence.*;
import lombok.Data;
import org.example.finalproject.dal.UserType;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false)
    private UserType type;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<TableOrder> tableOrders;

    @OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
    private List<Delivery> deliveries;

    public boolean isAdmin() {
        return type == UserType.Admin;
    }

    public boolean isDietitian() {
        return type == UserType.Dietitian;
    }
}
