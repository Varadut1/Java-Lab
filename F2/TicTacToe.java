import java.util.Scanner;

public class TicTacToe {
    private static final int SIZE = 3;
    private static final char EMPTY = '-';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    public static void main(String[] args) {
        char[][] board = new char[SIZE][SIZE];
        initializeBoard(board);

        Scanner scanner = new Scanner(System.in);
        printBoard(board);

        while (!isGameOver(board)) {
            playerMove(board, scanner);
            printBoard(board);

            if (isGameOver(board)) {
                break;
            }

            computerMove(board);
            printBoard(board);
        }

        announceResult(board);
        scanner.close();
    }

    private static void initializeBoard(char[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private static void printBoard(char[][] board) {
        System.out.println("Current Board:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void playerMove(char[][] board, Scanner scanner) {
        int row, col;
        do {
            System.out.print("Enter your move (row and column): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
        } while (!isValidMove(board, row, col));

        board[row][col] = PLAYER_X;
    }

    private static void computerMove(char[][] board) {
        int[] bestMove = findBestMove(board);
        board[bestMove[0]][bestMove[1]] = PLAYER_O;
        System.out.println("Computer plays at: " + bestMove[0] + ", " + bestMove[1]);
    }

    private static int[] findBestMove(char[][] board) {
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_O; // Assume a move for the computer
                    int score = evaluate(board);
                    board[i][j] = EMPTY; // Undo the assumed move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int evaluate(char[][] board) {
        // Simple heuristic: +1 for each O in a row, column, or diagonal
        int score = 0;

        for (int i = 0; i < SIZE; i++) {
            score += evaluateLine(board[i][0], board[i][1], board[i][2]); // Rows
            score += evaluateLine(board[0][i], board[1][i], board[2][i]); // Columns
        }

        score += evaluateLine(board[0][0], board[1][1], board[2][2]); // Diagonal
        score += evaluateLine(board[0][2], board[1][1], board[2][0]); // Anti-Diagonal

        return score;
    }

    private static int evaluateLine(char a, char b, char c) {
        int score = 0;
        if (a == PLAYER_O) {
            score++;
        }
        if (b == PLAYER_O) {
            score++;
        }
        if (c == PLAYER_O) {
            score++;
        }
        return score;
    }

    private static boolean isValidMove(char[][] board, int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == EMPTY;
    }

    private static boolean isGameOver(char[][] board) {
        // Check for a win or a full board
        return evaluate(board) > 0 || evaluate(board) < 0 || isBoardFull(board);
    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void announceResult(char[][] board) {
        int score = evaluate(board);
        if (score > 0) {
            System.out.println("Computer wins!");
        } else if (score < 0) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}
