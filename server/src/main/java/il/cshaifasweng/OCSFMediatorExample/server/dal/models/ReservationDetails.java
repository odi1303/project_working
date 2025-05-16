package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "Reservation Details")
public class ReservationDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    String branch;
    @Column
    String guestNumber;
    @Column
    String reservationSpace;
    @Column
    String reservationDate;
    @Column
    String time;
    public ReservationDetails(String branch,String guestNumber, String reservationSpace, String reservationDate, String time) {
        this.branch = branch;
        this.guestNumber = guestNumber;
        this.reservationSpace = reservationSpace;
        this.reservationDate = reservationDate;
        this.time = time;
    }

    public ReservationDetails() {

    }

    public String getBranch() {
        return branch;
    }
    public String getGuestNumber() {
        return guestNumber;
    }
    public String getReservationSpace() {
        return reservationSpace;
    }
    public String getReservationDate() {
        return reservationDate;
    }
    public String getTime() {
        return time;
    }

    public boolean isValid() {
        return isNonEmpty(branch)
                && isNonEmpty(guestNumber)
                && isNonEmpty(reservationSpace)
                && isNonEmpty(reservationDate)
                && isNonEmpty(time);
    }

    private boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }


}
