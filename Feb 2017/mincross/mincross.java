/*
ID: avikjai1
LANG: JAVA
TASK: mincross
*/

import java.io.*;
import java.util.*;

public class mincross {
  static int[] breeds1;
  static int[] breeds2;
  static int[] position1; // position[breedNumber] is the position where breed breedNumber is found
  static int[] position2;
  static boolean[][] crosses;
  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(new File("mincross.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("mincross.out")));
    int n = in.nextInt();
    breeds1 = new int[n];
    breeds2 = new int[n];
    position1 = new int[n]; // position[breedNumber] is the position where breed breedNumber is found
    position2 = new int[n];
    crosses = new boolean[n][n];
    for(int i = 0; i < n; i++) {
      breeds2[i] = in.nextInt() - 1;
      position1[breeds2[i]] = i;
    }
    for(int i = 0; i < n; i++) {
      breeds1[i] = in.nextInt() - 1;
      position2[breeds1[i]] = i;
    }
    int numCrosses = populateCrosses();
    int min = Integer.MAX_VALUE;
    for(int i = breeds1.length - 1; i >= 0; i--) { // breed i to be moved to beginning
      System.out.println("moving cow "+(breeds1[i]+1)+" to beginning");
      for(int j = 0; j < n; j++) {
        if(breeds1[i] != j) {
          if(crosses[breeds1[i]][j])
            numCrosses--;
          else 
            numCrosses++;
          crosses[breeds1[i]][j] = !crosses[breeds1[i]][j];
          crosses[j][breeds1[i]] = !crosses[j][breeds1[i]];
        }
      }
      min = Math.min(min, numCrosses);
      System.out.println(numCrosses);
    }
    out.println(min);
    out.close();
  }

  public static int populateCrosses() {
    int result = 0;
    for(int i = 0 ; i < position1.length; i++) {
      for(int j = i+1; j < position1.length; j++) {
        if(position1[i] > position1[j] && position2[i] < position2[j]
          || position1[i] < position1[j] && position2[i] > position2[j]) {
          crosses[i][j] = crosses[j][i] = true;
          result++;
        }
      }
    } 
    return result;
  }
  public static void rotate(int[] a) {
    for(int i = 0; i < a.length; i++)
      a[i] = (a[i] + 1) % a.length;
  }
}