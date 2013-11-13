import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class ArrayHopper {
	//The input array
	public ArrayList<Integer> input = new ArrayList();
	//A map of the current lowest path from a specific node
	HashMap<Integer,Path> map = new HashMap<Integer,Path>();
	
	
	/**
	 * @param args Should input a filename with a list of integers
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.print("Incorrect number of args");
		} else {

			String filename = args[0];
			int i = 0;
			try {
				Scanner in = new Scanner(new FileReader(filename));
				new ArrayHopper(in); // run arrayHopper

			} catch (FileNotFoundException e) {
				System.err.print("File not found: " + filename);
			}
		}

	}
	
	
	/** 
	 * The meat of the program. Reads in the file line by line, storing to a gloal array.
	 * Then runs the countHops algorithm starting at the 0 index.
	 * It then calls printOutput to properly format the output
	 * @param in A scanner of the file
	 */
	public ArrayHopper(Scanner in) {
		int i = 0;
		while (in.hasNext()) {
			try {
			i = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				System.err.print("Number not in correct format");
			}
			input.add(i);
		}
		if (input.size() == 0) //If the file is empty, simply return failure(though this was undefined in the specifications)
			System.out.print("failure");
		else {
			Path p = countHops(0, new Path());
			printOutput(p);
		}
	}
	
	
	/**
	 * Takes in a Path, and prints out the indexes that contain the shortest path one-by-one
	 * @param p The shortest-path to the end node
	 */
	public void printOutput(Path p){
		StringBuffer sb = new StringBuffer();
		if (p.hops == Integer.MAX_VALUE)
			System.out.print("failure");
		else {
			for (int i : p.indexes){
				sb.append(i + ", ");
			}
			sb.append("out");
			System.out.print(sb.toString());
		}
	}
	
	/** 
	 * A helper class that is used to keep track of the path we are iterating over, as
	 * well as the number of hops along that path
	 * The reason we have a seperate  variable for hops rather than indexes.size(), is because we want to set
	 * an infinite path length that is bad, to use for no-path finds and dead-end path finds
	 * 
	 *
	 */
	public class Path {
		ArrayList<Integer> indexes;
		int hops;
		public Path(Path p, int i) {
			indexes = new ArrayList<Integer>();
			indexes.addAll(p.indexes);
			indexes.add(i);
			hops = indexes.size();
		}

		
		/**
		 * Used to construct the initial  array that is passed in
		 */
		public Path(){
			this.indexes = new ArrayList<Integer>();
			indexes.add(0);
			hops = 0;
		}
		
		/**
		* Construct that allows us to create a "greatest hops" path, that will be used to check
		* If a path is false, or a path is undesirable
		* @param b
		*/
		public Path(boolean b) {
			this.indexes = new ArrayList<Integer>();
			hops = Integer.MAX_VALUE;
		}
	}

	/**
	 * A recursive algorithm that looks at a node and calculates every node that it can go to from that node
	 * passing in a path and a index. If a node has been visited already, and the path cost to that node is less than
	 * the current calculated path cost, it skips that node
	 * When it calculates all the paths to the end from a current node, it finds the lowest path cost
	 * and returns it.
	 * @param index The current index we are looking at
	 * @param p the current path cost to that index we are looking at
	 * @return the lowest path cost from this index to the end
	 */
	public Path countHops(int index, Path p) {
		int min = Integer.MAX_VALUE;
		//Set the biggest array size, which will always be overwritten by something else
		// Or returned if no path is possible or a better path exists
		Path returnPath = new Path(false); 
		if (map.get(index) == null || map.get(index).hops > p.hops) {
			map.put(index, p);
		} else { //there is already a better path to this node, so don't try to find the path anymore
				//Basically a short circuit
			return returnPath; 
		}

		int value = input.get(index);
		for (int i = index + 1; i <= index + value; i++){
			if (i <= input.size() - 1){ //If this index is less than the total array size
				Path test = countHops(i,new Path(p,i));
				if (test.hops < min) { //If this path is better than the current-best path, update the current best-path
					min = test.hops;
					returnPath = test;
				}
			} else {
				return p; // If we've reached past the end of the input array, return the current path
			}
		}
		return returnPath; // If you haven't reached the end, then you've reached a dead-end, in which case you'll return the max value, and this will not be taken as a best path
	}
}
