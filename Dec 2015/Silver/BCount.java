import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
public class BCount{
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("bcount.in"));
		PrintWriter out = new PrintWriter("bcount.out");
		int n = in.nextInt();
		int q = in.nextInt();
		int[][] counts = new int[n][3];
		for(int i = 0; i < n; i++){
			counts[i] = new int[3];
			counts[i][in.nextInt()-1]++;
		}
		for(int i = 1; i < n; i++)
			for(int j = 0; j < 3; j++)
				counts[i][j]+=counts[i-1][j];

		for(int i = 0; i < q; i++){
			int a = in.nextInt()-1;
			int b = in.nextInt()-1;
			int[] bs = counts[b];
			int[] as = a > 0 ? counts[a-1] : new int[]{0, 0, 0};
			String line = "";
			for(int j = 0; j < 3; j++)
				line+=bs[j]-as[j]+" ";
			line = line.trim();
			out.print(line);
			if(q-i>1)out.println();
		}
		out.close();
	}
}