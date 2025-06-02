package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import il.cshaifasweng.OCSFMediatorExample.entities.models.Reservation;
import java.io.Serializable;

public class BookReservationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Reservation reservation;

    public BookReservationRequest(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
