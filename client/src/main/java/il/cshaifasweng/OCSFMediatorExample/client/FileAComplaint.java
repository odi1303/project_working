/**
 * Sample Skeleton for 'file-a-complaint.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;


import il.cshaifasweng.OCSFMediatorExample.entities.models.Complaint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Date;


public class FileAComplaint {

    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    @FXML // fx:id="select_branch"
    private ComboBox<String> select_branch; // Value injected by FXMLLoader

    @FXML // fx:id="submit_button"
    private Button submit_button; // Value injected by FXMLLoader
    @FXML
    private TextArea description;
    @FXML
    private TextField headline;
    @FXML
    private TextField email;
    private String acceptedComplaint="Dear customer,\nWe deeply apologise for you feeling this way, we successfully got your complaint.\nWe hope to learn from our mistake and to see you again at our restaurant!\nYours,\nMama's restaurant\nHere is the description of the submitted complaint:\n";

    @FXML // fx:id="warning"
    private Label warning; // Value injected by FXMLLoader
    private EmailSender emailSender = new EmailSender();
    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }

    @FXML
    void selecting_branch(MouseEvent event) {
        select_branch.getItems().addAll("Kiryon", "Grand Kenyon");

    }

    @FXML
    void submit_complain(ActionEvent event) throws IOException {

        if (select_branch.getSelectionModel().getSelectedItem()==null ||email.getText().isEmpty()||
        headline.getText().isEmpty()||description.getText().isEmpty()) {
            warning.setVisible(true);
        }
        else {
            Date today=new Date();
            Complaint complaint=new Complaint(select_branch.getValue(),headline.getText(),description.getText(),today,email.getText());
            emailSender.send_email_respond(email.getText(),headline.getText(),acceptedComplaint+description.getText());
            new Thread(() -> {
                try {
                    App.sendMessageToServer(complaint);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            App.setRoot("home-page");
        }

    }
    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
            select_branch.accessibleTextProperty().set("Chose the relevant branch");
            // Populate the ChoiceBox with options
            select_branch.getItems().addAll("Kiryon", "Grand Kenyon");
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
