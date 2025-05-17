package il.cshaifasweng.OCSFMediatorExample.entities.dal.models.requests;


import il.cshaifasweng.OCSFMediatorExample.entities.dal.models.ReservationDetails;
import java.io.Serializable;

public class RequestReservationTimes implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ReservationDetails details;

    public RequestReservationTimes(ReservationDetails details) {
        this.details = details;
    }

    public ReservationDetails getDetails() {
        return details;
    }
}
