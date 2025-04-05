package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "restaurant_id")
    public Long id;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours sundayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours mondayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours thusdayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours wendsdayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours thursdayOpeningHours;
    @OneToOne(orphanRemoval = true,fetch = FetchType.EAGER)
    public OpeningHours fridayOpeningHours;
    @OneToOne(orphanRemoval = true,fetch = FetchType.EAGER)
    public OpeningHours saterdayOpeningHours;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public List<RestaurantTable> tables;

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
