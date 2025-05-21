package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.*;
import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.BookReservationRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.models.*;
import il.cshaifasweng.OCSFMediatorExample.server.bl.HardcodedDataProvider;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import il.cshaifasweng.OCSFMediatorExample.entities.OpeningTimes;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.models.Reservation;
//@ApplicationScoped
public class SimpleServer extends AbstractServer{
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	/*@Inject
	Database db;*/

	ManualDatabase db_;
	/**
	 * Constructs a new server.
	 *
	 * @param port the port number on which to listen.
	 */
	public SimpleServer() {
		super(3000);
		db_ = new ManualDatabase();

	}

	@Override
	protected synchronized void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		String msgString = msg.toString();
		System.out.println("SimpleServer " + msgString);
		client.sendToClient("received: " + msgString);
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msgString.startsWith("add client")) {
			SubscribedClient connection = new SubscribedClient(client); // User is null for now
			if (!SubscribersList.contains(connection)) {
				SubscribersList.add(connection);
				System.out.println("Client " + client.getId() + " added to SubscribersList.");
				client.sendToClient("added successfully");
			} else {
				System.out.println("Client " + client.getId() + " already in SubscribersList.");
			}
			//db.getBasicUsers().addUser(new User("pp", "pp", UserType.Admin));
			/*for (var o : db_.getAll(new User())) {
				System.out.println(o.toString());
			}
			System.out.println("supposedly added into db");*/
		} else if (msgString.startsWith("remove client")) {
			if (!SubscribersList.isEmpty()) {
				for (SubscribedClient subscribedClient : SubscribersList) {
					if (subscribedClient.getClient().equals(client)) {
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		} else if (msg instanceof GetUserType getUserType) {
			try {
				System.out.println("new request: "+msgString);
				System.out.println(getUserType.name + ", " + getUserType.password);
				var retval = /*db.getBasicUsers()*/db_.getUserType(getUserType.name, getUserType.password);
				System.out.println("method returned "+retval);
				client.sendToClient(retval);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (msgString.equals("#getAllComplaints")) {
			try {
				List<Complaint> complaints = ComplaintsBL.getAllComplains(db_.getSession());
				client.sendToClient(complaints);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msgString.equals("#getAllDeliveries")) {
			try {
				List<Delivery> deliveries = db_.getSession()
						.createQuery("FROM Delivery", Delivery.class)
						.getResultList();
				client.sendToClient(deliveries);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msgString.equals("#getAllReservations")) {
			try {
				List<TableOrder> tableOrders = db_.getSession()
						.createQuery("FROM TableOrder", TableOrder.class)
						.getResultList();
				client.sendToClient(tableOrders);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if (msgString.contains("send all complaints")) {
			System.out.println("got in");
			List<Complaint> openComplaints =new ArrayList<>();
			System.out.println(db_);
			List<Complaint> complaints = db_.getAll(new Complaint());
			for (Complaint complain : complaints) {
				if (complain.isHandled() == false) { // Corrected the condition to find open complaints
					openComplaints.add(complain);
				}
			}
			System.out.println("num of open complaints=" + openComplaints.size());
			client.sendToClient(openComplaints);
		} else if (msgString.equals("send all reservation")) {
			List<OrderClient>orders = db_.getAll(new OrderClient());
			System.out.println("num of orders=" + orders.size());
			client.sendToClient(orders);
		}
		else if (msgString.equals("send all orders")) {
			List<OrderClient>orders = db_.getAll(new OrderClient());
			System.out.println("num of orders=" + orders.size());
			client.sendToClient(orders);

		} else if (msgString.equals("send all MenuItems")) {
			List<?> items = db_.getAll(new MenuItem());
			System.out.println("num of items=" + items.size());
			client.sendToClient("sending menu items soon");
			client.sendToClient(items);
			System.out.println("sent all items");
		} else if (msg instanceof Complaint) {
			System.out.println("saved complain");
			System.out.println((Complaint)msg);
			db_.saveOrUpdate((Complaint)msg);
			List<Complaint> openComplaints =db_.getAll(new Complaint());
			System.out.println("num of open complaints=" + openComplaints.size());
		}
		else if (msg instanceof LocationInformation) {
			System.out.println("saved location");
			db_.saveOrUpdate((LocationInformation)msg);

		}
		else if (msg instanceof Message message) {
			Object payload = message.getPayload();
			if (payload instanceof GetBranchOpeningTimes request) {
				String branch = request.getBranchName();
				OpeningTimes response = new OpeningTimes(branch, "14:14");

				Message responseMessage = new Message(
						message.getKey(),
						response,
						GetBranchOpeningTimes.class,
						OpeningTimes.class
				);
				client.sendToClient(responseMessage);
			} if (payload instanceof GetBranchClosingTimes request) {
				String branch = request.getBranchName();
				ClosingTimes response = new ClosingTimes(branch, "22:00");

				Message responseMessage = new Message(
						message.getKey(),
						response,
						GetBranchClosingTimes.class,
						ClosingTimes.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof String request && request.equals("get all branches")) {
				List<String> branches = HardcodedDataProvider.getAllBranches();

				Message responseMessage = new Message(
						message.getKey(),
						branches,
						String.class,
						List.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof RequestReservationTimes request) {
				ReservationDetails details = request.getDetails();

				List<String> response = List.of("10:00", "15:00");

				Message responseMessage = new Message(
						message.getKey(),
						response,
						RequestReservationTimes.class,
						List.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof IsReservationPossibleRequest request) {
				Reservation reservation = request.getReservation();

				boolean isPossible = true;

				Message responseMessage = new Message(
						message.getKey(),
						isPossible,
						IsReservationPossibleRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof BookReservationRequest request) {
				Reservation reservation = request.getReservation();

				boolean bookedSuccessfully = false;

				Message responseMessage = new Message(
						message.getKey(),
						bookedSuccessfully,
						BookReservationRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof CanBeMadeInOneHourRequest request) {
				ReservationDetails details = request.getReservationDetails();

				boolean canBeMade = false; // or false

				Message responseMessage = new Message(
						message.getKey(),
						canBeMade,
						CanBeMadeInOneHourRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof CanBeMadeInSameDateRequest request) {
				ReservationDetails details = request.getReservationDetails();

				boolean canBeMade = true;

				Message responseMessage = new Message(
						message.getKey(),
						canBeMade,
						CanBeMadeInSameDateRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof PossibleReservationsTimesRequest request) {
				ReservationDetails details = request.getReservationDetails();

				// Hardcoded example values
				List<String> availableTimes = List.of("16:00", "17:00");

				Message responseMessage = new Message(
						message.getKey(),
						availableTimes,
						PossibleReservationsTimesRequest.class,
						List.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof OrderCancelationFeeRequest request) {
				OrderClient order = request.getOrder();

				double cancelationFee = 5.0;

				Message responseMessage = new Message(
						message.getKey(),
						cancelationFee,
						OrderCancelationFeeRequest.class,
						Double.class
				);
				client.sendToClient(responseMessage);
			} else if (payload instanceof ReservationCancelationFeeRequest request) {
				Reservation reservation = request.getReservation();

				double cancelationFee = 10.0;

				Message responseMessage = new Message(
						message.getKey(),
						cancelationFee,
						ReservationCancelationFeeRequest.class,
						Double.class
				);

				client.sendToClient(responseMessage);
			} else if (payload instanceof String request && request.equals("get all ingredients")) {
				List<String> ingredients = HardcodedDataProvider.getAllIngredients();

				Message responseMessage = new Message(
						message.getKey(),
						ingredients,
						String.class,
						List.class
				);

				client.sendToClient(responseMessage);
			}
		}
	}


	public void sendToAllClients(String message) {
		try {
			for (SubscribedClient subscribedClient : SubscribersList) {
				subscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
