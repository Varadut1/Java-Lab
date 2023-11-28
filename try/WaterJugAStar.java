import java.util.*;

class State {
    int jugA;
    int jugB;

    public State(int jugA, int jugB) {
        this.jugA = jugA;
        this.jugB = jugB;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return jugA == state.jugA && jugB == state.jugB;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jugA, jugB);
    }
}

class Node implements Comparable<Node> {
    State state;
    Node parent;
    int cost;
    int heuristic;

    public Node(State state, Node parent, int cost, int heuristic) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(cost + heuristic, other.cost + other.heuristic);
    }
}

public class WaterJugAStar {
    static final int capacityA = 4;
    static final int capacityB = 3;
    static final int target = 2;

    public static void main(String[] args) {
        State initialState = new State(0, 0);
        Node solution = solve(initialState);
        printSolution(solution);
    }

    static Node solve(State initialState) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<State> closedSet = new HashSet<>();

        Node startNode = new Node(initialState, null, 0, heuristic(initialState));
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.state.jugA == target || current.state.jugB == target) {
                return current; // Goal reached
            }

            closedSet.add(current.state);

            // Generate successors
            for (Node successor : getSuccessors(current)) {
                if (!closedSet.contains(successor.state)) {
                    openSet.add(successor);
                }
            }
        }

        return null; // No solution found
    }

    static List<Node> getSuccessors(Node node) {
        List<Node> successors = new ArrayList<>();

        // Pour water from jugA to jugB
        successors.add(new Node(
                new State(Math.max(0, node.state.jugA - (capacityB - node.state.jugB)), Math.min(capacityB, node.state.jugB + node.state.jugA)),
                node,
                node.cost + 1,
                heuristic(node.state)
        ));

        // Pour water from jugB to jugA
        successors.add(new Node(
                new State(Math.min(capacityA, node.state.jugA + node.state.jugB), Math.max(0, node.state.jugB - (capacityA - node.state.jugA))),
                node,
                node.cost + 1,
                heuristic(node.state)
        ));

        successors.add(new Node(
            new State(capacityA, node.state.jugB),
            node,
            node.cost + 1,
            heuristic(node.state)
        ));

        // Fill Jug B
        successors.add(new Node(
            new State(node.state.jugA, capacityB),
            node,
            node.cost + 1,
            heuristic(node.state)
        ));

        return successors;
    }

    static int heuristic(State state) {
        // A simple heuristic: the difference between the current total amount of water and the target
        return Math.abs(state.jugA + state.jugB - target);
    }

    static void printSolution(Node solution) {
        if (solution != null) {
            Stack<State> states = new Stack<>();
            Node current = solution;

            while (current != null) {
                states.push(current.state);
                current = current.parent;
            }

            while (!states.isEmpty()) {
                State state = states.pop();
                System.out.println("JugA: " + state.jugA + ", JugB: " + state.jugB);
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
