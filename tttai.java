import java.util.*;

public class tttai {
    static char playerSymbol = 'X';
    static char computerSymbol = 'O';
    static char empty = '_';
    static char[][] board = new char[3][3];

    public static void drawBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    public static boolean checkWin(char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j] == symbol && board[1][j] == symbol && board[2][j] == symbol) {
                return true;
            }
        }

        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true;
        }

        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return true;
        }
        return false;
    }

    public static boolean isTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == empty)
                    return false;
            }
        }
        return true;
    }

    public static void playerTurn() {
        Scanner sc = new Scanner(System.in);
        int row = -1;
        int col = -1;
        while (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != empty) {
            System.out.println("Enter row");
            row = sc.nextInt()-1;
            System.out.println("Enter Col");
            col = sc.nextInt()-1;
            if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != empty) {
                System.out.println("Please Enter Valid Location...");
            }
        }
        board[row][col] = playerSymbol;
        drawBoard();
        if (checkWin(playerSymbol))
            System.out.println("Player Won!!!!!!!");
        else if (isTie()) {
            System.out.println("It's a Tie!!");
        } else
            computerTurn();
    }

    public static void computerTurn() {
        int bestChoice[] = { -1, -1 };
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == empty) {
                    board[i][j] = computerSymbol;
                    int score = minMax(false);
                    board[i][j]=empty;
                    if (score > bestScore) {
                        bestScore = score;
                        bestChoice[0] = i;
                        bestChoice[1] = j;
                    } 
                }
            }
        }
        
        board[bestChoice[0]][bestChoice[1]] = computerSymbol;
        drawBoard();

        if (checkWin(computerSymbol)) {
            System.out.println("COMPUTER WON");
        } else if (isTie()) {
            System.out.println("Draw");
        } else {
            playerTurn();
        }
    }

    public static int minMax(boolean maximizing) {
        int score = 0;
        if (checkWin(computerSymbol))
            score=1;
        else if (checkWin(playerSymbol))
            score= -1;
        else if (isTie())
            score=0;
        else {
            int bestScore;
            if (maximizing) {
                bestScore = Integer.MIN_VALUE;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == empty) {
                            board[i][j] = computerSymbol;
                            int tempscore = minMax(false);
                            bestScore = Math.max(tempscore, bestScore);
                            board[i][j] = empty;
                        }
                    }
                }
            }
            else{
                bestScore=Integer.MAX_VALUE;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == empty) {
                            board[i][j] = playerSymbol;
                            int tempscore = minMax(true);
                            bestScore = Math.min(tempscore, bestScore);
                            board[i][j] = empty;
                        }
                    }
                }
            }
            score=bestScore;
        }
        return score;
    }

    
    public static void main(String args[]) {
        for (char[] row : board)
            Arrays.fill(row, empty);
        drawBoard();
        playerTurn();
    }
}