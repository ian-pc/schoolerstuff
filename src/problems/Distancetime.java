package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Distancetime<E> {

	private HashMap<E, Vertex> vertices = new HashMap<>();

	public class Vertex {

		public E info;
		public HashMap<Vertex, Double> neighbors = new HashMap<>();

		public Vertex(E info) {
			this.info = info;
		}

		public String neighborsToString() {
			String returnVal = "";
			for (Vertex s : this.neighbors.keySet()) {
				returnVal += s.info;
			}
			return returnVal;
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
//		System.out.println(vertices.get(v2));
		vertices.get(v1).neighbors.put(vertices.get(v2), distance);
		vertices.get(v2).neighbors.put(vertices.get(v1), distance);
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

	public ArrayList<Vertex> BackTrace(HashMap<Vertex, Vertex> leadsTo, Vertex start, Vertex end) {
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		Vertex cur = end;
		while (cur != null) {
			// System.out.println(cur);
			path.add(cur);
			cur = leadsTo.get(cur);
		}
		return path;
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
			//System.out.println(cur.info);
			cur = toVisit.pop();
			visited.put(cur, true);
			
			if (cur.info == endinfo) {
				ArrayList<Vertex> temp = BackTrace(leadsTo, start, end);
				System.out.println(distances.get(cur));
				for (int i = 0; i < temp.size(); i++) {
					System.out.println(temp.get(i).info);
				}
				return (BackTrace(leadsTo, start, end));
			}
			
			for (Vertex temp : cur.neighbors.keySet()) {
				if (visited.get(temp) == true) continue;
				
				double tempdist = distances.get(cur) + cur.neighbors.get(temp);
				//System.out.println(tempdist);
				if (tempdist < distances.get(temp)) {
					distances.replace(temp, tempdist);
					toVisit.put(temp, distances.get(temp));
					leadsTo.put(cur, temp);
				}
			}

		}
		return path;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Distancetime<String> DT = new Distancetime<>();
		DT.add("H");
		DT.add("I");
		DT.add("E");
		DT.add("C");
		DT.add("P");
		DT.connect("H", "I", 1.0);
		DT.connect("H", "E", 5.0);
		DT.connect("I", "C", 2.0);
		DT.connect("C", "E", 1.0);
		DT.connect("H", "C", 4.0);
		DT.djiaktras("H", "C");
	}

}
