package tictactoe;

import java.util.ArrayList;

public abstract class AIBase extends TicTacToe {

    final char piece;
    final char oppPiece;
    int bestMove;

    AIBase(char token) {
        piece = token;
        oppPiece = token == PLAYER1 ? PLAYER2 : PLAYER1;
    }

    public abstract int getBestMove(char[][] board);

    ArrayList getPossibleMoves(char[][] board) {
        ArrayList<Integer> list = new ArrayList<>(9);
        for (int i = 1; i <= 9; i++) {
            if (isSpotOpen(board, convertInput(i)[0], convertInput(i)[1])) {
                list.add(i);
            }
        }
        list.trimToSize();
        return list;
    }

    char[][] cloneGrid(char[][] board) {
        char[][] newGrid = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            char[] newState = board[i];
            int newLength = newState.length;
            newGrid[i] = new char[newLength];
            System.arraycopy(newState, 0, newGrid[i], 0, newLength);
        }
        return newGrid;
    }
}
