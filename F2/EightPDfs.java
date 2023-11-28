import java.util.*;

class EightPuzzleState {
    int[][] puzzle;
    int zeroRow, zeroCol;

    public EightPuzzleState(int[][] puzzle) {
        this.puzzle = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.puzzle[i][j] = puzzle[i][j];
                if (puzzle[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
    }

    public boolean isGoalState() {
        int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        return Arrays.deepEquals(puzzle, goal);
    }

    public boolean isValidMove(int newRow, int newCol) {
        return newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3;
    }

    public EightPuzzleState move(int newRow, int newCol) {
        int[][] newPuzzle = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newPuzzle[i][j] = puzzle[i][j];
            }
        }

        newPuzzle[zeroRow][zeroCol] = newPuzzle[newRow][newCol];
        newPuzzle[newRow][newCol] = 0;

        return new EightPuzzleState(newPuzzle);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(puzzle);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EightPuzzleState otherState = (EightPuzzleState) obj;
        return Arrays.deepEquals(puzzle, otherState.puzzle);
    }

    public void printState() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

public class EightPDfs {
    public static void main(String[] args) {
        int[][] initialPuzzle = {
                {1, 2, 3},
                {4, 5, 6},
                {0, 7, 8}
        };

        EightPuzzleState initialState = new EightPuzzleState(initialPuzzle);

        Stack<EightPuzzleState> stack = new Stack<>();
        HashSet<EightPuzzleState> visited = new HashSet<>();

        stack.push(initialState);
        visited.add(initialState);

        while (!stack.isEmpty()) {
            EightPuzzleState currentState = stack.pop();
            currentState.printState();

            if (currentState.isGoalState()) {
                System.out.println("Goal state found!");
                break;
            }

            int[] rowOffsets = {0, 0, 1, -1};
            int[] colOffsets = {1, -1, 0, 0};

            for (int i = 0; i < 4; i++) {
                int newRow = currentState.zeroRow + rowOffsets[i];
                int newCol = currentState.zeroCol + colOffsets[i];

                if (currentState.isValidMove(newRow, newCol)) {
                    EightPuzzleState nextState = currentState.move(newRow, newCol);

                    if (!visited.contains(nextState)) {
                        stack.push(nextState);
                        visited.add(nextState);
                    }
                }
            }
        }
    }
}
