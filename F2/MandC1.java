import java.util.ArrayDeque;
import java.util.Queue;

class State {
    int missionariesLeft;
    int cannibalsLeft;
    int boat;
    int missionariesRight;
    int cannibalsRight;
    State parent;

    public State(int missionariesLeft, int cannibalsLeft, int boat,
                 int missionariesRight, int cannibalsRight, State parent) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.boat = boat;
        this.missionariesRight = missionariesRight;
        this.cannibalsRight = cannibalsRight;
        this.parent = parent;
    }

    public boolean isValid() {
        if (missionariesLeft < 0 || cannibalsLeft < 0 || missionariesRight < 0 || cannibalsRight < 0) {
            return false;
        }
        if ((missionariesLeft < cannibalsLeft && missionariesLeft > 0) || (missionariesRight < cannibalsRight && missionariesRight > 0)) {
            return false;
        }
        return true;
    }

    public boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0;
    }

    public State move(int m, int c) {
        return new State(missionariesLeft - m, cannibalsLeft - c, 1 - boat, missionariesRight + m, cannibalsRight + c, this);
    }

   // @Override
    // public boolean equals(Object obj) {
    //     if (obj instanceof State) {
    //         State s = (State) obj;
    //         return missionariesLeft == s.missionariesLeft && cannibalsLeft == s.cannibalsLeft
    //                 && boat == s.boat && missionariesRight == s.missionariesRight && cannibalsRight == s.cannibalsRight;
    //     }
    //     return false;
    // }

    //@Override
    // public int hashCode() {
    //     return missionariesLeft * 10000 + cannibalsLeft * 1000 + boat * 100 + missionariesRight * 10 + cannibalsRight;
    // }
}

public class MandC1 {
    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        Queue<State> queue = new ArrayDeque<>();
        queue.add(new State(3, 3, 0, 0, 0, null));

        int step = 1;

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (currentState.isGoal()) {
                printSolution(currentState);
                break;
            }

            System.out.println("Step " + step + ":");
            printState(currentState);
            step++;

            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2; c++) {
                    if (m + c >= 1 && m + c <= 2) {
                        State nextState = currentState.move(m, c);
                        if (nextState.isValid() && !queue.contains(nextState)) {
                            queue.add(nextState);
                        }
                    }
                }
            }
        }
    }

    public static void printState(State state) {
        System.out.println("MCL | BOAT | MCR");
        System.out.println("----|------|----");
        System.out.println(state.missionariesLeft + "" + state.cannibalsLeft + "L | " +
                (state.boat == 0 ? "  B   " : "      ") + " | " + state.missionariesRight + "" + state.cannibalsRight + "R");
    }

    public static void printSolution(State goalState) {
        System.out.println("Missionaries and Cannibals solution:");
        while (goalState.parent != null) {
            printState(goalState);
            goalState = goalState.parent;
        }
        printState(goalState);
    }
}
