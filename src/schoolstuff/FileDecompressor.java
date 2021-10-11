package schoolstuff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class FileDecompressor {
	
	static HashMap<Character, String> letters = new HashMap<Character, String>();
	
	public static void readFile() throws IOException {
		
	    Scanner br = new Scanner(new FileReader("TreeFileCompressSample.txt")); 
	    BufferedBitReader BBR = new BufferedBitReader("BBWout.txt");
	    
	    int a;
	    br.nextLine();
        while (br.hasNextLine()) {
        	char file = br.nextLine().charAt(0);
            String file2 = br.nextLine();
            letters.put(file, file2);
        }
        
        System.out.println(BBR.readBit());
        
	}  
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readFile();
		
		
		
	}

}
