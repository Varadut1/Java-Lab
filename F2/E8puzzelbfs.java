import java.util.*;

class PuzzleNode{
    int[][]puzzleState;
    PuzzleNode parent;
    int zeroRow;
    int zeroCol;
    int heuristic=0;

    public PuzzleNode(int[][] puzzleState, PuzzleNode node, int zeroRow, int zeroCol){
        this.puzzleState=puzzleState;
        this.parent=node;
        this.zeroRow=zeroRow;
        this.zeroCol=zeroCol; 
    }
}

public class E8puzzelbfs{
    static int [][] initial=new int[3][3];
    static int [][] goal=new int[3][3];
    static PuzzleNode root;

    public static int[] findZero(int[][]puzzleState){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(puzzleState[i][j]==0)
                    return new int[] {i,j};
            } 
        }
        return new int[] {-1,-1};
    }

    public static boolean isGoalState(int[][]currentState,int[][]goalState){
        return Arrays.deepEquals(currentState, goalState);
    }

    public static void displayPuzzle(int[][] node){
        System.out.println("");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(node[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("");
    }

    public static void calculateHeurstic(PuzzleNode node){
        int heuristic=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(node.puzzleState[i][j]!=goal[i][j])
                    heuristic++;
            }
        }
        node.heuristic=heuristic;
    }

    public static void solvePuzzle(){
        PriorityQueue<PuzzleNode> queue= new PriorityQueue<>(Comparator.comparingInt(node->node.heuristic));
        Set<String> set=new HashSet<>();
        int []zero=findZero(initial);
        PuzzleNode root=new PuzzleNode(initial, null,zero[0], zero[1]);
        calculateHeurstic(root);
        queue.add(root);
        while(!queue.isEmpty()){
            PuzzleNode currNode=queue.poll();
            displayPuzzle(currNode.puzzleState); 
            if(isGoalState(currNode.puzzleState, goal)){
                System.out.println("Goal State found!!!!!");
                printSolution(currNode);
                return;
            }
            ArrayList<PuzzleNode> neighbours=generateNeighbours(currNode);
            for(PuzzleNode neighbour: neighbours){
                // if(!set.contains(Arrays.deepToString(neighbour.puzzleState))){
                //     set.add(Arrays.deepToString(neighbour.puzzleState));
                    queue.add(neighbour);
                // }
            }
        }
    }
    public static void printSolution(PuzzleNode node){
        // String ans;
        StringBuilder ans=new StringBuilder();
        while(node.parent!=null){
            int rowChange=node.parent.zeroRow-node.zeroRow;
            int colChange=node.parent.zeroCol-node.zeroCol;
            if(rowChange==1)
                ans.append("U");
            else if(rowChange==-1)
                ans.append("D");
            else if(colChange==1)
                ans.append("L");
            else
                ans.append("R");
            node=node.parent;
        }
        System.out.println(ans.reverse());
    }
    public static ArrayList<PuzzleNode> generateNeighbours(PuzzleNode node){
        ArrayList<PuzzleNode> newNeighbours=new ArrayList<>();
        int[][] moves={{1,0},{-1,0},{0,1},{0,-1}};
        for(int[]move: moves){
            if(node.zeroRow+move[0]<3 && node.zeroRow+move[0]>=0 && node.zeroCol+move[1]<3 && node.zeroCol+move[1]>=0){
                int[][] neighbour=new int[3][3];
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        neighbour[i][j]=node.puzzleState[i][j];
                    }
                }
                neighbour[node.zeroRow][node.zeroCol]=neighbour[node.zeroRow+move[0]][node.zeroCol+move[1]];
                neighbour[node.zeroRow+move[0]][node.zeroCol+move[1]]=0;
                PuzzleNode newNode=new PuzzleNode(neighbour, node, node.zeroRow+move[0],node.zeroCol+move[1]);
                calculateHeurstic(newNode);
                newNeighbours.add(newNode);
                // displayPuzzle(neighbour);
            }
        }
        return newNeighbours;
    }
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the initial State: ");
        // int k=1;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                initial[i][j]=sc.nextInt();
                // initial[i][j]=k++;
            }
        }
        // initial[1][1]=0;
        // k=9;
        System.out.println("Enter the goal State: ");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                // goal[i][j]=k--;
                goal[i][j]=sc.nextInt();
            }
        }
        // goal[0][0]=0;
        displayPuzzle(initial);
        solvePuzzle();
    }
}
