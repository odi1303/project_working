package org.example.finalproject.dal.models.complains;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.finalproject.dal.models.Restaurant;
import org.example.finalproject.dal.models.User;

import java.util.Date;

@Entity
@DiscriminatorValue("RESTAURANT")
@Data()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper=true)
public class RestaurantComplain extends Complain
{
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Restaurant restaurant;

    public RestaurantComplain(String description, Date date, User user, Restaurant restaurant) {
        super(description, date, user);
        this.restaurant = restaurant;
    }
}

