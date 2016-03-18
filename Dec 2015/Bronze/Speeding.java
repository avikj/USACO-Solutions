import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Speeding{
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("speeding.in"));
		int n = in.nextInt();
		int m = in.nextInt();
		int[] lims = new int[100];
		int[] speeds = new int[100];
		int index = 0;
		for(int i = 0; i < n; i++){
			int len = in.nextInt();
			int speed = in.nextInt();
			for(int j = 0; j < len; j++)
				lims[index++] = speed;
		}
		index = 0;
		for(int i = 0; i < m; i++){
			int len = in.nextInt();
			int speed = in.nextInt();
			for(int j = 0; j < len; j++)
				speeds[index++] = speed;
		}
		int max = -1;
		for(int i = 0; i < 100; i++)
			max = Math.max(max, speeds[i] - lims[i]);
		PrintWriter out = new PrintWriter("speeding.out");
		out.print(max < 0 ? 0 : max);
		out.close();
	}
}
