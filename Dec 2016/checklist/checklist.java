/*
ID: avikjai1
LANG: JAVA
TASK: checklist
*/

import java.io.*;
import java.util.*;

public class checklist {
  static int h;
  static int g;
  static int[] hx;
  static int[] hy;
  static int[] gx;
  static int[] gy;
  static int[][][] cache;
  static final int INF = -1;
  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(new File("checklist.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("checklist.out")));
    h = in.nextInt();
    g = in.nextInt();
    hx = new int[h];
    hy = new int[h];
    gx = new int[g];
    gy = new int[g];
    cache = new int[2][h+1][g+1]; // is current h?, next h, next g
    for(int i = 0; i < h; i++) {
      hx[i] = in.nextInt();
      hy[i] = in.nextInt();
    }
    for(int i = 0; i < g; i++) {
      gx[i] = in.nextInt();
      gy[i] = in.nextInt();
    }
    out.println(solve(hx[0], hy[0], 1, 0, true));
    out.close();
  }

  static int solve(int currx, int curry, int nextH, int nextG, boolean isCurrentH) {
    if(nextH == h && nextG != g) // finished h but not g
      return -1;
    if(nextH == h && nextG == g) // finished h and g
      return 0;
    if(cache[isCurrentH ? 1 : 0][nextH][nextG] != 0)
      return cache[isCurrentH ? 1 : 0][nextH][nextG];
    int result = -1;
    if(nextH != h || nextG == g) {
      int remaining = solve(hx[nextH], hy[nextH], nextH+1, nextG, true);
      if(remaining != -1)
        result = min(result, energy(currx, curry, hx[nextH], hy[nextH]) + remaining);
    }
    if(nextG != g) { // if can go to next G, try to
      int remaining = solve(gx[nextG], gy[nextG], nextH, nextG+1, false);
      if(remaining != -1)
        result = min(result, energy(currx, curry, gx[nextG], gy[nextG]) + remaining);
    }
    cache[isCurrentH ? 1 : 0][nextH][nextG] = result;
    while(result==INF);
    return result;
  }
  static int energy(int x1, int y1, int x2, int y2) {
    return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
  }
  static int min(int a, int b) {
    if(a == -1)
      return b;
    return Math.min(a, b);
  }
}