package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PopupDialogService {

    public <I, O, T extends PopupController<I, O>> O openPopup(String fxml, I input, Stage ownerStage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load(); //load fxml
        T controller = loader.getController();  //get fxml controller

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage); //set it so the pop-up window blocks actions in the calling window

        controller.reInitialize(input); //use reInitialize to pass the input to the scene controller and reinitialize the scene with the input.
        popupStage.setScene(new Scene(root)); //show the fxml in the popup window, not visible until now.
        //popupStage.setResizable(false); //blocks changing the pop-up window location, shape and size.

        popupStage.showAndWait();

        return controller.getOutput();
    }
}
