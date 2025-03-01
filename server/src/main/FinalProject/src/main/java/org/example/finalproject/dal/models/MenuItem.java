package org.example.finalproject.dal.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "menu_items")
public class MenuItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description", nullable=false)
    private String description;

    @Column(name = "price", nullable=false)
    private long price;

    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "available_for_takeout")
    private boolean availableForTakeout;

    @Column(name = "deleted_At")
    private Date deletedAt;

    public MenuItem() {}
    public MenuItem(String description, long price) {
        this.description = description;
        this.price = price;
    }
    public int getId() {
        return id;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }
}
