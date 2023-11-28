import java.util.Scanner;
public class ExamTicTacToe {
    char[][] board;
    char currentplayer;
    String playername;
    public ExamTicTacToe(String playername)
    {
        board=new char[3][3];
        this.currentplayer='X';
        this.playername=playername;
        initialBoard();
    }
    void initialBoard()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                board[i][j]='-';
            }
        }
    }
    public String getplayername()
    {
        return playername;
    }
    public void displayplayername()
    {
        System.out.println("playername: "+playername);
    }
    public boolean isBoardfull()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j]=='-')
                return false;
            }
        }
        return true;
    }
    public boolean checkWin(char player)
    {
        for(int i=0;i<3;i++)
        {
            if(board[i][0]==player && board[i][1]==player && board[i][2]==player)
            {
                return true;
            }
        }
        for(int i=0;i<3;i++)
        {
            if(board[0][i]==player && board[1][i]==player && board[2][i]==player)
            {
                return true;
            }
        }
        if(board[0][0]==player && board[1][1]==player && board[2][2]==player)
        {
            return true;
        }
        if(board[0][2]==player && board[1][1]==player && board[2][0]==player)
        {
            return true;
        }
      return false;
    }
    void play()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Tic Tac Toe game - you are X and i am O");
        System.out.println("here is the initial board");
        printBoard();
        while(true)
        {
            if(checkWin('X'))
            {
                System.out.println("AI is winner, betterluck next time");
                break;
            }
            else if(checkWin('O'))
            {
                System.out.println("Congratulation "+getplayername()+" is winner");
            }
            else if(isBoardfull())
            {
                System.out.println("game is draw");
            }
        }
    }
    void printBoard()
    {
        System.out.println("-------------");
        for(int i=0;i<3;i++)
        {
            System.out.print("| ");
            for(int j=0;j<3;j++)
            {
                System.out.print(board[i][j]+" |");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter our name: ");
        String playername=sc.nextLine();
        ExamTicTacToe  game=new ExamTicTacToe(playername);
        game.displayplayername();
        game.play();
    }
}
