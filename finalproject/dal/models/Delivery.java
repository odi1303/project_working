package org.example.finalproject.dal.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="arriavl_date")
    private Date arravilDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User inviter;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DeliveryItem> items;

    public Delivery() {}
    public Delivery(Date arravilDate, User inviter, List<DeliveryItem> items) {
        this.arravilDate = arravilDate;
        this.inviter = inviter;
        this.items = items;
    }

    public Long getId() {
        return id;
    }
    public Date getArravilDate() {
        return arravilDate;
    }
}
