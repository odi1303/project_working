package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.Arrays;
import java.util.List;

public class HardcodedReservations {

    public static List<Reservation> getSampleReservations() {

        ReservationDetails reservationDetails1 = new ReservationDetails(
                "Haifa Branch", "5", "Private Room", "2025-04-05", "18:00"
        );

        PersonalInformation personalInformation1 = new PersonalInformation(
                "Alice Johnson", "0521234567", "alice.johnson@example.com"
        );

        CreditInformation creditInformation1 = new CreditInformation(
                "1234567812345678", "12/26", "123"
        );

        Reservation reservation1 = new Reservation(reservationDetails1, personalInformation1, creditInformation1);


        ReservationDetails reservationDetails2 = new ReservationDetails(
                "Tel Aviv Branch", "3", "Outdoor Area", "2025-04-06", "20:00"
        );

        PersonalInformation personalInformation2 = new PersonalInformation(
                "Bob Smith", "0549876543", "bob.smith@example.com"
        );

        CreditInformation creditInformation2 = new CreditInformation(
                "8765432187654321", "05/27", "456"
        );

        Reservation reservation2 = new Reservation(reservationDetails2, personalInformation2, creditInformation2);


        ReservationDetails reservationDetails3 = new ReservationDetails(
                "Jerusalem Branch", "2", "VIP Lounge", "2025-04-07", "19:30"
        );

        PersonalInformation personalInformation3 = new PersonalInformation(
                "Charlie Brown", "0535556677", "charlie.brown@example.com"
        );

        CreditInformation creditInformation3 = new CreditInformation(
                "4567891245678912", "08/28", "789"
        );

        Reservation reservation3 = new Reservation(reservationDetails3, personalInformation3, creditInformation3);

        return Arrays.asList(reservation1, reservation2, reservation3);
    }
}
