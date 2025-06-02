package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;


import il.cshaifasweng.OCSFMediatorExample.entities.models.Reservation;

import java.io.Serializable;

public class IsReservationPossibleRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Reservation reservation;

    public IsReservationPossibleRequest(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
