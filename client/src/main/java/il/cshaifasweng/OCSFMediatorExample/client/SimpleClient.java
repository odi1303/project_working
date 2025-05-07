package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.ObservableSWRClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Complaint;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SimpleClient extends AbstractClient {
	//private static SimpleClient client = null;
	private static SimpleClient client;

    /*static {
        try {
            client = new SimpleClient("localhost", 3000);
			System.out.println("pp");
			try {
				System.out.println("initializing client");
				//EventBus.getDefault().register(this);
				client.sendToServer("add client");
			} catch (Exception e) {
				e.printStackTrace();
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private SimpleClient(String host, int port) throws IOException {
		super(host, port);
		//EventBus.getDefault().register(this);
	}

	@FXML
	public void initialize() {
		/*try {
			System.out.println("initializing client");
			//EventBus.getDefault().register(this);
			sendToServer("add client");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	public void onDestroy() {
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void handleMessageFromServer(Object msg) throws IOException {
		System.out.println("got the message from the server");
		System.out.println(msg);
		if (msg instanceof Warning) {
			EventBus.getDefault().post("ERROR");
		} else if (msg instanceof String message) {
            //System.out.println(message);
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
		} else if (msg instanceof UserType type) {
			System.out.println("hola");
			EventBus.getDefault().post(msg);
			if (type != UserType.Empty) {
				App.saveClientDetails(null, null, type);
			}
			/*} /else if (msg instanceof OpeningTimes){
			EventBus.getDefault().post(msg);*/
		} else if (msg instanceof List<?> list) {
			//List<?> list = (List<?>) msg;
			System.out.println("hola");
			if (!list.isEmpty()) {
				Object first = list.getFirst();

				if (first instanceof Complaint) {
					List<Complaint> complaints = (List<Complaint>) list;
					System.out.println("Got the list from complaint");
					EventBus.getDefault().post(complaints);

				} else if (first instanceof MenuItem) {
					List<MenuItem> items = (List<MenuItem>) list;
					System.out.println("Got the list from menu items");
					EventBus.getDefault().post(items);
				}
			}
		}


	}


	public static synchronized SimpleClient getClient() throws IOException {
		System.out.println("client == null"+client==null);
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
}