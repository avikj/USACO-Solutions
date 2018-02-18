/*
ID: avikjai1
LANG: JAVA
TASK: friendcross
*/

import java.io.*;
import java.util.*;

public class friendcross {
  static int[] breeds1;
  static int[] breeds2;
  static int[] position1; // position[breedNumber] is the position where breed breedNumber is found
  static int[] position2;
  static boolean[][] crosses;
  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(new File("friendcross.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friendcross.out")));
    int n = in.nextInt();
    int k = in.nextInt();
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
    int result = solve(k);
    out.println(result);
    out.close();
  }

  public static int solve(int k) {
    int result = 0;
    for(int i = 0 ; i < position1.length; i++) {
      for(int j = i+1; j < position1.length; j++) {
        if(position1[i] > position1[j] && position2[i] < position2[j]
          || position1[i] < position1[j] && position2[i] > position2[j]) {
          if(Math.abs(i-j) > k)
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