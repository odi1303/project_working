package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.ObservableSWRClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Optional;

public class SimpleClient extends AbstractClient {
	private static SimpleClient client = null;
	private SimpleClient(String host, int port) throws IOException {
		super(host, port);
	}


	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof Warning) {
			EventBus.getDefault().post("ERROR");
		} else if (msg instanceof String message) {
            System.out.println(message);
			if (message.equals("added successfully")) {
				EventBus.getDefault().post("added");
			}
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