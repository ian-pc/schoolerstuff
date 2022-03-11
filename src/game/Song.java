package game;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.awt.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Song {

	public String name, loc;
	public int difficulty, rows;

	private float samplerate;
	private double duration;
	int song[];
	AudioInputStream in;

	public void GetAudioGraph(String file) throws UnsupportedAudioFileException, IOException {
		AudioInputStream in = AudioSystem.getAudioInputStream(new File(file));
		AudioFormat baseFormat = in.getFormat();
		AudioFormat decodedFormat = new AudioFormat(Encoding.PCM_FLOAT, 44100, 32, 1, 4, 44100, false);
		AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, in);
		samplerate = baseFormat.getFrameRate();
		duration = (in.getFrameLength() + 0.0) / baseFormat.getFrameRate();
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
		float amplitudesSong[] = new float[(int) (in.getFrameLength() / (samplerate / 100)) + 1];
		song = new int[(int) (in.getFrameLength() / (samplerate / 100))];
		for (int i = 0; i < song.length; i++) {
			song[i] = -1;
		}
		for (int i = 0; i < in.getFrameLength(); i++) {
			float tempAverage = 0;// average amplitude in one hundredth of a sec
			tempAverage += amplitudes[i];

			if (i % (samplerate / 100) == 0) {
				int j = (int) (i / (samplerate / 100));
				tempAverage /= (samplerate / 100);
				amplitudesSong[j] = tempAverage;
//				System.out.println(amplitudesSong.length);
				tempAverage = 0;
			}
		}

		int dist = 5;
		float smoothedAmplitudesSong[] = new float[(int) (in.getFrameLength() / (samplerate / 100))];
		for (int i = dist / 2; i < song.length - dist / 2; i++) {
			for (int j = -dist / 2; j < dist / 2; j++) {
				smoothedAmplitudesSong[i] += amplitudesSong[j + i];
			}
			smoothedAmplitudesSong[i] /= dist;
		}

		float peakMax = 0;
		int c = 0;
		int tempDifficulty = (10 - difficulty) * 15;
		for (int i = 0; i < song.length; i++) {

			for (int j = (i - tempDifficulty > 0) ? i - tempDifficulty : tempDifficulty; j < ((i + tempDifficulty < song.length) ? i + tempDifficulty : song.length - tempDifficulty); j++) {
				if (Math.abs(smoothedAmplitudesSong[i]) < Math.abs(smoothedAmplitudesSong[j])
						|| amplitudesSong[i] == 0) {
					song[i] = -1;
					break;
				}
				if (j == i + tempDifficulty) {
					c++;
					if ((Math.abs(smoothedAmplitudesSong[i]) > peakMax))
						peakMax = (Math.abs(smoothedAmplitudesSong[i]));
					song[i] = (int) Math.floor((Math.abs(smoothedAmplitudesSong[i]) / peakMax) * (rows)) - 1;
					//System.out.println(song[i])s;
				}
			}
			System.out.println(song[i] + " "  + i);
		}
		//System.out.println(song[0]);
		System.out.println(c);

		JFrame frame2 = new JFrame();
		frame2.setSize(3968, 1000);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setLocationRelativeTo(null);

		JPanel canvas2 = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(new Color(0, 0, 255, 50));
				for (int i = 0; i < song.length; i += 3) {
					g.fillRect(i / 3, 500, 1, (int) Math.abs(smoothedAmplitudesSong[i] * 1000000));
					g.fillRect(i / 3, 500 - (int) Math.abs(smoothedAmplitudesSong[i] * 1000000), 1,
							(int) Math.abs(smoothedAmplitudesSong[i] * 1000000));
				}

				g.setColor(new Color(0, 255, 0, 50));
				for (int i = 0; i < song.length; i += 3) {
					g.fillRect(i / 3, 500, 1, (int) Math.abs(amplitudesSong[i] * 100000));
					g.fillRect(i / 3, 500 - (int) Math.abs(amplitudesSong[i] * 100000), 1,
							(int) Math.abs(amplitudesSong[i] * 100000));
				}

//				g.setColor(new Color(255, 0, 0)); {
//					for (int i = 0; i < song.length; i+= 3) {
//						if (song[i] != -1) {
//							System.out.println("ye");
//							g.fillRect(i/3, 500, 1, (int) Math.abs(amplitudesSong[i]* 100000));
//							g.fillRect(i/3, 500 - (int) Math.abs(amplitudesSong[i]* 100000), 1, (int) Math.abs(amplitudesSong[i]* 100000));
//						
//						}
//					}
//				}
			}
		};

		canvas2.setPreferredSize(new Dimension(3968, 1000));
		frame2.add(canvas2);
		frame2.setVisible(true);
	}

	public Song(int difficulty, int rows, String name, String fileLoc)
			throws UnsupportedAudioFileException, IOException {
		this.name = name;
		this.difficulty = difficulty;
		this.rows = rows;
		this.loc = fileLoc;

		GetAudioGraph(fileLoc);
		//System.out.println(this.song.length);
	}
}
