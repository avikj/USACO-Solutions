import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
public class HighCard{
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("highcard.in"));
		int n = in.nextInt();
		int[] eVals = new int[n];
		for(int i = 0; i < n; i++)
			eVals[i]=in.nextInt()-1;
		Arrays.sort(eVals);
		Card[] cards = new Card[n*2];
		int eIndex = 0;
		for(int i = 0; i < 2*n; i++){
			if(eIndex<n && eVals[eIndex]==i){
				cards[i] = new Card(false, i);
				eIndex++;
			}
			else{
				cards[i] = new Card(true, i);
			}
		}

		int ecl = 0;
		int result = 0;
		for(int i = 0; i < 2*n; i++){
			if(!cards[i].bessie)
				ecl++;
			else if(ecl > 0){
				ecl--;
				result++;
			}
		}
		PrintWriter out = new PrintWriter("highcard.out");
		out.print(result);
		out.close();
	}
}
class Card{
	boolean bessie;
	int val;
	public Card(boolean b, int v){
		bessie=b;
		val=v;
	}
}