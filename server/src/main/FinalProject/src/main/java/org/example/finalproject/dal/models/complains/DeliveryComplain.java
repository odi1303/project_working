package org.example.finalproject.dal.models.complains;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.finalproject.dal.models.Delivery;
import org.example.finalproject.dal.models.User;

import java.util.Date;

@Entity
@DiscriminatorValue("DELIVERY")
@Data()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper=true)
public class DeliveryComplain extends Complain
{
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Delivery delivery;

    public DeliveryComplain(String description, Date registeredAt, User complainer, Delivery delivery) {
        super(description, registeredAt, complainer);
        this.delivery = delivery;
    }
}

