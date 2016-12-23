/*
ID: avikjai1
LANG: JAVA
TASK: moocast
*/

import java.io.*;
import java.util.*;

public class moocast {
  static int n;
  static int[] x;
  static int[] y;
  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(new File("moocast.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
    n = in.nextInt();
    x = new int[n];
    y = new int[n];
    for(int i = 0; i < n; i++) {
      x[i] = in.nextInt();
      y[i] = in.nextInt();
    }
    out.println(binarySearch(0, 1250000000));
    out.close();
  }
  static int binarySearch(int low, int high) {
    if(high < low)
      return -1;
    int mid = (low+high)/2;
    if(works(mid) && !works(mid-1)) // lowest that works
      return mid;
    if(works(mid))
      return binarySearch(low, mid-1);
    return binarySearch(mid+1, high);
  }
  static boolean works(int K) {
    boolean[] visited = new boolean[n];
    floodfill(0, getAdjList(K), visited);
    for(boolean v : visited) 
      if(!v)
        return false;
    return true;
  }

  static void floodfill(int current, ArrayList<Integer>[] adjList, boolean[] visited) {
    if(visited[current])
      return;
    visited[current] = true;
    for(int i : adjList[current]) {
      floodfill(i, adjList, visited);
    }
  }

  static ArrayList<Integer>[] getAdjList(int K) {
    ArrayList<Integer>[] adjList = new ArrayList[n];
    for(int i = 0; i < n; i++)
      adjList[i] = new ArrayList<Integer>();
    for(int i = 0; i < n; i++) {
      for(int j = i+1; j < n; j++) {
        if((x[i]-x[j])*(x[i]-x[j]) + (y[i]-y[j])*(y[i]-y[j]) <= K) {
          adjList[i].add(j);
          adjList[j].add(i);
        } 
      }
    }
    return adjList;
  }
}