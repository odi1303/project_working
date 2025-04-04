package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

@Entity
@Table(name = "opening_hours")
public class OpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="start_hour")
    public Long startHour;

    @Column(name="end_hour")
    public Long endHour;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Restaurant restaurant;
}
