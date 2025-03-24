package il.cshaifasweng.OCSFMediatorExample.client;

public class Reservation {
    private final ReservationDetails reservationDetails;
    private final PersonalInformation personalInformation;
    private final CreditInformation creditInformation;

    public Reservation(ReservationDetails reservationDetails, PersonalInformation personalInformation, CreditInformation creditInformation) {
        this.reservationDetails = reservationDetails;
        this.personalInformation = personalInformation;
        this.creditInformation = creditInformation;
    }

    public ReservationDetails getReservationDetails() {
        return reservationDetails;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public CreditInformation getCreditInformation() {
        return creditInformation;
    }

    public boolean isComplete() {
        return reservationDetails != null &&
                personalInformation != null &&
                creditInformation != null &&
                personalInformation.isValid() &&
                reservationDetails.isValid() &&
                creditInformation.isValid();
    }
}
