import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
public class Lights{
	static Room[][] rooms;
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("lightson.in"));
		int N = in.nextInt();
		rooms = new Room[N][N];
		for(int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) 
				rooms[i][j] = new Room();
		rooms[0][0].on = true;
		int M = in.nextInt();
		for(int i = 0; i < M; i++){
			int x = in.nextInt()-1;
			int y = in.nextInt()-1;
			int a = in.nextInt()-1;
			int b = in.nextInt()-1;
			rooms[x][y].switches.add(new Coordinate(a, b));
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		while(floodfill(0, 0, new boolean[N][N]));
	//	floodfill(0, 0, new boolean[N][N]);
		int result = 0;
		for (int i = 0; i<rooms.length; i++) {
			for (int j = 0; j<rooms[i].length; j++) {
				if(rooms[i][j].on)
					result++;
			}
		}
		//System.out.println(floodfill(0, 0, new boolean[N][N]).value);
		PrintWriter out = new PrintWriter("lightson.out");
		out.print(result);
		out.close();
	}
	public static boolean floodfill(int x, int y, boolean[][] visited){
		boolean result = false;
		visited[x][y] = true;
		Room room = rooms[x][y];
		for(Coordinate c : room.switches)
			if( ! rooms[c.x][c.y].on){
				rooms[c.x][c.y].on = true;
				result = true;
			}
		if(x > 0 && !visited[x-1][y] && rooms[x-1][y].on)
			floodfill(x-1, y, visited);
		if(y > 0 && !visited[x][y-1] && rooms[x][y-1].on)
			floodfill(x, y-1, visited);
		if(x< visited.length-1 && !visited[x+1][y] && rooms[x+1][y].on)
			floodfill(x+1, y, visited);
		if(y < visited[0].length-1 && !visited[x][y+1] && rooms[x][y+1].on)
			floodfill(x, y+1, visited);
		return result;

	}
}
class Coordinate{
	public int x;
	public int y;
	public Coordinate(int a, int b){
		x=a;
		y=b;
	}
}
class Room{
	public boolean on;
	public ArrayList<Coordinate> switches;
	public Room(){
		on=false;
		switches = new ArrayList<Coordinate>();
	}
}