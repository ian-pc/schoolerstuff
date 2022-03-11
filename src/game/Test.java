package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {

	public static void GetAudioGraph(String file) throws UnsupportedAudioFileException, IOException {
		int difficulty = 4;
		int rows = 4;
		AudioInputStream in = AudioSystem.getAudioInputStream(new File(file));
		AudioFormat baseFormat = in.getFormat();
		AudioFormat decodedFormat = new AudioFormat(Encoding.PCM_FLOAT, 44100, 32, 1, 4, 44100, false);
		AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, in);
		float samplerate = baseFormat.getFrameRate();
		double duration = (in.getFrameLength() + 0.0) / baseFormat.getFrameRate();

		float amplitudes[] = new float[(int) in.getFrameLength()];
		byte[] array = new byte[4];
		int read = din.read(array);
		int iter = 0;
		while (read != -1) {
			ByteBuffer bb = ByteBuffer.wrap(array);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			float amplitude = bb.asFloatBuffer().get();
			read = din.read(array);
			amplitudes[iter] = amplitude;
			iter++;
		}
		float amplitudesSong[] = new float[(int) (in.getFrameLength() / (samplerate / 100))];
		int song[] = new int[(int) (in.getFrameLength() / (samplerate / 100))];
		for (int i = 0; i < in.getFrameLength(); i++) {
			float tempAverage = 0;// average amplitude in one hundredth of a sec
			tempAverage += amplitudes[i];

			if (i % (samplerate / 100) == 0) {
				int j = (int) (i / (samplerate / 100));
				tempAverage /= (samplerate / 100);
				amplitudesSong[j] = tempAverage;
				tempAverage = 0;
			}
		}

		
		int dist = (10 - difficulty) * 4;
		float smoothedAmplitudesSong[] = new float[(int) (in.getFrameLength() / (samplerate / 100))];
		for (int i = dist/2; i < song.length - dist/2; i++) {
			for (int j = -dist/2; j < dist/2; j++) {
				smoothedAmplitudesSong[i] += amplitudesSong[j + i];
			}
			smoothedAmplitudesSong[i]/=dist;
		}
		
		float peakMax = 0;
		for (int i = 2; i < song.length - 2; i++) {
			if (Math.abs(smoothedAmplitudesSong[i])> Math.abs(smoothedAmplitudesSong[i-1]) && 
					Math.abs(smoothedAmplitudesSong[i]) > Math.abs(smoothedAmplitudesSong[i - 2])&&
					Math.abs(smoothedAmplitudesSong[i]) > Math.abs(smoothedAmplitudesSong[i + 1]) &&
					Math.abs(smoothedAmplitudesSong[i]) > Math.abs(smoothedAmplitudesSong[i + 2])) {
				if (Math.abs(smoothedAmplitudesSong[i]) > peakMax) peakMax = Math.abs(smoothedAmplitudesSong[i]);
				song[i] = (int) Math.round((Math.abs(smoothedAmplitudesSong[i])/peakMax) * rows);
				System.out.println(song[i]);
			} else {
				song[i] = -1;
			}
		}
		
		
		
		JFrame frame = new JFrame();
		frame.setSize(3968, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(new Color(0, 0, 255, 50));
				for (int i = 0; i < song.length; i+= 3) {
					g.fillRect(i/3, 500, 1, (int) Math.abs(smoothedAmplitudesSong[i]* 1000000));
					g.fillRect(i/3, 500 - (int) Math.abs(smoothedAmplitudesSong[i]* 1000000), 1, (int) Math.abs(smoothedAmplitudesSong[i]* 1000000));
				}

				g.setColor(new Color(0, 255, 0, 50));
				for (int i = 0; i < song.length; i+= 3) {
					g.fillRect(i/3, 500, 1, (int) Math.abs(amplitudesSong[i]* 100000));
					g.fillRect(i/3, 500 - (int) Math.abs(amplitudesSong[i]* 100000), 1, (int) Math.abs(amplitudesSong[i]* 100000));
				}
			}
		};
		
		
		canvas.setPreferredSize(new Dimension(3968, 1000));
		frame.add(canvas);
		frame.setVisible(true);
		
//      System.out.println("format. " + baseFormat);
//      System.out.println(in.getFrameLength());
//      System.out.println(duration);
		
	}

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

		//GetAudioGraph("Files/Hot-Milk.wav");

		
	}

}
