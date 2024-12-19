package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private SubscribedClient[] subscribers = new SubscribedClient[SubscribersList.size()];
	private int numberOfSubscribers = 0;
	private String []signs={"X","O"};
	public Game game=new Game();
	public String sign;

	public SimpleServer(int port) {
		super(port);
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(msgString.startsWith("add client")){
			SubscribedClient connection = new SubscribedClient(client);
			SubscribersList.add(connection);
			//subscribers[numberOfSubscribers] = connection;
			try {
				System.out.println(numberOfSubscribers);
				System.out.println(signs[numberOfSubscribers]);
				client.sendToClient("client added successfully with sign "+signs[numberOfSubscribers]);
				numberOfSubscribers++;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (numberOfSubscribers==2){
				int i=0;
				for(SubscribedClient subscribedClient: SubscribersList){
					subscribers[i]=subscribedClient;
					i++;
				}
				System.out.println("all clients are connected");
				game_manegment();
			}
		}
		else if(msgString.startsWith("remove client")){
			if(!SubscribersList.isEmpty()){
				for(SubscribedClient subscribedClient: SubscribersList){
					if(subscribedClient.getClient().equals(client)){
						SubscribersList.remove(subscribedClient);
						break;
					}
				}
			}
		} else if (msgString.length()==3) {
			game.setMove(msgString.charAt(0),msgString.charAt(2),sign);
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
	public void game_manegment() throws IOException {
		ConnectionToClient tempClient;
		for(int i=0; i<9; i++){
			sign=signs[i%2];
			sendToAllClients(game.getBoard());
			subscribers[i%2].getClient().sendToClient("your turn");
			System.out.println(sign);
			System.out.println(game.getBoard());
		}
	}



}
