package problems;

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

import GPSstue.PriorityQueueDT;

public class Distancetime<E> {
	
	public Distancetime() {
		
	}
	
	public HashMap<E, Vertex> vertices = new HashMap<>();
	
	public class Vertex {

		public E info;
		
		public HashMap<Vertex, Double> neighbors = new HashMap<>();

		public Vertex(E info) {
			this.info = info;
		}
		
		public String neighborsToString() {
			String returnVal = "";
			for (Vertex s : this.neighbors.keySet()) {
				returnVal += " " + s.info;
			}
			return returnVal;
		}

		public ArrayList<E> neighborsToArray() {
			Set tempS = this.neighbors.keySet();
			ArrayList<E> output = new ArrayList<>();
			ArrayList<Vertex> tempA = new ArrayList<>(tempS);
			for (int i = 0; i < tempA.size(); i++) {
				output.add(tempA.get(i).info);
			}
			return output;

		}
	}

	public void add(E info) {
		if (vertices.containsKey(info) == false || vertices.size() == 0) {
			vertices.put(info, new Vertex(info));
		} else {
			vertices.get(info).info = info;
		}
	}

	public Vertex get(E info) {

		return vertices.get(info);

	}

	public void connect(E v1, E v2, double distance) {

		if (!vertices.get(v1).neighbors.containsKey(v2)) {

			vertices.get(v1).neighbors.put(vertices.get(v2), distance);
			vertices.get(v2).neighbors.put(vertices.get(v1), distance);

		}

	}

	public void remove(E info) {
		for (Vertex s : vertices.get(info).neighbors.keySet()) {
			s.neighbors.remove(vertices.get(info));
		}
		vertices.remove(info);
	}

	public String toString(Vertex s) {

		return (String) s.info;

	}

	public int size() {

		return vertices.size();

	}

	public ArrayList<E> toArray() {
		Set tempS = vertices.keySet();

		ArrayList<E> output = new ArrayList<>(tempS);
		return output;
	}

	public ArrayList<Vertex> BackTrace(HashMap<Vertex, Vertex> leadsTo, Vertex start, Vertex end) {
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		Vertex cur = end;
		while (cur != null) {
//			 System.out.println(cur.info);
			path.add(cur);
			cur = leadsTo.get(cur);
		}
		return path;
	}

	public ArrayList<Vertex> tempBackTrace(HashMap<Vertex, Vertex> leadsTo, Vertex start, Vertex end) {
		// back trace the leadsTo list to find the whole path
//
//		for (Vertex d : leadsTo.keySet()) {
//			System.out.println(d.info + " " + leadsTo.get(d).info);
//		}
//		
//		ArrayList<Vertex> path = new ArrayList<Vertex>(); // ArrayList of Vertexes that lead start to end
//		Vertex cur = end;// start with the end pointy
//		while (cur != null) { // if the curent is not the start vertex,
//			System.out.println(cur.info);
//			for (Vertex tempV : cur.neighbors.keySet()) { // look at all neighbors
//				if (tempV == leadsTo.get(cur)) {
//					// if there is a edge in the leadsTo map, that connect the neighbor and the
//					// curent vertex, add it to the path
//					path.add(cur);
//					System.out.println(cur.info);
//				}
//			}
//			cur = leadsTo.get(cur); // move the curent vertex that curent was originated from.
//		}
//
//		for (int i = 0; i < path.size(); i++) {
//			System.out.println("EHe" + path.get(i).info);
//		}
		/////////////////////
//		ArrayList<Vertex> tempBT = new ArrayList<>(leadsTo.keySet());
//		tempBT.add(end);
		
		ArrayList<Vertex> tempA = new ArrayList<>();
		tempA.add(start);
		while (leadsTo.get(tempA.get(tempA.size() - 1)) != null) {
			tempA.add(leadsTo.get(tempA.get(tempA.size() - 1)));
		}
		return tempA;
	}

	public ArrayList<Vertex> djiaktras(E startinfo, E endinfo) {

		HashMap<Vertex, Double> distances = new HashMap<>();
		HashMap<Vertex, Boolean> visited = new HashMap<>();
		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		ArrayList<Vertex> path = new ArrayList<>();

		for (Vertex temp : vertices.values()) {
			distances.put(temp, Double.MAX_VALUE);
			visited.put(temp, false);
		}

		Vertex start = vertices.get(startinfo);
		Vertex end = vertices.get(endinfo);
		// System.out.println(end.info);
		Vertex cur = start;

		distances.replace(start, 0.0);

		PriorityQueueDT<Vertex> toVisit = new PriorityQueueDT<>();
		toVisit.put(cur, distances.get(cur));

		// path.add(cur);
		leadsTo.put(cur, null);

		while (toVisit.size() != 0) {
			// System.out.println(cur.info);
			cur = toVisit.pop();
			visited.put(cur, true);

			if (cur.info == endinfo) {
				ArrayList<Vertex> tempBT = tempBackTrace(leadsTo, start, end);
//				for (int i = 0; i < tempBT.size(); i++) {
//					System.out.println(tempBT.get(i).info);
//				}
				return (tempBT);
			}

			for (Vertex temp : cur.neighbors.keySet()) {
				if (visited.get(temp) == true)
					continue;

				double tempdist = distances.get(cur) + cur.neighbors.get(temp);
//				System.out.println(tempdist);
				if (tempdist < distances.get(temp)) {
					distances.replace(temp, tempdist);
					toVisit.put(temp, distances.get(temp));
					leadsTo.put(cur, temp);
				}
			}

		}
		return path;
	}

	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		// TODO Auto-generated method stub
		Distancetime<String> DT = new Distancetime<>();
		DT.add("H");
		DT.add("I");
		DT.add("E");
		DT.add("C");
		DT.add("P");
		DT.add("D");
		DT.connect("H", "I", 1.0);
		DT.connect("H", "E", 5.0);
		DT.connect("I", "D", 1.0);
		DT.connect("D", "C", 1.0);
		DT.connect("E", "C", 1.0);
		DT.connect("H", "C", 4.0);
		DT.djiaktras("H", "C");
	}

}
