package org.example.finalproject.dal.models;

import jakarta.persistence.*;

@Entity
@Table(name = "opening_hours")
public class OpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="start_hour")
    private int startHour;

    @Column(name="end_hour")
    private int endHour;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
