package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import java.io.IOException;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;
	public static String sign;
	public static PrimaryController primaryController;
	private SimpleClient(String host, int port) throws IOException {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) throws IOException {
		String message = msg.toString();
		System.out.println(message + "=message");

		if (msg instanceof Warning) {
			// Handle Warning event
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		} else if (message.contains("there is")) {
			// Handle format: "0,0 there is X and the move is 1"
			try {
				String[] parts = message.split("there is");
				if (parts.length == 2) {
					String positionPart = parts[0].trim(); // e.g., "0,0"
					String[] position = positionPart.split(",");
					int row = Integer.parseInt(position[0].trim());
					int col = Integer.parseInt(position[1].trim());

					String[] moveParts = parts[1].trim().split("and the move is");
					String sign = moveParts[0].trim(); // e.g., "X"
					int move = Integer.parseInt(moveParts[1].trim()); // e.g., "1"

					// Post the move event with extracted data
					EventBus.getDefault().post(new MoveEvent(row, col, sign, move));
				} else {
					System.err.println("Invalid message format: " + message);
				}
			} catch (Exception e) {
				System.err.println("Error parsing move message: " + e.getMessage());
			}
		} else if (message.matches("\\d,\\d[XO]\\d")) {
			// Handle format: "0,0X1"
			try {
				String rowColSign = message.substring(0, message.length() - 1); // "0,0X"
				int move = Integer.parseInt(message.substring(message.length() - 1)); // "1"
				String[] position = rowColSign.split(",");
				int row = Integer.parseInt(position[0].trim());
				String colAndSign = position[1].trim(); // "0X"
				int col = Integer.parseInt(colAndSign.substring(0, 1)); // "0"
				String sign = colAndSign.substring(1); // "X"

				// Post the move event
				EventBus.getDefault().post(new MoveEvent(row, col, sign, move));
			} catch (Exception e) {
				System.err.println("Error parsing compact move message: " + e.getMessage());
			}
		} else if (message.contains("client added successfully with sign ")) {
			// Assign the sign (X or O)
			if (message.contains("X")) {
				sign = "X";
				System.out.println("My sign is X");
			} else if (message.contains("O")) {
				sign = "O";
				primaryController.disableBoard();
			}
		} else if (message.equals("all clients are connected")) {
			System.out.println("All clients are connected.");
		} else if (message.equals("0move")) {
			System.out.println("Received '0move'. No action required.");
		}
		else {
			System.err.println("Unhandled message: " + message);
		}
	}
	public static SimpleClient getClient() throws IOException {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
