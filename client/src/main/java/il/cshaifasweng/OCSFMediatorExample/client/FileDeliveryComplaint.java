/**
 * Sample Skeleton for 'file-delivery-complaint.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Complaint;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Delivery;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.OrderClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Date;

public class FileDeliveryComplaint {

    @FXML // fx:id="description"
    private TextArea description; // Value injected by FXMLLoader

    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TextField id; // Value injected by FXMLLoader// Value injected by FXMLLoader

    @FXML // fx:id="headline"
    private TextField headline; // Value injected by FXMLLoader

    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    @FXML // fx:id="select_branch"
    private ComboBox<String> select_branch; // Value injected by FXMLLoader

    @FXML // fx:id="submit_button"
    private Button submit_button; // Value injected by FXMLLoader
    @FXML // fx:id="warning"
    private Label warning; // Value injected by FXMLLoader
    private EmailSender emailSender = new EmailSender();
    private String acceptedComplaint="Dear customer,\nWe deeply apologise for you feeling this way, we successfully got your complaint.\nWe hope to learn from our mistake and to see you again at our restaurant!\nYours,\nMama's restaurant\nHere is the description of the submitted complaint:\n";


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
            App.sendMessageToServer("||delivery||id=" + id.getText() + "||email=" + email.getText());
            warning.setText("waiting to verify and to submit your complaint");
            warning.setTextFill(Color.BLUE);
        }
        App.setRoot("home-page");
    }
    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        try {
            select_branch.accessibleTextProperty().set("Chose the relevant branch");
            // Populate the ChoiceBox with options
            select_branch.getItems().addAll("Kiryon", "Grand Kenyon");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @Subscribe
    public void on_respond(OrderClient delivery) throws IOException {
        if (delivery!=null){
            Date today = new Date();
            Complaint complaint=new Complaint(select_branch.getValue(),headline.getText(),description.getText(),today,email.getText(),delivery);
            emailSender.send_email_respond(email.getText(),headline.getText(),acceptedComplaint+description.getText());
            new Thread(() -> {
                try {
                    App.sendMessageToServer(complaint);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            App.setRoot("home-page");
        }else {
            warning.setText("One or more of the details are wrong, check it again!");
            warning.setTextFill(Color.RED);
            warning.setVisible(true);
        }

    }
    @Subscribe
    public void onEventDummy(Object ignored) {}

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

}
