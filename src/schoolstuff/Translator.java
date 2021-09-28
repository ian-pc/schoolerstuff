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
        	while (true) {
            	String file = br.nextLine();
            	System.out.println(file);
                String file2 = br.nextLine();
                System.out.println(file2);

                EA.put(file, file2);
                
                if (br.nextLine() == null) {
                	break;
                }
        	}
            //String[] columns = br.nextLine().split("\n");
            //System.out.println(columns[0]);
        }
	}   
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readFile();
		Scanner sc = new Scanner(System.in);
		
		String word = sc.next();
		System.out.println(EA.get(word));
	}

}