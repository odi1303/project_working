package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
    public int getGuestNumber() {
        return Integer.parseInt(guestNumber);
    }
    public String getReservationSpace() {
        return reservationSpace;
    }
    public boolean isInside() {
        return Objects.equals(reservationSpace, "Indoors");
    }

    public String getReservationDate() {
        return reservationDate;
    }
    public String getTime() {
        return time;
    }

    public LocalTime getStartTime() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        return LocalTime.parse(time, timeFormatter);
    }
    public LocalDateTime getStartDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("y-M-d");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

        LocalDate date = LocalDate.parse(reservationDate, dateFormatter);
        LocalTime Time = LocalTime.parse(time, timeFormatter);

        return date.atTime(Time);
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
