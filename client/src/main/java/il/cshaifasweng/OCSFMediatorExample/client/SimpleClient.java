package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.CompactMenu;
import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import il.cshaifasweng.OCSFMediatorExample.entities.MenuUpdateEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.collections.FXCollections;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

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
		} else if (msg instanceof CompactMenu compactMenu) {
			try {
				var list = FXCollections.observableList(compactMenu.dishes);
				System.out.println("the length of list is :" + list.size());
				App.setMenu(list);
				App.setRoot("home-page");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("ABCD");
			EventBus.getDefault().post(msg);
		} else if (msg instanceof Dish) {
			try {
				App.setRoot("home-page");
			} catch (Exception e) {
				e.printStackTrace();
			}
			EventBus.getDefault().post(msg);
		} else if (msg instanceof MenuUpdateEvent) {
			System.out.println("im here");
			try {

				App.setRoot("quaternary");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public static synchronized SimpleClient getClient() throws IOException {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
}