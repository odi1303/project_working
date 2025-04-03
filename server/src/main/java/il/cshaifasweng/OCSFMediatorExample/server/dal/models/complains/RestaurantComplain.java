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
    @ManyToOne(optional = false)
    private Restaurant restaurant;
    @Id
    private Long id;

    public RestaurantComplain(String description, Date date, User user, Restaurant restaurant) {
        super(description, date, user);
        this.restaurant = restaurant;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

