package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor()
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

    public long sales;
    public Long getId() {
        return id;
    }

    public Long return_money(long amount_to_return)
    {
        return  Math.min(amount_to_return, sales);
    }
    public void add_money(long money)
    {
        sales += money;
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


    public Restaurant(OpeningHours sundayOpeningHours,OpeningHours mondayOpeningHours,
                      OpeningHours tuesdayOpeningHours,OpeningHours wednesdayOpeningHours,
                      OpeningHours thursdayOpeningHours,OpeningHours fridayOpeningHours,
                      OpeningHours saturdayOpeningHours,List<RestaurantTable> tables)
    {
        this.sundayOpeningHours = sundayOpeningHours;
        this.mondayOpeningHours = mondayOpeningHours;
        this.tuesdayOpeningHours = tuesdayOpeningHours;
        this.wednesdayOpeningHours = wednesdayOpeningHours;
        this.thursdayOpeningHours = thursdayOpeningHours;
        this.fridayOpeningHours = fridayOpeningHours;
        this.saturdayOpeningHours = saturdayOpeningHours;
        this.tables = tables;
        this.sales = 0;
    }

}
