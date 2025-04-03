package org.example.finalproject.dal.models;

import jakarta.persistence.*;
import lombok.Data;
import org.example.finalproject.dal.UserType;

import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
