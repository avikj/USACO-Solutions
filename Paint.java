import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
public class Paint{
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("paint.in"));
		int s1 = in.nextInt();
		int e1 = in.nextInt();
		int s2 = in.nextInt();
		int e2 = in.nextInt();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = s1; i < e1; i++)
			list.add(i);
		for(int i = s2; i < e2; i++)
			if(!list.contains(i))
				list.add(i);
		PrintWriter out = new PrintWriter("paint.out");
		out.print(list.size());
		out.close();
	}
}