package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
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

import java.util.List;

public class TableOrderScreenController {

    public Button option1;
    public Button option2;
    public Button option3;
    public Button option4;
    public Button option5;

    private Map<Button, List<String>> buttonsReservations = new HashMap<>();


    public HBox possibleOptions;

    public Button showTheOptionsButtons;
    // Link to FXML components
    @FXML
    private TextArea topText;

    @FXML
    private ComboBox<String> reservationSpace;

    @FXML
    private TextField guestNumber;

    @FXML
    private ComboBox<String> time;

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
      "19:00", "19:15", "19:30", "19:45",
      "20:00", "20:15", "20:30", "20:45",
      "21:00", "21:15", "21:30", "21:45",
      "22:00", "22:15", "22:30", "22:45",
      "23:00", "23:15", "23:30", "23:45"
);


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
      return "22:00";
    }

    private String getCurrentTime(){
      LocalTime currentTime = LocalTime.now();
      return currentTime.toString();
    }



    @FXML
    public void showOptions(ActionEvent event){
      List<String> reservationDetails = createReservation(reservationSpace.getValue(), time.getValue(), guestNumber.getText());

      if (!validateDetails(reservationDetails)){
        return;
      }

      List<String> possibleReservationsTimes = requestPossibleReservationsTimes(reservationDetails);
      LinkButtonsToReservationOptions(possibleReservationsTimes, reservationDetails);
        showTheOptionsButtons();

    }

    private List<String> requestPossibleReservationsTimes(List<String> details){
        return List.of("10:00", "15:00");
    }

    private void LinkButtonsToReservationOptions(List<String> possibleReservationsTimes, List<String> reservationDetails) {
        // List of available buttons
        List<Button> buttons = List.of(option1, option2, option3, option4, option5);
        buttonsReservations.clear();



        // Loop over possible reservation times and assign them to buttons
        for (int i = 0; i < possibleReservationsTimes.size() && i < buttons.size(); i++) {
            List<String> reservation = createReservation(reservationDetails.get(0), possibleReservationsTimes.get(i), reservationDetails.get(2));

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
        Button currentButton = (Button) event.getSource();
        List<String> reservation = buttonsReservations.get(currentButton);
        //check if reservation is stell posible
        //save reservation for now
        //get personal details
        //update reservation with personal details
        // save reservation
        //go back to home page
    }

    private List<String> createReservation(String space, String time, String guestNumber) {
        return List.of(space, time, guestNumber);
    }

    private boolean validateDetails(List<String> reservationDetails){
        return true;
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        App.setRoot("home-page");
    }
}
