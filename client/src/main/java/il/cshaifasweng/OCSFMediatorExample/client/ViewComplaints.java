package il.cshaifasweng.OCSFMediatorExample.client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.IOException;

public class ViewComplaints {
    private String APPROVED_STRING="";
    @FXML // fx:id="chose_complaint"
    private ComboBox<String> chose_complaint; // Value injected by FXMLLoader

    @FXML // fx:id="chose_status"
    private ComboBox<String> chose_status; // Value injected by FXMLLoader

    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    @FXML // fx:id="respod_text"
    private TextArea respond_text; // Value injected by FXMLLoader

    @FXML // fx:id="compensation_sum"
    private TextField compensation_sum; // Value injected by FXMLLoader

    @FXML // fx:id="send_respond_button"
    private Button send_respond_button; // Value injected by FXMLLoader
    private EmailSender emailSender = new EmailSender();
    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }
    @FXML
    void initialize() {
        chose_status.getItems().addAll("Approved", "Denied");
    }

    @FXML
    void send_email_respond(ActionEvent event) {
        if (chose_status.getValue().equals("Approved")) {

        }
        emailSender.send_email_respond("odifn567@gmail.com", "Complaint Response", respond_text.getText());
    }

}
