package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import problems.Graphtime.Vertex;

public class Labeledtime<E, T> {

	HashMap<E, Vertex> vertices;
	int tempiter = 0;

	public Labeledtime() {
		vertices = new HashMap<E, Vertex>();
	}

	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}

	public void connect(E info1, E info2, T label) {
		tempiter++;
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		//System.out.println(v1.info + " " + v2.info + "iter: " + tempiter);

		if (v1 == null || v2 == null) {
			System.out.println("Vertex " + (v1 == null ? v1 : v2).info + " not found");
			return;
		} else {
			Edge e = new Edge(label, v1, v2);

			v1.edges.add(e);
			v2.edges.add(e); 
		}
		
		
		
	}

	public String toString(Vertex v) {
		return (String) v.info;
	}
	
	private class Edge {
		T label;
		Vertex v1, v2;

		public Edge(T label, Vertex v1, Vertex v2) {
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
		}

		public Vertex getNeighbor(Vertex v) {
			if (v.info.equals(v1.info)) {
				return v2;
			}
			return v1;
		}

	}

	private class Vertex {
		E info;
		HashSet<Edge> edges;
		
		public Vertex(E info) {
			this.info = info;
			edges = new HashSet<Edge>();
		}
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
	
	public ArrayList<Vertex> BFS(E startinfo, E endinfo) {

		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		ArrayList<Vertex> path = new ArrayList<>();

		Vertex start = vertices.get(startinfo);
		Vertex end = vertices.get(endinfo);
		//System.out.println(end.info);
		Vertex cur = start;

		List<Vertex> toVisit = new ArrayList<>();
		toVisit.add(cur);

		// path.add(cur);
		leadsTo.put(cur, null);

		while (toVisit.size() != 0) {

			cur = toVisit.remove(0);
			// System.out.println("curr" + cur.info);
			for (Edge s : cur.edges) {
				System.out.println("ye");
				// System.out.println("neightbor: "+ s.info);
				if (s.getNeighbor(cur) == end) {

					leadsTo.put(s.getNeighbor(cur), cur);
					ArrayList<Vertex> temp = BackTrace(leadsTo, start, end);
					for (int i = 0; i < temp.size(); i++) {
						System.out.println(temp.get(i).info);
					}
					return (BackTrace(leadsTo, start, end));

				}
				if (leadsTo.containsKey(s.getNeighbor(cur)) == false) {
					toVisit.add(s.getNeighbor(cur));
					leadsTo.put(s.getNeighbor(cur), cur);
				}
			}
		}
		return path;
	}
	
	
	public static void main(String[] args) {


	}
}
