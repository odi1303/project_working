package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_items")
public class DeliveryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItem menuItem;

    private Long amount;

    public DeliveryItem() {}
    public DeliveryItem(MenuItem menuItem, Long amount) {
        this.menuItem = menuItem;
        this.amount = amount;
    }
}
