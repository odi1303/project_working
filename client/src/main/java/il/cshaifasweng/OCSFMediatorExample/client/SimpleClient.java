package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.ObservableSWRClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Optional;

public class SimpleClient extends AbstractClient {
	private static SimpleClient client = null;
	private SimpleClient(String host, int port) throws IOException {
		super(host, port);
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

	@Override
	protected void handleMessageFromServer(Object msg) throws IOException {
		if (msg instanceof Warning) {
			EventBus.getDefault().post("ERROR");
		} else if (msg instanceof String message) {
            System.out.println(message);
			if (message.equals("added successfully")) {
				EventBus.getDefault().post("added");
			} /*else if (message.contains("does not exist")) {
				EventBus.getDefault().post(message);
			} else if (message.contains("the password is ok")) {
				int startIndex = message.indexOf("(") + 1;
				int commaIndex = message.indexOf(",");
				int endIndex = message.indexOf(")");
				String username = message.substring(startIndex, commaIndex).trim();
				String password = message.substring(commaIndex + 1, endIndex).trim();
				String type=message.substring(endIndex + 1).trim();
				App.saveClientDetails(username,password,type);
			}*/
		} else if (msg instanceof UserType) {
			System.out.println("hola");
			EventBus.getDefault().post(msg);
		}
	}


	public static synchronized SimpleClient getClient() throws IOException {
		System.out.println(client == null);
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
}