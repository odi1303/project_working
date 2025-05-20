package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import il.cshaifasweng.OCSFMediatorExample.entities.models.Reservation;
import java.io.Serializable;

public class ReservationCancelationFeeRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Reservation reservation;

    public ReservationCancelationFeeRequest(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
