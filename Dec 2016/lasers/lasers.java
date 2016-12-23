/*ID: avikjai1
LANG: JAVA
TASK: lasers
*/

import java.io.*;
import java.util.*;

public class lasers {
  static int n;
  static int[] x; // fence post x
  static int[] y; // fence post y
  static final int INF = 1000000000;
  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(new File("lasers.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lasers.out")));

    // read input
    n = in.nextInt();
    x = new int[n+2]; // indices 0 and 1 represent laser and barn locations
    y = new int[n+2];    
    for(int i = 0; i < n+2; i++) {
      x[i] = in.nextInt();
      y[i] = in.nextInt();
    }

    // populate adjacency list
    ArrayList<Integer>[] adjList = new ArrayList[n+2];
    for(int i = 0; i < n+2; i++) 
      adjList[i] = new ArrayList<Integer>();
    for(int i = 0; i < n+2; i++) {
      for(int j = i+1; j < n+2; j++) {
        if(x[i] == x[j] || y[i] == y[j]) {
          adjList[i].add(j);
          adjList[j].add(i);
        }
      }
    }

    // run dijkstra's starting from laser
    int[] dist = new int[n+2];
    boolean[] removed = new boolean[n+2];
    Arrays.fill(dist, INF);
    dist[0] = 0; // distance from laser to laser is 0
    while(true) {
      int u = -1; // vertex with smallest distance
      for(int i = 0; i < n+2; i++)
        if((u == -1 || dist[i] < dist[u]) && !removed[i])
          u = i;
      if(u == 1)
        break;
      removed[u] = true;
      for(int v : adjList[u])
        dist[v] = Math.min(dist[v], dist[u] + 1);
    }
    out.println(dist[1]-1); // # of mirrors = # of steps - 1
    out.close();
  }
}