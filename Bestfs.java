import java.util.*;

class State{
    int [][] board;
    int zr;
    int zc;
    State parent;
    String action;
    int hn;
    State(int[][] board, int zr, int zc, State parent, String action, int hn){
        this.board= board;
        this.zr = zr; 
        this.zc = zc;
        this.parent = parent;
        this.action = action;
        this.hn = hn;
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

class Node{
    State state;
    Node next;
    Node(State state){
        this.state = state;
    }
}

public class Bestfs{
    static Scanner input = new Scanner(System.in);
    static int[][] initial = new int[3][3];
    static int[][] goal = new int[3][3];;
    static Node head; 
    static HashMap<State, State> path = new HashMap<>();
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
        State initialstate = new State(initial, zr, zc, null, "Root Node", heuristic(initial));

        head = new Node(initialstate);
        Node temp = head;
        boolean foundgoal = false;
        State goalstate = null;
        while(head != null){
            temp = head;
            head = head.next;
            System.out.println("Vertex");
            temp.state.display();
            System.out.println(temp.state.hn);
            ArrayList<State> neighbours = neighbours(temp.state);
            if(Arrays.deepEquals(temp.state.board, goal)){
                System.out.println("Goal reached");
                foundgoal = true;
                goalstate = temp.state;
                break;
            }
            for(State i : neighbours){
                if(temp.state.parent != null && Arrays.deepEquals(temp.state.parent.board, i.board)){
                    continue;
                }
                System.out.println("Child");
                i.display();
                System.out.println("heur: "+i.hn);
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
                System.out.println(itr.action+" with heuristic -> "+itr.hn);
            }
        }
        else{
            System.out.println("Goal state could not be found!");
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
                    newstate = new State(newboard,  newzeror, newzeroc, state, "Moved Up", heuristic(newboard));
                }
                else if(newzeror>state.zr){
                    newstate = new State(newboard,  newzeror, newzeroc, state, "Moved Down", heuristic(newboard));
                }
                else if(newzeroc<state.zc){
                    newstate = new State(newboard,  newzeror, newzeroc, state, "Moved Left", heuristic(newboard));
                }
                else{
                    newstate = new State(newboard,  newzeror, newzeroc, state, "Moved right", heuristic(newboard));
                }
               
                neighbouring.add(newstate);
            }
        }
        return neighbouring;
    }

    static void insert(State newstate){
        Node temp = head;
        while(temp != null){
            if(Arrays.deepEquals(temp.state.board, newstate.board)){
                return;
            }
            temp = temp.next;
        }
        if(head == null){
            head = new Node(newstate);
            return;
        }
        System.out.println("----Matching----");
        System.out.println(head.state.hn+" "+newstate.hn);
        if(head.state.hn >= newstate.hn){
            Node tempo = new Node(newstate);
            tempo.next = head;
            head = tempo;
            return;
        }
        else{
            temp = head;
            Node early = temp;
            while(temp.next!=null && temp.state.hn<=newstate.hn){
                early = temp;
                temp = temp.next;
            }
            Node tempo = new Node(newstate);
            tempo.next = temp;
            early.next = tempo;
            return;
        }

    }

    static int heuristic(int[][] board){
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