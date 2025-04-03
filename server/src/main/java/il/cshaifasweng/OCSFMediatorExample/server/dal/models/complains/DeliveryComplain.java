package il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Delivery;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

import java.util.Date;

@Entity
@DiscriminatorValue("DELIVERY")
@Data()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper=true)
public class DeliveryComplain extends Complain
{
    @ManyToOne(optional = false)
    private Delivery delivery;
    @Id
    private Long id;

    public DeliveryComplain(String description, Date registeredAt, User complainer, Delivery delivery) {
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

