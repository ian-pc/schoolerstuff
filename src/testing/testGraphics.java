package testing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.GameMain;



public class testGraphics {

	public int[] GetAudioGraph(String fileLoc) throws IOException, UnsupportedAudioFileException {
		int a[] = new int[10000000];
		// SoundPlayer player = new SoundPlayer("Hot-Milk.wav");
		// InputStream stream = new ByteArrayInputStream(player.getSamples());
		// player.play(stream);
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileLoc));
		int numBytes = inputStream.available();
		byte[] buffer = new byte[numBytes];
		inputStream.read(buffer,0,numBytes);

		BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File("bytes.txt")));

		ByteBuffer bb = ByteBuffer.wrap(buffer);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		int i = 0;
		while(bb.remaining()>1)
		{
			short current = bb.getShort();
			// fileOut.write(String.valueOf(current));
			// fileOut.newLine();
			a[i] = current;
			System.out.println(a[i]);
			i++;
		}
		return a;
	}
	public static void getAudioData(String fileLoc) throws UnsupportedAudioFileException, IOException {
	    String filename = fileLoc;
	    File fileIn = new File(filename);
        AudioInputStream in = AudioSystem.getAudioInputStream(fileIn);
//        System.out.println("format. " + in.getFormat());
//        System.out.println(in.getFrameLength());
        
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
        AudioFormat format = audioInputStream.getFormat();
        long audioFileLength = fileIn.length();
        int frameSize = format.getFrameSize();
        float frameRate = format.getFrameRate();
        float durationInSeconds = (audioFileLength / (frameSize * frameRate));
        System.out.println("LEnght " + fileIn.length());
        System.out.println("framesize " + format.getFrameSize());
        System.out.println("frameRate " + format.getFrameRate());
        System.out.println("DURATion in seconds " + durationInSeconds);
	}
	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(GameMain.class.getResourceAsStream(url));
					clip.open(inputStream);
					clip.start();
					
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	
	public static void music() {
		
	}
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		getAudioData("Hot-Milk.wav");
	}
}