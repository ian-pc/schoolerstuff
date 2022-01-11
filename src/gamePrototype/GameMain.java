package gamePrototype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import testing.SoundPlayer;
import testing.Sound.SoundEffect;

public class GameMain {

	public static int yoC = 0;

	static class Song {
		String name = "";
		int rows = 1;
		int map[];
		long average[];
		int count;

		public Song(String name, int rows, int difficulty, int a[]) throws InterruptedException {
			this.name = name;
			this.rows = rows;
			this.map = new int[a.length];
			average = new long[a.length];
			for (int i = 0; i < a.length; i++) {
				this.map[i] = -1;
			}
			for (int i = 0; i < a.length - 1000; i += 1000) {
				long tempAverage = 0;
				for (int j = i; i < j + 1000; i++) {
					tempAverage += Math.abs(a[j]);
				}
				tempAverage /= 1000;
				System.out.println("tempAverage" + tempAverage);
				for (int j = i; i < j + 1000; i++) {
					average[j] = tempAverage;
				}

			}

			for (int i = 0; i < a.length; i += 200) {
				if (Math.abs(a[i]) > 10 * (13 - difficulty) * average[i]) {
					this.map[i] = (int) ((Math.random() * rows));
					count++;
				}
//				if (i % 1000 == 0) {
//					//System.out.println("average: " + average[i]);
//				}
			}
			//139361
			System.out.println("count: "+count);
		}
	}

	static class Note {
		int row = -1;
		int y = 0;

		public Note(int row) {
			this.row = row;
			System.out.println("ye");
		}

		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			g.setColor(new Color(255, 255, 255));
			g.fillRect(Width / 2 + (int) ((Width / 2 * 0.1 + 300) / 4) * this.row, y, (int) (Width / 2 * 0.1 + 300) / 4,
					5);
		}
	}
	
	public class SoundEffect {
		
		Clip clip;
		
		public void setFile(String soundFileName){
			
			try{
				File file = new File(soundFileName);
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);	
				clip = AudioSystem.getClip();
				clip.open(sound);
			}
			catch(Exception e){
				
			}
		}
		
		public void play(){
			
			clip.setFramePosition(0);
			clip.start();
		}

	}
	
	static class Button {
		Font font = new Font("Helvetica", Font.PLAIN, 50);
		String text = "";
		int x1, x2, y1, y2;
		FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

		int textHeight = (int) (font.getStringBounds(text, frc).getHeight());

		public Button(String text, int x1, int y1, int x2, int y2, Font font) {
			this.text = text;
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			this.font = font;
			if (x1 > x2) {
				this.x1 = x2;
				this.x2 = x1;
			}
			if (y1 > y2) {
				this.y1 = y2;
				this.y2 = y1;
			}
		}

		public Button(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			if (x1 > x2) {
				this.x1 = x2;
				this.x2 = x1;
			}
			if (y1 > y2) {
				this.y1 = y2;
				this.y2 = y1;
			}
		}

		public void Draw(Graphics g) {

			g.setColor(new Color(200, 200, 255));
			g.drawRect(x1, y1, x2 - x1, y2 - y1);
			g.setFont(font);
			g.drawString(text, x1, y1 + textHeight);
		}
	}

	public int scroll = 0;
	public int Height = 600;
	public static int Width = 800;
	public int mainMenuButtonH = 100, mainMenuButtonW = 300;
	public boolean mainMenu = true, playMenu = false, optionMenu = false, gameMenu = false;
	public Song playingSong = null;
	public int playMenuButtonH = 75, playMenuButtonW = 300;
	public boolean selecting = false;
	public int selectingKey;
	public char keys[] = { 's', 'd', 'f', ' ', 'j', 'k', 'l' };
	public int score = 0, battery = 100;
	public boolean makingSong = false;
	public File selectedFile = null;
	public int currSongRows = 4, currSongDifficulty = 5;
	public int ij = 0;

	public ArrayList<Note> notes = new ArrayList<>();

	public static Vector<Song> songs = new Vector<Song>();


	public static void getAudioData(String fileLoc) {
	    String filename = fileLoc;
	    File fileIn = new File(filename);
	    try {
	        AudioInputStream in = AudioSystem.getAudioInputStream(fileIn);
	        System.out.println("format. " + in.getFormat());
	        System.out.println(in.getFrameLength());
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	}

	
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

	public GameMain() {

		String clickSound = "Hot-Milk.wav";
		JFrame frame = new JFrame();
		frame.setSize(Width, Height);
		// closes the window when closed and stops the program.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		SoundEffect se = new SoundEffect();
		
		se.setFile(clickSound);
		
		JPanel canvas = new JPanel() {
			// openfilechooser to get audio files
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(new Color(200, 200, 255));
				g.drawLine(Width / 2, 0, Width / 2, Height);
				g.drawLine(0, Height / 2, Width, Height / 2);

				g.setColor(new Color(0, 0, 0));

				if (mainMenu == true) {
					g.setColor(new Color(52, 58, 64));

//					g.drawRect(Width / 2 - 200, Height / 2 - 200, mainMenuButtonW, mainMenuButtonH);
//					g.setFont(new Font("Helvetica", Font.ITALIC, 80));
//					g.drawString("PLAY", Width / 2 - 200 + mainMenuButtonW / 2 - 100, Height / 2 - 200 + 75);
//
//					g.drawRect(Width / 2 - 200, Height / 2 - 80, mainMenuButtonW, mainMenuButtonH);
//					g.setFont(new Font("Helvetica", Font.ITALIC, 55));
//					g.drawString("OPTIONS", Width / 2 - 200 + mainMenuButtonW / 2 - 100 - 20, Height / 2 - 80 + 75);
//
//					g.drawRect(Width / 2 - 200, Height / 2 + 40, mainMenuButtonW, mainMenuButtonH);
//					g.setFont(new Font("Helvetica", Font.ITALIC, 80));
//					g.drawString("EXIT", Width / 2 - 200 + mainMenuButtonW / 2 - 100, Height / 2 + 40 + 75);
					
					g.drawRect(Width/2 -150, Height/2 - 150, 300, 300);
					
				}

				if (playMenu == true) {
					// back button
					g.setColor(new Color(0, 0, 0));
					g.drawRect(0, 0, 150, 75);
					g.setFont(new Font("Helvetica", Font.ITALIC, 50));
					g.drawString("BACK", 0, 50);

					for (int i = 0; i < songs.size(); i++) {
						g.drawRect(Width / 2, 50 + 70 * i + scroll, playMenuButtonW, playMenuButtonH);
						g.setFont(new Font("Helvetica", Font.BOLD, 50));
						g.drawString(songs.get(i).name, Width / 2 + 20, 50 + 50 + 75 * i + scroll);
					}

					g.drawOval(Width / 8, Height / 2 - Width / 8, Width / 4, Width / 4);
					if (makingSong == true) {
						g.setFont(new Font("Helvetica", Font.BOLD, 20));
						g.drawString("Name: " + selectedFile.getName(), Width / 16, 3 * Height / 4);
						g.drawString("Rows: " + currSongRows, Width / 16, 3 * Height / 4 + 50);
						g.drawString("Difficulty: " + currSongDifficulty, Width / 16, 3 * Height / 4 + 100);
					}
				}

				if (gameMenu == true) {
					
					
					
					
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, Width, Height);
					g.setColor(Color.white);
					g.fillRect(Width / 2, (int) (Height / 2 * 1.4), (int) (Width / 2 * 0.1 + 300), 1);

					g.setFont(new Font("Helvetica", Font.PLAIN, 30));
					g.drawString("Score: " + score, 50, 60);
					g.drawString("Battery: " + battery, 50, 160);

					ij+=20; 
					// System.out.println(ij);
					for (int i = 0; i < notes.size(); i++) {
						notes.get(i).draw(g);
						notes.get(i).y += Height/100;
						if (notes.get(i).y > Height / 2 * 1.4) {
							notes.remove(i);
						}
					}
					if (playingSong.map[ij] > -1) {
						notes.add(new Note(playingSong.map[ij]));
					}
					
				}

				if (optionMenu == true) {
					// back button
					g.setColor(new Color(0, 0, 0));
					g.drawRect(0, 0, 150, 75);
					g.setFont(new Font("Helvetica", Font.ITALIC, 50));
					g.drawString("BACK", 0, 50);

					for (int i = 0; i < 7; i++) {
						g.setColor(new Color(0, 0, 0));
						g.drawRect(Width / 2 - i * 50 - 50, Height / 2, 50, 50);
						g.setFont(new Font("Helvetica", Font.PLAIN, 30));
						g.drawString(String.valueOf(keys[i]), Width / 2 - i * 50 - 35, Height / 2 + 40);
					}

					if (selecting == true) {
						g.drawString("press key", (int) (Width / 2 - 4.75 * 50), Height / 2 - 50);
					}

				}

			}
		};

		canvas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

				if (optionMenu == true) {
					if (selecting == true) {

						for (int i = 0; i < 7; i++) {
							if (keys[i] == e.getKeyChar()) {
								keys[i] = keys[selectingKey];
							}
						}

						keys[selectingKey] = e.getKeyChar();
						selecting = false;
					}
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				 //e.getKeyCode() == KeyEvent.VK_DOWN;
				//e.getKeyChar() == ' ';
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});
 		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (mainMenu == true) {
					if (e.getPoint().x > Width / 2 - 200 && e.getPoint().x < Width / 2 - 200 + mainMenuButtonW) {
						if (e.getPoint().y > Height / 2 - 200 && e.getPoint().y < Height / 2 - 200 + mainMenuButtonH) {
							System.out.println("Play");
							mainMenu = false;
							playMenu = true;
						}
					}

					if (e.getPoint().x > Width / 2 - 200 && e.getPoint().x < Width / 2 - 200 + mainMenuButtonW) {
						if (e.getPoint().y > Height / 2 - 80 && e.getPoint().y < Height / 2 - 80 + mainMenuButtonH) {
							System.out.println("Options");
							mainMenu = false;
							optionMenu = true;
						}
					}

					if (e.getPoint().x > Width / 2 - 200 && e.getPoint().x < Width / 2 - 200 + mainMenuButtonW) {
						if (e.getPoint().y > Height / 2 + 40 && e.getPoint().y < Height / 2 + 40 + mainMenuButtonH) {
							System.out.println("Exit");
							System.exit(0);
						}
					}
				}

				if (playMenu == true) {
					if (e.getPoint().x > 0 && e.getPoint().x < 150) {
						if (e.getPoint().y > 0 && e.getPoint().y < 75) {
							System.out.println("back");
							playMenu = false;
							mainMenu = true;
						}
					}

					if (e.getPoint().x > Width / 2 && e.getPoint().x < Width / 2 + playMenuButtonW) {
						for (int j = 0; j < songs.size(); j++) {
							if (e.getPoint().y > 50 + 70 * j + scroll
									&& e.getPoint().y < 50 + 70 * j + scroll + playMenuButtonH) {
								System.out.println("game" + j);
								playMenu = false;
								playingSong = songs.get(j);
								gameMenu = true;
								se.play();
							}
						}
					}

					double distance = 0; // distance from center
					distance = Math.sqrt((e.getPoint().x - Width / 4) * (e.getPoint().x - Width / 4)
							+ (e.getPoint().y - Height / 2) * (e.getPoint().y - Height / 2));
					if (distance <= Width / 8) {
						if (makingSong == false) {
							JFileChooser j = new JFileChooser();
							// Open the save dialog
							int result = j.showOpenDialog(null);
							if (result == JFileChooser.APPROVE_OPTION) {
								selectedFile = j.getSelectedFile();
								// System.out.println("Selected file: " + selectedFile.getAbsolutePath());
								makingSong = true;
							}
						} else if (makingSong == true) {
							System.out.println("publishing...");
							try {
								songs.add(new Song(selectedFile.getName(), currSongRows, currSongDifficulty,
										GetAudioGraph(selectedFile.getAbsolutePath())));
							} catch (IOException | UnsupportedAudioFileException | InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}

				}

				if (optionMenu == true) {

					if (selecting == true) {
						selecting = false;
					}

					if (e.getPoint().x > 0 && e.getPoint().x < 150) {
						if (e.getPoint().y > 0 && e.getPoint().y < 75) {
							System.out.println("back");
							optionMenu = false;
							mainMenu = true;
						}
					}

					if (e.getPoint().y > Height / 2 && e.getPoint().y < Height / 2 + 50) {

						for (int i = 0; i < 7; i++) {
							if (e.getPoint().x > Width / 2 - 50 * (i + 1) && e.getPoint().x < Width / 2 - 50 * (i)) {
								selectingKey = i;
								selecting = true;
							}
						}
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		canvas.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				if (playMenu == true) {
					scroll += 10 * e.getWheelRotation() * e.getScrollAmount();
					if (scroll < 0) {
						scroll = 0;
					}
				}
			}
		});

		canvas.setPreferredSize(new Dimension(Width, Height));
		frame.add(canvas);
		frame.setVisible(true);

		while (true) {
			Width = frame.getSize().width;
			Height = frame.getSize().height;

			canvas.requestFocus();
			frame.getContentPane().repaint();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
//		int[] a = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//				0, 0, 0, 0, 0, 0, 0 };
//		songs.add(new Song("test", 4, 5, a));
//		songs.add(new Song("test2", 1, 5, a));

		new GameMain();
	}

}
