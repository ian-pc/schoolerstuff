package game;

import java.io.File;
import java.io.IOException;

import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Song {
	
	public String name;
	public int difficulty, rows;
	AudioInputStream inputStream;
	
	public Song(int difficulty, int rows, String name, String fileLoc) throws UnsupportedAudioFileException, IOException {
		this.name = name;
		this.difficulty = difficulty;
		this.rows = rows;
		this.inputStream = AudioSystem.getAudioInputStream(new File(fileLoc));
	}
	
	public void play (Graphics g) {
		
	}

}
