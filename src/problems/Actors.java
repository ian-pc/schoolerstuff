package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Actors {
	
	public HashMap<Integer, String> actors = new HashMap<>();
	public HashMap<Integer, String> movies = new HashMap<>();
	public HashMap<String, ArrayList<String>> tempSave= new HashMap<>();
	public Labeledtime<String, String> map = new Labeledtime<>();
	
	public void readActors(String fileloc) {
		
		Scanner sc = new Scanner(fileloc);
		HashMap<Integer, String> tempMap = new HashMap<>();
		while (sc.hasNext()) {
			String temp = sc.next();
			String[] temp2 = temp.split("~");
			tempMap.put(Integer.valueOf(temp2[1]), temp2[0]);
			map.addVertex(temp2[0]);
			map.addVertex(temp2[1]);
		}
		actors = tempMap;
	}
	
	public void readMovie(String fileloc) {
		Scanner sc = new Scanner(fileloc);
		HashMap<Integer, String> tempMap = new HashMap<>();
		while (sc.hasNext()) {
			String temp = sc.next();
			String[] temp2 = temp.split("~");
			tempMap.put(Integer.valueOf(temp2[1]), temp2[0]);
		}
		movies = tempMap;
		
	}
	
	public void readActorsinMovie(String fileloc) {
		Scanner sc = new Scanner(fileloc);
		while (sc.hasNext()) {
			String temp = sc.next();
			String[] temp2 = temp.split("~");
			if (tempSave.containsKey(temp2[0])) {
				tempSave.get(temp2[0]).add(temp2[1]);
			} else {
				tempSave.put(temp2[0], new ArrayList<String>());
				tempSave.get(temp2[0]).add(temp2[1]);
			}
		}
	}
	
	public void intoLabeltime() {
		for (String s : tempSave.keySet()) {
			for (int i = 0; i < tempSave.get(s).size(); i++) {
				for (int j = 0; j < tempSave.get(s).size(); j++) {
					map.connect(tempSave.get(s).get(i), tempSave.get(s).get(j), s);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
