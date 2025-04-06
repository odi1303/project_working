package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.complaint_to_answer;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class FileAComplaint {

    @FXML
    private ImageView home_icon;

    @FXML
    private ComboBox<String> select_branch;

    @FXML
    private Button submit_button;

    @FXML
    private TextArea description;

    @FXML
    private TextField headline;

    @FXML
    private TextField email;

    private String acceptedComplaint = "Dear customer,\nWe deeply apologise for you feeling this way, we successfully got your complaint.\nWe hope to learn from our mistake and to see you again at our restaurant!\nYours,\nMama's restaurant\nHere is the description of the submitted complaint:\n";

    @FXML
    private Label warning;

    private EmailSender emailSender = new EmailSender();

    @FXML
    void home_page(MouseEvent event) throws IOException {
        // Use Platform.runLater to handle scene navigation
        Platform.runLater(() -> {
            try {
                App.setRoot("home-page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void selecting_branch(MouseEvent event) {
        // Use Platform.runLater to update the select_branch ComboBox
        Platform.runLater(() -> {
            select_branch.getItems().addAll("Kiryon", "Grand Kenyon");
        });
    }

    @FXML
    void submit_complain(ActionEvent event) throws IOException {
        // Use Platform.runLater to handle UI updates and navigation
        Platform.runLater(() -> {
            if (select_branch.getSelectionModel().getSelectedItem() == null || email.getText().isEmpty() ||
                    headline.getText().isEmpty() || description.getText().isEmpty()) {
                warning.setVisible(true);
            } else {
                complaint_to_answer complaint = new complaint_to_answer(email.getText(), headline.getText(), description.getText(), select_branch.getValue());
                emailSender.send_email_respond(email.getText(), headline.getText(), acceptedComplaint + description.getText());
                new Thread(() -> {
                    try {
                        App.sendMessageToServer(complaint);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                try {
                    App.setRoot("home-page");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
            // Use Platform.runLater to update the select_branch ComboBox
            Platform.runLater(() -> {
                select_branch.accessibleTextProperty().set("Chose the relevant branch");
                select_branch.getItems().addAll("Kiryon", "Grand Kenyon");
            });
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}