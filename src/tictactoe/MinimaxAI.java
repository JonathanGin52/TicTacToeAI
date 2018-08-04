package tictactoe;

import java.util.ArrayList;

public class MinimaxAI extends AIBase {

    private int iterations;

    public MinimaxAI(char token) {
        super(token);
    }

    @Override
    public int getBestMove(char[][] board) {
        iterations = 0;
        max(cloneGrid(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return bestMove;
    }

    private int getUtility(char[][] board, int depth) {
        if (checkWin(oppPiece, board)) {
            return -1000 + depth;
        } else if (checkWin(piece, board)) {
            return +1000 - depth;
        } else {
            return 0;
        }
    }

    private char[][] editBoard(char[][] board, int location, char token, int depth) {
        ++iterations;
        char[][] newBoard = cloneGrid(board);
        newBoard[convertInput(location)[0]][convertInput(location)[1]] = token;
        displayInfo(board, depth);
        if (displayInfo) {
            System.out.println();
        }
        return newBoard;
    }

    private int max(char[][] board, int depth, int alpha, int beta) {
        ArrayList<Integer> options = getPossibleMoves(board);
        int[] values = new int[options.size()];
        int bestValue = Integer.MIN_VALUE;

        if (isGameOver(board)) {
            iterations++;
            displayInfo(board, depth);
            return getUtility(board, depth);
        }
        for (int i = 0; i < options.size(); i++) {
            values[i] = min(editBoard(board, options.get(i), piece, depth), ++depth, alpha, beta);
            if (values[i] > alpha) {
                alpha = values[i];
            }
            if (beta <= alpha) {
                break;
            }
        }

        for (int i = 0; i < values.length; i++) {
            if (values[i] > bestValue) {
                bestValue = values[i];
                bestMove = options.get(i);
            }
        }
        return bestValue;
    }

    private int min(char[][] board, int depth, int alpha, int beta) {
        ArrayList<Integer> options = getPossibleMoves(board);
        int[] values = new int[options.size()];
        int bestValue = Integer.MAX_VALUE;

        if (isGameOver(board)) {
            iterations++;
            displayInfo(board, depth);
            return getUtility(board, depth);
        }
        for (int i = 0; i < options.size(); i++) {
            values[i] = max(editBoard(board, options.get(i), oppPiece, depth), ++depth, alpha, beta);
            if (values[i] < beta) {
                beta = values[i];
            }
            if (beta <= alpha) {
                break;
            }
        }

        for (int value : values) {
            if (value < bestValue) {
                bestValue = value;
            }
        }
        return bestValue;
    }

    private void displayInfo(char[][] board, int depth) {
        if (displayInfo) {
            System.out.println();
            displayGrid(board);
            System.out.print("Iteration: " + iterations + " Depth: " + depth);
            if (isGameOver(board)) {
                System.out.println(" Utility: " + getUtility(board, depth));
            }
        }
    }
}
