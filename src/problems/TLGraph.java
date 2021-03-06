package problems;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TLGraph<E,T> {
	
	HashMap<E, Vertex> g = new HashMap<>();
	
	private class Vertex{
		E info;
		HashSet<Edge> neighbors = new HashSet();
		
		public Vertex(E info) {
			this.info = info;
		}
		
		public String toString() {
			return (String)info;
		}
		
	}
	
	private class Edge{
		Vertex v1, v2;
		T info;
		public Edge(Vertex v1, Vertex v2, T info) {
			this.v1 = v1;
			this.v2 = v2;
			this.info = info;
		}
		
		public String toString() {
			return v1.toString() +" - "+info+" - "+v2.toString();
		}
		
		public String infoToString() {
			return (String)info;
		}
		public Vertex getOtherVertex(Vertex v) {
			if(v == v1) {
				return v2;
			}else {
				return v1;
			}
		}
	}
	
	public void addVertex(E info) {
		g.put(info, new Vertex(info));
		
	}

	public void connect(E info1, E info2, T label) {
		if(g.get(info1)==null || g.get(info2)==null) {
			throw (new NullPointerException());
		}else {
			Vertex v1 = g.get(info1);
			Vertex v2 = g.get(info2);
			Edge lableEdge = new Edge(v1,v2, label);
			v1.neighbors.add(lableEdge);
			v2.neighbors.add(lableEdge);
		}
		
	}
	
	public Vertex getVertex(E info) {
		return g.get(info);
		
	}
	
	public HashSet getNeighbors(E info) {
		return getVertex(info).neighbors;
	}
	
	public String toString() {
		
		String s = "";
		
		for(E key: g.keySet()) {
			s+= key + "\n";
			
			for(Edge e: g.get(key).neighbors){
				s+= "["+e.getOtherVertex(g.get(key))+" "+e.info+"] ";
			}
			s+="\n";
		}
		
		
		return s;
	}
	
	public String toStringS() {
		
		String s = "";
		
		for(E key: g.keySet()) {
			s+= key + " --> ";
			
			for(Edge e: g.get(key).neighbors){
				s+= e.getOtherVertex(g.get(key))+" ";
			}
			s+="\n";
		}
		
		
		return s;
	}
	
	public void remove(E info) {
		for(Edge v : g.get(info).neighbors) {
			v.getOtherVertex( g.get(info)).neighbors.remove(v);
		}
		g.remove(info);
	}
	
	public int size() {
		return g.size();
	}
	
	public String BreadthFirst(E startinfo, E endinfo){
		
		Vertex start = getVertex(startinfo);
		Vertex end = getVertex(endinfo);
		
		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		Vertex curr=start;
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(curr);
		leadsTo.put(curr, null);
		while(toVisit.size()!= 0) {
			curr=toVisit.remove(0);
			for(Edge e: curr.neighbors) {
				if(e.getOtherVertex(curr) == end) {
					leadsTo.put(e.getOtherVertex(curr),curr);
					return PrintBackTrace(BackTrace(leadsTo,start,end), (String)startinfo);
				}
				if(!leadsTo.containsKey(e.getOtherVertex(curr))) {
					toVisit.add(e.getOtherVertex(curr));
					leadsTo.put(e.getOtherVertex(curr), curr);
				}
			}
		}
		return "Cannot Find the Path";
		
	}
	
	public ArrayList<Edge> BackTrace(HashMap<Vertex,Vertex> map, Vertex start, Vertex end){
		
		ArrayList<Edge> path = new ArrayList<Edge>();
		Vertex curr = end;
		while(curr != null) {
			
			for(Edge e : curr.neighbors) {
				if(e.getOtherVertex(curr)== map.get(curr)) {
					path.add(0,e);
				}
			}
			curr = map.get(curr);
		}
		return path;
	}
	
	public String PrintBackTrace(ArrayList<Edge> path, String start) {
		String ans = "";
		
		Vertex curr = g.get(start);
		
		for(Edge e: path) {
			ans+=curr.toString()+" - ";
			ans+=e.infoToString()+" - ";
			curr = e.getOtherVertex(curr);
		}
		ans+=curr.toString();
		return ans;
		
	}
	
//	public void writeFile(String FileName) throws IOException {
//		FileWriter out = new FileWriter(FileName+".txt");
//		out.write(toString());
//		out.close();
//	    System.out.println("Successfully wrote to the file.");
//	}
//	
//	public static LGraph<String,String> readFile(String FileName) throws IOException {
//		LGraph<String,String> gra = new LGraph<String,String>();
//		File f = new File(FileName+".txt");
//	    Scanner read = new Scanner(f);
//	    while(read.hasNextLine()) {
//	    	String key = read.nextLine();
//	    	if(!gra.g.containsKey(key)) {
//				gra.addVertex(key);
//			}
//	    	String value = read.nextLine();
//	    	String word = "";
//	    	for(int i=0; i<value.length(); i++) {
//	    		if(value.charAt(i)!= ' ') {
//	    			word += value.charAt(i);
//	    		}else {
//	    			System.out.println(word);
//	    			if(gra.g.containsKey(word)) {
//	    				gra.connect(word,key);
//	    				word = "";
//	    			}else {
//	    				gra.addVertex(word);
//	    				word = "";
//	    			}
//	    		}
//	    	}
//	    	
//	    }
//	    
//	    return gra;
//	    
//		
//	}
	
	public static void main(String[] args) throws IOException {
		TLGraph<String,String> g = new TLGraph<String,String>();
		g.addVertex("Tiffany");
		g.addVertex("Emma");
		g.addVertex("Linda");
		g.addVertex("Ryan");
		g.addVertex("Diana");
		g.addVertex("Jacob");
		g.addVertex("Cathy");
		g.addVertex("James");
		g.addVertex("Gregory");
		g.connect("Tiffany", "Emma","a");
		g.connect("Tiffany", "Ryan","b");
		g.connect("Tiffany", "Cathy","c");
		g.connect("Emma", "Linda","d");
		g.connect("Emma", "Diana","e");
		g.connect("Diana", "Jacob","f");
		g.connect("Diana", "Linda","g");
		g.connect("Ryan", "James","h");
		g.connect("Ryan", "Gregory","i");
		g.connect("Tiffany", "Gregory","j");
		
		System.out.println(g.BreadthFirst("Tiffany", "Jacob"));
//		g.writeFile("Friends");
//		LGraph<String,String> g2 = readFile("Friends");
//		System.out.println(g2.toStringS());
		
		
	}
	
	
	
}