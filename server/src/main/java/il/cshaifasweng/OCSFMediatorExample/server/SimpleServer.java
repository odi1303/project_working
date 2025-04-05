package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

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

public class SimpleServer extends AbstractServer{
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	/**
	 * Constructs a new server.
	 *
	 * @param port the port number on which to listen.
	 */
	public SimpleServer(int port) {
		super(port);
		Database.getInstance();
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		var config = new Configuration();
		config.addAnnotatedClass(Dish.class);
		config.addAnnotatedClass(Ingredient.class);
		config.addAnnotatedClass(PersonalPreference.class);

		var serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		return config.buildSessionFactory(serviceRegistry);
	}

	@Override
	protected synchronized void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		System.out.println("SimpleServer" + " " + msgString);
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
				client.sendToClient(Database.basicUsers.getUserType(getUserType.name, getUserType.password));
			} catch (IOException e) {
				e.printStackTrace();
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
