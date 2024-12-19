package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;
	public static String sign;
	private static PrimaryController primaryController=new PrimaryController();
	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		String message = msg.toString();
		System.out.println(message);
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		else if (message.contains("client added successfully with sign ")){
			if(message.contains("X"))
				sign="X";
			else if(message.contains("O"))
				sign="O";
		}

		else{
			String[][] board=translateFromStringToArr(message);
			System.out.println(board);
			myTurn(board);
		}

	}

	public String[][] translateFromStringToArr(String temp){
		String[][] board=new String[3][3];
		char tempChar;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tempChar = temp.charAt(i*3+j);
				board[i][j]=tempChar+"";
			}
		}
		return board;
	}

	public static String[][] myTurn(String[][] givenboard){

		primaryController.myTurn(givenboard);
		givenboard=primaryController.logicBoard;
		return givenboard;
	}

	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
