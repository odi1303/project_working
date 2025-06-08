package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Reservations")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private  ReservationDetails reservationDetails;
    /*@JoinColumn
    @ManyToOne*/
    private PersonalInformation personalInformation;
    /*@ManyToOne
    @JoinColumn(name = "credit_information_id")*/
    private CreditInformation creditInformation;
    public Reservation(ReservationDetails reservationDetails, PersonalInformation personalInformation, CreditInformation creditInformation) {
        this.reservationDetails = reservationDetails;
        this.personalInformation = personalInformation;
        this.creditInformation = creditInformation;
    }

    public Reservation() {

    }

    public int getId() {
        return id;
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
