import java.util.*;
class Pair{
    int j1,j2;
    List<Pair> path;
    Pair(int j1,int j2)
    {
        this.j1=j1;
        this.j2=j2;
        path=new ArrayList<>();
    }
    Pair(int j1,int j2,List<Pair> pair)
    {
        this.j1=j1;
        this.j2=j2;
        path=new ArrayList<>();
        path.addAll(pair);
        path.add(new Pair(this.j1,this.j2));
    }

}
public class WaterJug1 {
    public static void main(String[] args) throws java.lang.Exception{
        int jar1=4;
        int jar2=3;
        int target=2;
        solution(jar1,jar2,target);
    }
    public static void solution(int jar1,int jar2,int target)
    {
        boolean[][] visited=new boolean[jar1+1][jar2+1];
        Queue<Pair> queue=new LinkedList<>();
        Pair initial=new Pair(0,0);
        initial.path.add(new Pair(0,0));
        queue.offer(initial);
        while(!queue.isEmpty())
        {
            Pair current=queue.poll();
            if(current.j1>jar1 || current.j2 > jar2 || visited[current.j1][current.j2]==true)
            {
                continue;
            }
            visited[current.j1][current.j2]=true;
            if(current.j1==target || current.j2==target)
            {
                if(current.j1==target)
                {
                    current.path.add(new Pair(current.j1,0));
                }
                else{
                    current.path.add(new Pair(0,current.j2));
                }
                int n=current.path.size();
                System.out.println("path of state of jug is fallowd");
                for(int i=0;i<n;i++)
                {
                    System.out.println(current.path.get(i).j1+","+current.path.get(i).j2);
                }
                return;
            }
            queue.offer(new Pair(jar1,0,current.path));
            queue.offer(new Pair(0,jar2,current.path));
            queue.offer(new Pair(jar1,current.j2,current.path));
            queue.offer(new Pair(current.j1,jar2,current.path));
            queue.offer(new Pair(current.j1,0,current.path));
            queue.offer(new Pair(0,current.j2,current.path));
            int emptyjug=jar2-current.j2;
            int amount=Math.min(current.j1,emptyjug);
            int j1=current.j1-amount;
            int j2=current.j2+amount;
            queue.offer(new Pair(j1,j2,current.path));
            emptyjug=jar1-current.j1;
            amount=Math.min(current.j2,emptyjug);
            j1=current.j1+amount;
            j2=current.j2-amount;
            queue.offer(new Pair(j1,j2,current.path));
    

        }
        System.out.println("solutionis not possible");
    }
}
