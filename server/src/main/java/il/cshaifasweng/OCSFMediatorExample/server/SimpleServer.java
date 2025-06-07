package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.*;
import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.BookReservationRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.models.*;
import il.cshaifasweng.OCSFMediatorExample.entities.models.User;
import il.cshaifasweng.OCSFMediatorExample.server.bl.HardcodedDataProvider;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import jakarta.inject.Qualifier;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import il.cshaifasweng.OCSFMediatorExample.entities.clientRequests.GetBranchOpeningTimes;
import il.cshaifasweng.OCSFMediatorExample.entities.OpeningTimes;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.models.Reservation;
//@ApplicationScoped
public class SimpleServer extends AbstractServer{
	private static final ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

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
		}
		else if (msgString.startsWith("add client")) {
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
		}
		else if (msgString.startsWith("remove client")) {
			if (!SubscribersList.isEmpty()) {
				for (SubscribedClient subscribedClient : SubscribersList) {
					if (subscribedClient.getClient().equals(client)) {
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		}
		else if (msg instanceof GetUserType getUserType) {
			try {
				System.out.println("new request: " + msgString);
				System.out.println(getUserType.name + ", " + getUserType.password);
				var retval = /*db.getBasicUsers()*/db_.getUserType(getUserType.name, getUserType.password);
				System.out.println("method returned " + retval);
				client.sendToClient(retval);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (msg instanceof User user) {
			try {
				UserType type = db_.getUserType(user.getUsername(), user.getPassword());
				System.out.println("Authenticating user: " + user.getUsername() + ", returned type: " + type);
				client.sendToClient(type);
			} catch (Exception e) {
				e.printStackTrace();
				client.sendToClient(UserType.Empty);
			}
		}
		else if (msgString.equals("#getAllComplaints")) {
			try {
				List<Complaint> complaints = db_.getAll(Complaint.class);
				client.sendToClient(complaints);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (msgString.equals("#getAllDeliveries")) {
			try {
				List<Delivery> deliveries = db_.getAll(Delivery.class);
				client.sendToClient(deliveries);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (msgString.equals("#getAllReservations")) {
			try {
				List<TableOrder> tableOrders = db_.getAll(TableOrder.class);
				client.sendToClient(tableOrders);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (msgString.contains("send all complaints")) {
			System.out.println("got in");
			List<Complaint> openComplaints =db_.getAllOpenComplaints();
			/*System.out.println(db_);
			List<Complaint> complaints = db_.getAll(Complaint.class);
			for (Complaint complain : complaints) {
				if (complain.isHandled() == false) { // Corrected the condition to find open complaints
					openComplaints.add(complain);
				}
			}*/
			System.out.println("num of open complaints=" + openComplaints.size());
			client.sendToClient(openComplaints);
		}
		else if (msgString.equals("send all reservation")) {
			List<OrderClient>orders = db_.getAll(OrderClient.class);
			System.out.println("num of orders=" + orders.size());
			client.sendToClient(orders);
		}
		else if (msgString.equals("send all orders")) {
			List<OrderClient>orders = db_.getAll(OrderClient.class);
			System.out.println("num of orders=" + orders.size());
			client.sendToClient(orders);

		}
		else if (msgString.equals("send all MenuItems")) {
			List<?> items = db_.getAll(MenuItem.class);
			System.out.println("num of items=" + items.size());
			client.sendToClient("sending menu items soon");
			client.sendToClient(items);
			System.out.println("sent all items");
		}
		else if (msg instanceof Complaint complaint) {
			System.out.println("saved complain");
			System.out.println(complaint);
			db_.saveOrUpdate(complaint);
			List<Complaint> openComplaints = db_.getAllOpenComplaints();
			/*System.out.println(db_);
			List<Complaint> complaints = db_.getAll(Complaint.class);
			for (Complaint complain : complaints) {
				if (complain.isHandled() == false) { // Corrected the condition to find open complaints
					openComplaints.add(complain);
				}
			}*/
			sendToAllClients(openComplaints);

		}

		else if (msg instanceof GetBranchReportRequest request) {
			try {
				// Query the database for BranchReportEnt
				List<BranchReportEnt> reports = db_.getAll(BranchReportEnt.class);
				BranchReportEnt report = reports.stream()
						.filter(r -> r.getBranch().getId() == request.getBranchId() &&
								r.getYear() == request.getYear() &&
								r.getMonth() == request.getMonth())
						.findFirst()
						.orElse(null);
				System.out.println("Sending BranchReportEnt for branchId=" + request.getBranchId() +
						", year=" + request.getYear() + ", month=" + request.getMonth());
				client.sendToClient(report);
			} catch (Exception e) {
				e.printStackTrace();
				client.sendToClient(null);
			}
		}

		else if (msg instanceof OrderClient order) {
			try {
				System.out.println("saved order");

				db_.saveOrUpdate(order);  // suspect this is blocking or failing

				System.out.println("sending the order");
				List<OrderClient> openComplaints = db_.getAll(OrderClient.class);
				System.out.println("num of open orders=" + openComplaints.size());
                for (OrderClient openComplaint : openComplaints) {
                    if (order == openComplaint) {
                        client.sendToClient(openComplaint);
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
					throw(e);
				}
			}

			System.out.println("Delivery ID: " + id);
			System.out.println("Email: " + email);
			/*List<OrderClient> orders = db_.getAll(OrderClient.class);
			System.out.println("num of open orders=" + orders.size());
            for (OrderClient order : orders) {
                if (order.getId() == (long) id && order.getPersonalInformation().getEmail().equals(email)) {
                    client.sendToClient(order);
					return;
                }
            }*/
			String finalEmail = email;
			Optional<OrderClient> maybeOrder = db_.getById(OrderClient.class, id.longValue()).filter(o -> o.getPersonalInformation().getEmail().equals(finalEmail));
			client.sendToClient(maybeOrder.orElse(null));
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

			List<OrderClient> openComplaints = db_.getAll(OrderClient.class);
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

			List<Reservation> openReservations = db_.getAll(Reservation.class);
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






		else if (msg instanceof Message message) {
			Object payload = message.getPayload();
			if (payload instanceof GetBranchOpeningTimes request) {
				String branch = request.getBranchName();
				LocalDate date = request.getDate();

				OpeningTimes response = db_.getOpeningTimes(branch, date);

				Message responseMessage = new Message(
						message.getKey(),
						response,
						GetBranchOpeningTimes.class,
						OpeningTimes.class
				);
				client.sendToClient(responseMessage);
			}
			else if (payload instanceof GetBranchClosingTimes request) {
				String branch = request.getBranchName();
				LocalDate date = request.getDate();
				ClosingTimes response = db_.getClosingTimes(branch, date);

				Message responseMessage = new Message(
						message.getKey(),
						response,
						GetBranchClosingTimes.class,
						ClosingTimes.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof String request && request.equals("get all branches")) {
				List<String> branches = db_.getAll(Restaurant.class).stream().map(r -> r.name).toList();
				Message responseMessage = new Message(
						message.getKey(),
						branches,
						String.class,
						List.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof RequestReservationTimes request) {
				ReservationDetails details = request.getDetails();

				List<LocalTime> times = new ArrayList<>();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
				var start = details.getStartTime();
				if (db_.canReserve(details,0,0)) times.add(start);
				if (db_.canReserve(details,0,15)) times.add(start.plusMinutes(15));
				if (db_.canReserve(details,0,30)) times.add(start.plusMinutes(30));
				if (db_.canReserve(details,0,45)) times.add(start.plusMinutes(45));
				if (db_.canReserve(details,1,0)) times.add(start.plusHours(1));

				Message responseMessage = new Message(
						message.getKey(),
						times.stream().map(t -> t.format(formatter)).toList(),
						RequestReservationTimes.class,
						List.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof IsReservationPossibleRequest request) {
				// wait what
				ReservationDetails reservation_details = request.getReservation().getReservationDetails();
				boolean isPossible = true;//db_.canReserve(reservation_details,0,0);

				Message responseMessage = new Message(
						message.getKey(),
						isPossible,
						IsReservationPossibleRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			}


			else if (payload instanceof BookReservationRequest request) {
				Reservation reservation = request.getReservation();

				boolean bookedSuccessfully = false;

				Message responseMessage = new Message(
						message.getKey(),
						bookedSuccessfully,
						BookReservationRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof CanBeMadeInOneHourRequest request) {
				// Done
				ReservationDetails reservation_details = request.getReservationDetails();
				boolean canBeMade =
						db_.canReserve(reservation_details,0,0)  ||
						db_.canReserve(reservation_details,0,15) ||
						db_.canReserve(reservation_details,0,30) ||
						db_.canReserve(reservation_details,0,45) ||
						db_.canReserve(reservation_details,1,0);

				Message responseMessage = new Message(
						message.getKey(),
						canBeMade,
						CanBeMadeInOneHourRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof CanBeMadeInSameDateRequest request) {
				ReservationDetails details = request.getReservationDetails();

				boolean canBeMade = !db_.getAvailableTimes(details).isEmpty();

				Message responseMessage = new Message(
						message.getKey(),
						canBeMade,
						CanBeMadeInSameDateRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof PossibleReservationsTimesRequest request) {
				ReservationDetails details = request.getReservationDetails();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
				List<String> availableTimes = db_.getAvailableTimes(details).stream().map(t -> t.format(formatter)).toList();

				Message responseMessage = new Message(
						message.getKey(),
						availableTimes,
						PossibleReservationsTimesRequest.class,
						List.class
				);

				client.sendToClient(responseMessage);
			}

			else if (payload instanceof OrderCancellationFeeRequest request) {
				OrderClient order = request.getOrder();

				double cancellation_fee = 5.0;

				Message responseMessage = new Message(
						message.getKey(),
						cancellation_fee,
						OrderCancellationFeeRequest.class,
						Double.class
				);
				client.sendToClient(responseMessage);
			}
			else if (payload instanceof ReservationCancelationFeeRequest request) {
				Reservation reservation = request.getReservation();

				double cancellationFee = 10.0;

				Message responseMessage = new Message(
						message.getKey(),
						cancellationFee,
						ReservationCancelationFeeRequest.class,
						Double.class
				);

				client.sendToClient(responseMessage);
			}

			else if (payload instanceof SaveMenuChangesRequest request) {
				MenuClient oldMenu = request.getOldMenu();

				MenuClient newMenu = request.getNewMenu();

				boolean returnValue = true; //saved successfully

				Message responseMessage = new Message(
						message.getKey(),
						returnValue,
						SaveMenuChangesRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof SaveNewMenuRequest request) {
				MenuClient menuToSave = request.getMenuToSave();

				boolean returnValue = true; //saved successfully

				Message responseMessage = new Message(
						message.getKey(),
						returnValue,
						SaveNewMenuRequest.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof SubmitionRequestToNetworkManager request) {
				MenuClient menuToSubmit = request.getMenuToSave();

				boolean returnValue = true; //submitted successfully

				Message responseMessage = new Message(
						message.getKey(),
						returnValue,
						SubmitionRequestToNetworkManager.class,
						Boolean.class
				);

				client.sendToClient(responseMessage);
			}

			else if (payload instanceof String request && request.equals("get all ingredients")) {
				List<String> ingredients = db_.getAllIngredients();

				Message responseMessage = new Message(
						message.getKey(),
						ingredients,
						String.class,
						List.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof String request && request.equals("get main menu")) {
				MenuClient mainMenu = new MenuClient(db_.getByNaturalId(MenuServer.class, "menuName", "main menu").orElseThrow());

				Message responseMessage = new Message(
						message.getKey(),
						mainMenu,
						String.class,
						MenuClient.class
				);

				client.sendToClient(responseMessage);
			}
			else if (payload instanceof String request && request.equals("get all menus")) {
				List<MenuClient> Menus = db_.getAll(MenuServer.class).stream().map(MenuClient::new).toList();
				Message responseMessage = new Message(
						message.getKey(),
						Menus,
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
