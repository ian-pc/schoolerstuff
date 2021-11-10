package msPaints;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class msPaint {
	
	static class ShapePast {
		String shape = "";
		int x1, y1, x2, y2;
		Color color;
		int fontSize;
		int lineWidth;

		public ShapePast(String shape, int x1, int y1, int x2, int y2, Color color) {
			this.shape = shape;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.color = color;

			if (shape == "rectangle" || shape == "circle") {
				if (x1 > x2) {
					this.x1 = this.x2;
					this.x2 = x1;
				}
				if (y1 > y2) {
					this.y1 = this.y2;
					this.y2 = y1;
				}
			}

		}

		public ShapePast(String shape, int x1, int y1, Color color, int fontSize) {
			this.shape = shape;
			this.x1 = x1;
			this.y1 = y1;
			this.color = color;
			this.fontSize = fontSize;
		}
	}

	public static String textContent = "";
	public static int lineWidth = 1;
	public static int textSize = 1;
	public static String drawingTopic = "rectangle";
	public final static int Height = 600;
	public static final int Width = 600;
	public static JFrame frame;
	public static JButton rectangle, circle, line, text, chooseColor, delete, move, front, back, pen, save, undo;
	public static JTextField lineWidthField, textSizeField, textContentField;
	public static Color tempColor = new Color(0, 0, 0);
	public static JLabel lineWidthLabel, textSizeLabel, textContentLabel;
	public static boolean drawing = false;

	public static int x1Temp, x2Temp, y1Temp, y2Temp;

	public static ArrayList<Shape> shapes = new ArrayList<>();
	public static ArrayList<ArrayList<Shape>> archive = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		frame = new JFrame();
		lineWidthField = new JTextField();
		lineWidthField.setEditable(true);
		textSizeField = new JTextField();
		textSizeField.setEditable(true);
		textContentField = new JTextField();
		textContentField.setEditable(true);

		lineWidthField.setPreferredSize(new Dimension(100, 50));
		textSizeField.setPreferredSize(new Dimension(100, 50));
		textContentField.setPreferredSize(new Dimension(100, 50));

		lineWidthLabel = new JLabel("Line width: ");
		textContentLabel = new JLabel("Text content: ");

		textSizeLabel = new JLabel("Text size: ");

		rectangle = new JButton("rectangle");
		circle = new JButton("circle");
		chooseColor = new JButton("choose color");
		delete = new JButton("delete");
		move = new JButton("move");
		front = new JButton("front");
		back = new JButton("back");
		pen = new JButton("pen");
		save = new JButton("save");
		undo = new JButton("undo");
		line = new JButton("line");
		text = new JButton("text");

		// textField = new JTextField(16);

		frame.setSize(Width, Height);
		// closes the window when closed and stops the program.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

		JPanel buttons1 = new JPanel();
		buttons1.setLayout(new BoxLayout(buttons1, BoxLayout.X_AXIS));

		JPanel buttons2 = new JPanel();
		buttons2.setLayout(new BoxLayout(buttons2, BoxLayout.X_AXIS));

		JPanel buttons3 = new JPanel();
		buttons3.setLayout(new BoxLayout(buttons3, BoxLayout.X_AXIS));

		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);

				// g.fillRect(0, 0, Width, Height - 100);
				for (int i = 0; i < shapes.size(); i++) {
					shapes.get(i).draw(g);
				}

			}
		};

		canvas.setPreferredSize(new Dimension(Width, Height - 150));

		buttons1.add(rectangle);
		buttons1.add(line);
		buttons1.add(circle);
		buttons1.add(lineWidthLabel);
		buttons1.add(lineWidthField);

		buttons2.add(chooseColor);
		buttons2.add(text);
		buttons2.add(textSizeLabel);
		buttons2.add(textSizeField);
		buttons2.add(textContentLabel);
		buttons2.add(textContentField);

		buttons3.add(delete);
		buttons3.add(move);
		buttons3.add(front);
		buttons3.add(back);
		buttons3.add(pen);
		buttons3.add(save);
		buttons3.add(undo);

		buttons1.setPreferredSize(new Dimension(Width, 50));
		buttons2.setPreferredSize(new Dimension(Width, 50));
		buttons3.setPreferredSize(new Dimension(Width, 50));

		mainContainer.add(buttons1);
		mainContainer.add(buttons2);
		mainContainer.add(buttons3);
		mainContainer.add(canvas);

		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (drawingTopic == "rectangle") {
					if (drawing == false) {
						x1Temp = e.getPoint().x;
						y1Temp = e.getPoint().y;
						drawing = true;

					} else if (drawing == true) {
						x2Temp = e.getPoint().x;
						y2Temp = e.getPoint().y;
						drawing = false;
						shapes.add(new Rectangle(x1Temp, y1Temp, x2Temp, y2Temp, tempColor));
						x1Temp = x2Temp = y1Temp = y2Temp = 0;
						frame.getContentPane().repaint();
						
						if (archive.size() == 15) {
							archive.remove(0);
						}
						archive.add(shapes);
					}
				} else if (drawingTopic == "circle") {
					if (drawing == false) {
						x1Temp = e.getPoint().x;
						y1Temp = e.getPoint().y;
						drawing = true;

					} else if (drawing == true) {
						x2Temp = e.getPoint().x;
						y2Temp = e.getPoint().y;
						drawing = false;
						shapes.add(new Circle(x1Temp, y1Temp, x2Temp, y2Temp, tempColor));
						x1Temp = x2Temp = y1Temp = y2Temp = 0;
						frame.getContentPane().repaint();
						
						if (archive.size() == 15) {
							archive.remove(0);
						}
						archive.add(shapes);
					}
				} else if (drawingTopic == "line") {
					if (drawing == false) {
						x1Temp = e.getPoint().x;
						y1Temp = e.getPoint().y;
						drawing = true;

					} else if (drawing == true) {
						x2Temp = e.getPoint().x;
						y2Temp = e.getPoint().y;
						drawing = false;
						shapes.add(new Line(x1Temp, y1Temp, x2Temp, y2Temp, tempColor, lineWidth));
						x1Temp = x2Temp = y1Temp = y2Temp = 0;
						frame.getContentPane().repaint();
						
						if (archive.size() == 15) {
							archive.remove(0);
						}
						archive.add(shapes);
					}
				} else if (drawingTopic == "text") {
					shapes.add(new Text(textContentField.getText(), e.getPoint().x, e.getPoint().y, 
							tempColor, Integer.parseInt(textSizeField.getText())));
					frame.getContentPane().repaint();
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					archive.add(shapes);
				} else if (drawingTopic == "delete") {
					
					for (int i = 0; i < shapes.size(); i++) {
						if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
							shapes.remove(i);
						}
					}
					frame.getContentPane().repaint();
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					archive.add(shapes);
					
				} else if (drawingTopic == "move") {
					if (drawing == false) {
						x1Temp = e.getPoint().x;
						y1Temp = e.getPoint().y;
						drawing = true;

					} else if (drawing == true) {
						x2Temp = e.getPoint().x;
						y2Temp = e.getPoint().y;
						drawing = false;
						for (int i = 0; i < shapes.size(); i++) {
							if (shapes.get(i).isOn(x1Temp, y1Temp) == true) {
								shapes.get(i).move(x1Temp, y1Temp, x2Temp, y2Temp);
							}
						}
						x1Temp = x2Temp = y1Temp = y2Temp = 0;
						frame.getContentPane().repaint();
						
						if (archive.size() == 15) {
							archive.remove(0);
						}
						archive.add(shapes);
						
					}
					
				}else if (drawingTopic == "front") {
					
					for (int i = 0; i < shapes.size(); i++) {
						if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
							shapes.add(shapes.get(i));
							shapes.remove(i);
						}
					}
					frame.getContentPane().repaint();
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					archive.add(shapes);
					
				}else if (drawingTopic == "back") {
					
					for (int i = 0; i < shapes.size(); i++) {
						if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
							shapes.add(0, shapes.get(i));
							shapes.remove(i + 1);
						}
					}
					frame.getContentPane().repaint();
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					archive.add(shapes);
				}
				
				
				System.out.println(archive.get(0));

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

		rectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "rectangle";
				frame.getContentPane().repaint();
			}
		});

		circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "circle";
			}
		});

		line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "line";
				lineWidth = Integer.parseInt(lineWidthField.getText());
			}
		});

		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "text";
				textSize = Integer.parseInt(textSizeField.getText());
			}
		});
		
		chooseColor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				tempColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
				
			}

		});
		
		delete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "delete";
			}
			
		});
		
		move.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "move";
			}
			
		});
		
		front.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "front";
			}
			
		});
		
		back.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "back";
			}
			
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	    		
				BufferedImage image=new BufferedImage(canvas.getWidth(), canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
				
				Graphics2D g2=(Graphics2D)image.getGraphics();
				
				
				canvas.paint(g2);
				try {
					ImageIO.write(image, "png", new File("shot.png"));
				} catch (Exception e1) {
					
				}
			}
		});

		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (archive.size() != 0) {
					shapes = archive.get(archive.size() - 1);
				}
				frame.getContentPane().repaint();
			}
		});
		
		frame.add(mainContainer);

		frame.setVisible(true);

		

	}

}
