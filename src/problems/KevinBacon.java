package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class KevinBacon {
	
	static HashMap<String, String> actors = new HashMap<>();
	static HashMap<String, String> movies = new HashMap<>();
	static HashMap<String, ArrayList<String>> tempSave= new HashMap<>();
	static Labeledtime<String, String> map = new Labeledtime<>();
	
	public static void readActors(String fileloc) throws IOException {
		 
        FileReader fr=new FileReader(fileloc);    
        BufferedReader sc =new BufferedReader(fr);  
        String line;
		while ((line = sc.readLine()) != null) {
			String[] temp = line.split("~");
			actors.put(temp[0], temp[1]);
			map.addVertex(temp[1]);
		}
		sc.close();
	}
	
	public static void readMovie(String fileloc) throws IOException {
		FileReader fr=new FileReader(fileloc);    
        BufferedReader sc =new BufferedReader(fr);  
		HashMap<Integer, String> tempMap = new HashMap<>();
		String line;
		while ((line = sc.readLine()) != null) {
			String[] temp = line.split("~");
			movies.put(temp[0], temp[1]);
		}
		sc.close();
	}
	
	public static void readActorsinMovie(String fileloc) throws IOException {
		FileReader fr=new FileReader(fileloc);    
        BufferedReader sc =new BufferedReader(fr); 
        String line;
		while ((line = sc.readLine()) != null) {
			
			String[] temp = line.split("~");
			if (tempSave.containsKey(movies.get(temp[0]))) {
				tempSave.get(movies.get(temp[0])).add(actors.get(temp[1]));
			} else {
				tempSave.put(movies.get(temp[0]), new ArrayList<String>());
				tempSave.get(movies.get(temp[0])).add(actors.get(temp[1]));
			}
		}
		//System.out.println("done!");
		sc.close();
	}
	
	public static void intoLabeltime() {
		for (String s : tempSave.keySet()) {
			for (int i = 0; i < tempSave.get(s).size(); i++) {
				for (int j = i; j < tempSave.get(s).size(); j++) {
					//System.out.println(tempSave.get(s));
					map.connect(tempSave.get(s).get(i), tempSave.get(s).get(j), s);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readActors("Files/actors.txt");
		readMovie("Files/movies.txt");
		readActorsinMovie("Files/movie-actors.txt");
		intoLabeltime();
		map.BFS("Jackie Chan", "Jon Cook");
	}

}
