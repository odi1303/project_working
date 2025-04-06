package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_items")
public class DeliveryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    public MenuItem menuItem;

    public Long amount;

    public DeliveryItem() {}
    public DeliveryItem(MenuItem menuItem, Long amount) {
        this.menuItem = menuItem;
        this.amount = amount;
    }
    public long DeliveryItemPrice()
    {
        if(menuItem == null)
            return 0;
        return menuItem.price;
    }
}
