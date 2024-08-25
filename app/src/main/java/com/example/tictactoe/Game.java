package com.example.tictactoe;
public class Game {
    public final char EMPTY = '\u0000';
    protected boolean [][] winPos;
    protected char[][] board;
    protected char currentPlayer;
    protected int moves;
    protected String winner;
    public Game() {
        board = new char[3][3];
        winPos = new boolean[3][3];
        currentPlayer = 'x';
        moves = 0;
        winner = "";
    }
    public void toggle(){
        currentPlayer = (currentPlayer == 'x') ? 'o' : 'x';
    }
    public void reset(){
        board = new char[3][3];
        winPos = new boolean[3][3];
        currentPlayer = 'x';
        moves = 0;
        winner = "";
    }
    public boolean isEmptyCell(int row, int col) {
        return board[row][col] == EMPTY;
    }
    public boolean isBoardFull() {
        boolean isFull = true;
        for (int i = 0; i < 9; i++)
            if (isEmptyCell(i/3, i%3)) {
                isFull = false;
                break;
            }
        return isFull;
    }
    public boolean[][] getWinPos() {
        return winPos;
    }
    public String getWinner() {
        return winner;
    }
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean checkWin(char player) {
        for (int i = 0; i < 3; i++)
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player){
                winPos[i][0] = true;
                winPos[i][1] = true;
                winPos[i][2] = true;
                return true;
            }
            else if (board[0][i] == player && board[1][i] == player && board[2][i] == player){
                winPos[0][i] = true;
                winPos[1][i] = true;
                winPos[2][i] = true;
                return true;
            }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player){
            winPos[0][0] = true;
            winPos[1][1] = true;
            winPos[2][2] = true;
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player){
            winPos[0][2] = true;
            winPos[1][1] = true;
            winPos[2][0] = true;
            return true;
        }
        return false;
    }
    public char checkWin(char [][] b) {
        for (int i = 0; i < 3; i++)
            if (b[i][0] == b[i][1] && b[i][1] == b[i][2] && b[i][2] != EMPTY) return b[i][0]; // check rows
            else if (b[0][i] == b[1][i] && b[1][i] == b[2][i] && b[2][i] != EMPTY) return b[0][i]; // check columns
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2] && b[2][2] != EMPTY) return b[0][0]; // check diagonal
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0] && b[2][0] != EMPTY) return b[0][2]; // check diagonal
        boolean isFull = true;
        for (int i = 0; i < 9; i++)
            if (b[i/3][i%3] == EMPTY) {
                isFull = false;
                break;
            }
        if(isFull) return 'D';
        return EMPTY;
    }
    public void makeMove(int row, int col) {
        // if cell is empty, make the move
        if (isEmptyCell(row, col)) {
            board[row][col] = currentPlayer;
            moves++;
            if (checkWin(currentPlayer)) {
                winner = String.valueOf(currentPlayer);
            } else if (moves == 9) {
                winner = "draw";
            }
        }
    }

}
