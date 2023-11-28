import java.util.*;

public class minmax{
    static Scanner input = new Scanner(System.in);
    static char user = 'X';
    static char computer = 'O';
    static char[][] board = new char[3][3];
    public static void main(String[] args){
        for(int i=0; i<5; i++){
            if(!wins('O')){
                System.out.println("Enter row: ");
                int row = input.nextInt();
                System.out.println("Enter col: ");
                int col = input.nextInt();
                board[row][col] = user;
                display();
                if(wins('X')){
                    System.out.println("X wins!");
                    return;
                }
                if(tie()) break;
                System.out.println("AI chance");
                bestmove(board);

                display();
                if(tie()) break;
            }
            else{
                System.out.println("O wins!");
                return;
            }
        }
        System.out.println("It was a draw!");
    }


    static void bestmove(char[][] board){
        double bestscore = Double.NEGATIVE_INFINITY;
        int[] move = {-1, -1};
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(board[i][j]=='\0'){
                    board[i][j]=computer;
                    double score = minimax(board, false, 0);
                    System.out.println(score);
                    board[i][j]='\0';
                    if(score>bestscore){
                        bestscore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        board[move[0]][move[1]] = computer;
    }


    static double minimax(char[][] board, boolean maximizing_player, int depth){
        if(wins('X')){
            return -1;
        }
        if(wins('O')){
            return 1;
        }
        if(tie()){
            return 0;
        }
        
        if(maximizing_player){
            double bestscore = Double.NEGATIVE_INFINITY;
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    if(board[i][j]=='\0'){
                        board[i][j]=computer;
                        double score = minimax(board, false, depth+1);
                        // System.out.println(score);
                        bestscore = Math.max(score, bestscore);
                        board[i][j]='\0';
                    }
                }
            }
            return bestscore;
        }

        else{
            double bestscore = Double.POSITIVE_INFINITY;
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    if(board[i][j]=='\0'){
                        board[i][j]=user;
                        double score = minimax(board, true, depth+1);
                        // System.out.println(score);
                        bestscore = Math.min(score, bestscore);
                        board[i][j]='\0';
                    }
                }
            }
            return bestscore;
        }
    }

    static boolean wins(char symbol){
        boolean win = false;
        for(int i=0; i<3; i++){
            if(board[i][0]==symbol && board[i][1]==symbol && board[i][2]==symbol){
                win = true;
            }
        }
        for(int i=0; i<3; i++){
            if(board[0][i]==symbol && board[1][i]==symbol && board[2][i]==symbol){
                win = true;
            }
        }
        if(board[0][0]==symbol && board[1][1]==symbol && board[2][2]==symbol){
            win = true;
        }
        if(board[0][2]==symbol && board[1][1]==symbol && board[2][0]==symbol){
            win = true;
        }
        return win;
    }
    static boolean tie(){
        for(int i = 0; i< 3; i++){
            for(int j = 0; j<3; j++){
                if(board[i][j]=='\0'){
                    return false;
                }
            }
        }
        return true;
    }
    static void display(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(board[i][j] == '\0'){
                    System.out.print("| "+"_"+" ");
                }
                else{
                    System.out.print("| "+board[i][j]+" ");
                }
                
            }
            System.out.println(" |");
            System.out.println("--------------");
        }
    }

}