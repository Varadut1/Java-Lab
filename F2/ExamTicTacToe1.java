import java.util.Scanner;
public class ExamTicTacToe1 {
    char[][] board;
    char currentPlayer;
    String playerName;
    public ExamTicTacToe1(String playerName)
    {
        board=new char[3][3];
        currentPlayer='X';
        this.playerName=playerName;
        initilizeBoard();
    }
    public void initilizeBoard()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                board[i][j]='-';
            }
        }
    }
    public String getPlayername()
    {
        return playerName;
    }
    public void displayPlayername()
    {
        System.out.println("player name is: "+playerName);
    }
    public boolean isBoardFull()
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
    public void updateHeuristic(int row,int col)
    {
        int[][] manhattanDistance={
            {0,1,2},
            {1,2,1},
            {2,1,0}
        };
        int ScoreO=0;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j]=='O')
                {
                ScoreO+=manhattanDistance[i][j];
            }
        }
        }
        int AiScore=evaluateManhattan();
        AiScore-=ScoreO;
    }
    public int evaluateManhattan()
    {
        int ScoreX=0;
        int ScoreO=0;
        int [][] manhattanDistance={
            {0,1,2},
            {1,2,1},
            {2,1,0}
        };
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j]=='X')
                {
                    ScoreX+=manhattanDistance[i][j];
                }
                if(board[i][j]=='O')
                {
                    ScoreO+=manhattanDistance[i][j];
                }
            }
        }
        return ScoreX-ScoreO;
    }
    public int minimax(int depth,boolean isMaximize)
    {
        if(checkWin('X'))
        {
            return 10-depth;

        }
        if(checkWin('O'))
        {
            return depth-10;
        }
        if(isBoardFull())
        {
            return 0;
        }
        int bestScore;
        if(isMaximize)
        {
            bestScore=Integer.MIN_VALUE;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(board[i][j]=='-')
                    {
                        board[i][j]='X';
                        int score=minimax(depth+1,false);
                        board[i][j]='-';
                        bestScore=Math.max(score,bestScore);
                    }
                }
            }
        }
        else{
            bestScore=Integer.MAX_VALUE;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(board[i][j]=='-')
                    {
                        board[i][j]='O';
                        int score=minimax(depth+1,true);
                        board[i][j]='-';
                        bestScore=Math.min(score,bestScore);
                    }
                }
            }
        }
        if(bestScore==Integer.MAX_VALUE || bestScore==Integer.MIN_VALUE)
        {
            return 0;
        }
        return bestScore;
    }
    public void makemove()
    {
        int bestScore=Integer.MIN_VALUE;
        int bestrow=-1;
        int bestcol=-1;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j]=='-')
                {
                    board[i][j]='X';
                    int score=minimax(0,false);
                    board[i][j]='-';
                    if(score>bestScore)
                    {
                        bestScore=score;
                        bestrow=i;
                        bestcol=j;
                    }
                }
            }
        }
        board[bestrow][bestcol]='X';
    }
    public void play()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("you are O and AI is X");
        System.out.println("here is the initial board");
        printBoard();
        while(true)
        {
            if(checkWin('X'))
            {
                System.out.println("AI is win, better luck next time");
                break;
            }
            else if(checkWin('Y'))
            {
                System.out.println(getPlayername()+" is winner");
                break;
            }
            else if(isBoardFull())
            {
                System.out.println("Game is draw");
                break;
            }
            System.out.println("Enter row and col(0-2): ");
            int row=sc.nextInt();
            int col=sc.nextInt();
            if(row <0 || row >2 || col <0 || col>2 || board[row][col]!='-')
            {
                System.out.println("Invalid move, please try again");
                continue;
            }
            board[row][col]='O';
            printBoard();
            updateHeuristic(row,col);
            if(!checkWin('X')&& !isBoardFull())
            {
                makemove();
                System.out.println("AI move");
                printBoard();
                int Aiscore=evaluateManhattan();
                System.out.println("AI Score= "+Aiscore);
            }
        }
    }
    public void printBoard()
    {
        System.out.println("-------------");
        for(int i=0;i<3;i++)
        {
            System.out.print("| ");
            for(int j=0;j<3;j++)
            {
                System.out.print(board[i][j]+" | ");

            }
            System.out.println();
            System.out.println("-------------");
        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the your name: ");
        String playerName=sc.nextLine();
        ExamTicTacToe1 game=new ExamTicTacToe1(playerName);
        game.displayPlayername();
        game.play();
    }
}
