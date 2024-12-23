package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private SubscribedClient[] subscribers = new SubscribedClient[SubscribersList.size()];
	private int numberOfSubscribers = 0;
	private String []signs={"X","O"};
	private int pickedSignIndex ;
	//public Game game=new Game();
	public String sign;
	public int move=0;

	public SimpleServer(int port) {
		super(port);
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		String msgString = msg.toString();
		System.out.println(msgString);
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
				if (numberOfSubscribers==0){
					Random random = new Random();
					pickedSignIndex=random.nextInt(2);
					client.sendToClient("client added successfully with sign "+signs[pickedSignIndex]);
					numberOfSubscribers++;
				}
				else if (numberOfSubscribers==1){
					client.sendToClient("client added successfully with sign "+signs[1-pickedSignIndex]);
					numberOfSubscribers++;
				}
				if (numberOfSubscribers==2){
					System.out.println(numberOfSubscribers);
					System.out.println("sent all client are connected");
					sendToAllClients("all clients are connected");
					sendToAllClients(move+"move");
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
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
		} else if (msgString.length()==4) {
			System.out.println("got the message "+msgString);
			//sendToAllClients(msgString);
			System.out.println("got in");
			move++;
			System.out.println(msgString+move);

			sendToAllClients(msgString+move);


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
