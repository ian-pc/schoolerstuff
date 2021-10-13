package schoolstuff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class FileCompressor  {
	//HashMap storing the letters and their frequency
	static HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
	//HashMap storing the letters and their binary code from the Binary Tree
	static HashMap<Character, String> letters = new HashMap<Character, String>();
	
	//The String for the file
	public static String file = "";
	//The String of 0's and 1's that is to be put into the Buffered Bit Writer
	public static String preBBW = "";
	
	//Function that reads the text file that is to be compressed
	public static void readFile() throws IOException {
		
	    FileReader sc = new FileReader("FileCompressSample.txt"); 
	    
	    //if the character does not exist in "hm" it creates a new pair with the new letter and it's frequency (1)
	    //else, it's the character's frequency is increased by one in "hm"
	    int a;
	    while((a=sc.read()) != -1) {
	    	if (hm.containsKey((char) a) == false) {
	    		hm.put((char) a, 1);
	    	} else {
	    		hm.replace((char) a, hm.get((char) a) + 1);
	    	}
	    	file += (char) a;
	    }
	}   
	
	//code that adds the characters and their binary tree code to the letters hashmap
	public static void genCode(String code, Branch<Character> curr) {
		if(curr.isLeaf) {
			letters.put(curr.getInfo(), code);
		} else {
			genCode(code + "0", curr.left);
			genCode(code + "1", curr.right);
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		readFile();
		
		//creates a priority queue which has a branch of each character and their frequency
		PriorityQueue<Branch<Character>> pqueue = new PriorityQueue<>();
		for(Character c: hm.keySet()) {
			pqueue.add(new Branch<Character>(c), hm.get(c));
		}
		
		//putting the Branches into the priority queue
		while(pqueue.size() > 1) {
			
			int a = pqueue.getFirstPrior();
			Branch<Character> A = pqueue.pop();

			int b = pqueue.getFirstPrior();
			Branch<Character> B = pqueue.pop();
			
			pqueue.add(new Branch<Character>(A, B), a + b);
		}
		
		//starts the genCode function
		genCode("", pqueue.pop());
		
		//Converts the original document to a string of 0's and 1's from the hashMap "letters"
		for (int i = 0; i < file.length(); i++) {
			preBBW += letters.get(file.charAt(i));
		}
		
		//Converts the preBBW (the binary code) to Buffered Bit Writer code
		BufferedBitWriter BBW = new BufferedBitWriter("BBWout.txt");
		for (int i = 0; i < file.length(); i++) {
			if (preBBW.charAt(i) == '0') {
				BBW.writeBit(false);
			} else if (preBBW.charAt(i) == '1') {
				BBW.writeBit(true);
			}
		}
		
		//writes all the characters in "letters" and their binary tree binary code
		FileWriter BW = new FileWriter(new File("TreeFileCompressSample.txt"));
		for (Character key : letters.keySet()) {
			BW.write(key);
			BW.write("\r\n");
			BW.write(letters.get(key));
			BW.write("\r\n");
		}
		
		BW.close();		
		BBW.close();
		
	}

}