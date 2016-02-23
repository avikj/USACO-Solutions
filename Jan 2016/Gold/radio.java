import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
public class radio{
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("radio.in"));
		int n = in.nextInt();
		int m = in.nextInt();
		int[][] dp = new int[n+1][m+1];
		for(int i = 0; i <= n; i++)
			for(int j = 0; j <= m; j++)
				dp[i][j] = -1;
		Coord fc = new Coord(in.nextInt(), in.nextInt());
		Coord bc = new Coord(in.nextInt(), in.nextInt());
		String fpath = in.next();
		String bpath = in.next();
		PrintWriter out = new PrintWriter("radio.out");
		out.println(getMinEnergy(fc, bc, fpath, bpath, 0, 0, dp));
		out.close();
	}

	public static int getMinEnergy(Coord fc, Coord bc, String fpath, String bpath, int fi, int bi, int[][] dp){
		if(fi == fpath.length() && bi == bpath.length())
			return 0;
		if(dp[fi][bi] != -1)
			return dp[fi][bi];
		if(fi == fpath.length()){
			
			int result = fc.distSq(bc.mutate(bpath.charAt(bi)))+getMinEnergy(fc, bc.mutate(bpath.charAt(bi)), fpath, bpath, fi, bi+1, dp);
			dp[fi][bi] = result;
			return result;
		}
		if(bi == bpath.length()){
			int result = fc.mutate(fpath.charAt(fi)).distSq(bc)+getMinEnergy(fc.mutate(fpath.charAt(fi)), bc, fpath, bpath, fi+1, bi, dp);
			dp[fi][bi] = result;
			return result;
		}

		int bothMove = fc.mutate(fpath.charAt(fi)).distSq(bc.mutate(bpath.charAt(bi)))+getMinEnergy(fc.mutate(fpath.charAt(fi)), bc.mutate(bpath.charAt(bi)), fpath, bpath, fi+1, bi+1, dp);
		int justF = fc.mutate(fpath.charAt(fi)).distSq(bc)+getMinEnergy(fc.mutate(fpath.charAt(fi)), bc, fpath, bpath, fi+1, bi, dp);
		int justB = fc.distSq(bc.mutate(bpath.charAt(bi)))+getMinEnergy(fc, bc.mutate(bpath.charAt(bi)), fpath, bpath, fi, bi+1, dp);
		int result = Math.min(Math.min(justF, justB), bothMove);
		dp[fi][bi] = result;
		return result;

	}

	static class Coord{
		public int x; 
		public int y;
		public Coord(int a, int b){
			x = a;
			y = b;
		}
		public Coord(Coord o){
			x = o.x;
			y = o.y;
		}
		public Coord mutate(char c){
			Coord o = new Coord(this);
			switch(c){
				case 'N':
					o.y++; break;
				case 'E':
					o.x++;break;
				case 'S':
					o.y--;break;
				case 'W':
					o.x--;
			}
			return o;
		}
		public void unMutate(char c){
			switch(c){
				case 'N':
					y--; break;
				case 'E':
					x--;break;
				case 'S':
					y++;break;
				case 'W':
					x++;
			}
		}
		public int distSq(Coord o){
			return (y-o.y)*(y-o.y)+(x-o.x)*(x-o.x);
		}
	}
}