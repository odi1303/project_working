package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */



public class PrimaryController {

	@FXML
	private Label welcomeLabel;
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

	public String[][] logicBoard = {
			{"0", "0", "0"},
			{"0", "0", "0"},
			{"0", "0", "0"}
	};
	private int move=0;
	private Game game=new Game();

	@FXML
	void write00(ActionEvent event) throws IOException {
		Button00.setText(SimpleClient.sign);
		logicBoard[0][0]=SimpleClient.sign;
		SimpleClient.getClient().sendToServer("0,0"+SimpleClient.sign);
		disableBoard();
		Tie();
		printBoard();
	}

	@FXML
	void write01(ActionEvent event) throws IOException {
		Button01.setText(SimpleClient.sign);
		logicBoard[0][1]=SimpleClient.sign;
		disableBoard();
		Tie();
		SimpleClient.getClient().sendToServer("0,1"+SimpleClient.sign);
	}

	@FXML
	void write02(ActionEvent event) throws IOException {
		Button02.setText(SimpleClient.sign);
		logicBoard[0][2]=SimpleClient.sign;
		disableBoard();
		Tie();
		SimpleClient.getClient().sendToServer("0,2"+SimpleClient.sign);
	}

	@FXML
	void write10(ActionEvent event) throws IOException {
		Button10.setText(SimpleClient.sign);
		logicBoard[1][0]=SimpleClient.sign;
		disableBoard();
		Tie();
		SimpleClient.getClient().sendToServer("1,0"+SimpleClient.sign);
	}

	@FXML
	void write11(ActionEvent event) throws IOException {
		Button11.setText(SimpleClient.sign);
		logicBoard[1][1]=SimpleClient.sign;
		disableBoard();
		Tie();
		SimpleClient.getClient().sendToServer("1,1"+SimpleClient.sign);
	}

	@FXML
	void write12(ActionEvent event) throws IOException {
		Button12.setText(SimpleClient.sign);
		logicBoard[1][2]=SimpleClient.sign;
		disableBoard();
		Tie();
		SimpleClient.getClient().sendToServer("1,2"+SimpleClient.sign);
	}

	@FXML
	void write20(ActionEvent event) throws IOException {
		Button20.setText(SimpleClient.sign);
		logicBoard[2][0]=SimpleClient.sign;
		disableBoard();
		Tie();
		SimpleClient.getClient().sendToServer("2,0"+SimpleClient.sign);
	}

	@FXML
	void write21(ActionEvent event) throws IOException {
		Button21.setText(SimpleClient.sign);
		logicBoard[2][1]=SimpleClient.sign;
		disableBoard();
		Tie();
		SimpleClient.getClient().sendToServer("2,1"+SimpleClient.sign);
	}

	@FXML
	void write22(ActionEvent event) throws IOException {
		Button22.setText(SimpleClient.sign);
		logicBoard[2][2]=SimpleClient.sign;
		disableBoard();
		Tie();
		SimpleClient.getClient().sendToServer("2,2"+SimpleClient.sign);

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
	void initialize() {
		try {
			EventBus.getDefault().register(this); // Register to EventBus
			System.out.println("PrimaryController registered to EventBus.");
			SimpleClient.primaryController = this;
			SimpleClient.getClient().sendToServer("add client");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		initializeBoard();
	}

	@FXML
	void onDestroy() {
		EventBus.getDefault().unregister(this); // Unregister from EventBus
		System.out.println("PrimaryController unregistered from EventBus.");
	}

	// Handle MoveEvent
	@Subscribe
	public void onMoveEvent(MoveEvent event) throws IOException {
		int row = event.getRow();
		int col = event.getCol();
		String sign = event.getSign();
		int move=event.getMove();
		this.move=move;
		if (row >= 0 && row < 3 && col >= 0 && col < 3) {
			updateLogicBoard(row, col, sign);
			Platform.runLater(() -> {
                try {
                    this.updateButtons();
					Tie();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
		} else {
			System.err.println("Invalid move received: row=" + row + ", col=" + col);
		}
	}




	void updateButtons() throws IOException {
		//unableBoard();
		System.out.println("update buttons");
		System.out.println(logicBoard[0][0]);
		//Button00.setDisable(false);
		//Button00.setText("X");
		System.out.println(!logicBoard[0][0].equals("0"));
		if (!logicBoard[0][0].equals("0")){
			System.out.println("got int");
			try {
				Button00.setText(logicBoard[0][0]);
				Button00.setDisable(true);
			}
			catch (Exception e){
				System.out.println(e);
			}
		}
		else{
			Button00.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button00.setDisable(false);
			else
				Button00.setDisable(true);
		}

		if (!logicBoard[0][1].equals("0")){
			Button01.setDisable(true);
			Button01.setText(logicBoard[0][1]);
		}
		else{
			Button01.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button01.setDisable(false);
			else
				Button00.setDisable(true);
		}

		if (!logicBoard[0][2].equals("0")){
			Button02.setDisable(true);
			Button02.setText(logicBoard[0][2]);
		}
		else{
			Button02.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button02.setDisable(false);
			else
				Button02.setDisable(true);
		}
		if (!logicBoard[1][0].equals("0")){
			Button10.setDisable(true);
			Button10.setText(logicBoard[1][0]);
		}
		else{
			Button10.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button10.setDisable(false);
			else
				Button10.setDisable(true);
		}
		if (!logicBoard[1][1].equals("0")){
			Button11.setDisable(true);
			Button11.setText(logicBoard[1][1]);
		}
		else{
			Button11.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button11.setDisable(false);
			else
				Button11.setDisable(true);
		}
		if (!logicBoard[1][2].equals("0")){
			Button12.setDisable(true);
			Button12.setText(logicBoard[1][2]);
		}
		else{
			Button12.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button12.setDisable(false);
			else
				Button12.setDisable(true);
		}
		if (!logicBoard[2][0].equals("0")){
			Button20.setDisable(true);
			Button20.setText(logicBoard[2][0]);
		}
		else{
			Button20.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button20.setDisable(false);
			else
				Button20.setDisable(true);
		}

		if (!logicBoard[2][1].equals("0")){
			Button21.setDisable(true);
			Button21.setText(logicBoard[2][1]);
		}
		else{
			Button21.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button21.setDisable(false);
			else
				Button21.setDisable(true);
		}
		if (!logicBoard[2][2].equals("0")){

			Button22.setDisable(true);
			Button22.setText(logicBoard[2][2]);
		}
		else{
			Button22.setText(" ");
			if (move!=-1&&(SimpleClient.sign.equals("X")&&move%2==0)||(SimpleClient.sign.equals("O")&&move%2==1))
				Button22.setDisable(false);
			else
				Button22.setDisable(true);
		}
		if(game.checkForWin()){
			disableBoard();
			String winner= game.getWinner();
			Platform.runLater(() -> {
				welcomeLabel.setText(winner+" won!");
			});
		}
	}

	@FXML
	void Tie(){
		if (game.checkForTie()){
			Platform.runLater(() -> {
				welcomeLabel.setText("It's a tie!");
			});
		}
	}

	void disableBoard(){
		System.out.println("disable board");
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

	void enableBoard(){
		Button00.setDisable(false);
		Button01.setDisable(false);
		Button02.setDisable(false);
		Button10.setDisable(false);
		Button11.setDisable(false);
		Button12.setDisable(false);
		Button20.setDisable(false);
		Button21.setDisable(false);
		Button22.setDisable(false);
	}

	@FXML
	void writingSign(String sign){
		Platform.runLater(() -> {
			welcomeLabel.setText("My sign is "+sign);
			welcomeLabel.setText("My sign is "+sign);
			welcomeLabel.setText("My sign is "+sign);
		});

	}

	void initializeBoard(){
		System.out.println("initialize board");
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				logicBoard[i][j]="0";
			}
		}
		printBoard();
	}

	void printBoard(){
		for (int i=0;i<3;i++){
			for (int j=0;j<3;j++){
				System.out.print(logicBoard[i][j]+"\t");
			}
			System.out.println();
		}
	}

	void updateLogicBoard(int row,int col, String sign){
		System.out.println("update logicboard, "+row+" "+col);
		logicBoard[row][col]=sign;
		game.setMove(col,row,sign);
		//Button00.setDisable(true);
		//Button00.setText("X");
		System.out.println(logicBoard[row][col]);
		printBoard();

	}


}
