package tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class HeuristicAI extends AIBase {

    public HeuristicAI(char token) {
        super(token);
    }

    @Override
    public int getBestMove(char[][] board) {
        if (checkWinningMove(board)) {
            return bestMove;
        }
        if (checkCorners(board)) {
            return bestMove;
        }
        if (isSpotOpen(board, 1, 1)) {
            return 5;
        }
        ArrayList<Integer> options = getPossibleMoves(board);
        return options.get(new Random().nextInt(options.size()));
    }

    private boolean checkWinningMove(char[][] origBoard) {
        ArrayList<Integer> options = getPossibleMoves(origBoard);
        char[][] board;
        int[] location;

        for (Integer option : options) {
            board = cloneGrid(origBoard);
            location = convertInput(option);
            board[location[0]][location[1]] = piece;
            if (checkWin(piece, board)) {
                bestMove = option;
                return true;
            }
            board[location[0]][location[1]] = oppPiece;
            if (checkWin(oppPiece, board)) {
                bestMove = option;
                return true;
            }
        }
        return false;
    }

    private boolean checkCorners(char[][] board) {
        if (isSpotOpen(board, 0, 0)) {
            bestMove = 1;
        } else if (isSpotOpen(board, 0, 2)) {
            bestMove = 3;
        } else if (isSpotOpen(board, 2, 0)) {
            bestMove = 7;
        } else if (isSpotOpen(board, 2, 2)) {
            bestMove = 9;
        }
        return isSpotOpen(board, 0, 0) || isSpotOpen(board, 0, 2) || isSpotOpen(board, 2, 0) || isSpotOpen(board, 2, 2);
    }
}
