//Iterative deepning or dfs

import java.util.*;

class State{
    int[][] board;
    State parent;
    int zr;
    int zc;
    String action;
    public State(int[][] board, State parent, int zr, int zc, String action){
        this.board = board;
        this.parent = parent;
        this.zr = zr; 
        this.zc = zc;
        this.action = action;
    }
    void display(){
        for(int i = 0 ; i < 3; i++){
            for(int j = 0; j< 3; j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }
}

public class dfs {
    static HashSet<String> visited = new HashSet<>();
    static HashMap<State, State> path = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    static Stack<State> stack = new Stack<>();
    static int [][] initial = new int[3][3];
    static int[][] goal = new int[3][3];
    public static void main(String[] args){
        System.out.println("Enter the present board ");
        int zr = 0;
        int zc = 0;
        for(int i = 0; i<3; i++){
            for(int j=0; j<3; j++){
                initial[i][j] = input.nextInt();
                if(initial[i][j] == 0){
                    zr = i;
                    zc = j;
                }
            }
        }

        System.out.println("Enter the goal board");
        for(int i = 0; i<3; i++){
            for(int j=0; j<3; j++){
                goal[i][j] = input.nextInt();
            }
        }
        State initialstate = new State(initial, null, zr, zc, "Root Node");
        State finalstate = null;
        stack.push(initialstate);
        boolean foundgoal = false;
        int level = 0;
        int idslimit = 8;   // Enter the limit for ids
        visited.add(Arrays.deepToString(initialstate.board));
        while(!stack.isEmpty()){
            State vertex = stack.pop();
            
            if(Arrays.deepEquals(vertex.board, goal)){
                finalstate = vertex;
                foundgoal = true;
                break;
            }
            
            if(level<idslimit){
                System.out.println("Level -> " + level);
                ArrayList<State> neigbourings = neighbours(vertex);
                boolean remaining = false;
                for(State i : neigbourings){
                    if(!visited.contains(Arrays.deepToString(i.board))){
                        remaining = true;
                        stack.push(vertex);
                        stack.push(i);
                        visited.add(Arrays.deepToString(i.board));
                        level++;
                        path.put(i, vertex);
                        break;
                    }
                }
                if(!remaining){
                    level--;
                }
            }
            else{
                System.out.println("Limit reached");
                level--;
            }
        }

        if(foundgoal){
            State counter = finalstate;
            Stack<State> stack = new Stack<>();
            while(initialstate!=counter){
                stack.push(counter);
                counter = path.get(counter);
            }
            stack.push(counter);
            while(!stack.isEmpty()){
                State newstate = stack.pop();
                newstate.display();
                System.out.println("You "+newstate.action);
                System.out.println();
            }
        }
        else{
            System.out.println("Goal state unreachable with given depth");
        }

    }

    static ArrayList<State> neighbours(State state){
        ArrayList<State> neighbouring = new ArrayList<>();
        int[][] position = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int i=0; i<4; i++){
            int newzeror = state.zr + position[i][0];
            int newzeroc = state.zc + position[i][1];
            int[][] newboard = new int[3][3];
            if(newzeror<=2 && newzeror>=0 && newzeroc<=2 && newzeroc>=0){
                for(int row = 0; row<3; row++){
                    for(int col=0; col<3; col++){
                        if(row == newzeror && col == newzeroc){
                            newboard[row][col] = 0;
                        }
                        else if(row == state.zr && col== state.zc){
                            newboard[row][col] = state.board[newzeror][newzeroc];
                        }
                        else{
                            newboard[row][col] = state.board[row][col];
                        }
                    }
                }
                State newstate;
                if(newzeror<state.zr){
                    newstate = new State(newboard, state, newzeror, newzeroc, "Moved Up");
                }
                else if(newzeror>state.zr){
                    newstate = new State(newboard, state, newzeror, newzeroc, "Moved Down");
                }
                else if(newzeroc<state.zc){
                    newstate = new State(newboard, state, newzeror, newzeroc, "Moved Left");
                }
                else{
                    newstate = new State(newboard, state, newzeror, newzeroc, "Moved right");
                }
               
                neighbouring.add(newstate);
            }
        }
        return neighbouring;
    }
    
}
