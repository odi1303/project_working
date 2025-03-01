package org.example.finalproject.dal.models.requests;

import jakarta.persistence.*;
import org.example.finalproject.dal.RequestStatus;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="action", discriminatorType = DiscriminatorType.STRING)
public abstract class Request
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private RequestStatus status;

    public Request() { }

    public int getId() {
        return id;
    }

    public void approve() {
        status = RequestStatus.Approved;
    }
    public void reject() {
        status = RequestStatus.Rejected;
    }
}

