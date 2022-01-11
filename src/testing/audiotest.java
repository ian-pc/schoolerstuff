package testing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class audiotest {
	public int[] GetAudioGraph(String fileLoc) throws IOException, UnsupportedAudioFileException {
		int a[] = new int[10000000];
//		 SoundPlayer player = new SoundPlayer("Hot-Milk.wav");
//		 InputStream stream = new ByteArrayInputStream(player.getSamples());
//		 player.play(stream);
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileLoc));
		int numBytes = inputStream.available();
		byte[] buffer = new byte[numBytes];
		inputStream.read(buffer, 0, numBytes);

//		BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File("bytes.txt")));

		ByteBuffer bb = ByteBuffer.wrap(buffer);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		int i = 0;
		while (bb.remaining() > 1) {
			short current = bb.getShort();
//			 fileOut.write(String.valueOf(current));
//			 fileOut.newLine();
			a[i] = current;
			System.out.println(a[i]);
			i++;
		}
		return a;
	}
	
	public audiotest() throws IOException, UnsupportedAudioFileException {
		
		int[] a = GetAudioGraph("Files/Hot-Milk.wav");
		
		JFrame frame = new JFrame();
		frame.setSize(1000, 800);
		//closes the window when closed and stops the program. 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				for (int i = 0; i < a.length; i+=100) {
					g.drawLine(i/50, 800/2, 1 + i/50, 800/2 + Math.abs(a[i]/10));
				}
			}
		};
		

		
		canvas.setPreferredSize(new Dimension(1000, 800));
		frame.add(canvas);
		frame.setVisible(true);
		System.out.println(a.length);

	}
	
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
		// TODO Auto-generated method stub
		new audiotest();
	}

}
