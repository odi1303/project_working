package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours sundayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours mondayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours tuesdayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours wednesdayOpeningHours;
    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    public OpeningHours thursdayOpeningHours;
    @OneToOne(orphanRemoval = true,fetch = FetchType.EAGER)
    public OpeningHours fridayOpeningHours;
    @OneToOne(orphanRemoval = true,fetch = FetchType.EAGER)
    public OpeningHours saturdayOpeningHours;

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
    public OpeningHours getTuesdayOpeningHours() {
        return tuesdayOpeningHours;
    }
    public OpeningHours getWednesdayOpeningHours() {
        return wednesdayOpeningHours;
    }
    public OpeningHours getThursdayOpeningHours() {
        return thursdayOpeningHours;
    }
    public OpeningHours getFridayOpeningHours() {
        return fridayOpeningHours;
    }
    public OpeningHours getSaturdayOpeningHours() {
        return saturdayOpeningHours;
    }



}
