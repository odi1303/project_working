package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Complaint;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Delivery;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.TableOrder;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.DeliveryComplain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.RestaurantComplain;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import jakarta.inject.Qualifier;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
		System.out.println("SimpleServer" + " " + msgString);
		Session session = null;
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msgString.startsWith("add client")) {
			SubscribedClient connection = new SubscribedClient(client);
			SubscribersList.add(connection);
			System.out.println(msgString);
			/*System.out.println("Database pointer " + db);
			System.out.println(db.getBasicUsers());*/
			System.out.println("hello there");
			//db.getBasicUsers().addUser(new User("pp", "pp", UserType.Admin));
			for (var o : db_.getAll(new User())) {
				System.out.println(o.toString());
			}
			System.out.println("supposedly added into db");
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
		} else if (msg instanceof Complaint) {
			System.out.println("saved complain");
			System.out.println((Complaint)msg);
			db_.saveOrUpdate((Complaint)msg);
			List<Complaint> openComplaints =db_.getAll(new Complaint());
			System.out.println("num of open complaints=" + openComplaints.size());
		}}
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
