package il.cshaifasweng.OCSFMediatorExample.client;

public class ReservationDetails {
    String branch;
    String guestNumber;
    String reservationSpace;
    String reservationDate;
    String time;

    public ReservationDetails(String branch,String guestNumber, String reservationSpace, String reservationDate, String time) {
        this.branch = branch;
        this.guestNumber = guestNumber;
        this.reservationSpace = reservationSpace;
        this.reservationDate = reservationDate;
        this.time = time;
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
