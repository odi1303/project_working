package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "opening_hours")
public class OpeningHours implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="start_hour")
    public long startHour;

    @Column(name="end_hour")
    public long endHour;

    public OpeningHours() {}
    public OpeningHours(long StartHour, long EndHour) {
        startHour = StartHour;
        endHour = EndHour;
    }
   /* @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Restaurant restaurant;*/
}
