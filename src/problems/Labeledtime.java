package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Labeledtime<E, T> {

	HashMap<E, Vertex> vertices;

	public Labeledtime() {
		vertices = new HashMap<E, Vertex>();
	}

	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}

	public void connect(E info1, E info2, T label) {
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);

		if (v1 == null || v2 == null) {
			System.out.println("Vertex " + (v1 == null ? v1 : v2).toString() + " not found");
			return;
		}

		Edge e = new Edge(label, v1, v2);

		v1.edges.add(e);
		v2.edges.add(e);
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

	
	public static void main(String[] args) {


	}
}
