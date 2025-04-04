package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ReservationCardController {



    @FXML
    private Button ReservationDetailsButton;
    @FXML
    private Button PersonalInformationButton;
    @FXML
    private Button CreditInformationButton;

    private Reservation reservation;


    @FXML
    private VBox ReservationDetailsSection;

    //reservation details buttons
    @FXML
    private Label ReservationBranchLabel;
    @FXML
    private Label ReservationGuestNumberLabel;
    @FXML
    private Label ReservationReservationSpaceLabel;
    @FXML
    private Label ReservationReservationDateLabel;
    @FXML
    private Label ReservationTimeLabel;


    @FXML
    private VBox PersonalInformationSection;

    //personal Information buttons
    @FXML
    private Label PersonalInformationFullNameLabel;
    @FXML
    private Label PersonalInformationPhoneNumberLabel;
    @FXML
    private Label PersonalInformationEmailLabel;


    @FXML
    private VBox CreditInformationSection;
    //Credit Information buttons
    @FXML
    private Label CreditInformationCardNumberLabel;
    @FXML
    private Label CreditInformationExpirationDateLabel;
    @FXML
    private Label CreditInformationCVVLabel;

//    public void initialize() {
//        List<Reservation> reservations = HardcodedReservations.getSampleReservations();
//        setData(reservations.getFirst());
//    }


    public void Initialize() {

    }

    public void setData(Reservation reservation) {
        this.reservation = reservation;
        setDataInLabels();
    }
    private void setDataInLabels() {
        setReservationDetailsDataInLabels();
        setPersonalInformationDataInLabels();
        setCreditInformationDataInLabels();
    }
    private void setReservationDetailsDataInLabels() {
        ReservationDetails reservationDetails = reservation.getReservationDetails();
        ReservationBranchLabel.setText(reservationDetails.getBranch());
        ReservationGuestNumberLabel.setText(reservationDetails.getGuestNumber());
        ReservationReservationSpaceLabel.setText(reservationDetails.getReservationSpace());
        ReservationReservationDateLabel.setText(reservationDetails.getReservationDate());
        ReservationTimeLabel.setText(reservationDetails.getTime());
    }
    private void setPersonalInformationDataInLabels() {
        PersonalInformation personalInformation = reservation.getPersonalInformation();
        PersonalInformationFullNameLabel.setText(personalInformation.getFullName());
        PersonalInformationPhoneNumberLabel.setText(personalInformation.getPhoneNumber());
        PersonalInformationEmailLabel.setText(personalInformation.getEmail());
    }
    private void setCreditInformationDataInLabels() {
        CreditInformation creditInformation = reservation.getCreditInformation();
        CreditInformationCardNumberLabel.setText(creditInformation.getCardNumber());
        CreditInformationExpirationDateLabel.setText(creditInformation.getExpirationDate());
        CreditInformationCVVLabel.setText(creditInformation.getCvv());
    }



    @FXML
    private void switchToReservationDetails(){
        CreditInformationSection.setVisible(false);
        CreditInformationSection.setManaged(false);
        PersonalInformationSection.setVisible(false);
        PersonalInformationSection.setManaged(false);
        ReservationDetailsSection.setVisible(true);
        ReservationDetailsSection.setManaged(true);
    }
    @FXML
    private void switchToPersonalInformation(){
        CreditInformationSection.setVisible(false);
        CreditInformationSection.setManaged(false);
        ReservationDetailsSection.setVisible(false);
        ReservationDetailsSection.setManaged(false);
        PersonalInformationSection.setVisible(true);
        PersonalInformationSection.setManaged(true);
    }
    @FXML
    private void switchToCreditInformation(){
        ReservationDetailsSection.setVisible(false);
        ReservationDetailsSection.setManaged(false);
        PersonalInformationSection.setVisible(false);
        PersonalInformationSection.setManaged(false);
        CreditInformationSection.setVisible(true);
        CreditInformationSection.setManaged(true);
    }

//    public Reservation getData() {
//        return reservation;
//    }
}
