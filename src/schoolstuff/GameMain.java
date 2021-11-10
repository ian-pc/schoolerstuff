package schoolstuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.Vector;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameMain {

	static class Song {
		String name = "";
		int rows = 1;
		int map[] = new int[100];
		public Song(String name, int rows) {
			this.name = name;
			this.rows = rows;
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
	public static int Height = 600;
	public static int Width = 800;
	public int mainMenuButtonH = 100, mainMenuButtonW = 300;
	public boolean mainMenu = true, playMenu = false, optionMenu = false, gameMenu = false;
	public Song playingSong = null;
	public int playMenuButtonH = 75, playMenuButtonW = 300;
	public static boolean selecting = false;
	public static int selectingKey;
	public static char keys[] = {'s', 'd', 'f', ' ', 'j', 'k', 'l'};
	
	public static Vector<Song> songs = new Vector<Song>();
	
	
	public GameMain() {

		JFrame frame = new JFrame();
		frame.setSize(Width, Height);
		// closes the window when closed and stops the program.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel canvas = new JPanel() {
			// openfilechooser to get audio files
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(new Color(200, 200, 255));
				g.drawLine(Width / 2, 0, Width / 2, Height);
				g.drawLine(0, Height / 2, Width, Height / 2);

				g.setColor(new Color(0, 0, 0));

				if (mainMenu == true) {
					g.setColor(new Color(0, 0, 0));

					g.drawRect(Width / 2 - 200, Height / 2 - 200, mainMenuButtonW, mainMenuButtonH);
					g.setFont(new Font("Helvetica", Font.ITALIC, 80));
					g.drawString("PLAY", Width / 2 - 200 + mainMenuButtonW / 2 - 100, Height / 2 - 200 + 75);

					g.drawRect(Width / 2 - 200, Height / 2 - 80, mainMenuButtonW, mainMenuButtonH);
					g.setFont(new Font("Helvetica", Font.ITALIC, 55));
					g.drawString("OPTIONS", Width / 2 - 200 + mainMenuButtonW / 2 - 100 - 20, Height / 2 - 80 + 75);

					g.drawRect(Width / 2 - 200, Height / 2 + 40, mainMenuButtonW, mainMenuButtonH);
					g.setFont(new Font("Helvetica", Font.ITALIC, 80));
					g.drawString("EXIT", Width / 2 - 200 + mainMenuButtonW / 2 - 100, Height / 2 + 40 + 75);
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
				}

				if (gameMenu == true) {
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, Width, Height);
					g.setColor(Color.white);
					g.fillRect(Width/2, Height/2, (int) (Width/2 * 0.1 + 300), 2);
				}
				
				
				if (optionMenu == true) {
					// back button
					g.setColor(new Color(0, 0, 0));
					g.drawRect(0, 0, 150, 75);
					g.setFont(new Font("Helvetica", Font.ITALIC, 50));
					g.drawString("BACK", 0, 50);
					
					
					for (int i = 0; i < 7; i++) {
						g.setColor(new Color(0, 0, 0));
						g.drawRect(Width/2 - i * 50 - 50, Height/2, 50, 50);
						g.setFont(new Font("Helvetica", Font.PLAIN, 30));
						g.drawString(String.valueOf(keys[i]), Width/2 - i * 50 - 35, Height/2 + 40);
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
				
				if (gameMenu == true) {
					if (e.getKeyChar() == ' ') {
						System.out.println("ye");
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				// e.getKeyCode() == KeyEvent.VK_DOWN

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
							}
						}
					}

				}

				if (optionMenu == true) {
					if (e.getPoint().x > 0 && e.getPoint().x < 150) {
						if (e.getPoint().y > 0 && e.getPoint().y < 75) {
							System.out.println("back");
							optionMenu = false;
							mainMenu = true;
						}
					}
					
					if (e.getPoint().y > Height / 2 && e.getPoint().x < Height / 2 + 50) {
						for (int j = 0; j < 8; j++) {
							if (e.getPoint().x > Width/2 - 50 * (j+1) - 50 * 7 + 50
									&& e.getPoint().x < Width/2 - 50 * (j+1)) {
								selectingKey = j;
								System.out.println(j);
								//g.drawRect(Width/2 - i * 50 - 50, Height/2, 50, 50);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		songs.add(new Song("test", 4));
		songs.add(new Song("test2", 1));
		
		
		
		new GameMain();
	}

}
