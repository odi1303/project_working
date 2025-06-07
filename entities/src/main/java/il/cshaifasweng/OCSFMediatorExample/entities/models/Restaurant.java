package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
//import org.hibernate.annotations.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor()
@Entity
@Table(name = "restaurants")
public class Restaurant extends BranchEnt implements Serializable {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "restaurant_id")
    public Long id;
    @NaturalId
    @Column(unique = true, nullable = false)
    public String name;
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

    public OpeningHours getOpeningHours(LocalDate date) {
        return switch(date.getDayOfWeek()){
            case SUNDAY -> getSundayOpeningHours();
            case MONDAY -> getMondayOpeningHours();
            case TUESDAY -> getTuesdayOpeningHours();
            case WEDNESDAY -> getWednesdayOpeningHours();
            case THURSDAY -> getThursdayOpeningHours();
            case FRIDAY -> getFridayOpeningHours();
            case SATURDAY -> getSaturdayOpeningHours();
        };
    }
    public OpeningHours getOpeningHours(LocalDateTime date) {
        return switch(date.getDayOfWeek()){
            case SUNDAY -> getSundayOpeningHours();
            case MONDAY -> getMondayOpeningHours();
            case TUESDAY -> getTuesdayOpeningHours();
            case WEDNESDAY -> getWednesdayOpeningHours();
            case THURSDAY -> getThursdayOpeningHours();
            case FRIDAY -> getFridayOpeningHours();
            case SATURDAY -> getSaturdayOpeningHours();
        };
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

    public Restaurant getRestaurant(int id){
        if (this.id.equals(id)){
            return this;
        }
        else{
            return null;
        }
    }
    public Restaurant getRestaurant(String name){
        if (this.name.equals(name)){
            return this;
        }
        else{
            return null;
        }
    }

    public String getBranchName() {
        return name;
    }
}
