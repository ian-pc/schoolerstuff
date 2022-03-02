package game;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Song {
	
	public String name;
	public int difficulty, rows;
	
	public Song(int difficulty, int rows, String name, String fileLoc) throws UnsupportedAudioFileException, IOException {
		this.name = name;
		this.difficulty = difficulty;
		this.rows = rows;
	}
	
	public void play (Graphics g) {
		
	}

}
