package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "menu_items")
public class MenuItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "description", nullable=false)
    public String description;

    @Column(name = "price", nullable=false)
    public Long price;

    //@JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Restaurant restaurant;
/*
    @Column(name = "restaurant_id")
    public Long restaurantId;*/

    @Column(name = "available_for_takeout")
    public boolean availableForTakeout;

    @Column(name = "deleted_At")
    public Date deletedAt;

    public MenuItem() {}
    public MenuItem(String description, long price) {
        this.description = description;
        this.price = price;
    }
    public Long getId() {
        return id;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRestaurantId() {
        return restaurant.id;
    }
}
