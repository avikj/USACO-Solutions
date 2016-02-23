import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class lightsout{
	static HashMap<Integer, Integer> distanceCache = new HashMap<Integer, Integer>();
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("lightsout.in"));
		int n = in.nextInt();
		int[][] vertices = new int[n][2];
		int[] distTo = new int[n];
		boolean[] turnLeft = new boolean[n];
		for(int i = 0; i < n; i++){
			vertices[i][0] = in.nextInt();
			vertices[i][1] = in.nextInt();
		}
		for (int i = 1; i < n; i++) {
			distTo[i] = Math.abs(vertices[i][0] == vertices[i-1][0] ? vertices[i][1]-vertices[i-1][1] : vertices[i][0]-vertices[i-1][0]);
		}
		distTo[0] = Math.abs(vertices[0][0] == vertices[n-1][0] ? vertices[0][1]-vertices[n-1][1] : vertices[0][0]-vertices[n-1][0]);
		for(int i = 1; i <  n+1; i++){
			turnLeft[i%n] = (direction(vertices[(i-1)%n], vertices[i%n])-direction(vertices[i%n], vertices[(i+1)%n])+4)%4 == 1;
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int maxDiff = -1;
		int maxI = -1;
		outer:for(int b = 1; b < n; b++){
			int difference = 0;
			ArrayList<Boolean> angles = new ArrayList<Boolean>();
			ArrayList<Integer> dists = new ArrayList<Integer>();
			angles.add(turnLeft[b]);
			for(int len = 1; Math.abs(countMatches(distTo, turnLeft, dists, angles)) != 1; len++){
				angles.add(turnLeft[(b+1)%n]);
				dists.add(distTo[(b+1)%n]);
				if((b+1)%n==0)
					continue outer;
			}
			if(countMatches(distTo, turnLeft, dists, angles)==1){
				System.out.println("found one match");
				int sum = 0;
				for(int i : distTo)
					sum += i;
				difference = sum+normDistToStart(distTo, b+dists.size())-normDistToStart(distTo, b);
			}else{
				int sum = 0;
				for(int i = b+1; i <= n; i++)
					sum+=distTo[i%n];
				difference = sum- normDistToStart(distTo, b);
				//System.out.println("got to start early in "+(sum)+" instead of "+normDistToStart(distTo, b)+" starting at "+b);
			}
			if(difference > maxDiff){
				maxDiff = difference;
				maxI = b;
			}
		}

		PrintWriter out = new PrintWriter("lightsout.out");
		out.print(maxDiff);
		out.close();
		/*ArrayList<Integer> dists = new ArrayList<Integer>();
		ArrayList<Boolean> angles = new ArrayList<Boolean>();
		angles.add(true);
		angles.add(false);
		angles.add(true);
		angles.add(true);
		dists.add(1);
		dists.add(2);
		dists.add(3);
		System.out.println(countMatches(distTo, turnLeft, dists, angles));*/

	}
	public static int normDistToStart(int[] distTo, int vertex){
		if(distanceCache.containsKey(vertex))
			return distanceCache.get(vertex);
		int clock = 0;
		for(int i = vertex+1; i <= distTo.length; i++)
			clock+=distTo[i%distTo.length]; 
		int count = 0;
		for(int i = 1; i <= vertex; i++)
			count+=distTo[i];
		distanceCache.put(vertex, Math.min(count, clock));
		return distanceCache.get(vertex);
	}

	public static int countMatches(int[] distTo, boolean[] turnLeft, ArrayList<Integer> dists, ArrayList<Boolean> angles){
		int c = 0;
		for(int start = 1; start < distTo.length; start++){
			if(angles.get(0)==turnLeft[start]){
				boolean valid = true;
				for(int i = 0; i < dists.size(); i++){
					if(dists.get(i) != distTo[(i+start+1)%distTo.length]){
						valid = false;
					}
					if(angles.get(i+1) != turnLeft[(i+start+1)%distTo.length])
						valid = false;
					if((i+start+1)%distTo.length==0)
						return -1;
				}
				if(valid){
					c++;
				}
			}
		}
		return c;
	}

	public static int getMatchIndex(int[] distTo, boolean[] turnLeft, ArrayList<Integer> dists, ArrayList<Boolean> angles){
		int c = 0;
		for(int start = 1; start < distTo.length; start++){
			if(angles.get(0)==turnLeft[start]){
				boolean valid = true;
				for(int i = 0; i < dists.size(); i++){
					if(dists.get(i) != distTo[(i+start+1)%distTo.length]){
						valid = false;
					}
					if(angles.get(i+1) != turnLeft[(i+start+1)%distTo.length])
						valid = false;
				}
				if(valid){
					return start;
				}
			}
		}
		return -1;
	}

	public static int direction(int[] a, int[] b){//up = 0, right = 1, down = 2;  left = 3;
		return b[1] > a[1] ? 0 : b[0] > a[0] ? 1 : b[1] < a[1] ? 2 : 3;
	}
}