import java.util.*;
 
class Point{
    int row;
    int col;
    Point parent;
    Point(int row, int col, Point parent){
        this.row=row;
        this.col=col;
        this.parent=parent;
    }
}
public class Maze{
    static int[][] maze = {
        {0, 0, 1, 1, 1},
        {0, 0, 1, 0, 1},
        {0, 1, 0, 1, 1},
        {0, 0, 0, 0, 0},
        {1, 0, 0, 1, 0}
    };
    static Point start=new Point(0, 0, null);
    static Point end=new Point(maze.length-1, maze[0].length-1, null);

    public static void findPath(){  
        Queue<Point> queue=new LinkedList<>();
        Set<Point> visited=new HashSet<>();
        queue.add(start);
        visited.add(start);
        while(!queue.isEmpty()){
            Point now=queue.poll();
            // System.out.println("row: " + now.row + " col: "+now.col);
            if(now.row==end.row && now.col==end.col){
                System.out.println("Path found");
                printPath(now);
                break;
            }
            ArrayList<Point> neighbours=generateNeighbours(now);
            for(Point neighbour:neighbours){
                if(!visited.contains(neighbour)){
                    queue.add(neighbour);
                    visited.add(neighbour);
                }
            }
        }
        // for(Point p:visited){
        //     System.out.println("row: "+p.row+ " col: "+p.col); 
        // }
    }

    public static void printPath(Point node){
        StringBuilder path=new StringBuilder();
        while(node.parent!=null){
            if(node.parent.row-node.row==1){
                path.append("U");
                node=node.parent;
            }
            else if(node.parent.row-node.row==-1){
                path.append("D");
                node=node.parent;
            }
            else if(node.parent.col-node.col==1){
                path.append("L");
                node=node.parent;
            }
            else if(node.parent.col-node.col==-1){
                path.append("R");
                node=node.parent;
            }
        }
        System.out.println(path.reverse());
    }

    public static ArrayList<Point> generateNeighbours(Point parent){
        ArrayList<Point> neighbours=new ArrayList<>();
        int moves[][]={{0,1},{0,-1},{1,0},{-1,0}};
        for(int[]move:moves){
            if(isValid(new int[]{parent.row+move[0],parent.col+move[1]})){
                Point neighbour=new Point(parent.row+move[0],parent.col+move[1],parent);
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    public static boolean isValid(int[]pos){
        if(pos[0]<0 || pos[0]>=maze.length || pos[1]<0 || pos[1]>=maze[0].length)
            return false;
        if(maze[pos[0]][pos[1]]!=0)
            return false;
        return true;
    }

    public static void main(String args[]){
        findPath();
    }
}