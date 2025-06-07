/**
 * Sample Skeleton for 'cancel-delivey.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.models.OrderClient;
import il.cshaifasweng.OCSFMediatorExample.entities.models.Reservation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CancelDelivey {

    @FXML // fx:id="description"
    private TextArea description; // Value injected by FXMLLoader

    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader

    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TextField id; // Value injected by FXMLLoader

    @FXML // fx:id="select_cancel"
    private ComboBox<String> select_cancel; // Value injected by FXMLLoader

    @FXML // fx:id="submit_button"
    private Button submit_button; // Value injected by FXMLLoader

    @FXML // fx:id="warning_label"
    private Label warning_label; // Value injected by FXMLLoader

    private EmailSender emailSender = new EmailSender();
    private String acceptedCancel ="Dear customer,\nWe deeply apologise for you feeling this way, we successfully got your cancellation";
    private String ending="\nWe hope to see you again at our restaurant!\nYours,\nMama's restaurant";
    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }

    @FXML
    void selecting_cancel(ActionEvent event) {

    }

    @FXML
    void submit_complain(ActionEvent event) throws IOException {

        if (select_cancel.getSelectionModel().getSelectedItem()==null ||email.getText().isEmpty()||
                id.getText().isEmpty()) {
            warning_label.setText("Please complete all necessary information to complete the cancellation.");
            warning_label.setVisible(true);
        }
        else {
            if (select_cancel.getValue().equals("Delivery")){
                App.sendMessageToServer("@@delivery@@id=" + id.getText() + "@@email=" + email.getText());
            }
            else
                App.sendMessageToServer("@@reservat@@id=" + id.getText() + "@@email=" + email.getText());
            warning_label.setText("waiting to verify and to submit your complaint");
            warning_label.setTextFill(Color.BLUE);
            warning_label.setVisible(true);
        }
    }

    @Subscribe
    public void on_respond(OrderClient delivery) throws IOException {
        System.out.println("got into on respond");
        if (delivery!=null){
            Date now = new Date();
            String delivery_num="Delivery no.";
            String canceled=" canceled successfully.";
            long diffMillis =now.getTime() - delivery.getDeliveryTime().getTime();
            long diffHours = diffMillis / (1000 * 60 * 60);
            if (diffHours >= 3) {
                emailSender.send_email_respond(email.getText(),delivery_num+delivery.getId().toString()+canceled, acceptedCancel+ending);
                System.out.println("your delivery canceled successfully for free");
            } else if (diffHours<3 && diffHours>1){
                String temp=" due to the late announcement you will be charged with 50% of the order price";
                emailSender.send_email_respond(email.getText(),delivery_num+delivery.getId().toString()+canceled, acceptedCancel+temp+ending);
                System.out.println("your delivery canceled successfully for 50% of the order price");
            }
            else {
                String temp=" due to the last minute announcement you will be charged with full price";
                emailSender.send_email_respond(email.getText(),delivery_num+delivery.getId().toString()+canceled, acceptedCancel+temp+ending);
                System.out.println("your delivery canceled successfully for full price");
            }
            App.setRoot("home-page");
        }else {
            Platform.runLater(() -> {
                warning_label.setText("One or more of the details are wrong, check it again!");
                warning_label.setTextFill(Color.RED);
                warning_label.setVisible(true);
            });

        }
    }
    @Subscribe
    public void on_respond(Reservation reservation) throws IOException, ParseException {
        if (reservation != null) {
            Date now = new Date();
            String timeString =  reservation.getReservationDetails().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = sdf.parse(timeString);
            long diffMillis =now.getTime() -date.getTime();
            long diffHours = diffMillis / (1000 * 60 * 60);
            if (diffHours >= 1) {
                emailSender.send_email_respond(email.getText(),"Reservation no."+reservation.getId()+" canceled successfully", acceptedCancel+ending);
                System.out.println("your reservation canceled successfully for free");
            }
            else {
                String late=" due to the late announcement you will be charged with 10 nis for the cancellation";
                emailSender.send_email_respond(email.getText(),"Reservation no."+reservation.getId()+" canceled successfully", acceptedCancel+late+ending);
                System.out.println("your reservation canceled successfully for 10 nis");
            }
            App.setRoot("home-page");
        }
        else {
            warning_label.setText("One or more of the details are wrong, check it again!");
            warning_label.setTextFill(Color.RED);
            warning_label.setVisible(true);
        }
    }


    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        try {
            // Populate the ChoiceBox with options
            select_cancel.getItems().addAll("Reservation", "Delivery");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @Subscribe
    public void onEventDummy(Object ignored) {}

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
