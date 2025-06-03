package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.*;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

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
		//client.sendToClient("received: " + msgString);
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
				System.out.println("new request: " + msgString);
				System.out.println(getUserType.name + ", " + getUserType.password);
				var retval = /*db.getBasicUsers()*/db_.getUserType(getUserType.name, getUserType.password);
				System.out.println("method returned " + retval);
				client.sendToClient(retval);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msgString.equals("#getAllComplaints")) {
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
		} else if (msgString.contains("send all complaints")) {
			System.out.println("got in");
			List<Complaint> openComplaints = new ArrayList<>();
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
			List<OrderClient> orders = db_.getAll(new OrderClient());
			System.out.println("num of orders=" + orders.size());
			client.sendToClient(orders);
		} else if (msgString.equals("send all orders")) {
			List<OrderClient> orders = db_.getAll(new OrderClient());
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
			System.out.println((Complaint) msg);
			db_.saveOrUpdate((Complaint) msg);
			List<Complaint> openComplaints = new ArrayList<>();
			System.out.println(db_);
			List<Complaint> complaints = db_.getAll(new Complaint());
			for (Complaint complain : complaints) {
				if (complain.isHandled() == false) { // Corrected the condition to find open complaints
					openComplaints.add(complain);
				}
			}
			sendToAllClients(openComplaints);

		}
		else if (msg instanceof OrderClient order) {
			try {
				System.out.println("saved order");

				db_.saveOrUpdate(order);  // suspect this is blocking or failing

				System.out.println("sending the order");
				List<OrderClient> openComplaints = db_.getAll(new OrderClient());
				System.out.println("num of open orders=" + openComplaints.size());
				for (int i = 0; i < openComplaints.size(); i++) {
					if (order == openComplaints.get(i)) {
						client.sendToClient(openComplaints.get(i));
					}
				}
			} catch (Exception e) {
				System.err.println("Exception occurred while saving or sending order:");
				e.printStackTrace();
			}
		}
		else if (msgString.startsWith("||delivery||id=")) {
			String[] parts = msgString.split("\\|\\|");
			String idStr = null;
			String email = null;

			for (String part : parts) {
				if (part.startsWith("id=")) {
					idStr = part.substring(3); // Extract the value after "id="
				} else if (part.startsWith("email=")) {
					email = part.substring(6); // Extract the value after "email="
				}
			}

			Integer id = null;
			if (idStr != null && idStr.matches("\\d+")) {
				try {
					id = Integer.parseInt(idStr);
				} catch (NumberFormatException e) {
					System.err.println("Error: Could not parse ID as an integer.");
				}
			}

			System.out.println("Delivery ID: " + id);
			System.out.println("Email: " + email);
			List<OrderClient> openComplaints = db_.getAll(new OrderClient());
			System.out.println("num of open orders=" + openComplaints.size());
			for (int i = 0; i < openComplaints.size(); i++) {
				if (openComplaints.get(i).getId() == (long) id && openComplaints.get(i).getPersonalInformation().getEmail().equals(email)) {
					System.out.println(openComplaints.get(i));
					client.sendToClient(openComplaints.get(i));
				}
			}
			client.sendToClient(null);
		}
		else if (msgString.startsWith("@@delivery@@id=")) {
			String[] parts = msgString.split("@@");
			String idStr = null;
			String email = null;

			for (String part : parts) {
				if (part.startsWith("id=")) {
					idStr = part.substring(3); // Extract the value after "id="
				} else if (part.startsWith("email=")) {
					email = part.substring(6); // Extract the value after "email="
				}
			}

			Integer id = null;
			if (idStr != null && idStr.matches("\\d+")) {
				try {
					id = Integer.parseInt(idStr);
				} catch (NumberFormatException e) {
					System.err.println("Error: Could not parse ID as an integer.");
				}
			}

			System.out.println("Delivery ID: " + id);
			System.out.println("Email: " + email);

			List<OrderClient> openComplaints = db_.getAll(new OrderClient());
			for (OrderClient order : openComplaints) {
				if (order.getId() == (long) id && order.getPersonalInformation().getEmail().equals(email)) {
					Thread t1=new Thread(()->{
                        try {
                            client.sendToClient(order);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });//deleting the object from database
					t1.start();

					db_.delete_object(order);
				}
			}
			client.sendToClient(null);
		}
		else if (msgString.startsWith("@@reservat@@id=")) {
			String[] parts = msgString.split("@@");
			String idStr = null;
			String email = null;

			for (String part : parts) {
				if (part.startsWith("id=")) {
					idStr = part.substring(3); // Extract the value after "id="
				} else if (part.startsWith("email=")) {
					email = part.substring(6); // Extract the value after "email="
				}
			}

			Integer id = null;
			if (idStr != null && idStr.matches("\\d+")) {
				try {
					id = Integer.parseInt(idStr);
				} catch (NumberFormatException e) {
					System.err.println("Error: Could not parse ID as an integer.");
				}
			}

			System.out.println("Reservation ID: " + id);
			System.out.println("Email: " + email);

			List<Reservation> openReservations = db_.getAll(new Reservation());
			for (Reservation reservation : openReservations) {
				if (reservation.getId() == (long) id && reservation.getPersonalInformation().getEmail().equals(email)) {
					Thread t1=new Thread(()->{
                        try {
							System.out.println(reservation);
                            client.sendToClient(reservation);
                        } catch (IOException e) {
                             e.printStackTrace();
                        }
                    });//deleting the object from database
					t1.start();

					db_.delete_object(reservation);
				}
			}
			client.sendToClient(null);
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
