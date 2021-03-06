package problems;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TKBgame {
	private HashMap<Integer, String> actors = new HashMap<Integer, String>();
	private HashMap<Integer, String> movies = new HashMap<Integer, String>();
	private TLGraph<String, String> g = new TLGraph<String, String>();
	
	public void readActors() throws FileNotFoundException {
		File f = new File("Files/actors.txt");
	    Scanner read = new Scanner(f);
	    while(read.hasNextLine()) {
	    	String[] s = read.nextLine().split("~");
	    	actors.put(Integer.parseInt(s[0]),s[1]);
	    	g.addVertex(s[1]); // ?
	    }
	}
	
	public void readMovie() throws FileNotFoundException {
		File f = new File("Files/movies.txt");
	    Scanner read = new Scanner(f);
	    while(read.hasNextLine()) {
	    	String[] s = read.nextLine().split("~");
	    	movies.put(Integer.parseInt(s[0]),s[1]);
	    }
	}
	
	public void createMap() throws FileNotFoundException {
		File f = new File("Files/movie-actors.txt");
	    Scanner read = new Scanner(f);
	    String[] s = read.nextLine().split("~");
    	int currmovie = Integer.parseInt(s[0]);
    	ArrayList<String> actorsInMovie = new ArrayList<String>();
    	
	    while(read.hasNextLine()) {
	    	if(currmovie == Integer.parseInt(s[0])) {
	    		actorsInMovie.add(actors.get(Integer.parseInt(s[1])));
	    		s = read.nextLine().split("~");
	    	}else {
	    		for(int i=0; i<actorsInMovie.size(); i++) {
	    	    	for(int j=i; j<actorsInMovie.size(); j++) {
	    	    		g.connect(actorsInMovie.get(i), actorsInMovie.get(j), movies.get(currmovie));
	    	    	}
	    	    }
	    		currmovie = Integer.parseInt(s[0]);
	    		actorsInMovie.clear();
	    	}
	    }
    	currmovie = Integer.parseInt(s[0]);
    	actorsInMovie.clear();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		TKBgame k = new TKBgame();
		
		k.readActors();
		k.readMovie();
		k.createMap();
		System.out.println(k.g.BreadthFirst("Dileep Rao", "Kevin Bacon"));
		
	}
	
	
}