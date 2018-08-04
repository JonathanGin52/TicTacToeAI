package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

class TicTacToe {

    static final boolean displayInfo = false;
    static final char PLAYER1 = 'x';
    static final char PLAYER2 = 'o';
    private static final Scanner keyboard = new Scanner(System.in);
    private static AIBase ai;
    private static char[][] grid = getGrid();

    public static void main(String[] args) {
        Boolean playerFirst;
        boolean running = true;
        String input;
        char token;
        char oppPiece;

        while (running) {
            do {
                playerFirst = null;
                System.out.print("Do you want to go first? Y/N: ");
                input = keyboard.next();
                if (input.equalsIgnoreCase("y")) {
                    playerFirst = true;
                } else if (input.equalsIgnoreCase("n")) {
                    playerFirst = false;
                } else {
                    System.out.println("Invalid input, please try again.\n");
                }
            } while (playerFirst == null);

            token = playerFirst ? PLAYER1 : PLAYER2;
            oppPiece = token == PLAYER1 ? PLAYER2 : PLAYER1;

            do {
                System.out.print("Which AI would you like to face? Minimax(M) or Heuristic(H): ");
                input = keyboard.next();
                if (input.equalsIgnoreCase("m")) {
                    ai = new MinimaxAI(oppPiece);
                } else if (input.equalsIgnoreCase("h")) {
                    ai = new HeuristicAI(oppPiece);
                } else {
                    System.out.println("Invalid input, please try again.\n");
                }
            } while (ai == null);

            while (!isGameOver(grid)) {
                if (playerFirst) {
                    playTurn(token);
                } else {
                    computerMove(oppPiece);
                }
                playerFirst = !playerFirst;
            }
            displayGrid(grid);
            if (checkWin(PLAYER1, grid)) {
                System.out.println("Player 1 wins!");
            } else if (checkWin(PLAYER2, grid)) {
                System.out.println("PLayer 2 wins!");
            } else {
                System.out.println("Draw game");
            }
            do {
                System.out.print("Would you like to play again? Y/N: ");
                input = keyboard.next().toLowerCase();
                if (input.equals("y") || input.equals("n")) {
                    running = input.equals("y");
                    System.out.println();
                    grid = getGrid();
                    ai = null;
                } else {
                    System.out.println("Invalid input, please try again.\n");
                }
            } while (!(input.equals("y") || input.equals("n")));
        }
        System.out.println("Thanks for playing!");
    }

    private static char[][] getGrid() {
        return new char[][]{{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
    }

    static void displayGrid(char[][] board) {
        System.out.println("-------------");
        for (char[] aBoard : board) {
            System.out.print("| ");
            for (int col = 0; col < board.length; col++) {
                System.out.print(aBoard[col] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    static boolean isGameOver(char[][] board) {
        if (checkWin(PLAYER1, board) || checkWin(PLAYER2, board)) {
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (isSpotOpen(board, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkWin(char token, char[][] board) {
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] == token || board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[1][1] == token) {
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][1] == token || board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[1][i] == token) {
                return true;
            }
        }
        return false;
    }

    private static void playTurn(char token) {
        int position;
        int[] location;

        System.out.println("\nPlayer turn:");
        displayGrid(grid);
        while (true) {
            System.out.print("Input position: ");
            try {
                position = keyboard.nextInt();
                location = convertInput(position);
                if (isSpotOpen(grid, location[0], location[1])) {
                    grid[location[0]][location[1]] = token;
                    break;
                } else {
                    System.out.println("Sorry, this spot is taken, please select another spot!\n");
                }
            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                System.out.println("Please input a valid number from 1-9.\n");
                keyboard.nextLine();
            }
        }
    }

    static int[] convertInput(int pos) {
        return new int[]{(pos - 1) / 3, (pos - 1) % 3};
    }

    static boolean isSpotOpen(char[][] board, int row, int col) {
        return board[row][col] != PLAYER1 && board[row][col] != PLAYER2;
    }

    private static void computerMove(char oppPiece) {
        int[] location = convertInput(ai.getBestMove(grid));
        grid[location[0]][location[1]] = oppPiece;
    }
}
