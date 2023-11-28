import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

class State{
    int [][] board;
    int zr;
    int zc;
    State parent;
    String action;
    int depth;
    int fn;
    State(int [][] board, int zr, int zc, State parent, String action, int depth, int fn){
        this.board = board;
        this.zr = zr;
        this.zc =zc;
        this.parent = parent;
        this.action = action;
        this.depth = depth;
        this.fn = fn;
    }
    void display(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }
}

public class Astar {
    static Scanner input = new Scanner(System.in);
    static int[][] initial = new int[3][3];
    static int[][] goal = new int[3][3];;
    static Node head; 
    static HashMap<State, State> path = new HashMap<>();
    static HashSet<State> visited = new HashSet<>();
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
        boolean foundgoal = false;
        State goalstate = null;
        State initialstate = new State(initial, zr, zc, null, "Root Node", 0, function(initial));
        head = new Node(initialstate);
        visited.add(initialstate);
        while(head != null){
            Node temp = head;
            head = head.next;
            if(Arrays.deepEquals(temp.state.board, goal)){
                foundgoal = true;
                goalstate = temp.state;
                break;
            }
            visited.add(temp.state);
            ArrayList<State> neighbouring = neighbours(temp.state);
            for(State i : neighbouring){
                if(temp.state.parent != null && Arrays.deepEquals(temp.state.parent.board, i.board)){
                    continue;
                }
                insert(i);
                path.put(i, temp.state);
            }
        }
        if(foundgoal){
            Stack<State> p1 = new Stack<>();
            State itr = goalstate;
            while(path.get(itr)!=null){
                p1.push(itr);
                itr = path.get(itr);
            }
            p1.push(itr);
            while(!p1.isEmpty()){
                itr = p1.pop();
                itr.display();
                System.out.println(itr.action+" with f(n) -> "+itr.fn);
            }
        }
        else{
            System.out.println("Goal state could not be found!");
        }
    }

    static void insert(State newstate){
        Node temp = head;
        for(State i : visited){
            if(Arrays.deepEquals(i.board, newstate.board)){
                if(i.fn >= newstate.fn){
                    visited.remove(i);
                }
            }
        }
        // if(Arrays.deepEquals(temp.next.state.board, newstate.board) && temp.next.state.fn >= newstate.fn){
        //     temp.next = temp.next.next;
        // }
        // while(temp.next != null){
        //     if(Arrays.deepEquals(temp.next.state.board, newstate.board) && temp.next.state.fn >= newstate.fn){
        //         temp.next = temp.next.next;
        //     }
        // }
        if(head == null || head.state.fn>newstate.fn){
            Node tempo = new Node(newstate);
            tempo.next = head;
            head = tempo;
            return;
        }
        else{
            temp = head;
            while(temp.next != null && temp.next.state.fn <= newstate.fn){
                temp = temp.next;
            }
            Node tempo = new Node(newstate);
            tempo.next = temp.next;
            temp.next = tempo;
            return;
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
                    newstate = new State(newboard,  newzeror, newzeroc, state, "Moved Up", function(newboard), state.depth+1);
                }
                else if(newzeror>state.zr){
                    newstate = new State(newboard,  newzeror, newzeroc, state, "Moved Down", function(newboard), state.depth+1);
                }
                else if(newzeroc<state.zc){
                    newstate = new State(newboard,  newzeror, newzeroc, state, "Moved Left", function(newboard), state.depth+1);
                }
                else{
                    newstate = new State(newboard,  newzeror, newzeroc, state, "Moved right", function(newboard), state.depth+1);
                }
               
                neighbouring.add(newstate);
            }
        }
        return neighbouring;
    }

    static int function(int[][] board){
        int heuristic = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                for(int row = 0; row<3; row++){
                    for(int col = 0; col<3; col++){
                        if(goal[i][j] == board[row][col]){
                            heuristic = heuristic + Math.abs(row - i) + Math.abs(col - j);
                        }
                    }
                }
            }
        }
        return heuristic;
    }
}
