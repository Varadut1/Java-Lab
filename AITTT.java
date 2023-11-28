import java.util.*;

public class AITTT {
    private static final char PLAYER_SYMBOL = 'X';
    private static final char AI_SYMBOL = 'O';
    private static final char EMPTY_CELL = '_';

    private static void displayBoard(char[][] board) {
        System.out.println("Current Game Board:");
        System.out.println("-------------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("|");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println();
            System.out.println("-------------------");
        }
    }

    private static boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char[][] board, char symbol) {
         if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true; 
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return true;
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true; 
            }
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
                return true;
            }
        }

        return false;
    }

    private static int minimaxAlgorithm(char[][] board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (checkWin(board, PLAYER_SYMBOL)) {
            return -10 + depth;
        }
        if (checkWin(board, AI_SYMBOL)) {
            return 10 - depth;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY_CELL) {
                        board[i][j] = AI_SYMBOL;
                        int eval = minimaxAlgorithm(board, depth + 1, alpha, beta, false);
                        board[i][j] = EMPTY_CELL;
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }   
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY_CELL) {
                        board[i][j] = PLAYER_SYMBOL;
                        int eval = minimaxAlgorithm(board, depth + 1, alpha, beta, true);
                        board[i][j] = EMPTY_CELL;
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }

    private static int[] findBestMoveForAI(char[][] board) {
        int[] bestMove = {-1, -1};
        int bestEval = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    board[i][j] = AI_SYMBOL;
                    int eval = minimaxAlgorithm(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    board[i][j] = EMPTY_CELL;
                    if (eval > bestEval) {
                        bestEval = eval;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    public static void main(String[] args) {
        char[][] gameBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(gameBoard[i], EMPTY_CELL);
        }

        Scanner inputScanner = new Scanner(System.in);
        while (!isBoardFull(gameBoard) && !checkWin(gameBoard, PLAYER_SYMBOL) && !checkWin(gameBoard, AI_SYMBOL)) {
            displayBoard(gameBoard);

            System.out.print("Enter Move (Row and Column): ");
            int row = inputScanner.nextInt();
            int col = inputScanner.nextInt();
            while (gameBoard[row][col] != EMPTY_CELL) {
                System.out.println("Invalid Move");
                System.out.print("Enter Move (Row and Column): ");
                row = inputScanner.nextInt();
                col = inputScanner.nextInt();
            }
            gameBoard[row][col] = PLAYER_SYMBOL;

            // AI
            if (!isBoardFull(gameBoard) && !checkWin(gameBoard, AI_SYMBOL)) {
                int[] aiMove = findBestMoveForAI(gameBoard);
                gameBoard[aiMove[0]][aiMove[1]] = AI_SYMBOL;
            }
        }

        displayBoard(gameBoard);

        if (checkWin(gameBoard, PLAYER_SYMBOL)) {
            System.out.println("Congratulations!!! You won the game!");
        } else if (checkWin(gameBoard, AI_SYMBOL)) {
            System.out.println("AI wins!!!");
        } else {
            System.out.println("Draw!!");
        }

        inputScanner.close();
    }
}