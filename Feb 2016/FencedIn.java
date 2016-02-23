import java.util.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class FencedIn{
    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(new File("fencedin.in"));
        int A = in.nextInt();
        int B = in.nextInt();
        int n = in.nextInt();
        int m = in.nextInt();

        int[] a = new int[n+2];
        int[] b = new int[m+2];
        a[0] = 0;
        b[0] = 0;
        for(int i = 1; i < n+1; i++)
            a[i] = in.nextInt();
        for(int i = 1; i < m+1; i++)
            b[i] = in.nextInt();
        a[n+1] = A;
        b[m+1] = B;

        ArrayList<Edge> allEdges = new ArrayList<Edge>();
        ArrayList<Edge> treeEdges = new ArrayList<Edge>();

        ArrayList<Node> allNodes = new ArrayList<Node>();
        ArrayList<Node> containedNodes = new ArrayList<Node>();

        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < m+1; j++){
                allNodes.add(new Node(i, j));
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                allEdges.add(new Edge(new Node(i, j), new Node(i+1, j), b[j+1]-b[j]));
                allEdges.add(new Edge(new Node(i, j), new Node(i, j+1), a[i+1]-a[i]));
            }
        }
        for(int i = 0; i < n; i++){
            allEdges.add(new Edge(new Node(i, m), new Node(i+1, m), b[m+1]-b[m]));
        }
        for(int j = 0; j < m; j++){
            allEdges.add(new Edge(new Node(n, j), new Node(n, j+1), a[n+1]-a[n]));
        }

        long result = 0;
        Collections.sort(allEdges);
        for(int i = 0; i < allEdges.size(); i++){
            if(!containedNodes.contains(allEdges.get(i).a) || !containedNodes.contains(allEdges.get(i).b)){
                treeEdges.add(allEdges.get(i));
                containedNodes.add(allEdges.get(i).a);
                containedNodes.add(allEdges.get(i).a);
            }
        }
        for(Edge e : treeEdges){
            result+=e.weight;
        }

        PrintWriter out = new PrintWriter("fencedin.out");
        out.print(result);out.close();
    }

}

class Node{
    public int i;
    public int j;
    public Node(int a, int b){
        i = a;
        j = b;
    }
    public boolean equals(Node n){
        return i==n.i && j==n.j;
    }
}

class Edge implements Comparable<Edge>{
    public Node a;
    public Node b;
    int weight;
    public Edge(Node x, Node y, int w){
        a = x;
        b = y;
        weight = w;
    }
    public int compareTo(Edge e){
        return weight-e.weight;
    }
}