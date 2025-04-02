package org.example.finalproject.dal.models;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_items")
public class DeliveryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItem menuItem;

    private int amount;

    public DeliveryItem() {}
    public DeliveryItem(MenuItem menuItem, int amount) {
        this.menuItem = menuItem;
        this.amount = amount;
    }
}
