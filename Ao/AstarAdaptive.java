import java.util.*;
class PuzzleState {
    int[][] board;
    int size;
    int heuristic; // Manhattan Distance heuristic
    int cost;

    public PuzzleState(int[][] board, int size) {
        this.board = new int[size][size];
        this.size = size;
        this.cost = 0;

        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, size);
        }

        calculateHeuristic();
    }

    private void calculateHeuristic() {
        heuristic = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = board[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / size;
                    int targetCol = (value - 1) % size;
                    heuristic += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
    }

    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    // Last cell should be empty
                    if (board[i][j] != 0) {
                        return false;
                    }
                } else if (board[i][j] != i * size + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<PuzzleState> getSuccessors() {
        List<PuzzleState> successors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    for (int k = 0; k < 4; k++) {
                        int ni = i + dx[k];
                        int nj = j + dy[k];
                        if (ni >= 0 && ni < size && nj >= 0 && nj < size) {
                            int[][] newBoard = new int[size][size];
                            for (int x = 0; x < size; x++) {
                                System.arraycopy(board[x], 0, newBoard[x], 0, size);
                            }
                            newBoard[i][j] = board[ni][nj];
                            newBoard[ni][nj] = 0;
                            PuzzleState successor = new PuzzleState(newBoard, size);
                            successor.cost = cost + 1;
                            successors.add(successor);
                        }
                    }
                    return successors;
                }
            }
        }

        return successors;
    }
}

public class AstarAdaptive {
    public static void main(String[] args) {
        int[][] initialBoard = {
                {1, 2, 3},
                {8, 0, 4},
                {7, 6, 5}
        };

        int size = 3;
        PuzzleState initialState = new PuzzleState(initialBoard, size);

        PuzzleState solution = solve(initialState);
        printSolution(solution);
    }

    public static PuzzleState solve(PuzzleState initialState) {
        PriorityQueue<PuzzleState> openSet = new PriorityQueue<>(Comparator.comparingInt(state -> state.cost + state.heuristic));
        Set<PuzzleState> closedSet = new HashSet<>();

        openSet.add(initialState);

        while (!openSet.isEmpty()) {
            PuzzleState current = openSet.poll();

            if (current.isGoal()) {
                return current; // Goal reached
            }

            closedSet.add(current);

            for (PuzzleState successor : current.getSuccessors()) {
                if (!closedSet.contains(successor) && !openSet.contains(successor)) {
                    openSet.add(successor);
                }
            }
        }

        return null; // No solution found
    }

    public static void printSolution(PuzzleState solution) {
        if (solution != null) {
            System.out.println("Solution found!");
            System.out.println("Cost: " + solution.cost);
        } else {
            System.out.println("No solution found.");
        }
    }
}

