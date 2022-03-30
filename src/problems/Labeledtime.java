package problems;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import problems.Graphtime.Vertex;

public class Labeledtime<E, T> {

	HashMap<E, Vertex> vertices;

	//creates a new hashmap called vertices
	public Labeledtime() {
		vertices = new HashMap<E, Vertex>();
	}

	//creates a new vertice and adds it to the hashmap
	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}

	//creates an edge and connects the variables to eachother
	public void connect(E info1, E info2, T label) {
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
		T label;
		Vertex v1, v2;

		public Edge(T label, Vertex v1, Vertex v2) {
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
	
	//BFS
	public ArrayList<Vertex> BFS(E startinfo, E endinfo) {

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
	
	//returns a BFS but with an array of the info instead of the vertex itself
	public ArrayList<E> infoBFS(E startinfo, E endinfo) {

		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		ArrayList<Vertex> path = new ArrayList<>();

		Vertex start = vertices.get(startinfo);
		Vertex end = vertices.get(endinfo);
		Vertex cur = start;

		List<Vertex> toVisit = new ArrayList<>();
		toVisit.add(cur);

		leadsTo.put(cur, null);

		ArrayList<E> BackTraceTempT= new ArrayList<>();
		while (toVisit.size() != 0) {

			cur = toVisit.remove(0);
			
			for (Edge s : cur.edges) {

				if (s.getNeighbor(cur) == end) {

					//stores the infos of backtrace in backtracetempT
					leadsTo.put(s.getNeighbor(cur), cur);
					ArrayList<Vertex> temp = BackTrace(leadsTo, start, end);
					ArrayList<Vertex> BackTraceTemp = (BackTrace(leadsTo, start, end));
					for (int i = 0; i < BackTraceTemp.size(); i++) {
						BackTraceTempT.add(BackTraceTemp.get(i).info);
					}
					return BackTraceTempT;
				}
				if (leadsTo.containsKey(s.getNeighbor(cur)) == false) {
					toVisit.add(s.getNeighbor(cur));
					leadsTo.put(s.getNeighbor(cur), cur);
				}
			}
		}
		return null;
	}
	
	//returns a BFS but with a pair that has the info of the vertices as well as the name of the edge that they are connected by. 
	public ArrayList<AbstractMap.SimpleEntry<E, T>> moviesBFS(E startinfo, E endinfo) {

		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		ArrayList<Vertex> path = new ArrayList<>();

		Vertex start = vertices.get(startinfo);
		Vertex end = vertices.get(endinfo);
		Vertex cur = start;

		List<Vertex> toVisit = new ArrayList<>();
		toVisit.add(cur);

		leadsTo.put(cur, null);

		ArrayList<AbstractMap.SimpleEntry<E, T>> BackTraceTempT= new ArrayList<>();
		HashMap<Edge, T> templist = new HashMap<>();
		Iterator templistIter;
		while (toVisit.size() != 0) {

			cur = toVisit.remove(0);
			for (Edge s : cur.edges) {

				templist.put(s, s.label);
				if (s.getNeighbor(cur) == end) {
					//uses templistiter to iterate through the hashmap templist which stores the movie and the actor. 
					templistIter = templist.entrySet().iterator();
					leadsTo.put(s.getNeighbor(cur), cur);
					ArrayList<Vertex> temp = BackTrace(leadsTo, start, end);
					ArrayList<Vertex> BackTraceTemp = (BackTrace(leadsTo, start, end));
					//returns the movie and the actor it's connected to. 
					for (int i = 0; i < BackTraceTemp.size(); i++) {
						BackTraceTempT.add(new AbstractMap.SimpleEntry(BackTraceTemp.get(i).info, templistIter.next().toString().split("=")[1]));
					}
					return BackTraceTempT;
				}
				if (leadsTo.containsKey(s.getNeighbor(cur)) == false) {
					templist.remove(s);
					toVisit.add(s.getNeighbor(cur));
					leadsTo.put(s.getNeighbor(cur), cur);
				}
			}
		}
		return null;
	}
	
	
	//finds the furthest vertex from a point in a graph
	public E findFurthest(E info) {
		E furthest = null;
		
		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		ArrayList<Vertex> path = new ArrayList<>();

		Vertex start = vertices.get(info);
		Vertex cur = start;

		PriorityQueue<Vertex> toVisit = new PriorityQueue<>();
		toVisit.put(cur);
		leadsTo.put(cur, null);

		//unlike BFS. keeps going until there are no edges to visit and that is the return val
		//instead of stopping at an end value, it will keep going until there is no vertice to visit (toVisit) 
		while (toVisit.size() != 0) {

			cur = toVisit.remove(0);
			furthest = cur.info;
			for (Edge s : cur.edges) {
				if (leadsTo.containsKey(s.getNeighbor(cur)) == false) {
					toVisit.add(s.getNeighbor(cur));
					leadsTo.put(s.getNeighbor(cur), cur);
				}
			}
		}
		
		return furthest;
	}
	
	
	//is same as findFurthest but returns the distance away isntead. 
	public ArrayList<E> findDistAway(int dist, E info) {
		ArrayList<E> returnVal = new ArrayList<>();
		
		
		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		ArrayList<Vertex> path = new ArrayList<>();

		Vertex start = vertices.get(info);
		Vertex cur = start;

		List<Vertex> toVisit = new ArrayList<>();
		toVisit.add(cur);

		leadsTo.put(cur, null);

		int tempCount = 0;
		while (toVisit.size() != 0) {

			cur = toVisit.remove(0);
			for (Edge s : cur.edges) {
				if (leadsTo.containsKey(s.getNeighbor(cur)) == false) {
					toVisit.add(s.getNeighbor(cur));
					leadsTo.put(s.getNeighbor(cur), cur);
				}
				if (tempCount == dist) {
					returnVal.add(s.getNeighbor(cur).info);
				}
			}
			if (tempCount == dist) {
				break;
			}
			tempCount++;
		}
		
		return returnVal;
	}
	
	public static void main(String[] args) {

		

	}
}
