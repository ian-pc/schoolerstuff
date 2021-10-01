package schoolstuff;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class FileCompressor  {

	static HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
	
	public static void readFile() throws IOException {
		
	    FileReader sc = new FileReader("FileCompressSample.txt"); 
	    
	    int a;
	    while((a=sc.read()) != -1) {
	    	if (hm.containsKey((char) a) == false) {
	    		hm.put((char) a, 1);
	    	} else {
	    		hm.replace((char) a, hm.get((char) a) + 1);
	    	}
	    }
	}   
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readFile();
		
		PriorityQueue<Integer> pqueue = new PriorityQueue<Integer>();
		for (int i = 0; i < hm.size(); i++) {
			
		}
		
		System.out.println(hm.get('o'));
	}

}
