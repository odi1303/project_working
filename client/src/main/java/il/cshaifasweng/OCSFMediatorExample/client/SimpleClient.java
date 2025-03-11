package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import java.io.IOException;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;
	public static String sign;
	public static PrimaryController primaryController;
	private SimpleClient(String host, int port) throws IOException {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) throws IOException {
		String message = msg.toString();
		System.out.println(message + "=message");

		if (msg instanceof Warning) {
			// Handle Warning event
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
	}
	public static SimpleClient getClient() throws IOException {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
