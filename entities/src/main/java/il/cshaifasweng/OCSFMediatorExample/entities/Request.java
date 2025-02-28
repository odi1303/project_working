package il.cshaifasweng.OCSFMediatorExample.entities;

import jakarta.persistence.*;

public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;
    @Column(name = "descriptions")
    private String description;

    public Request() {
    }

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

