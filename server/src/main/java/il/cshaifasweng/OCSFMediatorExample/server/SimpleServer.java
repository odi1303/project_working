package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

public class SimpleServer extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private static SubscribedClient[] subscribers = new SubscribedClient[SubscribersList.size()];
	private int numberOfSubscribers = 0;
	private String []signs={"X","O"};
	public String[][] board = new String[3][3];
	public String sign;

	public SimpleServer(int port) {
		super(port);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = " ";
			}
		}
	}
	public String[][] getBoard() {
		return board;
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
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
			try {
				client.sendToClient("client added successfully with sign "+signs[numberOfSubscribers]);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			numberOfSubscribers++;
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
			board[msgString.charAt(0)][msgString.charAt(2)] = sign;
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
			sendToAllClients(board);
			subscribers[i%2].getClient().sendToClient("your turn");

		}
	}
	//checking if there's a win in the current board
	public boolean checkForWin(){
		if (checkHorizontal()||checkVertical()||checkDiagonal())
			return true;
		return false;
	}
	//checking for win in horizontal way
	public boolean checkHorizontal(){
		for (int i = 0; i < 3; i++) {
			if (board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][0]!=" ") {
				return true;
			}
		}
		return false;
	}
	//checking for win in vertical way
	public boolean checkVertical(){
		for (int i = 0; i < 3; i++) {
			if (board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[0][i]!=" ") {
				return true;
			}
		}
		return false;
	}
	//checking for win in diagonal way
	public boolean checkDiagonal(){
		if (board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[2][2]!=" ") {
			return true;
		}
		else if (board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[0][2]!=" ") {
			return true;
		}
		else
			return false;
	}


}
