package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {

	
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//		int a[] = new int[10000000];
//		AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Files/Hot-Milk.wav"));
//		
//		int numBytes = inputStream.available();
//		byte[] buffer = new byte[numBytes];
//		inputStream.read(buffer, 0, numBytes);
//
//		ByteBuffer bb = ByteBuffer.wrap(buffer);
//		bb.order(ByteOrder.LITTLE_ENDIAN);
//		int i = 0;
//		while (bb.remaining() > 1) {
//			short current = bb.getShort();
//			a[i] = current;
//			i++;
//		}
		
		final AudioFormat audioFormat = new AudioFormat(44100, 8, 1, true, true);
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, 1);
        final SourceDataLine soundLine = (SourceDataLine) AudioSystem.getLine(info);
		final int bufferSize = 2200; // in Bytes
		soundLine.open(audioFormat, bufferSize);
		soundLine.start();
		byte counter = 0;
		final byte[] buffer = new byte[bufferSize];
		byte sign = 1;
		while (true) {
		    int threshold = (int) audioFormat.getFrameRate();
		    for (int i = 0; i < bufferSize; i++) {
		        if (counter > threshold) {
		            sign = (byte) -sign;
		            counter = 0;
		        }
		        buffer[i] = (byte) (sign * 30);
		        counter++;
		    }
		    // the next call is blocking until the entire buffer is 
		    // sent to the SourceDataLine
		    soundLine.write(buffer, 0, bufferSize);
		}
	}

}
