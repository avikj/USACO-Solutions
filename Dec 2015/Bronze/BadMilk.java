import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
public class BadMilk{
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("badmilk.in"));
		
		int N = in.nextInt();
		int M = in.nextInt();
		int D = in.nextInt();
		int S = in.nextInt();
		int[][] d = new int[D][3];
		for(int i = 0; i < D; i++){
			for(int j = 0; j < 3; j++){
				d[i][j] = in.nextInt();
			}
		}
		int[][] s = new int[S][2];
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < 2; j++) {
				s[i][j] = in.nextInt();
			}
		}
		ArrayList<Integer> badMilks = new ArrayList<Integer>();
		//initially populate badMilks with milks that first person drank before getting sick
		for(int j = 0; j < d.length; j++){
			if(s[0][0] == d[j][0] && s[0][1] > d[j][2]){
				badMilks.add(d[j][1]);
			}
		}
		for(int i = 1; i < S; i++){
			ArrayList<Integer> milks = new ArrayList<Integer>();
			for (int j = 0; j < D; j++) {
				if(s[i][0] == d[j][0] && s[i][1] > d[j][2]){
					milks.add(d[j][1]);
				}
			}
			for(int m = 0; m < badMilks.size(); m++){
				if(!milks.contains(badMilks.get(m))){
					badMilks.remove(m);
					m--;
				}
			}
		}
		//badmilks is now populated with all potential bad milks
		int max = 0;
		int badMilk = -1;
		for(Integer milk : badMilks){
			ArrayList<Integer> people = new ArrayList<Integer>();
			for (int j = 0; j < D; j++) {
				if (d[j][1] == milk && !people.contains(d[j][0])) {
					people.add(d[j][0]);
				}
			}
			if(people.size() > max){
				max = people.size();
				badMilk = milk;
			}
		}
		PrintWriter out = new PrintWriter("badmilk.out");
		out.print(max);
		out.close();
		System.out.println(max);
	}
}