package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import problems.Graphtime.Vertex;

public class Graphtime<E> {

	private HashMap<E, Vertex> graph = new HashMap<>();

	public class Vertex {

		public E info;
		public Set<Vertex> neighbors = new HashSet<Vertex>();

		public Vertex(E info) {
			this.info = info;
		}
    
    public String neighborsToString() {
      String returnVal = "";
      for (Vertex s : this.neighbors) {
        returnVal += s.info;
      }
      return returnVal;
    }
	
	}

	public void add(E info) {
		if (graph.containsKey(info) == false || graph.size() == 0) {
			graph.put(info, new Vertex(info));
		} else {
			graph.get(info).info = info;
		}
	}

	public Vertex get(E info) {

		return graph.get(info);

	}

	public void connect(E v1, E v2) {
		graph.get(v1).neighbors.add(graph.get(v2));
		graph.get(v2).neighbors.add(graph.get(v1));
	}

	public void remove(E info) {
		for (Vertex s : graph.get(info).neighbors) {
			s.neighbors.remove(graph.get(info));
		}
		graph.remove(info);
	}

	
	public String toString(Vertex s) {
		
		return (String) s.info;

	}

	public int size() {

		return graph.size();

	}

	public Graphtime() {

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

		Vertex start = graph.get(startinfo);
		Vertex end = graph.get(endinfo);
		//System.out.println(end.info);
		Vertex cur = start;

		List<Vertex> toVisit = new ArrayList<>();
		toVisit.add(cur);

		// path.add(cur);
		leadsTo.put(cur, null);

		while (toVisit.size() != 0) {

			cur = toVisit.remove(0);
			// System.out.println("curr" + cur.info);
			for (Vertex s : cur.neighbors) {
				// System.out.println("neightbor: "+ s.info);
				if (s.info == endinfo) {

					leadsTo.put(s, cur);
					ArrayList<Vertex> temp = BackTrace(leadsTo, start, end);
					for (int i = 0; i < temp.size(); i++) {
						System.out.println(temp.get(i).info);
					}
					return (BackTrace(leadsTo, start, end));

				}
				if (leadsTo.containsKey(s) == false) {
					toVisit.add(s);
					leadsTo.put(s, cur);
				}
			}
		}
		return path;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graphtime<String> graph = new Graphtime<>();
		graph.add("Ian");
		graph.add("James");
		graph.add("Tiffany");
		graph.add("Jason");
		graph.add("Ivy");
		graph.add("David");
		graph.add("justin");

		graph.connect("Ian", "James");
		graph.connect("James", "Tiffany");
		graph.connect("James", "Jason");
		graph.connect("Jason", "Ivy");
		graph.connect("Ivy", "Tiffany");
		graph.connect("Tiffany", "David");
		graph.connect("David", "justin");

		graph.BFS("Ian", "justin");
	}

}
