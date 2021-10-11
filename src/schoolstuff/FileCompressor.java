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

	static HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
	static HashMap<Character, String> letters = new HashMap<Character, String>();
	
	public static String file = "";
	public static String preBBW = "";
	
	public static void readFile() throws IOException {
		
	    FileReader sc = new FileReader("FileCompressSample.txt"); 
	    
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
		
		PriorityQueue<Branch<Character>> pqueue = new PriorityQueue<>();
		for(Character c: hm.keySet()) {
			pqueue.add(new Branch<Character>(c), hm.get(c));
		}
		
		//System.out.println(pqueue.toString());
		
		while(pqueue.size() > 1) {
			
			int a = pqueue.getFirstPrior();
			Branch<Character> A = pqueue.pop();

			int b = pqueue.getFirstPrior();
			Branch<Character> B = pqueue.pop();
			
			pqueue.add(new Branch<Character>(A, B), a + b);
		}
		
		//System.out.println(tree.right.right.left.info);
		//System.out.println(file);
		
		genCode("", pqueue.pop());
		
		//System.out.println(letters);
		for (int i = 0; i < file.length(); i++) {
			preBBW += letters.get(file.charAt(i));
		}
		
		//System.out.println(postBBW);
		BufferedBitWriter BBW = new BufferedBitWriter("BBWout.txt");
		for (int i = 0; i < file.length(); i++) {
			if (preBBW.charAt(i) == '0') {
				BBW.writeBit(false);
			} else if (preBBW.charAt(i) == '1') {
				BBW.writeBit(true);
			}
		}
		
		BufferedWriter BW = new BufferedWriter(new FileWriter(new File("TreeFileCompressSample.txt")));
		
		for (Character key : letters.keySet()) {
			BW.write(key);
			BW.newLine();
			BW.write(letters.get(key));
			BW.newLine();
		}
		BW.close();
		
		
		BBW.close();
		
	}

}