package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */


public class PrimaryController {

	@FXML
	void sendWarning(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#warning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		try {
			EventBus.getDefault().register(this); // Register to EventBus
			System.out.println("PrimaryController registered to EventBus.");
			SimpleClient.primaryController = this;
			SimpleClient.getClient().sendToServer("add client");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void onDestroy() {
		EventBus.getDefault().unregister(this); // Unregister from EventBus
		System.out.println("PrimaryController unregistered from EventBus.");
	}
}
