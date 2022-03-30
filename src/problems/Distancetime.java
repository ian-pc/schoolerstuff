package problems;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import problems.Graphtime.Vertex;

public class Distancetime<E> {
	
	HashMap<E, Vertex> vertices;

	//creates a new hashmap called vertices
	public Distancetime() {
		vertices = new HashMap<E, Vertex>();
	}

	//creates a new vertice and adds it to the hashmap
	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}

	//creates an edge and connects the variables to eachother
	public void connect(E info1, E info2, Double label) {
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		if (v1 == null || v2 == null) {
			System.out.println("Vertex " + (v1 == null ? v1 : v2).info + " not found");
			return;
		} else {
			Edge e = new Edge(label, v1, v2);

			v1.edges.add(e);
			v2.edges.add(e); 
		}
	}

	//.toString()
	public String toString(Vertex v) {
		return (String) v.info;
	}
	
	//a datatype which represents a relationship between vertices with a name (label)
	private class Edge {
		Double label;
		Vertex v1, v2;

		public Edge(Double label, Vertex v1, Vertex v2) {
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
		}

		//gets the neighbor of a vertice of an edge 
		public Vertex getNeighbor(Vertex v) {
			if (v.info.equals(v1.info)) {
				return v2;
			}
			return v1;
		}

	}

	//datatype of a vertex
	private class Vertex {
		E info;
		HashSet<Edge> edges;
		
		public Vertex(E info) {
			this.info = info;
			edges = new HashSet<Edge>();
		}
	}

	
	//returns the path in the form of an ArrayList of start to end vertices given a hashmap of leading
	public ArrayList<Vertex> BackTrace(HashMap<Vertex, Vertex> leadsTo, Vertex start, Vertex end) {
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		Vertex cur = end;
		while (cur != null) {
			path.add(cur);
			cur = leadsTo.get(cur);
		}
		return path;
	}
	
	public ArrayList<Vertex> DijktrasP(E startinfo, E endinfo) {

		//a hashmap with each key being a vertex which connects to it's value that is also a vertex
		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		ArrayList<Vertex> path = new ArrayList<>();

		Vertex start = vertices.get(startinfo);
		Vertex end = vertices.get(endinfo);
		Vertex cur = start;

		//a list of vertices on the current layer of the graph
		List<Vertex> toVisit = new ArrayList<>();
		toVisit.add(cur);

		leadsTo.put(cur, null);

		while (toVisit.size() != 0) {

			cur = toVisit.remove(0);
			for (Edge s : cur.edges) {
				//if the current edge contains the end vertex, return it's backtrace. 
				if (s.getNeighbor(cur) == end) {

					leadsTo.put(s.getNeighbor(cur), cur);
					ArrayList<Vertex> temp = BackTrace(leadsTo, start, end);
					return (BackTrace(leadsTo, start, end));

				}
				//otherwise, go down a deeper layer and add another variable to leadsTo
				if (leadsTo.containsKey(s.getNeighbor(cur)) == false) {
					toVisit.add(s.getNeighbor(cur));
					leadsTo.put(s.getNeighbor(cur), cur);
				}
			}
		}
		return path;
	}
	
	public static void main(String[] args) {
		Distancetime<String> DT = new Distancetime<>();
		DT.addVertex("H");
		DT.addVertex("I");
		DT.addVertex("E");
		DT.addVertex("P");
		DT.connect("H", "I", 1.0);
		DT.connect("H", "E", 5.0);
		DT.connect("I", "C", 2.0);
		DT.connect("C", "E", 1.0);
	}
}
