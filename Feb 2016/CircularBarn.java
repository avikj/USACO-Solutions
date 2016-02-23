import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
public class CircularBarn{
	public static void main(String[] args) throws FileNotFoundException{
		Scanner in = new Scanner(new File("cbarn.in"));
		int n = in.nextInt();
		Room[] rooms = new Room[n];
		for(int i = 0; i < n; i++)
			rooms[i] = new Room(in.nextInt());
		long energyUsed = 0;
		for(int i = 0; i < n*2; i++){
			if(rooms[i%n].numCows() > 1)
				rooms[i%n].moveCows(rooms[i%n].numCows()-1, rooms[(i+1)%n]);
		}
		for(Room room : rooms){
			energyUsed += room.getSingleCow().getDistSquared();
		}
		PrintWriter out = new PrintWriter("cbarn.out");
		out.print(energyUsed);
		out.close();
	}
}
class Room{
	private ArrayList<Cow> cows;
	public Room(int numCows){
		cows = new ArrayList<Cow>();
		for(int i = 0; i < numCows; i++)
			cows.add(new Cow());
	}

	public int numCows(){
		return cows.size();
	}

	public void moveCows(int numCows, Room nextRoom){
		ArrayList<Cow> cowsToMove = new ArrayList<Cow>();
		for(int i = 0; i < numCows; i++)
			cowsToMove.add(cows.remove(0));
		nextRoom.addCows(cowsToMove);
	}

	public void addCows(ArrayList<Cow> newCows){
		for(Cow newCow : newCows){
			cows.add(newCow);
			newCow.incrementDist();
		}
	}

	public Cow getSingleCow(){
		return cows.get(0);
	}
}

class Cow{
	public int distMoved;
	public Cow(){
		distMoved = 0;
	}
	public void incrementDist(){
		distMoved++;
	}
	public int getDistSquared(){
		return distMoved*distMoved;
	}
}