package il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests;

import jakarta.persistence.*;
import il.cshaifasweng.OCSFMediatorExample.server.dal.RequestStatus;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="action", discriminatorType = DiscriminatorType.STRING)
public abstract class Request
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private RequestStatus status;

    public Request() { }

    public Long getId() {
        return id;
    }

    public void approve() {
        status = RequestStatus.Approved;
    }
    public void reject() {
        status = RequestStatus.Rejected;
    }
}

