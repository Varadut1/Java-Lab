import java.util.*;
public class AStar {
    static int[][] maze={
        {0,0,0,1,1,1},
        {0,0,1,1,1,0},
        {0,0,0,1,0,1},
        {1,1,0,0,0,0},
        {0,1,0,0,0,1},
        {0,1,1,0,0,0}
    };
    static int numRows=maze.length;
    static int numCols=maze[0].length;
    static boolean isValid(int row,int col)
    {
        return (row>=0 && row<numRows && col>=0 && col<numCols && maze[row][col]==0);
    }
    static boolean Astar(int startRow,int startCol,int endRow,int endCol)
    {
        PriorityQueue<int[]> openlist=new PriorityQueue<>(Comparator.comparingInt(a->fScore[a[0]][a[1]]));
        openlist.add(new int[]{startRow,startCol});
        int[][]parent=new int[numRows][numCols];
        for(int i=0;i<numRows;i++)
        {
            for(int j=0;j<numCols;j++)
            {
                parent[i][j]=-1;
            }
        }
        while(!openlist.isEmpty())
        {
            int[] current=openlist.poll();
            int row=current[0];
            int col=current[1];
            if(row==endRow && col==endCol)
            {
                printPath(parent,endRow,endCol);
                return true;
            }
            if(isValid(row,col))
            {
                maze[row][col]=2;
                int[][] directions={{0,1},{0,-1},{1,0},{-1,0}};
                for(int[] dir:directions)
                {
                    int newrow=row+dir[0];
                    int newcol=col+dir[1];
                    if(isValid(newrow,newcol))
                    {
                        int tentitiveGscore=gScore[row][col]+1;
                        if(tentitiveGscore < gScore[newrow][newcol])
                        {
                            parent[newrow][newcol]=row*numCols+col;
                            gScore[newrow][newcol]=tentitiveGscore;
                            fScore[newrow][newcol]=tentitiveGscore+heuristic(newrow,newcol,endRow,endCol);
                            openlist.add(new int[]{newrow,newcol});
                        }
                    }
                }
            
        }
    }
    return false;
}
static void printPath(int[][] parent,int row,int col)
{
    Stack<Integer> path=new Stack<>();
    int current=row*numCols+col;
    while(current!=-1)
    {
        path.push(current);
        int prevRow=current/numCols;
        int prevCol=current%numCols;
        current=parent[prevRow][prevCol];
    }
    System.out.println("Path is exit");
    while(!path.isEmpty())
    {
        int position=path.pop();
        int r=position/numCols;
        int c=position%numCols;
        System.out.print("(" + r+ "," + c+ ")");
        if(!path.isEmpty())
        {
            System.out.print(" -> ");
        }
    }
    System.out.println();
}
    static int[][] gScore;
    static int[][] fScore;
    static int heuristic(int x1,int y1,int x2,int y2)
    {
        return (int) ((int)Math.pow(x2-x1,2)+ Math.pow(y2-y1,2));
    }
    public static void main(String[] args) {
        int startRow=0;
        int startCol=0;
        int endRow=numRows-1;
        int endCol=numCols-1;
        gScore=new int[numRows][numCols];
        fScore=new int[numRows][numCols];
        for(int i=0;i<numRows;i++)
        {
            Arrays.fill(gScore[i],Integer.MAX_VALUE);
            Arrays.fill(fScore[i],Integer.MAX_VALUE);
        }
        gScore[startRow][startCol]=0;
        fScore[startRow][startCol]=heuristic(startRow,startCol,endRow,endCol);
        if(Astar(startRow,startCol,endRow,endCol))
        {
            System.out.println("Congratulation path is found");
        }
        else{
            System.out.println("path is not exit");
        }
    }
}
