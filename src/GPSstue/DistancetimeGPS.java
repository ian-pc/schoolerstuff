package GPSstue;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import problems.Distancetime;

public class DistancetimeGPS {

	public DistancetimeGPS(String fileloc) throws FileNotFoundException {// reading the file on instantiation

		HashMap<String, Location> tempHM = new HashMap<>();
		// temporary hashmap the stores the name of the point and the location

		Scanner sc = new Scanner(new File("Files/GPSstuff.txt")); // x y name connections
		// using a scanner to go line by line on the file

		while (sc.hasNextLine()) {
			String[] tempS = sc.nextLine().split(" ");
			// splitting the line by it's spaces
			Location tempL = new Location(Integer.parseInt(tempS[0]), Integer.parseInt(tempS[1]), tempS[2]);
			// the first three words of each line indicate it's x, y, and name
			this.add(tempL);
			// add to the graph.
			tempHM.put(tempS[2], tempL);
			// put the name and location into the temporary hashmap
		}
		sc.close();
		Scanner sc2 = new Scanner(new File("Files/GPSstuff.txt")); // x y name connections
		while (sc2.hasNextLine()) {
			String[] tempS = sc2.nextLine().split(" ");
			// if the line has tempS in it,
			for (int i = 3; i < tempS.length; i += 2) {
				this.connect(tempHM.get(tempS[2]), tempHM.get(tempS[i]), Double.parseDouble(tempS[i + 1]));
			}
			// every two words after the first three of each line, indicates the name of
			// it's neighbor and it's distance, the two are then connected
		}
		sc2.close();
	}

	public HashMap<Location, Vertex> vertices = new HashMap<>();

	public class Vertex {

		public Location info;

		public HashMap<Vertex, Double> neighbors = new HashMap<>();
		// each vertex has a hashmap of neighbors: first value the neighbor, second
		// value the distance

		public Vertex(Location info) {
			this.info = info;
		}

		public String neighborsToString() { // used for writing into the file as well as testing
			String returnVal = "";
			for (Vertex s : this.neighbors.keySet()) {
				returnVal += " " + s.info;
			}
			return returnVal;
		}

		public ArrayList<Location> neighborsToArray() { // used for finding a vertex's neighbors
			Set tempS = this.neighbors.keySet();
			ArrayList<Location> output = new ArrayList<>();
			ArrayList<Vertex> tempA = new ArrayList<>(tempS);
			for (int i = 0; i < tempA.size(); i++) {
				output.add(tempA.get(i).info);
			}
			return output;

		}
	}

	public void add(Location info) { // adds the Locaiton to the hashmap of vertices info, name
		if (vertices.containsKey(info) == false || vertices.size() == 0) {
			vertices.put(info, new Vertex(info));
		} else {
			vertices.get(info).info = info;
		}
	}

	public Vertex get(Location info) {

		return vertices.get(info);

	}

	public void connect(Location v1, Location v2, double distance) {

		if (!vertices.get(v1).neighbors.containsKey(v2)) {

			vertices.get(v1).neighbors.put(vertices.get(v2), distance);
			vertices.get(v2).neighbors.put(vertices.get(v1), distance);

		}

	}

	public void remove(Location info) {
		for (Vertex s : vertices.get(info).neighbors.keySet()) {
			s.neighbors.remove(vertices.get(info));
		}
		// traverses the vertice's neighbrr to remove the neighbor as a friend
		vertices.remove(info);
		// removes the vertice
	}

	public String toString(Vertex s) {

		return s.info.toString();

	}

	public int size() {

		return vertices.size();

	}

	public ArrayList<Location> toArray() { // used in the GPS
		Set tempS = vertices.keySet();

		ArrayList<Location> output = new ArrayList<>(tempS);
		return output;
	}

	public ArrayList<Vertex> tempBackTrace(HashMap<Vertex, Vertex> leadsTo, Vertex start, Vertex end) {
		// back trace the leadsTo list to find the whole path

		ArrayList<Vertex> tempA = new ArrayList<>();
		tempA.add(start);
		while (leadsTo.get(tempA.get(tempA.size() - 1)) != null) {
			tempA.add(leadsTo.get(tempA.get(tempA.size() - 1)));
		}
		// traverses backwards through the leads to hashmap
		tempA.remove(tempA.size() - 1);
		tempA.add(end);
		//fixing a bug where the last value is not equal to the end value. 
		return tempA;
	}
	
	public ArrayList<Vertex> djiaktras(Location startinfo, Location endinfo) {

		HashMap<Vertex, Double> distances = new HashMap<>();
		// distances from each vertice to the start
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		// each point visited and the distance from the last point
		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		// a hashmap of each connection used to find the way back after djiakstras
		ArrayList<Vertex> path = new ArrayList<>();
		// output

		for (Vertex temp : vertices.values()) {
			distances.put(temp, Double.MAX_VALUE);
			visited.put(temp, false);
		}
		// each distance is default to the maximum inorder to find which path is the
		// shortest by equating to something lower each time

		Vertex start = vertices.get(startinfo);
		Vertex end = vertices.get(endinfo);
		Vertex cur = start;

		distances.replace(start, 0.0);
		// distance from the start to the start is 0

		PriorityQueueDT<Vertex> toVisit = new PriorityQueueDT<>();
		// new proiority queuein order
		toVisit.put(cur, distances.get(cur));
		// each vertice to the current one must be visited

		leadsTo.put(cur, null);
		// inorder to indicate the end

		while (toVisit.size() != 0) {
			cur = toVisit.pop();
			// set current to the top value of tovisit and then remove it
			visited.put(cur, true);
			// visited that point (to not visit it again)

			if (cur.info == endinfo) {
				ArrayList<Vertex> tempBT = tempBackTrace(leadsTo, start, end);
				return (tempBT);
			}
			// if at the end, end

			for (Vertex temp : cur.neighbors.keySet()) {
				if (visited.get(temp) == true)
					continue;
				// if already visited, skip

				double tempdist = distances.get(cur) + cur.neighbors.get(temp);
				if (tempdist < distances.get(temp)) {
					distances.replace(temp, tempdist);
					toVisit.put(temp, distances.get(temp));
					leadsTo.put(cur, temp);
				}
				// if the distance is less than the current one (to the start), make that the
				// better path and replace the distance, put it into leadsTo and add its
				// neighbors to to visit
			}

		}
		return path;
	}

}