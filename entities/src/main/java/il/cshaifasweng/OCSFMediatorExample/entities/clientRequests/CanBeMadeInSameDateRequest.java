package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import il.cshaifasweng.OCSFMediatorExample.entities.models.ReservationDetails;
import java.io.Serializable;

public class CanBeMadeInSameDateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ReservationDetails reservationDetails;

    public CanBeMadeInSameDateRequest(ReservationDetails reservationDetails) {
        this.reservationDetails = reservationDetails;
    }

    public ReservationDetails getReservationDetails() {
        return reservationDetails;
    }
}
