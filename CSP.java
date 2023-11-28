public class CSP{
 
    public static void main(String[] args) {
        int n=5;
        boolean[][] board=new boolean[n][n];
        System.out.println(queens(board,0));
    }
    public static int queens(boolean[][]board, int row){
        if(row==board.length){
            System.out.println();
            displayPuzzle(board);
            return 1;
        }
        else{
            int count=0;
            for(int col=0;col<board.length;col++){
                if(isSafe(board,row,col)){
                    board[row][col]=true;
                    count+=queens(board, row+1);
                    board[row][col]=false;
                }
            }
            return count;
        }
    }
    public static boolean isSafe(boolean board[][],int row, int col){
        //vertical
        for(int i=0;i<row;i++){
            if(board[i][col]==true)
                return false;
        }
        //diagonal left
        int maxLeft = Math.min(row,col);
        for(int i=1;i<=maxLeft;i++){
            if(board[row-i][col-i]==true)
                return false;
        }
        //diagonal right
        int maxRight=Math.min(row,board.length-col-1);
        for(int i=1;i<=maxRight;i++){
            if(board[row-i][col+i]==true)
                return false;
        }
        return true;
    }

    public static void displayPuzzle(boolean [][]board){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j])
                    System.out.print("Q ");
                else    
                    System.out.print("X ");
            }
            System.out.println();
        }
    }
}