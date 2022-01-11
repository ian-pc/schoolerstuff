package game;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Graphics2D;
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

public class GameMain2 {
	
	private static int Width, Height;
	private static int playButtonXDisplacement = 0, playButtonX, playButtonY;
	private static int mouseX, mouseY;
	private static int mouseHoveringThickness = 0;
	private static double mouseHoveringThicknessD = 0;
	private static int menuButtonX, menuButtonW;
	private Boolean playButtonOpen = false;
	private static Boolean exitting = false;
	private static int exittingShade = 0;
	private String room = "main";
	private String importStatus = "import";
	private static ArrayList<Song> songs = new ArrayList<>();
	private static int songsListYDisplacement = 25;
	private static Color colors[] = {new Color(248, 249, 250), new Color(233, 236, 239), new Color(222, 226, 230), 
			new Color(206, 212, 218), new Color(173, 181, 189), new Color(108, 117, 125), new Color(73, 80, 87), 
			new Color(52, 58, 64), new Color(33, 37, 41)
	};
	
	
	private static Font ModernSans(int style, int size) {
		Font tempFont = null;
		try {
		    //create the font to use. Specify the size!
		    tempFont = Font.createFont(Font.TRUETYPE_FONT, new File("Files/ModernSans-Light.ttf")).deriveFont(
		    		style, size);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(tempFont);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		return tempFont;

	}
	
	private static void HoveringEffect(Graphics g, int x, int y, int w, int h) {
		//Ian's code
		int innerThickness = 10;
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			g.setColor(new Color(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), 150));
			g.fillRect(x - innerThickness, y - innerThickness, w + 2*innerThickness, h + 2*innerThickness);
			
			g.setColor(new Color(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), 255 - 5 * mouseHoveringThickness));
			g.fillRect(x - mouseHoveringThickness, 
					y - mouseHoveringThickness, 
					w + 2*mouseHoveringThickness, h + 2*mouseHoveringThickness);
			
			
			mouseHoveringThicknessD+=0.5;
			mouseHoveringThickness = (int) mouseHoveringThicknessD;
			if (mouseHoveringThickness == 50) {mouseHoveringThickness = 0; mouseHoveringThicknessD = 0;}
			
			//fix the resetting later
		} 
	}
	
	private static void HoveringEffectC(Graphics g, int x, int y, int w, int h) {
		//Ian's code
		int innerThickness = 10;
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			g.setColor(new Color(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), 150));
			g.fillOval(x - innerThickness, y - innerThickness, w + 2*innerThickness, h + 2*innerThickness);
			
			g.setColor(new Color(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), 255 - 5 * mouseHoveringThickness));
			g.fillOval(x - mouseHoveringThickness, 
					y - mouseHoveringThickness, 
					w + 2*mouseHoveringThickness, h + 2*mouseHoveringThickness);
			
			
			mouseHoveringThicknessD+=0.5;
			mouseHoveringThickness = (int) mouseHoveringThicknessD;
			if (mouseHoveringThickness == 50) {mouseHoveringThickness = 0; mouseHoveringThicknessD = 0;}
			
			//fix the resetting later
		} 
	}
	
	public GameMain2() {
		
		//a
		JFrame frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setUndecorated(true);
		frame.setVisible(true);
		Width = frame.getSize().width;
		Height = frame.getSize().height;
		frame.getContentPane().setBackground(colors[1]);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//a
		 
		//v
		playButtonX = Width/2 - 200;
		playButtonY = Height/2 - 200;
		//v
		
		
		Font playButtonFont = ModernSans(Font.PLAIN, 100);
		Font menuButtonFont = ModernSans(Font.PLAIN, 75);
		Font backButtonFont = ModernSans(Font.BOLD, 75);
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				
				//l
				g.setColor(new Color(200, 200, 255));
				g.drawLine(Width / 2, 0, Width / 2, Height);
				g.drawLine(0, Height / 2, Width, Height / 2);
				//l
				
				if (room == "main") {
					//m

					HoveringEffect(g, playButtonX + playButtonXDisplacement, playButtonY, 400, 400);
					
					//b
					if (playButtonOpen == true) {
						if (playButtonXDisplacement >= -300) {
							playButtonXDisplacement -= 30;
						}
						//k
						menuButtonX = playButtonX + playButtonXDisplacement + 400 + (int) (-0.1*playButtonXDisplacement);
						menuButtonW = (int) (-1.5*playButtonXDisplacement);
						
						HoveringEffect(g, menuButtonX, playButtonY, menuButtonW, 120);

						g.setColor(colors[6]);
						g.fillRect(menuButtonX, playButtonY, menuButtonW, 120);
						Text gameButtonText = new Text(g, "Play", menuButtonX + (int) menuButtonW/2, 
								playButtonY + 60, menuButtonFont, colors[1]);
						
						
						HoveringEffect(g, menuButtonX, playButtonY + 140, menuButtonW, 120);

						g.setColor(colors[6]);
						g.fillRect(menuButtonX, playButtonY + 140, menuButtonW, 120);
						Text optionButtonText = new Text(g, "Option", menuButtonX + (int) menuButtonW/2, 
								playButtonY + 60 + 140, menuButtonFont, colors[1]);
						
						
						HoveringEffect(g, menuButtonX, playButtonY + 280, menuButtonW, 120);

						g.setColor(colors[6]);
						g.fillRect(menuButtonX, playButtonY + 280, menuButtonW, 120);
						Text exitButtonText = new Text(g, "Exit", menuButtonX + (int) menuButtonW/2, 
								playButtonY + 60 + 280, menuButtonFont, colors[1]);
						//k
						g.setColor(colors[8]);
					} else if (playButtonOpen == false) {
						if (playButtonXDisplacement < 0) {
							playButtonXDisplacement += 30;
						}
						
						g.setColor(colors[6]);
					}
					//b

					g.fillRect(playButtonX + playButtonXDisplacement, playButtonY, 400, 400);
					Text playButtonText = new Text(g, "Play!", Width/2 + playButtonXDisplacement, Height/2, 
							playButtonFont, colors[1]);
					
					if (exitting == true) {
						exittingShade += 10;
						if (1000 - exittingShade < 0) System.exit(0);
						g.setColor(new Color(colors[0].getRed(), colors[0].getGreen(), colors[0].getBlue(), 
								(exittingShade > 255) ? 255 : exittingShade));
						g.fillRect(0, 0, Width, Height);
					}
					
					//m
				} else if (room == "play") {

					for (int i = 0; i < songs.size(); i++) {
						HoveringEffect(g, Width / 2, 50 + 70 * i + songsListYDisplacement + 170, Width/2 - 100, 100);
						g.setColor(colors[6]);
						g.fillRect(Width / 2, 50 + 70 * i + songsListYDisplacement + 170, Width/2 - 100, 100);
						Text backButtonText = new Text(g, songs.get(i).name, 
								Width / 2 + 200, 50 + 50 + 75 * i + songsListYDisplacement + 170, 
								backButtonFont, colors[1]);
					}
					
					g.setColor(colors[8]);
					g.fillRect(0, 0, Width, 170);
					g.fillRect(0, 0, 520, 420);
					g.fillRect(0, Height-220, Width, 220);
					
					g.setColor(colors[2]);
					g.fillRect(0, 0, Width, 150);
					g.fillRect(0, 0, 500, 400);
					g.fillRect(0, Height-200, Width, 200);
					
					
					HoveringEffect(g, 50, Height - 160, 350, 125);
					g.setColor(colors[6]);
					g.fillRect(50, Height-160, 350, 125);
					g.setColor(colors[0]);
					
					Text backButtonText = new Text(g, "back", 50 + 350/2, Height - 160 + 125/2, 
							backButtonFont, colors[1]);
					
					//c
					HoveringEffectC(g, 100, 400 + (Height - 400 - 200)/2 - 150, 300, 300);
					g.setColor(colors[6]);
					g.fillOval(100, 400 + (Height - 400 - 200)/2 - 150, 300, 300);
					Text importStatusButtonText = new Text(g, importStatus, 250, 400 + (Height - 400 - 200)/2, 
							backButtonFont, colors[1]);
					
					//c
					
					
				} else if (room == "option") {
					
					
					
				}
				
			}
		};
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (room == "main") {
					//a
					if (mouseX > playButtonX + playButtonXDisplacement && 
							mouseX < playButtonX + 400 + playButtonXDisplacement &&
							mouseY > playButtonY && mouseY < playButtonY + 400) {
						if (playButtonOpen == true) {
							playButtonOpen = false;
						} else {
							playButtonOpen = true;
						}
					}
					//a
					
					if (playButtonOpen == true && playButtonXDisplacement < -100) {
						//b
						if (mouseX > menuButtonX && mouseX < menuButtonX + menuButtonW) {
							if (mouseY > playButtonY && mouseY < playButtonY + 120) {
								room = "play";
							}
							if (mouseY > playButtonY + 140 && mouseY < playButtonY + 140 + 120) {
								room = "option";
							}
							if (mouseY > playButtonY + 280 && mouseY < playButtonY + 280 + 120) {
								exitting = true;
							}
						}
						//b
					}
					
				} else if (room == "play") {
					
					//ba
					if (mouseX > 50 && mouseX < 400 && mouseY > Height - 160 && mouseY < Height - 160 + 125) {
						room = "main";
					}
					//ba
					
					
					
					
				} else if (room == "option") {
					
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
		
		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseX = e.getX();
				mouseY = e.getY();
			}
			
		});
		
		canvas.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				if (room == "play") {
					songsListYDisplacement += 10 * e.getWheelRotation() * e.getScrollAmount();
					if (songsListYDisplacement < 0) {
						songsListYDisplacement = 0;
					}
				}
			}
		});
		
		//a
		frame.add(canvas);
		frame.setVisible(true);
		while (true) {
			Width = frame.getSize().width;
			Height = frame.getSize().height;
			
			
			canvas.requestFocus();
			
			frame.getContentPane().repaint();
		}
		//a
	}
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		// TODO Auto-generated method stub

		songs.add(new Song(5, 4, "test", "Files/Hot-Milk.wav"));
		new GameMain2();
		
	}

}
