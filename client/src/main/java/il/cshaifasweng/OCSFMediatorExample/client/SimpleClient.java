package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.GetBranchReportRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.models.*;
import javafx.fxml.FXML;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.models.OrderClient;

public class SimpleClient extends AbstractClient {
	//private static SimpleClient client = null;
	private static SimpleClient client;
	public static List<Restaurant> BranchList = new ArrayList<>();
	public static List<Long> userBranchesIdList = new ArrayList<>();

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
		//System.out.println("got the message from the server");
		System.out.println("the received from server is "+msg);
		if (msg instanceof Warning) {
			EventBus.getDefault().post("ERROR");
		} else if (msg instanceof String message) {
            //System.out.println(message);
			if (message.equals("added successfully")) {
				EventBus.getDefault().post("added");
			} /*else if (message.contains("does not exist")) {
				EventBus.getDefault().post(message);
			} else if (message.contains("the password is ok")) {
				int startIndex = message.indexOf("(") + 1;F
				int commaIndex = message.indexOf(",");
				int endIndex = message.indexOf(")");
				String username = message.substring(startIndex, commaIndex).trim();
				String password = message.substring(commaIndex + 1, endIndex).trim();
				String type=message.substring(endIndex + 1).trim();
				App.saveClientDetails(username,password,type);
			}*/
		} else if (msg instanceof Message message) {
			Object payload = message.getPayload();
			Class<?> expectedType = message.getResponseType();

			if (payload != null && expectedType.isInstance(payload)) {
				RequestManager.getInstance().setResponse(message.getKey(), payload);
			}
		} else if (msg instanceof UserType type) {
			EventBus.getDefault().post(msg);
			if (type != UserType.Empty) {
				App.saveClientDetails(null, null, type);
			}
		}

		else if (msg instanceof BranchReportEvent) {
			EventBus.getDefault().post((BranchReportEvent) msg);
		}

		else if (msg instanceof BranchReportEnt) {
			EventBus.getDefault().post(msg);
		}

		else if (msg instanceof List<?> list) {
			//List<?> list = (List<?>) msg;
			//System.out.println("hola");
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
		else if (msg instanceof OrderClient order){
			System.out.println("gor the order");
			EventBus.getDefault().post(order);
		}


	}


	public void sendGetBranchReport(int year, int month, Long branchId) throws IOException {
		GetBranchReportRequest request = new GetBranchReportRequest(branchId, year, month);
		sendToServer(request);
	}

	public static synchronized SimpleClient getClient() throws IOException {
		System.out.println("client == null"+client==null);
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
}