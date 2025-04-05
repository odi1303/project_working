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

        switch (type) {
            case Admin:
                setRoot("manager_personal_page");
                break;
            case User:
                break;
            case Employee:
                break;
            case Dietitian:
                break;
            case ChainManager:
                break;
            case CustomerServiceWorker:
                break;
            case BranchManager:
                break;
        }
    }

    public static <T> T setRootAndGetController(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        scene.setRoot(fxmlLoader.load());
        return fxmlLoader.getController();
    }

    @Override
    public void start(Stage stage) throws IOException {
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
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void setMenu(ObservableList<String> list){
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
    public static void sendMessageToServer(Object message) throws IOException {
        client.sendToServer(message);
    }

	public static void main(String[] args) {
        launch();
    }

}