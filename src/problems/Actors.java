package problems;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Actors {
	
	static HashMap<Integer, String> actors = new HashMap<>();
	static HashMap<Integer, String> movies = new HashMap<>();
	static HashMap<String, ArrayList<String>> tempSave= new HashMap<>();
	static Labeledtime<String, String> map = new Labeledtime<>();
	
	public static void readActors(String fileloc) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(fileloc));
		
		while (sc.hasNextLine()) {
			String[] temp = sc.nextLine().split("~");
			actors.put(Integer.valueOf(temp[0]), temp[1]);
			map.addVertex(temp[0]);
			map.addVertex(temp[1]);
		}
	}
	
	public static void TiffanyreadActors(String fileloc) throws FileNotFoundException {
		File f = new File(fileloc);
	    Scanner read = new Scanner(f);
	    while(read.hasNextLine()) {
	    	String[] s = read.nextLine().split("~");
	    	System.out.println(s[1]);
	    	actors.put(Integer.parseInt(s[0]),s[1]);
	    	map.addVertex(s[1]); // ?
	    }
	}
	
	public static void readMovie(String fileloc) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileloc));
		HashMap<Integer, String> tempMap = new HashMap<>();
		while (sc.hasNextLine()) {
			String[] temp = sc.nextLine().split("~");
			movies.put(Integer.valueOf(temp[0]), temp[1]);
		}		
	}
	
	public static void readActorsinMovie(String fileloc) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileloc));
		while (sc.hasNextLine()) {
			String[] temp = sc.nextLine().split("~");
			if (tempSave.containsKey(temp[0])) {
				tempSave.get(temp[0]).add(temp[1]);
			} else {
				tempSave.put(temp[0], new ArrayList<String>());
				tempSave.get(temp[0]).add(temp[1]);
			}
		}
	}
	
	public static void intoLabeltime() {
		for (String s : tempSave.keySet()) {
			for (int i = 0; i < tempSave.get(s).size(); i++) {
				for (int j = 0; j < tempSave.get(s).size(); j++) {
					map.connect(tempSave.get(s).get(i), tempSave.get(s).get(j), s);
				}
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		readActors("Files/actors.txt");
		readMovie("Files/movies.txt");
		readActorsinMovie("Files/movie-actors.txt");
		intoLabeltime();
		map.BFS("Brandon Blagg", "Jon Cook");
	}

}
