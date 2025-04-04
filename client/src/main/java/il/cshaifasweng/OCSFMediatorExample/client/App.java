package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.hibernate.Session;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Session session;
    private static Scene scene;
    private static SimpleClient client;
    public static ObservableList<String> menu;
    private static String userName = null;
    private static String password = null;
    public static UserType type=null;


    @Override
    public void start(Stage stage) throws IOException {
        EventBus.getDefault().register(this);
        client = SimpleClient.getClient();
        client.openConnection();
        client.sendToServer("add client"); // לא באמת יודע איפה לשים את השורה הזו, באב טיפוס שמנו אותה בINIT של הCONTROLLER הראשון

        scene = new Scene(loadFXML("home-page"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setMenu(ObservableList<String> list) {
        menu = list;
    }


    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub
        EventBus.getDefault().unregister(this);
        client.sendToServer("remove client");
        client.closeConnection();
        super.stop();
    }

    @Subscribe
    public void onWarningEvent(WarningEvent event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING,
                    String.format("Message: %s\nTimestamp: %s\n",
                            event.getWarning().getMessage(),
                            event.getWarning().getTime().toString())
            );
            alert.show();
        });

    }
    public void saveComplaint(String recipient,String subject, String respond_text, String branch){

    }

    public static void sendMessageToServer(Object message) throws IOException {
        System.out.println("message sent");
        client.sendToServer(message);
    }

    //saves the details of the client for this session so he will not be needed to enter every time
    public static void saveClientDetails(String userName, String password, String type) throws IOException {
        App.userName = userName;
        App.password = password;
        if (type.equals("Admin")){
            App.type = UserType.Admin;
            setRoot("manager_personal_page");
        }
        else if (type.equals("User")) {
            App.type = UserType.User;
            setRoot("client_personal_page");
        } else if (type.equals("employee")) {
            App.type=UserType.employee;
            setRoot("employee_personal_page");
        } else if (type.equals("dietician")) {
            App.type=UserType.dietician;
        } else if (type.equals("branchManager")) {
            App.type=UserType.branchManager;
        }
        else
            App.type=UserType.chainManager;
    }
    //return true if he entered once and false otherwise
    public boolean detailsExist(){
        if (userName == null || password == null){
            return false;
        }
        return true;
    }
    //returns the client saved username
    public String getUserName(){
        return userName;
    }
    //returns the client saved password
    public String getPassword(){
        return password;
    }


	public static void main(String[] args) {
        launch();
    }

}