package com.example.tictactoe;

import androidx.annotation.NonNull;


public class Bot extends Player {
    private final String level;

    public Bot(String level) {
        super("Bot");
        this.level = level;
    }

    public int[] select(char[][]board) {
        if(level.equals("Easy")) return easyLevel(board);
        else if (level.equals("Mid")) return midLevel(board);
        else return hardLevel(board);
    }

    public int[] easyLevel(@NonNull char[][]board) {
        int[] move = new int[2];
        do {
            move[0] = (int) (Math.random() * 3);
            move[1] = (int) (Math.random() * 3);
        } while (board[move[0]][move[1]] != EMPTY);
        return move;
    }

    public int[] midLevel(char[][]board) {
        int[] bestMove = new int[]{-1, -1};

        // Check for a winning move
        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == EMPTY) {
                board[i / 3][i % 3] = 'o';
                if (checkWin('o')) {
                    board[i / 3][i % 3] = EMPTY;
                    return new int[]{i / 3, i % 3};
                }
                board[i / 3][i % 3] = EMPTY;
            }
        }

        // Block opponent's winning move
        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == EMPTY) {
                board[i / 3][i % 3] = 'x';
                if (checkWin(board) == 'x') {
                    board[i / 3][i % 3] = EMPTY;
                    return new int[]{i / 3, i % 3};
                }
                board[i / 3][i % 3] = EMPTY;
            }
        }

        // Choose a strategic move: prioritize center and corners
        int[][] strategicMoves = {
                {1, 1}, // Center
                {0, 0}, {0, 2}, {2, 0}, {2, 2}, // Corners
                {0, 1}, {1, 0}, {1, 2}, {2, 1}  // Edges
        };

        for (int[] move : strategicMoves) {
//            System.out.println(Arrays.deepToString(board));
            if (board[move[0]][move[1]] == EMPTY) {
//                System.out.println("Strategic move: " + move[0] + ", " + move[1]);
                return move;
            }
        }
        return bestMove;
    }

    public int[] hardLevel(char[][]board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < 9; i++) {
            if (board[i / 3][i % 3] == EMPTY) {
                board[i / 3][i % 3] = 'o';
                int score = minimax(board, 0, false);
                board[i / 3][i % 3] = EMPTY;
                if (score > bestScore) {
                    bestScore = score;
                    bestMove[0] = i / 3;
                    bestMove[1] = i % 3;
                }
            }
        }
        return bestMove;
    }

    public int minimax(char[][] board, int depth, boolean isMaximizing) {
        char win = checkWin(board);
        if (win != EMPTY) {
            if (win == 'x') return depth - 10;
            else if (win == 'o') return 10 - depth;
            else return 0;
        }
        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++)
                if (board[i / 3][i % 3] == EMPTY) {
                    board[i / 3][i % 3] = 'o';
                    int score = minimax(board, depth + 1, false);
                    board[i / 3][i % 3] = EMPTY;
                    bestScore = Math.max(score, bestScore);
                }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++)
                if (board[i / 3][i % 3] == EMPTY) {
                    board[i / 3][i % 3] = 'x';
                    int score = minimax(board, depth + 1, true);
                    board[i / 3][i % 3] = EMPTY;
                    bestScore = Math.min(score, bestScore);
                }
        }
        return bestScore;
    }
}