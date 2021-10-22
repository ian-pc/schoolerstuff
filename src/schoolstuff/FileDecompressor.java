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

	// HashMap with the key being a binary string and the value being the char it
	// corresponds to.
	static HashMap<String, Character> letters = new HashMap<>();

	// the text to be printed
	static String text = "";

	static Scanner br;
	static BufferedBitReader BBR;

	static FileWriter BW;

	// reads the file containing the characters and their corresponding string of
	// Binary and sorts it into a HashMap
	public static void readFile() throws IOException {

		br = new Scanner(new FileReader("TreeFileCompressSample.txt"));

		// as long as the file with the key and value for each character has a "next
		// line" it will print it out.
		while (br.hasNextLine()) {
			String filetemp = br.nextLine();
			char file;
			// line breaks are worth two lines on windows, so make sure to skip a line if it
			// detect this and skip a line
			if (filetemp.isEmpty()) {
				file = '\n';
				br.nextLine();
			} else {
				file = filetemp.charAt(0);
			}
			String file2 = br.nextLine();
			letters.put(file2, file);
		}
	}

	// converts the HashMap and the BufferedBit file to the original file
	public static void translate() throws IOException {

		BW = new FileWriter(new File("FileDecompressSample.txt"));
		BBR = new BufferedBitReader("BBWout.txt");

		String postBBR = "";

		while (BBR.hasNext()) {
			// reads the binary and adds it to a string
			Boolean a = BBR.readBit();
			if (a == false) {
				postBBR += '0';
			} else {
				postBBR += '1';
			}

			// if a string is not translatable from the binary, a new binary character is
			// added until one can be translate
			if (letters.get(postBBR) == null) {
				continue;
			}

			// writes the string's character value
			BW.write(letters.get(postBBR));

			// clear's the string
			postBBR = "";
		}
		BW.close();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readFile();
		translate();
	}

}
