package org.example.finalproject.dal.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private OpeningHours sundayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private OpeningHours mondayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private OpeningHours thusdayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private OpeningHours wendsdayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private OpeningHours thursdayOpeningHours;
    @OneToOne(orphanRemoval = true,fetch = FetchType.EAGER)
    private OpeningHours fridayOpeningHours;
    @OneToOne(orphanRemoval = true,fetch = FetchType.EAGER)
    private OpeningHours saterdayOpeningHours;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<RestaurantTable> tables;

    public Long getId() {
        return id;
    }

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public OpeningHours getSundayOpeningHours() {
        return sundayOpeningHours;
    }

    public OpeningHours getMondayOpeningHours() {
        return mondayOpeningHours;
    }
}
