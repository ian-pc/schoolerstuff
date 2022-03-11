package game;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Test2 {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException{
  
	  Scanner scanner = new Scanner(System.in);
	  
	  File file = new File("Files/Hot-Milk.wav");
	  AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	  Clip clip = AudioSystem.getClip();
	  clip.open(audioStream);
	  

	  clip.start();
	  while (true) {
		  System.out.println(clip.getMicrosecondPosition()/10000);
	  }
	}
 }