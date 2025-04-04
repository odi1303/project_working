package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer{
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private static Session session;

	/**
	 * Constructs a new server.
	 *
	 * @param port the port number on which to listen.
	 */
	public SimpleServer(int port) {
		super(port);
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
	protected synchronized void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		if (msg instanceof complaint_to_answer){
			System.out.println("message received");
		}
		String msgString = msg.toString();
		System.out.println(msgString);
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
						session.close();
						break;
					}
				}
			}
		} else if (msgString.startsWith("is user exists?")) {
			int startIndex = msgString.indexOf("(") + 1;
			int commaIndex = msgString.indexOf(",");
			int endIndex = msgString.indexOf(")");
			String username = msgString.substring(startIndex, commaIndex).trim();
			String password = msgString.substring(commaIndex + 1, endIndex).trim();
			UsersRepository usersRepository = new UsersRepository();
			if(!usersRepository.userExists(username)){
				//if the user doesn't;t exist send it in a message
				client.sendToClient("user named "+username+" does not exist");
			}
			else {
				if(usersRepository.correctPassword(username, password)){
					UserType type=usersRepository.getUserType(username,password);
					String stringType=type.toString();
					client.sendToClient("the password is ok("+username+","+password+")"+stringType);
				}
				else {
					client.sendToClient("wrong password");
				}
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
