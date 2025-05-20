package il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import il.cshaifasweng.OCSFMediatorExample.entities.models.Restaurant;
import il.cshaifasweng.OCSFMediatorExample.entities.models.User;

import java.util.Date;

@Entity
@DiscriminatorValue("RESTAURANT")
@Data()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper=true)
public class RestaurantComplain extends Complain
{
    @ManyToOne(optional = false)
    public Restaurant restaurant;
    @Id
    public Long id;

    public RestaurantComplain(String description, Date date, User user, Restaurant restaurant) {
        super(description, date, user);
        this.restaurant = restaurant;
    }

    public RestaurantComplain(String branch, String headline, String description, Date registeredAt, String email) {
        this.branch = branch;
        this.headline = headline;
        this.description = description;
        this.registeredAt = registeredAt;
        this.email = email;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

