package il.cshaifasweng.OCSFMediatorExample.entities.dal.models;

//import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
@Entity
@Table(name = "delivery")
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="arriavl_date")
    public Date arravilDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public User inviter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Restaurant restaurant;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    public List<DeliveryItem> items;

    public Delivery() {}
    public Delivery(Date arravilDate, User inviter, List<DeliveryItem> items,Restaurant restaurant) {
        this.arravilDate = arravilDate;
        this.inviter = inviter;
        this.items = items;
        this.restaurant = restaurant;
    }
    public long DeliveryPrice ()
    {
        long price = 0;
        for(DeliveryItem item : items)
        {
            price += item.DeliveryItemPrice();
        }
        return price;
    }

    public Long getId() {
        return id;
    }
    public Date getArravilDate() {
        return arravilDate;
    }
}
