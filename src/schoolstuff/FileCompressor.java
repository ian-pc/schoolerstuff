package schoolstuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class FileCompressor {
	// HashMap storing the letters and their frequency
	static HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
	// HashMap storing the letters and their binary code from the Binary Tree
	static HashMap<Character, String> letters = new HashMap<Character, String>();
	// creates a priority queue which has a branch of each character and their frequency
	static PriorityQueue<Branch<Character>> pqueue = new PriorityQueue<>();
	// Length of the file
	public static int fileLength = 0;
	
	// Function that reads the text file that is to be compressed
	public static void readFile() throws IOException {

		FileReader sc = new FileReader("FileCompressSample.txt");

		// if the character does not exist in "hm" it creates a new pair with the new
		// letter and it's frequency (1)
		// else, it's the character's frequency is increased by one in "hm"
		int a;
		while ((a = sc.read()) != -1) {
			if (hm.containsKey((char) a) == false) {
				hm.put((char) a, 1);
			} else {
				hm.replace((char) a, hm.get((char) a) + 1);
			}
			fileLength++;
		}
	}

	// Finds the location of the character at the location
	public static char findChar(int loc) throws IOException{
		
        File file = new File("FileCompressSample.txt");
        char out = 0;
        
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (int i = 0; i < loc - 1; i++) {
				br.read();
			}
			out = (char) br.read();
			//System.out.println(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	// sets up the priority queue that will be used later
	public static void setupPqueue() {
		// puts all the characters into the priority queue
		for (Character c : hm.keySet()) {
			pqueue.add(new Branch<Character>(c), hm.get(c));
		}

		// orders the priority
		while (pqueue.size() > 1) {

			int a = pqueue.getFirstPrior();
			Branch<Character> A = pqueue.pop();

			int b = pqueue.getFirstPrior();
			Branch<Character> B = pqueue.pop();

			pqueue.add(new Branch<Character>(A, B), a + b);
		}
	}
	
	// code that adds the characters and their binary tree code to the letters HashMap
	public static void genCode(String code, Branch<Character> curr) {
		if (curr.isLeaf) {
			letters.put(curr.getInfo(), code);
		} else {
			genCode(code + "0", curr.left);
			genCode(code + "1", curr.right);
		}
	}

	// writes all the characters in "letters" and their binary tree binary code
	public static void writeTree() throws IOException {
		FileWriter BW = new FileWriter(new File("TreeFileCompressSample.txt"));
		for (Character key : letters.keySet()) {
			BW.write(key);
			BW.write("\r\n");
			BW.write(letters.get(key));
			BW.write("\r\n");
		}

		BW.close();
	}

	// writes all the characters in the buffered bit writer form after turned into binary
	public static void writeBBR() throws IOException {
		
		// Converts the original document to a string of 0's and 1's from the hashMap "letters"
		BufferedBitWriter BBW = new BufferedBitWriter("BBWout.txt");
		
		for (int i = 0; i < fileLength; i++) {
			//preBBW += letters.get(file.charAt(i));
			String tChar = letters.get(findChar(i));
			
			// Converts the preBBW (the binary code) to Buffered Bit Writer code
			for (int j = 0; j < tChar.length(); j++) {
				if (tChar.charAt(j) == '0') {
					BBW.writeBit(false);
				} else if (tChar.charAt(j) == '1') {
					BBW.writeBit(true);
				}
			}
		}

		BBW.close();
	}

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readFile();
		System.out.println("read file");
		setupPqueue();
		System.out.println("setup pqueue");
		genCode("", pqueue.pop());
		System.out.println("generated queue");
		writeTree();
		System.out.println("wrote tree");
		writeBBR();
		System.out.println("done");
	}

}