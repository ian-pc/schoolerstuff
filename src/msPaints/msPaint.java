package msPaints;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

import msPaints.Pen.Pixel;



public class msPaint {
	
	class ShapePast {
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

	public String textContent = "";
	public int lineWidth = 1;
	public int textSize = 1;
	public String drawingTopic = "rectangle";
	public final int Height = 600;
	public final int Width = 600;
	public JFrame frame;
	public JButton rectangle, circle, line, text, chooseColor, delete, move, front, back, pen, save, undo, clear;
	public JTextField lineWidthField, textSizeField, textContentField;
	public Color tempColor = new Color(0, 0, 0);
	public JLabel lineWidthLabel, textSizeLabel, textContentLabel;
	public boolean drawing = false;
	public boolean holding = false;
	public int MDX = 0, MDY = 0;
	public int movingIndex;
	public boolean moving = false;
	
	public int x1Temp, x2Temp, y1Temp, y2Temp;

	public ArrayList<Shape> shapes = new ArrayList<>();
	public ArrayList<ArrayList<Shape>> archive = new ArrayList<>();

	public msPaint() {
		frame = new JFrame();
		lineWidthField = new JTextField();
		lineWidthField.setEditable(true);
		textSizeField = new JTextField();
		textSizeField.setEditable(true);
		textContentField = new JTextField();
		textContentField.setEditable(true);


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
		clear = new JButton("clear");


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
		buttons3.add(clear);

		buttons1.setPreferredSize(new Dimension(Width, 50));
		buttons2.setPreferredSize(new Dimension(Width, 50));
		buttons3.setPreferredSize(new Dimension(Width, 50));

		lineWidthField.setMaximumSize(new Dimension(100, 50));
		textSizeField.setMaximumSize(new Dimension(100, 50));
		textContentField.setMaximumSize(new Dimension(100, 50));

		mainContainer.add(buttons1);
		mainContainer.add(buttons2);
		mainContainer.add(buttons3);
		mainContainer.add(canvas);

		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (drawingTopic == "text") {
					shapes.add(new Text(textContentField.getText(), e.getPoint().x, e.getPoint().y, 
							tempColor, Integer.parseInt(textSizeField.getText())));
					frame.getContentPane().repaint();
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					ArrayList<Shape> tempShapes = new ArrayList<>();
					for (int i = 0; i < shapes.size(); i++) {
						tempShapes.add(shapes.get(i).copy());
					}
					archive.add(tempShapes);
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
					ArrayList<Shape> tempShapes = new ArrayList<>();
					for (int i = 0; i < shapes.size(); i++) {
						tempShapes.add(shapes.get(i).copy());
					}
					archive.add(tempShapes);
					
				} else if (drawingTopic == "front") {
					
					for (int i = shapes.size(); i < 0; i--) {
						if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
							shapes.add(shapes.get(i));
							shapes.remove(i);
							break;	
						}
					}
					frame.getContentPane().repaint();
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					ArrayList<Shape> tempShapes = new ArrayList<>();
					for (int i = 0; i < shapes.size(); i++) {
						tempShapes.add(shapes.get(i).copy());
					}
					archive.add(tempShapes);
					
				}else if (drawingTopic == "back") {
					
					for (int i = 0; i < shapes.size(); i++) {
						if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
							shapes.add(0, shapes.get(i));
							shapes.remove(i + 1);
							break;
						}
					}
					frame.getContentPane().repaint();
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					ArrayList<Shape> tempShapes = new ArrayList<>();
					for (int i = 0; i < shapes.size(); i++) {
						tempShapes.add(shapes.get(i).copy());
					}
					archive.add(tempShapes);
				} 
				//System.out.println(archive);

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				holding = true;
				if (drawingTopic == "rectangle") {
					if (drawing == false) {
						x1Temp = e.getPoint().x;
						y1Temp = e.getPoint().y;
						shapes.add(new Rectangle(x1Temp, y1Temp, x1Temp, y1Temp, tempColor));
						drawing = true;
					} 
				} else if (drawingTopic == "circle") {
					if (drawing == false) {
						x1Temp = e.getPoint().x;
						y1Temp = e.getPoint().y;
						shapes.add(new Circle(x1Temp, y1Temp, x2Temp, y2Temp, tempColor));
						drawing = true;
					} 
				} else if (drawingTopic == "line") {
					if (drawing == false) {
						x1Temp = e.getPoint().x;
						y1Temp = e.getPoint().y;
						shapes.add(new Line(x1Temp, y1Temp, x2Temp, y2Temp, tempColor, lineWidth));
						drawing = true;

					} 
				} else if (drawingTopic == "move") {
					//System.out.println(drawing);
					if (drawing == false) {
						x1Temp = e.getPoint().x;
						y1Temp = e.getPoint().y;
						for (int i = 0; i < shapes.size(); i++) {
							if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
								moving = true;
								movingIndex = i;
								break;
							}
						}

						MDX = e.getX() - shapes.get(movingIndex).x1;
						MDY = e.getY() - shapes.get(movingIndex).y1; 
						drawing = true;

					} 
				} else if (drawingTopic == "pen") {
					if (drawing == false) {
						
						shapes.add(new Pen(tempColor, lineWidth, new ArrayList<Pixel>()));
						drawing = true;
					} 
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				holding = false;
				if (drawingTopic == "rectangle") {
					if (drawing == true) {
						x2Temp = e.getPoint().x;
						y2Temp = e.getPoint().y;
						drawing = false;
						
						if (archive.size() == 15) {
							archive.remove(0);
						}
						ArrayList<Shape> tempShapes = new ArrayList<>();
						for (int i = 0; i < shapes.size(); i++) {
							tempShapes.add(shapes.get(i).copy());
						}
						archive.add(tempShapes);
					}
				} else if (drawingTopic == "circle") {
					if (drawing == true) {
						x2Temp = e.getPoint().x;
						y2Temp = e.getPoint().y;
						drawing = false;
						if (archive.size() == 15) {
							archive.remove(0);
						}
						ArrayList<Shape> tempShapes = new ArrayList<>();
						for (int i = 0; i < shapes.size(); i++) {
							tempShapes.add(shapes.get(i).copy());
						}
						archive.add(tempShapes);
					}
				} else if (drawingTopic == "line") {
					if (drawing == true) {
						x2Temp = e.getPoint().x;
						y2Temp = e.getPoint().y;
						drawing = false;
						frame.getContentPane().repaint();
						
						if (archive.size() == 15) {
							archive.remove(0);
						}
						ArrayList<Shape> tempShapes = new ArrayList<>();
						for (int i = 0; i < shapes.size(); i++) {
							tempShapes.add(shapes.get(i).copy());
						}
						archive.add(tempShapes);
					}
				} else if (drawingTopic == "move") {
					
					moving = false;
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					drawing = false;
					ArrayList<Shape> tempShapes = new ArrayList<>();
					for (int i = 0; i < shapes.size(); i++) {
						tempShapes.add(shapes.get(i).copy());
					}
					archive.add(tempShapes);
				} else if (drawingTopic == "pen") {
					drawing = false;
					frame.getContentPane().repaint();
					
					if (archive.size() == 15) {
						archive.remove(0);
					}
					ArrayList<Shape> tempShapes = new ArrayList<>();
					for (int i = 0; i < shapes.size(); i++) {
						tempShapes.add(shapes.get(i).copy());
					}
					archive.add(tempShapes);
					
				}
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
				if (drawingTopic == "rectangle") {
					shapes.get(shapes.size() - 1).resize(x1Temp, y1Temp, e.getPoint().x, e.getPoint().y);
				} else if (drawingTopic == "circle") {
					shapes.get(shapes.size() - 1).resize(x1Temp, y1Temp, e.getPoint().x, e.getPoint().y);
				} else if (drawingTopic == "line") {
					shapes.get(shapes.size() - 1).resize(x1Temp, y1Temp, e.getPoint().x, e.getPoint().y);
				} else if (drawingTopic == "pen") {
					shapes.get(shapes.size() - 1).resize(e.getPoint().x, e.getPoint().y, 0, 0);
				} else if (drawingTopic == "move") {
					//shapes.get(shapes.size() - 1).move(x1Temp, y1Temp, e.getPoint().x, e.getPoint().y);
					if (moving == true) {
						shapes.get(movingIndex).move(MDX, MDY, e.getPoint().x, e.getPoint().y);
						MDX = e.getX() - shapes.get(movingIndex).x1;
						MDY = e.getY() - shapes.get(movingIndex).y1; 
					}
				
				}
				
				frame.getContentPane().repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
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
				if (lineWidthField.getText().equals("") == false) {
					lineWidth = Integer.parseInt(lineWidthField.getText());
				} else {
					lineWidth = 4;
				}
			}
		});

		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingTopic = "text";
				if (textSizeField.getText().equals("") == false) {
					textSize= Integer.parseInt(textSizeField.getText());
				} else {
					textSize = 20;
				}
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
		
		pen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	    		
				drawingTopic = "pen";
				if (lineWidthField.getText().equals("") == false) {
					lineWidth = Integer.parseInt(lineWidthField.getText());
				} else {
					lineWidth = 4;
				}
				
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
				//System.out.println(archive);
				if (archive.size() > 1) {
					shapes = archive.get(archive.size() - 2);
					archive.remove(archive.size() - 1);
				} else if (archive.size() == 1 && archive.get(0).size() == 1) {
					shapes = new ArrayList<>();
					archive.remove(0);
				}
				
				frame.getContentPane().repaint();
			}
		});
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapes.clear();
				if (archive.size() == 15) {
					archive.remove(0);
				}
				ArrayList<Shape> tempShapes = new ArrayList<>();
				for (int i = 0; i < shapes.size(); i++) {
					tempShapes.add(shapes.get(i).copy());
				}
				archive.add(tempShapes);
				frame.getContentPane().repaint();
			}
		});
		
		frame.add(mainContainer);

		frame.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new msPaint();

	}

}
