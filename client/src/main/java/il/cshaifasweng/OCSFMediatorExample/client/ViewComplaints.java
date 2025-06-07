package il.cshaifasweng.OCSFMediatorExample.client;


import il.cshaifasweng.OCSFMediatorExample.entities.models.Complaint;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewComplaints {
    private String APPROVED_STRING="";
    @FXML
    private ComboBox<String> chose_complaint;
    @FXML // fx:id="chose_status"
    private ComboBox<String> chose_status; // Value injected by FXMLLoader

    @FXML // fx:id="home_icon"
    private ImageView home_icon; // Value injected by FXMLLoader

    @FXML // fx:id="respod_text"
    private TextArea respond_text; // Value injected by FXMLLoader
    @FXML
    private TextArea complaint_text;
    @FXML // fx:id="compensation_sum"
    private TextField compensation_sum; // Value injected by FXMLLoader

    @FXML // fx:id="warning"
    private Label warning; // Value injected by FXMLLoader

    @FXML // fx:id="send_respond_button"
    private Button send_respond_button; // Value injected by FXMLLoader
    private EmailSender emailSender = new EmailSender();
    public static List<Complaint> complaints;
    public Complaint current_complaint;
    private String respond="The respond to your complaint is as following:\n";
    private String pitzoi="The decided compensation sum is:";
    private String acceptedComplaint="Dear customer,\nWe deeply apologise for you feeling this way, we successfully got your complaint.\nWe hope to learn from our mistake and to see you again at our restaurant!\nYours,\nMama's restaurant\nHere is the description of the submitted complaint:\n";
    @FXML
    void home_page(MouseEvent event) throws IOException {
        App.setRoot("home-page");
    }
    @FXML
    void initialize() throws IOException {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
            chose_status.getItems().addAll("Approved", "Denied");
        }
        //chose_complaint.getItems().add("there are no complaints today");
        if (complaints==null||complaints.isEmpty()) {
            new Thread(() -> {
                try {
                    System.out.println("requesting all the complaints");
                    App.sendMessageToServer("send all complaints");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    @Subscribe
    public void on_respond(List<?> list) throws IOException {
        complaints = (List<Complaint>) list;
        System.out.println("got the list, new way");
        List<String> strings1 = new ArrayList<>();
        if (complaints != null && !complaints.isEmpty()) {
            for (Complaint complaint : complaints) {
                String head = complaint.getHeadline() + "||" + (complaint.getDate()).toString();
                strings1.add(head);
                System.out.println(head);
            }
            Platform.runLater(() -> {
                chose_complaint.getItems().removeAll();  // clear previous if any
                chose_complaint.getItems().addAll(strings1);
            });
        }
    }


    @FXML
    void send_email_respond(ActionEvent event) throws IOException {
        if (chose_status.getValue()!=null){
            current_complaint.setHandled(true);
            Thread emailThread = new Thread(() -> {
                String temp=acceptedComplaint+current_complaint.getDescription()+respond+respond_text.getText()+pitzoi+current_complaint.getCompensation();
                if (chose_status.getValue().equals("Approved")) {
                    emailSender.send_email_respond(current_complaint.getEmail(),
                            "Complaint Response: Approved Complaint", temp);
                } else {
                    emailSender.send_email_respond(current_complaint.getEmail(),
                            "Complaint Response: Denied Complaint",temp);
                }
            });

            Thread serverThread = new Thread(() -> {
                try {
                    if (compensation_sum.getText().isEmpty())
                        current_complaint.setCompensation(0);
                    else
                        current_complaint.setCompensation(Integer.parseInt(compensation_sum.getText()));
                    current_complaint.setAnsweredAt(new Date());
                    App.sendMessageToServer(current_complaint);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            emailThread.start();
            serverThread.start();

            try {
                emailThread.join();
                serverThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            current_complaint = null;
            if (!complaints.isEmpty())
                complaints = null;

            App.setRoot("home-page");

        }
        else{
            warning.setText("All the fields should be filled before submitting!");
        }


    }
    @FXML  // Ensure this annotation is present
    private void edit_compensation(ActionEvent event) {
        System.out.println("Edit compensation button clicked!");
        int compensation=0;
        if (current_complaint!=null&&chose_status.getValue().equals("Approved")&&chose_complaint.getValue()!=null) {
            try {
                compensation=Integer.parseInt(compensation_sum.getText());
            }
            catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }

            this.current_complaint.setCompensation(compensation);
        }
    }
    @FXML
    void show_complaint_chosen(ActionEvent event) {
        if (chose_complaint.getValue()==null||!chose_complaint.getValue().isEmpty()){
            String string=chose_complaint.getValue();
            System.out.println("Show complaint chosen button clicked!"+string);

            String[] parts = string.split("\\|\\|");
            String headline = parts[0];
            String date = parts[1];
            System.out.println(complaints.isEmpty());
            for (Complaint c : complaints) {
                System.out.println(c.getHeadline().equals(headline));
                System.out.println(c.getDate()+"?"+date.toString());
                System.out.println(c.getDate().toString().equals(date));
                if (c.getHeadline().equals(headline)&&date.equals(c.getDate().toString())) {
                    System.out.println("gor in"+c.getDescription());
                    complaint_text.setText(c.getDescription());
                    current_complaint=c;
                }
            }
        }

    }

}
