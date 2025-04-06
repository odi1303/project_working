package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stg;

    @FXML
    public void initialize() {
        try {
//            EventBus.getDefault().register(this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    public void start(Stage stage) throws IOException {
        if (stg == null) {
            stage = new Stage();
        }
        this.stg = stage;
        // Use Platform.runLater to ensure the initial scene setup is thread-safe
        Stage finalStage = stage;
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-page.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 629, 386);
                finalStage.setTitle("Restaurant App");
                finalStage.setScene(scene);
                finalStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 629, 386);
        // Use Platform.runLater to set the scene
        Platform.runLater(() -> stg.setScene(scene));
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 629, 386);
        // Use Platform.runLater to set the scene
        Platform.runLater(() -> stg.setScene(scene));
    }

    public static void main(String[] args) {
        launch();
    }
}