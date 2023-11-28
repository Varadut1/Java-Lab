import java.util.*;

public class minmax2 {
    static char[][] board = new char[3][3];
    static char player='X';
    static char computer='O';
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args){
        display();
        while(true){

            playerturn();
            display();
            if(win('X')){
                System.out.println("X Wins!");
                break;
            }
            if(tie()){
                System.out.println("Game Draw!");
                break;
            }

            computerturn();
            display();
            if(win('O')){
                System.out.println("O Wins!");
                break;
            }
            if(tie()){
                System.out.println("Game Draw!");
                break;
            }
        }
    }
    static void playerturn(){
        System.out.println("Your turn");
        System.out.println("Enter row col: ");
        int row = input.nextInt();
        int col = input.nextInt();
        if(row>3 || col>3 || row <1 || col<1){
            System.out.println("Enter valid");
            playerturn();
        }
        board[row-1][col-1]=player;
    }

    static void computerturn(){
        System.out.println("Please wait for AI turn.....");
        try{
            Thread.sleep(1500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        int bestscore = Integer.MIN_VALUE;
        int[] move = {-1, -1};
        for(int i = 0; i< 3; i++){
            for(int j = 0; j<3; j++){
                if(board[i][j]=='\0'){
                    board[i][j] = computer;
                    int score = minmax(false);
                    board[i][j] = '\0';
                    if(score > bestscore){
                        bestscore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        board[move[0]][move[1]] = computer;
    }
    static int minmax(boolean ismaximizing){
        if(win('X')) 
            return -1;
        if(win('O')) 
            return 1;
        if(tie()) 
            return 0;
        else{
            int bestscore;
            if(ismaximizing){
                bestscore = Integer.MIN_VALUE;
                for(int i = 0; i< 3; i++){
                    for(int j = 0; j<3; j++){
                        if(board[i][j]=='\0'){
                            board[i][j] = computer;
                            int score = minmax(false);
                            bestscore = Math.max(score, bestscore);
                            board[i][j] = '\0';
                        }
                    }
                }
                return bestscore;
            }
            else{
                bestscore = Integer.MAX_VALUE;
                for(int i = 0; i< 3; i++){
                    for(int j = 0; j<3; j++){
                        if(board[i][j]=='\0'){
                            board[i][j] = player;
                            int score = minmax(true);
                            bestscore = Math.min(score, bestscore);
                            board[i][j] = '\0';
                        }
                    }
                }
                return bestscore;
            }
        }
    }


    
    static boolean win(char symbol){
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
