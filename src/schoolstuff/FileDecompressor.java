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
	
	static HashMap<String, Character> letters = new HashMap<>();
	static String postBBR = "";
	static String text = "";
	
	public static void readFile() throws IOException {
		
	    Scanner br = new Scanner(new FileReader("TreeFileCompressSample.txt")); 
	    BufferedBitReader BBR = new BufferedBitReader("BBWout.txt");
	    
        while (br.hasNextLine()) {
        	char file = br.nextLine().charAt(0);
            String file2 = br.nextLine();
            letters.put(file2, file);
        }
        //System.out.println(letters);
     	while (BBR.hasNext()) {
     		Boolean a = BBR.readBit();
            //System.out.println(a);
     		if (a == false) {
     			postBBR += '0';
     		} else {
     			postBBR += '1';
     		}
     	}
	}  
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readFile();
		
		String letterCode = "";
		//System.out.println(postBBR);
		while(postBBR.length() != 0) {
			int k = 0;
			
			if (letters.get(letterCode) == null) {
				//System.out.println(postBBR.charAt(k));
				letterCode += postBBR.charAt(k);
				k++; 
				postBBR = postBBR.substring(k, postBBR.length());
				//System.out.println(postBBR);
				continue;
			}
			//System.out.println(letterCode);
			text += letters.get(letterCode);
			//System.out.println(letters.get(letterCode));
			//System.out.println(postBBR);
			letterCode = "";
			//System.out.println(text);
			System.out.println(postBBR);
		}
		System.out.println("ended");
		System.out.println(text);
	}

}
