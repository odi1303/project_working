package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import il.cshaifasweng.OCSFMediatorExample.entities.models.ReservationDetails;
import java.io.Serializable;

public class CanBeMadeInOneHourRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ReservationDetails reservationDetails;

    public CanBeMadeInOneHourRequest(ReservationDetails reservationDetails) {
        this.reservationDetails = reservationDetails;
    }

    public ReservationDetails getReservationDetails() {
        return reservationDetails;
    }
}
