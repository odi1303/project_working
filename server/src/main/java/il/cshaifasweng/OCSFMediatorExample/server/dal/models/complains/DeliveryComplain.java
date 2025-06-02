package il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import il.cshaifasweng.OCSFMediatorExample.entities.models.Delivery;
import il.cshaifasweng.OCSFMediatorExample.entities.models.User;

import java.util.Date;

@Entity
@DiscriminatorValue("DELIVERY")
@Data()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper=true)
public class DeliveryComplain extends Complain
{
    @ManyToOne(optional = false)
    public Delivery delivery;
    @Id
    public Long id;

    public DeliveryComplain(String branch, String headline, String description, Date registeredAt, User complainer,String email, Delivery delivery) {
        super(description, registeredAt, complainer);
        this.delivery = delivery;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

