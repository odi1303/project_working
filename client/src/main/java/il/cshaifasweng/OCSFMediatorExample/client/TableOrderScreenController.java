package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Map;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;


import java.util.List;

public class TableOrderScreenController {

    public Button option1;
    public Button option2;
    public Button option3;
    public Button option4;
    public Button option5;


    public Button homePageButton;

    private Map<Button, ReservationDetails> buttonsReservations = new HashMap<>();


    public HBox possibleOptions;

    public Button showTheOptionsButtons;
    // Link to FXML components
    @FXML
    private TextArea topText;

    @FXML
    private ComboBox<String> reservationSpace ;
    @FXML
    private ComboBox<String> branch_selection;
    @FXML
    private TextField guestNumber;

    @FXML
    private ComboBox<String> time;
    private ObservableList<String> spaces=FXCollections.observableArrayList("Kiryon-Kiryat Bialik","Grand Kenyon-Haifa");

    @FXML
    private DatePicker reservationDate;

    @FXML
    private ComboBox<String> branch;


    private ObservableList<String> reservationOptions = FXCollections.observableArrayList("Outdoors", "Indoors");

    private ObservableList<String> timeOptions = FXCollections.observableArrayList(
      "00:00", "00:15", "00:30", "00:45",
      "01:00", "01:15", "01:30", "01:45",
      "02:00", "02:15", "02:30", "02:45",
      "03:00", "03:15", "03:30", "03:45",
      "04:00", "04:15", "04:30", "04:45",
      "05:00", "05:15", "05:30", "05:45",
      "06:00", "06:15", "06:30", "06:45",
      "07:00", "07:15", "07:30", "07:45",
      "08:00", "08:15", "08:30", "08:45",
      "09:00", "09:15", "09:30", "09:45",
      "10:00", "10:15", "10:30", "10:45",
      "11:00", "11:15", "11:30", "11:45",
      "12:00", "12:15", "12:30", "12:45",
      "13:00", "13:15", "13:30", "13:45",
      "14:00", "14:15", "14:30", "14:45",
      "15:00", "15:15", "15:30", "15:45",
      "16:00", "16:15", "16:30", "16:45",
      "17:00", "17:15", "17:30", "17:45",
      "18:00", "18:15", "18:30", "18:45",
      "19:00"
);

    @FXML
    private void initialize() {
        try {
            EventBus.getDefault().register(this);

            // Disable past dates in the DatePicker
            reservationDate.setDayCellFactory((final DatePicker datePicker) -> new javafx.scene.control.DateCell() {
                @Override
                public void updateItem(final LocalDate item, final boolean empty) {
                    super.updateItem(item, empty);
                    if (item.isBefore(LocalDate.now())) {
                        setDisable(true);
                    }
                }
            });

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @FXML
    private void initializeComboBoxForBranch_Selection() {
        if (branch_selection.getItems().isEmpty())
            branch_selection.getItems().addAll(spaces);
    }
    // Loads options for reservationSpace ComboBox on first click only
    @FXML
    private void initializeComboBoxForReservationSpace() {
        if (reservationSpace.getItems().isEmpty()) {
            reservationSpace.setItems(reservationOptions);
        }
    }

    // Loads options for time ComboBox on first click only
    @FXML
    private void initializeComboBoxForTime() {
      String openingTime = getOpenTime();
      String closingTime = getClosingTime();
      String currentTime = getCurrentTime();

      final String baseTimeForReservations = convertTimeToIntMinutes(currentTime) > convertTimeToIntMinutes(openingTime) ? currentTime : openingTime;

      String lastReservationTime = getLastReservationTime(closingTime);


      if (time.getItems().isEmpty()) {
        ObservableList<String> filteredOptions = timeOptions.filtered(option -> {
        int optionTime = convertTimeToIntMinutes(option);
        int baseTime = convertTimeToIntMinutes(baseTimeForReservations);
        int lastTime = convertTimeToIntMinutes(lastReservationTime);
        return optionTime >= baseTime && optionTime <= lastTime;});
        time.setItems(filteredOptions);

      }
    }


    private int convertTimeToIntMinutes(String time){
       String[] timeParts = time.split(":");
       int hours = Integer.parseInt(timeParts[0]);
       int minutes = Integer.parseInt(timeParts[1]);
       return hours * 60 + minutes;
    }

    private String getLastReservationTime(String closingTime){
      LocalTime closing = LocalTime.parse(closingTime);
      LocalTime lastReservationTime = closing.minusHours(1);
      return lastReservationTime.toString();
    }

    private String getOpenTime(){
      return "08:00";
    }

    private String getClosingTime(){
      return "23:00";
    }

    private String getCurrentTime(){
      LocalTime currentTime = LocalTime.now();
      return currentTime.toString();
    }

    @FXML
    private void initializeComboBoxForBranch() {
        if (branch.getItems().isEmpty()) {
            ObservableList<String> branchOptions = FXCollections.observableArrayList("1", "2", "3");
            branch.setItems(branchOptions);
        }
    }


    @FXML
    public void showOptions(){
        ReservationDetails reservationDetails = new ReservationDetails(branch.getValue(), guestNumber.getText(), reservationSpace.getValue(), reservationDate.getValue().toString(), time.getValue());

      if (reservationDetails.isValid()){
          if(canTheReservationBeMadeInOneHour(reservationDetails)){
              List<String> possibleReservationsTimes = requestPossibleReservationsTimes(reservationDetails);
              LinkButtonsToReservationOptions(possibleReservationsTimes, reservationDetails);
              showTheOptionsButtons();
          }else if(canTheReservationBeMadeInSameDate(reservationDetails)) {
              List<String> possibleReservationsTimesInSameDate = requestPossibleReservationsTimesInSameDate(reservationDetails);
              PopupDialogService popupDialogService = new PopupDialogService();
              try {
                  popupDialogService.openPopup("InformationWindow.fxml", String.join(", ", possibleReservationsTimesInSameDate), (Stage) reservationSpace.getScene().getWindow());
              } catch (Exception e) {}
          }else{
              PopupDialogService popupDialogService = new PopupDialogService();
              try {
                  popupDialogService.openPopup("InformationWindow.fxml", "A new reservation can't be made in this date", (Stage) reservationSpace.getScene().getWindow());
              }catch (Exception e) {}

          }



      }
    }

    private List<String> requestPossibleReservationsTimes(ReservationDetails details){
        return List.of("10:00", "15:00");
    }

    private void LinkButtonsToReservationOptions(List<String> possibleReservationsTimes, ReservationDetails reservationDetails) {
        // List of available buttons
        List<Button> buttons = List.of(option1, option2, option3, option4, option5);
        buttonsReservations.clear();



        // Loop over possible reservation times and assign them to buttons
        for (int i = 0; i < possibleReservationsTimes.size() && i < buttons.size(); i++) {
            ReservationDetails reservation = new ReservationDetails(reservationDetails.getBranch(), reservationDetails.getGuestNumber(), reservationDetails.getReservationSpace(), reservationDetails.getReservationDate(), possibleReservationsTimes.get(i));

            // Assign each reservation to a corresponding button
            Button currentButton = buttons.get(i);
            buttonsReservations.put(currentButton, reservation);  // Store the reservation for this button

            // Update button text to display reservation time
            currentButton.setText(possibleReservationsTimes.get(i));
        }
    }

    private void showTheOptionsButtons() {
        List<Button> buttons = List.of(option1, option2, option3, option4, option5);

        for (Button currentButton : buttons) {
            if (buttonsReservations.containsKey(currentButton)) {
                currentButton.setVisible(true);
            }
        }
        possibleOptions.setVisible(true);
    }


    @FXML
    private void selectReservation(ActionEvent event){
        PopupDialogService popupDialogService = new PopupDialogService();
        try{
            popupDialogService.openPopup("InformationWindow.fxml","testing", (Stage) reservationSpace.getScene().getWindow());
            PersonalInformation personalInformation = popupDialogService.openPopup("PersonalInformationPopupWindow.fxml","testing", (Stage) reservationSpace.getScene().getWindow());
            if (personalInformation == null){
                showOptions();
                return;
            }
            CreditInformation creditInformation = popupDialogService.openPopup("CreditInformationPopupWindow.fxml","testing", (Stage) reservationSpace.getScene().getWindow());
            if (creditInformation == null){
                showOptions();
                return;
            }

            Button currentButton = (Button) event.getSource();
            ReservationDetails reservation = buttonsReservations.get(currentButton);
            Reservation fullReservation = new Reservation(reservation, personalInformation, creditInformation);
            if (isReservationPossible(fullReservation)){
                boolean isConfirmed = popupDialogService.openPopup("ConfirmationWindow.fxml","testing", (Stage) reservationSpace.getScene().getWindow());
                if (!isConfirmed){
                    showOptions();
                    return;
                }else if (isReservationPossible(fullReservation)){
                    boolean isReserved = bookReservation(fullReservation);
                    if (isReserved){
                        popupDialogService.openPopup("InformationWindow.fxml","reservation booked", (Stage) reservationSpace.getScene().getWindow());
                        goToHomePage();
                    } else{
                        popupDialogService.openPopup("InformationWindow.fxml","reservation failed", (Stage) reservationSpace.getScene().getWindow());
                        showOptions();
                    }
                    return;
                }
            }else{
                popupDialogService.openPopup("InformationWindow.fxml","reservation taken", (Stage) reservationSpace.getScene().getWindow());
                showOptions();
                return;
            }
        } catch (IOException e) {}

        //check if reservation is still posible
        //save reservation for now
        //get personal details

        //update reservation with personal details
        // save reservation
        //go back to home page
    }

//    private ReservationDetails createReservation(String branch,String guestNumber, String reservationSpace, String reservationDate, String time) {
//        return new ReservationDetails(branch, guestNumber, reservationSpace, reservationDate, time);
//    }


    @FXML
    private void goToHomePage() throws IOException {
        App.setRoot("home-page");
    }

//    private void closeWindow() {
//        Stage stage = (Stage) reservationSpace.getScene().getWindow();
//        stage.close();
//    }


    //temporary should be at server
    boolean isReservationPossible(Reservation fullReservation) {
        return true;
    }
    //temporary should be at server
    boolean bookReservation(Reservation fullReservation) {
        return true;
    }
    //temporary should be at server
    boolean canTheReservationBeMadeInOneHour(ReservationDetails ReservationDetails) {
        return true;
    }
    //temporary should be at server
    boolean canTheReservationBeMadeInSameDate(ReservationDetails ReservationDetails) {
        return true;
    }

    List<String> requestPossibleReservationsTimesInSameDate(ReservationDetails details){
        return List.of("16:00", "17:00");
    }

}

