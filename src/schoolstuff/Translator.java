 package schoolstuff;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Translator {
	
	static HashMap<String, String> EA = new HashMap<String, String>();
	
	public static void readFile() throws IOException {

	    Scanner br = new Scanner(new FileReader("EnglishToArabicDictionary.txt")); 
	    
        while (br.hasNextLine()) {

        	String file = br.nextLine();
            String file2 = br.nextLine();
            EA.put(file, file2);
        }
	}   
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readFile();
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			String word = sc.next();
			System.out.println(EA.get(word));
			if (word == null) {
				System.out.println("word is not in dictionary");
			}
			if (word != null || word == "quit") {
				break;
			}
		}
	}
}
