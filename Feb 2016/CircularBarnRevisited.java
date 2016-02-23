import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

 public class CircularBarnRevisited{
 	public static void main(String[] args) throws FileNotFoundException {
 		Scanner in = new Scanner(new File("cbarn2.in"));
 		int n = in.nextInt();
 		int k = in.nextInt();
 		Room[] rooms = new Room[n];
 		int[] starts = new int[k];
 		for(int i = 0; i < n; i++){
 			rooms[i] = new Room(rooms, i, in.nextInt());
 		}
 		int[] indices = new int[n];
 		for(int i= 0; i < indices.length; i++)
 			indices[i] = i;
 		getCombinations(rooms, starts, indices, n, k);
 		PrintWriter out = new PrintWriter("cbarn2.out");
 		out.println(distance(rooms, starts));
 		out.close();
 	}

 	public static long distance(Room[] rooms, int[] starts){
 		int j = 0;
 		int c = 0;
 		int result = 0;
 		for(int i = starts[0]%rooms.length; i < rooms.length + starts[0]%rooms.length; i++){
 			if(contains(rooms.length, starts, i%rooms.length))
 				c = 0;
 			result += (c++)*rooms[i%rooms.length].getCap();
 		}
 		return result;
 	}

 	public static boolean contains(int n, int[] a, int x){
 		for(int i : a)
 			if(i%n == x)
 				return true;
 		return false;
 	}

 	static void combinationUtil(Room[] rooms, int[] starts, int arr[], int n, int r, int index, int data[], int i)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            if(distance(rooms, data) < distance(rooms, starts)){
            	for(int d = 0; d < starts.length; d++)
            		starts[d] = data[d];
            }
        return;
        }
 
        // When no more elements are there to put in data[]
        if (i >= n)
        return;
 
        // current is included, put next at next location
        data[index] = arr[i];
        combinationUtil(rooms, starts, arr, n, r, index+1, data, i+1);
 
        // current is excluded, replace it with next (Note that
        // i+1 is passed, but index is not changed)
        combinationUtil(rooms, starts, arr, n, r, index, data, i+1);
    }
 
    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static void getCombinations(Room[] rooms, int[] starts, int arr[], int n, int r)
    {
        // A temporary array to store all combination one by one
        int data[]=new int[r];
 
        // Print all combination using temprary array 'data[]'
        combinationUtil(rooms, starts, arr, n, r, 0, data, 0);
    }
 }

 class Room implements Comparable<Room>{
 	int capacity;
 	int index;
 	Room[] rooms;
 	public Room(Room[] r, int i, int cap){
 		rooms = r;
 		capacity = cap;
 		index = i;
 	}
 	public int getCap(){
 		return capacity;
 	}
 	public int getIndex(){
 		return index;
 	}
 	public long rank(){
 		long r = 0;
 		int c = 1;
 		for(int i = index; i < rooms.length+index; i++){
 			r+= rooms[i%rooms.length].capacity/(c++);
 		}
 		return r;
 	}
 	public int compareTo(Room other){
 		return (int)(rank() - other.rank());
 	}
 }