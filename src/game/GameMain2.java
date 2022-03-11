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
import java.util.Stack;
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
import java.sql.Time;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import testing.SoundPlayer;
import testing.Sound.SoundEffect;

public class GameMain2 {

	public int Width, Height;
	private int playButtonXDisplacement = 0, playButtonX, playButtonY;
	private static int mouseX;
	private static int mouseY;
	private static int mouseHoveringThickness = 0;
	private static double mouseHoveringThicknessD = 0;
	private int menuButtonX, menuButtonW;
	private int windowedButtonXDisplacement = 0;
	private Boolean playButtonOpen = false;
	private Boolean windowedButtonOpen = false;
	private Boolean exitting = false;
	private int exittingShade = 0;
	private String room = "main";
	private static ArrayList<Song> songs = new ArrayList<>();
	private int songsListYDisplacement = 25;
	private char[] keyBinds = { 's', 'd', 'f', ' ', 'j', 'k', 'l' };
	private static Color colors[] = { new Color(248, 249, 250), new Color(233, 236, 239), new Color(222, 226, 230),
			new Color(206, 212, 218), new Color(173, 181, 189), new Color(108, 117, 125), new Color(73, 80, 87),
			new Color(52, 58, 64), new Color(33, 37, 41) };
	private int curColorScheme = 0;
	private String[] curColorSchemeText = { "Gray Scale", "Rainbow" };
	private Color[][] colorsList = {
			{ new Color(248, 249, 250), new Color(233, 236, 239), new Color(222, 226, 230), new Color(206, 212, 218),
					new Color(173, 181, 189), new Color(108, 117, 125), new Color(73, 80, 87), new Color(52, 58, 64),
					new Color(33, 37, 41) },
			{ Color.RED, Color.BLUE, Color.PINK, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.CYAN, Color.MAGENTA,
					Color.LIGHT_GRAY } };
	private int SelectingKey = -1;
	private boolean selectingColorScheme = false;
	private int keyBindButtonTextHeight;
	private boolean makingSong = false;
	private File selectedFile;
	private int curSongRows = 4, curSongDifficulty = 5;
	private String curSongName = "";
	private boolean writingCurSongName = false;
	private int curSongNameOpacity = 100;
	private Song playingSong = null;
	private int playingSongScore = 0;
	private int playingSongNotesPlayed = 1;
	private int curFrame = 0;
	private Stack<Note> notesOnScreen = new Stack<>();
	private long elapsedTime;
	private int timeToReachLine;
	private boolean paused = false;
	long pausedTime;
	
	private class Note {
		public int row;
		public int yDisplacement = 0;

		public Note(int row) {
			this.row = row;
		}
	}

	// custom font loader
	private static Font ModernSans(int style, int size) {
		Font tempFont = null;
		try {
			// create the font to use. Specify the size!
			tempFont = Font.createFont(Font.TRUETYPE_FONT, new File("Files/ModernSans-Light.ttf")).deriveFont(style,
					size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont(tempFont);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		return tempFont;

	}

	// Rectangle
	private static void HoveringEffect(Graphics g, int x, int y, int w, int h) {
		// Ian's code
		int innerThickness = 10;
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			g.setColor(new Color(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), 150));
			g.fillRect(x - innerThickness, y - innerThickness, w + 2 * innerThickness, h + 2 * innerThickness);

			g.setColor(new Color(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(),
					255 - 5 * mouseHoveringThickness));
			g.fillRect(x - mouseHoveringThickness, y - mouseHoveringThickness, w + 2 * mouseHoveringThickness,
					h + 2 * mouseHoveringThickness);

			mouseHoveringThicknessD += 0.5;
			mouseHoveringThickness = (int) mouseHoveringThicknessD;
			if (mouseHoveringThickness == 50) {
				mouseHoveringThickness = 0;
				mouseHoveringThicknessD = 0;
			}

			// fix the resetting later
		}
	}

	// Circle
	private static void HoveringEffectC(Graphics g, int x, int y, int w, int h) {
		// Ian's code
		int innerThickness = 10;
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			g.setColor(new Color(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(), 150));
			g.fillOval(x - innerThickness, y - innerThickness, w + 2 * innerThickness, h + 2 * innerThickness);

			g.setColor(new Color(colors[4].getRed(), colors[4].getGreen(), colors[4].getBlue(),
					255 - 5 * mouseHoveringThickness));
			g.fillOval(x - mouseHoveringThickness, y - mouseHoveringThickness, w + 2 * mouseHoveringThickness,
					h + 2 * mouseHoveringThickness);

			mouseHoveringThicknessD += 0.5;
			mouseHoveringThickness = (int) mouseHoveringThicknessD;
			if (mouseHoveringThickness == 50) {
				mouseHoveringThickness = 0;
				mouseHoveringThicknessD = 0;
			}

			// fix the resetting later
		}
	}

	int rowsAlpha[] = new int[7];

	private void clickEffect(Graphics g) {
		for (int i = 0; i < rowsAlpha.length; i++) {
			// System.out.println(rowsAlpha[i]);
			if (rowsAlpha[i] > 0) {
				g.setColor(new Color(colors[5].getRed(), colors[5].getGreen(), colors[5].getBlue(), rowsAlpha[i]));

				g.fillRect(Width / 2 + i * (Width / 2 - 200) / playingSong.rows - 5, Height - 200 - 10,
						(Width / 2 - 200) / playingSong.rows + 5, 23);
				rowsAlpha[i] -= 5;
			}
		}
	}

	private Clip clip;

	private void playSong(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File file = new File(filename);
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
	}

	public GameMain2() {

		// a
		JFrame frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
		Width = frame.getSize().width;
		Height = frame.getSize().height;
		frame.getContentPane().setBackground(colors[1]);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// a

		// v
		playButtonX = Width / 2 - 200;
		playButtonY = Height / 2 - 200;
		// v

		Font playButtonFont = ModernSans(Font.PLAIN, 100);
		Font menuButtonFont = ModernSans(Font.PLAIN, 75);
		Font backButtonFont = ModernSans(Font.BOLD, 75);
		Font optionMenuFont = ModernSans(Font.BOLD, 50);
		Font keyBindsFont = ModernSans(Font.PLAIN, 25);
		Font playMenuFont = ModernSans(Font.BOLD, 30);
		Font playMenuFont2 = ModernSans(Font.PLAIN, 50);
		Font resultGradeFont = ModernSans(Font.BOLD, 400);
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);

				Width = frame.getSize().width;
				Height = frame.getSize().height;
				// l
				g.setColor(new Color(200, 200, 255));
				g.drawLine(Width / 2, 0, Width / 2, Height);
				g.drawLine(0, Height / 2, Width, Height / 2);
				// l

				if (room == "main") {
					// m

					HoveringEffect(g, playButtonX + playButtonXDisplacement, playButtonY, 400, 400);

					// b
					if (playButtonOpen == true) {
						if (playButtonXDisplacement >= -300) {
							playButtonXDisplacement -= 30;
						}
						// k
						menuButtonX = playButtonX + playButtonXDisplacement + 400
								+ (int) (-0.1 * playButtonXDisplacement);
						menuButtonW = (int) (-1.5 * playButtonXDisplacement);

						HoveringEffect(g, menuButtonX, playButtonY, menuButtonW, 120);

						g.setColor(colors[6]);
						g.fillRect(menuButtonX, playButtonY, menuButtonW, 120);
						Text gameButtonText = new Text(g, "Play", menuButtonX + (int) menuButtonW / 2, playButtonY + 60,
								menuButtonFont, colors[1]);

						HoveringEffect(g, menuButtonX, playButtonY + 140, menuButtonW, 120);

						g.setColor(colors[6]);
						g.fillRect(menuButtonX, playButtonY + 140, menuButtonW, 120);
						Text optionButtonText = new Text(g, "Option", menuButtonX + (int) menuButtonW / 2,
								playButtonY + 60 + 140, menuButtonFont, colors[1]);

						HoveringEffect(g, menuButtonX, playButtonY + 280, menuButtonW, 120);

						g.setColor(colors[6]);
						g.fillRect(menuButtonX, playButtonY + 280, menuButtonW, 120);
						Text exitButtonText = new Text(g, "Exit", menuButtonX + (int) menuButtonW / 2,
								playButtonY + 60 + 280, menuButtonFont, colors[1]);
						// k
						g.setColor(colors[8]);
					} else if (playButtonOpen == false) {
						if (playButtonXDisplacement < 0) {
							playButtonXDisplacement += 30;
						}

						g.setColor(colors[6]);
					}
					// b

					g.fillRect(playButtonX + playButtonXDisplacement, playButtonY, 400, 400);
					Text playButtonText = new Text(g, "Play!", Width / 2 + playButtonXDisplacement, Height / 2,
							playButtonFont, colors[1]);

					if (exitting == true) {
						exittingShade += 10;
						if (1000 - exittingShade < 0)
							System.exit(0);
						g.setColor(new Color(colors[0].getRed(), colors[0].getGreen(), colors[0].getBlue(),
								(exittingShade > 255) ? 255 : exittingShade));
						g.fillRect(0, 0, Width, Height);
					}

					// m
				} else if (room == "play") {

					for (int i = 0; i < songs.size(); i++) {
						HoveringEffect(g, Width / 2, 50 + 120 * i + songsListYDisplacement + 170, Width / 2 - 100, 100);
						g.setColor(colors[6]);
						g.fillRect(Width / 2, 50 + 120 * i + songsListYDisplacement + 170, Width / 2 - 100, 100);
						Text backButtonText = new Text(g, songs.get(i).name, Width / 2 + 200,
								50 + 50 + 120 * i + songsListYDisplacement + 170, backButtonFont, colors[1]);
					}

					g.setColor(colors[8]);
					g.fillRect(0, 0, Width, 170);
					g.fillRect(0, Height - 220, Width, 220);

					g.setColor(colors[2]);
					g.fillRect(0, 0, Width, 150);
					g.fillRect(0, Height - 200, Width, 200);

					HoveringEffect(g, 50, Height - 160, 350, 125);
					g.setColor(colors[6]);
					g.fillRect(50, Height - 160, 350, 125);
					g.setColor(colors[0]);

					Text backButtonText = new Text(g, "back", 50 + 350 / 2, Height - 160 + 125 / 2, backButtonFont,
							colors[1]);

					// c
					HoveringEffect(g, 100, 400 + (Height - 400 - 400) / 2 - 150, 300, 300);
					g.setColor(colors[6]);
					g.fillRect(100, 400 + (Height - 400 - 400) / 2 - 150, 300, 300);
					Text importStatusButtonText = new Text(g, makingSong ? "publish" : "import", 250,
							400 + (Height - 400 - 400) / 2, backButtonFont, colors[1]);

					// c

					if (makingSong == true) {
						g.setColor(colors[5]);
						g.fillRoundRect(425, Height / 2 - 50, 320, 10, 5, 5);
						g.fillRoundRect(425, Height / 2 + 50, 320, 10, 5, 5);
						g.fillRect(425, Height / 2 + 200, 320, 3);

						g.setColor(colors[6]);
						Text curSongRowsText = new Text(g, "Rows: ", 425, Height / 2 - 50 - 50, playMenuFont, colors[6],
								true);
						Text curSongDifficultyText = new Text(g, "Difficulty: ", 425, Height / 2 + 50 - 50,
								playMenuFont, colors[6], true);
						Text curSongNameText = new Text(g, "Name: ", 425, Height / 2 + 200 - 100, playMenuFont,
								colors[6], true);
						g.fillRoundRect(425 + (320 / 7) * curSongRows, Height / 2 - 60, 10, 30, 5, 5);
						g.fillRoundRect(425 + (320 / 10) * curSongDifficulty, Height / 2 + 40, 10, 30, 5, 5);
						Text curSongNameText2 = new Text(g, curSongName, 425, Height / 2 + 200 - 60, playMenuFont2,
								colors[6], true);

						if (writingCurSongName == true) {
							curSongNameOpacity -= ((curSongNameOpacity > 50) ? 1 : curSongNameOpacity - 255);
							g.setColor(new Color(colors[5].getRed(), colors[5].getGreen(), colors[5].getBlue(),
									curSongNameOpacity));
							g.fillRect(425 + (int) (curSongNameText2.w * 1.02), Height / 2 + 203 - 60, 3,
									curSongNameText2.h);
						}
					}

				} else if (room == "option") {

					HoveringEffect(g, 100, Height - 160, 350, 125);
					g.setColor(colors[6]);
					g.fillRect(100, Height - 160, 350, 125);
					g.setColor(colors[0]);

					Text backButtonText = new Text(g, "back", 100 + 350 / 2, Height - 160 + 125 / 2, backButtonFont,
							colors[1]);

					// k
					Text keyBindSettingText = new Text(g, "Keybinds: ", 100, 75, optionMenuFont, colors[7], true);

					Text[] keyBindText = new Text[7];
					for (int i = 0; i < 7; i++) {
						HoveringEffect(g, 100 + 75 * i + 10 * i, 75 + keyBindSettingText.h + 25, 75, 75);
						g.setColor(colors[7]);
						g.fillRect(100 + 75 * i + 10 * i, 75 + keyBindSettingText.h + 25, 75, 75);
						g.setColor(colors[2]);
						g.fillRect(100 + 5 + 75 * i + 10 * i, 75 + keyBindSettingText.h + 25 + 5, 65, 65);
						keyBindText[i] = new Text(g, Character.toString(keyBinds[i]), 100 + 5 + 75 * i + 10 * i + 33,
								75 + keyBindSettingText.h + 25 + 5 + 5 + 33, optionMenuFont, colors[7]);
					}
					keyBindButtonTextHeight = keyBindSettingText.h;
					if (SelectingKey != -1) {
						Text selectingText = new Text(g, "Press a key ", 100 + 5 + 75 * 7 + 10 * 7 + 33 + 200,
								75 + keyBindSettingText.h + 25 + 5 + 5 + 33, optionMenuFont, colors[7]);
					}

					// k

					Text windowedSettingText = new Text(g, "Windowed: ", 100, 375, optionMenuFont, colors[7], true);

					if (windowedButtonOpen) {
						if (windowedButtonXDisplacement <= 140 - 55 - 5 - 5) {
							windowedButtonXDisplacement += 2;
						}
					} else {
						if (windowedButtonXDisplacement >= 0) {
							windowedButtonXDisplacement -= 2;
						}
					}

					HoveringEffect(g, 100, 375 + windowedSettingText.h + 25, 150, 75);
					g.setColor(colors[7]);
					g.fillRect(100, 375 + windowedSettingText.h + 25, 150, 75);
					g.setColor(windowedButtonOpen ? colors[5] : colors[2]);
					g.fillRect(100 + 5, 375 + windowedSettingText.h + 25 + 5, 140, 65);
					g.setColor(colors[7]);
					g.fillRect(100 + 5 + 5 + windowedButtonXDisplacement, 375 + windowedSettingText.h + 25 + 5 + 5, 55,
							55);

					Text colorThemeSettingText = new Text(g, "Color Scheme: ", 100, 225, optionMenuFont, colors[7],
							true);

					if (!selectingColorScheme) {
						HoveringEffect(g, 100, 225 + colorThemeSettingText.h + 25, 400, 75);
					}
					g.setColor(colors[7]);
					g.fillRect(100, 225 + colorThemeSettingText.h + 25, 400, 75);
					g.setColor(colors[2]);
					g.fillRect(100 + 5, 225 + colorThemeSettingText.h + 25 + 5, 390, 65);
					String tempText = null;
					Text ColorTheme = new Text(g, curColorSchemeText[curColorScheme], 100 + 5 + 75,
							225 + colorThemeSettingText.h + 33, optionMenuFont, colors[7], true);
					if (!selectingColorScheme) {
						g.fillPolygon(new int[] { 100 + 5 + 25, 100 + 5 + 25 + 25, 100 + 5 + 25 + 12 },
								new int[] { 225 + colorThemeSettingText.h + 25 + 30,
										225 + colorThemeSettingText.h + 25 + 30,
										225 + colorThemeSettingText.h + 25 + 50 },
								3);
					} else {
						g.fillPolygon(new int[] { 100 + 5 + 25, 100 + 5 + 25 + 25, 100 + 5 + 25 + 12 },
								new int[] { 225 + colorThemeSettingText.h + 25 + 50,
										225 + colorThemeSettingText.h + 25 + 50,
										225 + colorThemeSettingText.h + 25 + 30 },
								3);
					}
					if (selectingColorScheme) {
						for (int i = 0; i < colorsList.length; i++) {
							if (selectingColorScheme) {
								HoveringEffect(g, 100, 225 + colorThemeSettingText.h + 25 + (i + 1) * 70, 400, 75);
							}
							g.setColor(colors[7]);
							g.fillRect(100, 225 + colorThemeSettingText.h + 25 + (i + 1) * 70, 400, 75);
							g.setColor(colors[2]);
							g.fillRect(100 + 5, 225 + colorThemeSettingText.h + 25 + 5 + (i + 1) * 70, 390, 65);
							Text tempButtonText = new Text(g, curColorSchemeText[i], 100 + 5 + 75,
									225 + colorThemeSettingText.h + 33 + (i + 1) * 70, optionMenuFont, colors[7], true);
						}
					}

				} else if (room == "game") {
					
					long startTime = System.nanoTime();
					
					if (clip.getFramePosition() / 10000 == playingSong.song.length) {
						room = "play";
						curFrame = 0;
						notesOnScreen = new Stack<>();
					}

					curFrame = (int) (clip.getMicrosecondPosition() / 10000);

					g.setColor(colors[2]);
					g.fillRect(0, 0, Width, Height);

					g.setColor(colors[7]);
					g.fillRect(Width / 2, Height - 200, Width / 2 - 200, 3);

					Text playingSongName = new Text(g, playingSong.name, 100, 50, optionMenuFont, colors[7]);
					Text playingSongScoreT = new Text(g, "Score: " + playingSongScore, 100, 200, optionMenuFont,
							colors[7]);

					// System.out.println(curFrame/100);
					if (playingSong.song[(int) (((curFrame - timeToReachLine) > 0) ? (curFrame - timeToReachLine - 1) : 0)] != -1) {
						notesOnScreen.push(new Note(playingSong.song[(int) (((curFrame - timeToReachLine) > 0) ? (curFrame - timeToReachLine - 1) : 0)/*curFrame*/]));
					}
					for (int i = 0; i < notesOnScreen.size(); i++) {
						notesOnScreen.get(i).yDisplacement += 2;
						g.setColor(colors[6]);
						g.fillRect(Width / 2 + notesOnScreen.get(i).row * (Width / 2 - 200) / playingSong.rows,
								notesOnScreen.get(i).yDisplacement, (Width / 2 - 200) / playingSong.rows, 5);
					}

					clickEffect(g);
					elapsedTime = (System.nanoTime() - startTime) / 10000000;
					timeToReachLine = (int) (elapsedTime * (Height - 200)/5);
					
					if (notesOnScreen.size() > 0) {
						if (notesOnScreen.firstElement().yDisplacement > Height - 200 + 75) {
							notesOnScreen.pop();
						}
					}
					
				} else if (room == "result") {
					
					String grade;
					if (playingSongScore/playingSongNotesPlayed > 0.98) {
						grade = "S";
					} else if (playingSongScore/playingSongNotesPlayed > 0.90) {
						grade = "A";
					} else if (playingSongScore/playingSongNotesPlayed > 0.80) {
						grade = "B";
					} else if (playingSongScore/playingSongNotesPlayed > 0.75) {
						grade = "C";
					} else {
						grade = "D";
					}
					
					Text resultGradeText = new Text(g, grade, Width/2 + 100,
							Height/2, resultGradeFont, colors[6]);
				}

			}
		};

		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (room == "main") {
					// a
					if (mouseX > playButtonX + playButtonXDisplacement
							&& mouseX < playButtonX + 400 + playButtonXDisplacement && mouseY > playButtonY
							&& mouseY < playButtonY + 400) {
						if (playButtonOpen == true) {
							playButtonOpen = false;
						} else {
							playButtonOpen = true;
						}
					}
					// a

					if (playButtonOpen == true && playButtonXDisplacement < -100) {
						// b
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
						// b
					}

				} else if (room == "play") {

					// back
					if (mouseX > 50 && mouseX < 400 && mouseY > Height - 160 && mouseY < Height - 160 + 125) {
						room = "main";
					}
					// back

					// play

					if (mouseX > Width / 2 && mouseX < Width / 2 + Width / 2 - 100) {
						for (int i = 0; i < songs.size(); i++) {
							if (mouseY > 50 + 120 * i + songsListYDisplacement + 170
									&& mouseY < 50 + 120 * i + songsListYDisplacement + 170 + 100) {
								room = "game";
								try {
									playSong(songs.get(i).loc);
								} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								playingSong = songs.get(i);
							}
						}
					}

					// play

					// import
					if (mouseX > 100 && mouseX < 100 + 300 && mouseY > 400 + (Height - 400 - 400) / 2 - 150
							&& mouseY < 400 + (Height - 400 - 400) / 2 - 150 + 300) {

						if (makingSong == false) {
							JFileChooser j = new JFileChooser();
							// Open the save dialog
							int result = j.showOpenDialog(null);
							if (result == JFileChooser.APPROVE_OPTION) {
								selectedFile = j.getSelectedFile();
								// System.out.println("Selected file: " + selectedFile.getAbsolutePath());
								curSongName = selectedFile.getName().replace(".wav", "");
								makingSong = true;
							}
						} else if (makingSong == true) {
							makingSong = false;
							try {
								songs.add(
										new Song(curSongDifficulty, curSongRows, curSongName, selectedFile.getAbsolutePath()));
							} catch (IOException | UnsupportedAudioFileException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}

					if (makingSong == true) {
						if (mouseX > 425 && mouseX < 425 + 300) {
							if (mouseY > Height / 2 + 200 - 80 && mouseY < Height + 200) {
								writingCurSongName = true;

							}
						}

						if (mouseX < 425 || mouseX > 425 + 300) {
							writingCurSongName = false;
						}
						if (mouseY < Height / 2 + 200 - 80 || mouseY > Height + 200) {
							writingCurSongName = false;
						}
					}

					// import

				} else if (room == "option") {

					// back
					if (mouseX > 100 && mouseX < 400 && mouseY > Height - 160 && mouseY < Height - 160 + 125) {
						room = "main";
					}
					// back

					for (int i = 0; i < 7; i++) {
						if (mouseX > 100 + 75 * i + 10 * i && mouseX < 100 + 75 * i + 10 * i + 75) {
							if (mouseY > 75 + keyBindButtonTextHeight + 25
									&& mouseY < 75 + keyBindButtonTextHeight + 25 + 75) {
								if (SelectingKey == -1) {
									SelectingKey = i;
								} else {
									SelectingKey = -1;
								}
							}
						}
					}

					if (mouseX > 100 && mouseX < 100 + 400) {
						if (mouseY > 225 + keyBindButtonTextHeight + 25
								&& mouseY < 225 + keyBindButtonTextHeight + 25 + 75) {
							selectingColorScheme = !selectingColorScheme;
						}
					}

					if (selectingColorScheme) {
						for (int i = 0; i < colorsList.length; i++) {
							if (mouseX > 100 + 5 && mouseX < 100 + 5 + 390) {
								if (mouseY > 225 + keyBindButtonTextHeight + 25 + 5 + (i + 1) * 70
										&& mouseY < 225 + keyBindButtonTextHeight + 25 + 5 + (i + 1) * 70 + 65) {
									curColorScheme = i;
									colors = colorsList[i];
								}
							}

						}
					}

					// windowed
					if (mouseX > 100 && mouseX < 100 + 150) {
						if (mouseY > 375 + keyBindButtonTextHeight + 25
								&& mouseY < 375 + keyBindButtonTextHeight + 25 + 75) {
							windowedButtonOpen = !windowedButtonOpen;
							if (!windowedButtonOpen) {

								frame.dispose();
								frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
								frame.setUndecorated(true);

								frame.setUndecorated(true);
								frame.setVisible(true);
							} else {
								frame.dispose();
								frame.setExtendedState(JFrame.NORMAL);
								frame.setSize(Width, Height);
								frame.setLocationRelativeTo(null);

								frame.setUndecorated(false);
								frame.setVisible(true);
							}
						}
					}

					// windowed

				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (room == "main") {

				} else if (room == "play") {
					if (makingSong == true) {
						if (mouseX > 425 && mouseX < 425 + 320) {
							if (mouseY > Height / 2 - 70 && mouseY < Height / 2 - 70 + 40) {
								curSongRows = Math.round((mouseX - 425) / (320 / 7));
							}
							if (mouseY > Height / 2 + 45 && mouseY < Height / 2 + 45 + 35) {
								curSongDifficulty = Math.round((mouseX - 425) / (320 / 9));
							}
						}
					}
				} else if (room == "option") {

				}
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

		canvas.addKeyListener(new KeyListener() {

			/**
			 * @param e
			 */
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

				if (room == "main") {

				} else if (room == "play") {

				} else if (room == "option") {
					if (SelectingKey != -1) {
						for (int i = 0; i < 7; i++) {
							if (keyBinds[i] == e.getKeyChar()) {
								keyBinds[i] = keyBinds[SelectingKey];
								keyBinds[SelectingKey] = e.getKeyChar();
							}
						}
						keyBinds[SelectingKey] = e.getKeyChar();
						SelectingKey = -1;
					}

				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (room == "play") {
					if (makingSong == true) {
						if (writingCurSongName == true) {
							if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
								if (curSongName.length() > 0) {
									curSongName = curSongName.substring(0, curSongName.length() - 1);
								}
							} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER) {
								writingCurSongName = false;
							} else {
								curSongName += e.getKeyChar();
							}
						}
					}
				} else if (room == "game") {
					char keysRows[] = new char[playingSong.rows];
					
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						paused = !paused;
					}
					
					if (playingSong.rows == 1) {
						keysRows[0] = keyBinds[3];
						if (e.getKeyChar() == keyBinds[4]) {
							rowsAlpha[0] = 255;
						}
					} else if (playingSong.rows == 2) {

						keysRows[0] = keyBinds[2];
						keysRows[1] = keyBinds[4];
						
						if (e.getKeyChar() == keyBinds[2]) {
							rowsAlpha[0] = 255;
						}
						if (e.getKeyChar() == keyBinds[4]) {
							rowsAlpha[1] = 255;
						}
					} else if (playingSong.rows == 3) {

						keysRows[0] = keyBinds[2];
						keysRows[1] = keyBinds[3];
						keysRows[2] = keyBinds[4];
						if (e.getKeyChar() == keyBinds[2]) {
							rowsAlpha[0] = 255;
						}
						if (e.getKeyChar() == keyBinds[3]) {
							rowsAlpha[1] = 255;
						}
						if (e.getKeyChar() == keyBinds[4]) {
							rowsAlpha[2] = 255;
						}
					} else if (playingSong.rows == 4) {
						keysRows[0] = keyBinds[1];
						keysRows[1] = keyBinds[2];
						keysRows[2] = keyBinds[4];
						keysRows[3] = keyBinds[5];
						System.out.println(keysRows[3]);
						if (e.getKeyChar() == keyBinds[1]) {
							rowsAlpha[0] = 255;
						}
						if (e.getKeyChar() == keyBinds[2]) {
							rowsAlpha[1] = 255;
						}
						if (e.getKeyChar() == keyBinds[4]) {
							rowsAlpha[2] = 255;
						}
						if (e.getKeyChar() == keyBinds[5]) {
							rowsAlpha[3] = 255;
						}
					} else if (playingSong.rows == 5) {
						keysRows[0] = keyBinds[2];
						keysRows[1] = keyBinds[3];
						keysRows[2] = keyBinds[4];
						keysRows[3] = keyBinds[5];
						keysRows[4] = keyBinds[6];
						if (e.getKeyChar() == keyBinds[2]) {
							rowsAlpha[0] = 255;
						}
						if (e.getKeyChar() == keyBinds[3]) {
							rowsAlpha[1] = 255;
						}
						if (e.getKeyChar() == keyBinds[4]) {
							rowsAlpha[2] = 255;
						}
						if (e.getKeyChar() == keyBinds[5]) {
							rowsAlpha[3] = 255;
						}
						if (e.getKeyChar() == keyBinds[6]) {
							rowsAlpha[4] = 255;
						}
					} else if (playingSong.rows == 6) {
						keysRows[0] = keyBinds[0];
						keysRows[1] = keyBinds[1];
						keysRows[2] = keyBinds[2];
						keysRows[3] = keyBinds[4];
						keysRows[4] = keyBinds[5];
						keysRows[5] = keyBinds[6];
						if (e.getKeyChar() == keyBinds[0]) {
							rowsAlpha[0] = 255;
						}
						if (e.getKeyChar() == keyBinds[1]) {
							rowsAlpha[1] = 255;
						}
						if (e.getKeyChar() == keyBinds[2]) {
							rowsAlpha[2] = 255;
						}
						if (e.getKeyChar() == keyBinds[4]) {
							rowsAlpha[3] = 255;
						}
						if (e.getKeyChar() == keyBinds[5]) {
							rowsAlpha[4] = 255;
						}
						if (e.getKeyChar() == keyBinds[6]) {
							rowsAlpha[5] = 255;
						}
					} else if (playingSong.rows == 7) {
						keysRows[0] = keyBinds[0];
						keysRows[1] = keyBinds[1];
						keysRows[2] = keyBinds[2];
						keysRows[3] = keyBinds[3];
						keysRows[4] = keyBinds[4];
						keysRows[5] = keyBinds[5];
						keysRows[6] = keyBinds[6];
						if (e.getKeyChar() == keyBinds[0]) {
							rowsAlpha[0] = 255;
						}
						if (e.getKeyChar() == keyBinds[1]) {
							rowsAlpha[1] = 255;
						}
						if (e.getKeyChar() == keyBinds[2]) {
							rowsAlpha[2] = 255;
						}
						if (e.getKeyChar() == keyBinds[3]) {
							rowsAlpha[3] = 255;
						}
						if (e.getKeyChar() == keyBinds[4]) {
							rowsAlpha[4] = 255;
						}
						if (e.getKeyChar() == keyBinds[5]) {
							rowsAlpha[5] = 255;
						}
						if (e.getKeyChar() == keyBinds[6]) {
							rowsAlpha[6] = 255;
						}
					}
					
					if (notesOnScreen.size() > 0) {
						if (keysRows[notesOnScreen.firstElement().row] == e.getKeyChar()) {
							System.out.println(notesOnScreen.pop());
						}
					}
					
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseX = e.getX();
				mouseY = e.getY();
				if (room == "main") {

				} else if (room == "play") {
					if (makingSong == true) {
						if (mouseX > 420 && mouseX < 425 + 325) {
							if (mouseY > Height / 2 - 70 && mouseY < Height / 2 - 70 + 40) {
								curSongRows = Math.round((mouseX - 425) / (320 / 7));
							}
							if (mouseY > Height / 2 + 45 && mouseY < Height / 2 + 45 + 35) {
								curSongDifficulty = Math.round((mouseX - 425) / (320 / 10));
							}
						}
					}
				} else if (room == "option") {

				}
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

				}
			}
		});

		// a
		frame.add(canvas);
		frame.setVisible(true);
		while (true) {
			Width = frame.getSize().width;
			Height = frame.getSize().height;

			canvas.requestFocus();

			frame.getContentPane().repaint();
		}
		// a
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		// TODO Auto-generated method stub

		songs.add(new Song(9, 4, "test", "Files/Hot-Milk.wav"));
		new GameMain2();

	}

}
