import java.util.*;
class Nqueen{
    private static int N;
    private static void print(int[][] board)
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                System.out.print(board[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static boolean issafe(int[][] board,int row,int col)
    {
        int i,j;
        for(i=0;i<col;i++)
        {
            if(board[row][i]==1)
            return false;

        }
        for(i=row,j=col;i>=0 && j>=0;i--,j--)
        {
            if(board[i][j]==1)
            return false;
        }
        for(i=row,j=col;i<N&& j>=0;i++,j--)
        {
            if(board[i][j]==1)
            {
                return false;
            }
        }
        return true;
    }
    public static boolean SolveNQutil(int[][] board,int col)
    {
        if(col>=N)
        {
            print(board);
            return true;
        }
        boolean res=false;
        for(int i=0;i<N;i++)
        {
            if(issafe(board,i,col))
            {
                board[i][col]=1;
                res=SolveNQutil(board,col+1) || res;
                board[i][col]=0;
            }
        }
        return res;
    }
    public static void SolveNq(int n)
    {
        N=n;
        int[][]  board=new int[N][N];
        if(!SolveNQutil(board,0))
        {
            System.out.println("solution is not exit");
        }

    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the queen value: ");
        int number=sc.nextInt();
        SolveNq(number);
    }
}