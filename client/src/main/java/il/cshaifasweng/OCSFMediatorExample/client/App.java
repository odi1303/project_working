package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

    public static String username;
    public static String password;
    public static UserType userType;

    public static void saveClientDetails(String username, String password, UserType type) throws IOException {
        assert type != UserType.Empty;
        App.username = username;
        App.password = password;
        userType = type;

        // Use Platform.runLater to ensure UI updates are thread-safe
        Platform.runLater(() -> {
            try {
                switch (type) {
                    case Admin:
                        setRoot("manager_personal_page");
                        break;
                    case User:
                        // Optionally update UI elements instead of changing the scene
                        System.out.println("User logged in: " + username);
                        break;
                    case Employee:
                        System.out.println("Employee logged in: " + username);
                        break;
                    case Dietitian:
                        System.out.println("Dietitian logged in: " + username);
                        break;
                    case ChainManager:
                        System.out.println("Chain Manager logged in: " + username);
                        break;
                    case CustomerServiceWorker:
                        System.out.println("Customer Service Worker logged in: " + username);
                        break;
                    case BranchManager:
                        System.out.println("Branch Manager logged in: " + username);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static <T> T setRootAndGetController(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        // Use Platform.runLater to set the scene root
        Platform.runLater(() -> scene.setRoot(root));
        return fxmlLoader.getController();
    }

    @Override
    public void start(Stage stage) throws IOException {
        EventBus.getDefault().register(this);
        client = SimpleClient.getClient();
        client.openConnection();
        scene = new Scene(loadFXML("home-page"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        // Use Platform.runLater to set the scene root
        Platform.runLater(() -> scene.setRoot(root));
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

    public static void sendMessageToServer(Object message) throws IOException {
        client.sendToServer(message);
    }

    public static void main(String[] args) {
        launch();
    }
}