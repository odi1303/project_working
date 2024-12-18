package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */
/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

import javafx.scene.control.Button;


public class PrimaryController {

	@FXML // fx:id="Button00"
	private Button Button00; // Value injected by FXMLLoader

	@FXML // fx:id="Button01"
	private Button Button01; // Value injected by FXMLLoader

	@FXML // fx:id="Button02"
	private Button Button02; // Value injected by FXMLLoader

	@FXML // fx:id="Button10"
	private Button Button10; // Value injected by FXMLLoader

	@FXML // fx:id="Button11"
	private Button Button11; // Value injected by FXMLLoader

	@FXML // fx:id="Button12"
	private Button Button12; // Value injected by FXMLLoader

	@FXML // fx:id="Button20"
	private Button Button20; // Value injected by FXMLLoader

	@FXML // fx:id="Button21"
	private Button Button21; // Value injected by FXMLLoader

	@FXML // fx:id="Button22"
	private Button Button22; // Value injected by FXMLLoader
	String [][] logicBoard =new String[3][3];

	@FXML
	void write00(ActionEvent event) throws IOException {
		Button00.setText(SimpleClient.sign);
		logicBoard[0][0]=Button00.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("0,0");
	}

	@FXML
	void write01(ActionEvent event) throws IOException {
		Button01.setText(SimpleClient.sign);
		logicBoard[0][1]=Button01.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("0,1");
	}

	@FXML
	void write02(ActionEvent event) throws IOException {
		Button02.setText(SimpleClient.sign);
		logicBoard[0][2]=Button02.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("0,2");
	}

	@FXML
	void write10(ActionEvent event) throws IOException {
		Button10.setText(SimpleClient.sign);
		logicBoard[1][0]=Button10.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("1,0");
	}

	@FXML
	void write11(ActionEvent event) throws IOException {
		Button11.setText(SimpleClient.sign);
		logicBoard[1][1]=Button11.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("1,1");
	}

	@FXML
	void write12(ActionEvent event) throws IOException {
		Button12.setText(SimpleClient.sign);
		logicBoard[1][2]=Button12.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("1,2");
	}

	@FXML
	void write20(ActionEvent event) throws IOException {
		Button20.setText(SimpleClient.sign);
		logicBoard[2][0]=Button20.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("2,0");
	}

	@FXML
	void write21(ActionEvent event) throws IOException {
		Button21.setText(SimpleClient.sign);
		logicBoard[2][1]=Button21.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("2,1");
	}

	@FXML
	void write22(ActionEvent event) throws IOException {
		Button22.setText(SimpleClient.sign);
		logicBoard[2][2]=Button22.getText();
		disableBoard();
		SimpleClient.getClient().sendToServer("2,2");

	}

	@FXML
	void sendWarning(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#warning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void initialize(){
		try {
			SimpleClient.getClient().sendToServer("add client");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disableBoard();

	}
	@FXML
	void myTurn(String[][]board){
		updateButtons();
		logicBoard=board;
		disableBoard();//the turn is over

	}
	@FXML
	void updateButtons(){
		Button00.setText(logicBoard[0][0]);
		if (Button00.getText()==" ")
			Button00.setDisable(false);
		Button01.setText(logicBoard[0][1]);
		if (Button01.getText()==" ")
			Button01.setDisable(false);
		Button02.setText(logicBoard[0][2]);
		if (Button02.getText()==" ")
			Button02.setDisable(false);
		Button10.setText(logicBoard[1][0]);
		if (Button10.getText()==" ")
			Button10.setDisable(false);
		Button11.setText(logicBoard[1][1]);
		if (Button11.getText()==" ")
			Button11.setDisable(false);
		Button12.setText(logicBoard[1][2]);
		if (Button12.getText()==" ")
			Button12.setDisable(false);
		Button20.setText(logicBoard[2][0]);
		if (Button20.getText()==" ")
			Button20.setDisable(false);
		Button21.setText(logicBoard[2][1]);
		if (Button21.getText()==" ")
			Button21.setDisable(false);
		Button22.setText(logicBoard[2][2]);
		if (Button22.getText()==" ")
			Button22.setDisable(false);
	}
	void disableBoard(){
		Button00.setDisable(true);
		Button01.setDisable(true);
		Button02.setDisable(true);
		Button10.setDisable(true);
		Button11.setDisable(true);
		Button12.setDisable(true);
		Button20.setDisable(true);
		Button21.setDisable(true);
		Button22.setDisable(true);
	}
}
