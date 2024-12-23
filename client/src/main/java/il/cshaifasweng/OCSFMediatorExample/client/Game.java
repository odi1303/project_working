package il.cshaifasweng.OCSFMediatorExample.client;

public class Game {
    public String[][] board = {
            {"0", "0", "0"},
            {"0", "0", "0"},
            {"0", "0", "0"}
    };
    String winner;

    public void setMove(int col, int row, String sign){
        board[row][col] = sign;
        //System.out.println(board);
        return;
    }
    public String getBoard() {
        return flattenBoard();
    }

    public String flattenBoard() {
        String temp="";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp += board[i][j];
            }
        }
        return temp;
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
            if (board[i][0]!="0"&&board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])  ) {
                winner=board[i][0];
                return true;
            }
        }
        return false;
    }
    //checking for win in vertical way
    public boolean checkVertical(){
        for (int i = 0; i < 3; i++) {
            if (board[0][i]!="0"&&board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) ) {
                winner=board[0][i];
                return true;
            }
        }
        return false;
    }
    //checking for win in diagonal way
    public boolean checkDiagonal(){
        if (board[2][2]!="0"&&board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) ) {
            winner=board[2][2];
            return true;
        }
        else if (board[0][2]!="0"&&board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])  ) {
            winner=board[1][1];
            return true;
        }
        else
            return false;
    }
    public String getWinner(){
        return winner;
    }
    public boolean checkForTie(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("0"))
                    return false;
            }
        }
        return true;
    }
}
