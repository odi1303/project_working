package il.cshaifasweng.OCSFMediatorExample.server;

import java.util.Objects;

public class Game {
    public String[][] board = new String[3][3];

    public void main(String[] args) {
        initialize();
    }
    public void initialize() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "0";
            }
        }
    }
    public void setMove(int col, int row, String sign){
        board[row][col] = sign;
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
            if (Objects.equals(board[i][0], board[i][1]) && Objects.equals(board[i][1], board[i][2]) && !Objects.equals(board[i][0], " ")) {
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
