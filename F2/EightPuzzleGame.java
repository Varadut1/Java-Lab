import java.util.*;

public class EightPuzzleGame {
    private static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private int[][] initialState;
    private int[][] currentState;
    private int blankRow, blankCol;

    public EightPuzzleGame(int[][] initialState) {
        this.initialState = initialState;
        this.currentState = initialState;
        this.blankRow = 0;
        this.blankCol = 0;
    }

    private boolean isGoalState(int[][] state) {
        return Arrays.deepEquals(state, GOAL_STATE);
    }

    private List<int[][]> getSuccessors(int[][] state) {
        List<int[][]> successors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = blankRow + dx[i];
            int newCol = blankCol + dy[i];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int[][] newState = new int[3][3];
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        newState[r][c] = state[r][c];
                    }
                }
                newState[blankRow][blankCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;
                successors.add(newState);
            }
        }

        return successors;
    }

    private void findBlankTile() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentState[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    return;
                }
            }
        }
    }

    public List<int[][]> bfs() {
        Queue<int[][]> queue = new LinkedList<>();
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, int[][]> parentMap = new HashMap<>();

        String initialStateString = Arrays.deepToString(initialState);
        visited.put(initialStateString, true);
        queue.add(initialState);

        while (!queue.isEmpty()) {
            int[][] current = queue.poll();
            if (isGoalState(current)) {
                return reconstructPath(parentMap, current);
            }

            currentState = current;
            findBlankTile();
            List<int[][]> successors = getSuccessors(current);

            for (int[][] successor : successors) {
                String successorString = Arrays.deepToString(successor);
                if (!visited.containsKey(successorString)) {
                    visited.put(successorString, true);
                    parentMap.put(successorString, current);
                    queue.add(successor);
                }
            }
        }

        return null;
    }

    public List<int[][]> dfs() {
        Stack<int[][]> stack = new Stack<>();
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, int[][]> parentMap = new HashMap<>();

        String initialStateString = Arrays.deepToString(initialState);
        visited.put(initialStateString, true);
        stack.push(initialState);

        while (!stack.isEmpty()) {
            int[][] current = stack.pop();
            if (isGoalState(current)) {
                return reconstructPath(parentMap, current);
            }

            currentState = current;
            findBlankTile();
            List<int[][]> successors = getSuccessors(current);

            for (int[][] successor : successors) {
                String successorString = Arrays.deepToString(successor);
                if (!visited.containsKey(successorString)) {
                    visited.put(successorString, true);
                    parentMap.put(successorString, current);
                    stack.push(successor);
                }
            }
        }

        return null;
    }

    private List<int[][]> reconstructPath(Map<String, int[][]> parentMap, int[][] goalState) {
        List<int[][]> path = new ArrayList<>();
        String currentStateString = Arrays.deepToString(goalState);

        while (currentStateString != null) {
            int[][] currentState = parentMap.get(currentStateString);
            if (currentState != null) {
                path.add(0, currentState);
                currentStateString = Arrays.deepToString(currentState);
            } else {
                path.add(0, goalState);
                currentStateString = null;
            }
        }

        return path;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] initialState = new int[3][3];
        int move =0;

        System.out.println("Enter the initial state (0 for the blank tile):");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                initialState[i][j] = scanner.nextInt();
            }
        }

        EightPuzzleGame game = new EightPuzzleGame(initialState);

        System.out.println("Select search method:");
        System.out.println("1. Breadth-First Search (BFS)");
        System.out.println("2. Depth-First Search (DFS)");

        int searchMethod = scanner.nextInt();
        List<int[][]> solutionPath;

        switch (searchMethod) {
            case 1:
                solutionPath = game.bfs();
                break;
            case 2:
                solutionPath = game.dfs();
                break;
            default:
                System.out.println("Invalid search method selected.");
                scanner.close();
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

        scanner.close();
    }
}