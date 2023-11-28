import java.util.*;
class State{
    int ML;
    int CL;
    int boat;
    int MR;
    int CR;
    State parent;
    public State(int ML,int CL, int boat,int MR,int CR,State parent)
    {
        this.ML=ML;
        this.CL=CL;
        this.boat=boat;
        this.MR=MR;
        this.CR=CR;
        this.parent=parent;
    }
    public boolean isValid()
    {
        if(ML<CL&&ML>0 || MR<CR&&MR>0)
        {
            return false;
        }
        if(ML<0 || CL<0 || MR<0||CR<0)
        {
            return false;
        }
        return true;
    }
public boolean isGoal()
{
    return ML==0 && CL==0;
}
public State move(int m,int c)
{
    return new State(ML-m, CL-c,1-boat,MR+m,CR+c,this);
}

}

class MandC{
    public static void main(String[] args) {
        solve();
    }
    public static void solve()
    {
        Queue<State> qbfs=new ArrayDeque<>();
        qbfs.add(new State(3,3,0,0,0,null));
        int step=1;
        while(!qbfs.isEmpty())
        {
            State current=qbfs.poll();
            if(current.isGoal())
            {
                printSolution(current);
                break;
            }
            System.out.println("Step "+step+":");
            printState(current);
            step++;
            for(int m=0;m<=2 ;m++)
            {
                for(int c=0;c<=2;c++)
                {
                        if(m+c>=1 && m+c<=2)
                        {
                            State nextState=current.move(m,c);
                
                            if(nextState.isValid() && !qbfs.contains(nextState))
                            {
                                qbfs.add(nextState);
                            }
                        }
                }
            }
        }
    }
    public static void printState(State state) {
        System.out.println("MCL | BOAT | MCR");
        System.out.println("----|------|----");
        System.out.println(state.ML + "" + state.CL + "L | " +
                (state.boat == 0 ? "  B   " : "      ") + " | " + state.MR + "" + state.CR + "R");
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