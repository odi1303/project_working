package org.example.finalproject.dal.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tables")
@Data
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Restaurant restaurant;

    @Column(name="size", nullable = false)
    private int size;

    @Column(name="inside", nullable = false)
    private boolean inside;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<TableOrder> tableOrders;

    public RestaurantTable() {}
}
