import java.util.*;
public class EightPuzzlegame1 {
    private static final int[][] GoalState={{1,2,3},{4,5,6},{7,8,0}};
    private int[][] initialState;
    private int[][] currentState;
    private int blankRow,blankCol;
    public EightPuzzlegame1(int[][] state)
    {
        this.initialState=state;
        this.currentState=state;
        this.blankRow=0;
        this.blankCol=0;

    }
    public boolean isGoalState(int[][] state)
    {
        return Arrays.deepEquals(state,GoalState);
    }
    public void findBlankTile()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(currentState[i][j]==0)
                {
                    blankRow=i;
                    blankCol=j;
                    return;
                }
            }
        }
    }
    public List<int[][]> getSuccessor(int[][] state)
    {
        List<int[][]> Successor=new ArrayList<>();
        int[] dx={-1,1,0,0};
        int[] dy={0,0,-1,1};
        for(int i=0;i<4;i++)
        {
            int newRow=blankRow+dx[i];
            int newCol=blankCol+dy[i];
            if(newRow>=0 && newRow<3 && newCol>=0 && newCol<3)
            {
                int[][] newState=new int[3][3];
                for(int r=0;r<3;r++)
                {
                    for(int c=0;c<3;c++)
                    {
                        newState[r][c]=state[r][c];
                    }
                    
                }
                newState[blankRow][blankCol]=newState[newRow][newCol];
                    newState[newRow][newCol]=0;
                    Successor.add(newState);
            }
        }
        return Successor;
    }
    public List<int[][]> bfs()
    {
        Queue<int[][]> queue=new LinkedList<>();
        Map<String,Boolean> Visited=new HashMap<>();
        Map<String,int[][]> parentMap=new HashMap<>();
        String initialstateString=Arrays.deepToString(initialState);
        Visited.put(initialstateString,true);
        queue.add(initialState);
        while(!queue.isEmpty())
        {
            int[][] current=queue.poll();
            if(isGoalState(current))
            {
              return reconstructionPath(parentMap,current);
            }
            currentState=current;
            findBlankTile();
            List<int[][]> successors=getSuccessor(current);
            for(int[][] successor:successors)
            {
                String successorString=Arrays.deepToString(successor);
                if(!Visited.containsKey(successorString))
                {
                    Visited.put(successorString,true);
                    parentMap.put(successorString,current);
                    queue.add(successor);
                }
            }
        }
        return null;
    }
    public List<int[][]> reconstructionPath(Map<String,int[][]> parentMap,int[][] goalState)
    {
        List<int[][]> path=new ArrayList<>();
        String currentStateString=Arrays.deepToString(goalState);
        while(currentStateString!=null)
        {
            int[][] current=parentMap.get(currentStateString);
            if(current!=null)
            {
                path.add(0,current);
                currentStateString=Arrays.deepToString(current);
            }
            else{
                path.add(0,goalState);
                currentStateString=null;
            }
        }
        return path;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int move=0;
        int[][] initialState=new int[3][3];
        System.out.println("Enter the initial state of 8 puzzle(0 for null):");
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                initialState[i][j]=sc.nextInt();
            }
        }
        EightPuzzlegame1 game=new EightPuzzlegame1(initialState);
        List<int[][]> solutionPath;
        System.out.println("Enter the choose of search: ");
        System.out.println("1->bfs and 2->dfs");
        int choose=sc.nextInt();
        switch(choose)
        {
            case 1:
                solutionPath=game.bfs();
                break;
            // case 2:
            //     solutionPath=game.dfs();
            //     break;
            default:
                System.out.println("Invalid choice, plz try again");
                return;
        }
        if (solutionPath != null) {
            System.out.println("Solution found!");
            for (int[][] state : solutionPath) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        System.out.print(state[i][j] + " ");
                    }
                    System.out.println();
                    
                }
                move++;
                System.out.println();
                System.out.println("Move " + move);
                
            }
        } else {
            System.out.println("No solution found!");
        }

        
    }
}
