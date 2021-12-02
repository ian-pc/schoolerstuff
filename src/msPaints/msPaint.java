package msPaints;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import msPaints.Pen.Pixel;

public class msPaint {
	
	//elements
	private final int Height = 600, Width = 600;
	private JFrame frame;
	private JButton rectangle, circle, line, text, chooseColor, delete, move, front, back, pen, export, undo, clear, save, load;
	private JTextField lineWidthField, textSizeField, textContentField;
	private JLabel lineWidthLabel, textSizeLabel, textContentLabel;
	
	//temporary Color
	private Color tempColor = new Color(0, 0, 0);
	
	//width of the line
	private int lineWidth = 1;
	
	//what is the user drawing?
	private String drawingTopic = "rectangle";
	
	//is the user drawing?
	private boolean drawing = false;
	
	
	//mouse displacement proportional to the shape (for move function)
	private int MDX = 0, MDY = 0;
	
	//the index of the part of the pen that is being moved. 
	private int movingIndex;
	
	//is the shape moving?
	private boolean moving = false;
	
	//is the picture saved?
	private boolean saved = false;
	
	//the file location of the current file
	private String curFile = null;
	
	//temporary location of the first mouse position
	private int xTemp, yTemp;
	
	//ArrayList of shapes
	private ArrayList<Shape> shapes = new ArrayList<>();
	
	//ArrayLists of previous iterations of shape (for undo)
	private ArrayList<ArrayList<Shape>> archive = new ArrayList<>();

	//adds shapes to archive (for undo)
	private void addToArchive() {
		
		//can only undo 15 times
		if (archive.size() == 15) {
			archive.remove(0);
		}
		ArrayList<Shape> tempShapes = new ArrayList<>();
		for (int i = 0; i < shapes.size(); i++) {
			tempShapes.add(shapes.get(i).copy());
		}
		archive.add(tempShapes);
	} 
	
	public msPaint() {
		
		//adding all the graphic elements
		frame = new JFrame();
		lineWidthField = new JTextField();
		lineWidthField.setEditable(true);
		textSizeField = new JTextField();
		textSizeField.setEditable(true);
		textContentField = new JTextField();
		textContentField.setEditable(true);

		lineWidthLabel = new JLabel(" Line/Pen width: ");
		textContentLabel = new JLabel(" Text content: ");

		textSizeLabel = new JLabel(" Text size: ");

		rectangle = new JButton("rectangle");
		circle = new JButton("circle");
		chooseColor = new JButton("choose color");
		delete = new JButton("delete");
		move = new JButton("move");
		front = new JButton("front");
		back = new JButton("back");
		pen = new JButton("pen");
		export = new JButton("export");
		undo = new JButton("undo");
		line = new JButton("line");
		text = new JButton("text");
		clear = new JButton("clear");
		save = new JButton("save");
		load = new JButton("load");


		//sets the frame size
		frame.setSize(Width, Height); 
		//don't close the frame when the "x" is clicked so that the warning message can show
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//containers for the buttons (buttons in buttons1, 2, 3... buttons1, 2, 3 & main canvas in mainContainer)
		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

		JPanel buttons1 = new JPanel();
		buttons1.setLayout(new BoxLayout(buttons1, BoxLayout.X_AXIS));

		JPanel buttons2 = new JPanel();
		buttons2.setLayout(new BoxLayout(buttons2, BoxLayout.X_AXIS));

		JPanel buttons3 = new JPanel();
		buttons3.setLayout(new BoxLayout(buttons3, BoxLayout.X_AXIS));

		@SuppressWarnings("serial")
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				 
				for (int i = 0; i < shapes.size(); i++) {
					shapes.get(i).draw(g);
				}

			}
		};

		//setting preferred size
		canvas.setPreferredSize(new Dimension(Width, Height - 150));

		//adding elements to the containers
		buttons1.add(rectangle);
		buttons1.add(circle); 
		buttons1.add(line);
		buttons1.add(pen);
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
		buttons3.add(export);
		buttons3.add(undo);
		buttons3.add(clear); 
		buttons3.add(save);
		buttons3.add(load);

		//setting the preferred size of the containers and elements
		buttons1.setPreferredSize(new Dimension(Width, 50));
		buttons2.setPreferredSize(new Dimension(Width, 50));
		buttons3.setPreferredSize(new Dimension(Width, 50));

		lineWidthField.setMaximumSize(new Dimension(100, 25));
		textSizeField.setMaximumSize(new Dimension(100, 25));
		textContentField.setMaximumSize(new Dimension(100, 25));
		
		//adding buttons to the mainContainer
		mainContainer.add(buttons1);
		mainContainer.add(buttons2);
		mainContainer.add(buttons3);
		mainContainer.add(canvas);

		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (drawingTopic == "text") {
					
					//adds a "text" object to shapes
					shapes.add(new Text(textContentField.getText(), e.getPoint().x, e.getPoint().y, 
							tempColor, Integer.parseInt(textSizeField.getText())));
					frame.getContentPane().repaint();
					
					addToArchive();
					
				} else if (drawingTopic == "delete") {
					
					//deletes something if the mouse isOn it
					for (int i = 0; i < shapes.size(); i++) {
						if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
							shapes.remove(i);
						}
					}
					frame.getContentPane().repaint();
					
					addToArchive();
					
				} else if (drawingTopic == "front") {
					
					//moves something that the mouse is hovering over to the top of the shapes array so that it's drawn last (on the top)
					for (int i = shapes.size() - 1; i > -1 ; i--) {
					
						if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
							shapes.add(shapes.get(i));
							shapes.remove(i);
							break;	
						}
					}
					frame.getContentPane().repaint();	
					
					addToArchive();
					
				}else if (drawingTopic == "back") {
					
					//moves something that the mouse is hovering over to the bottom of the shapes array so that it's drawn first (on the bottom)
					for (int i = 0; i < shapes.size(); i++) {
						if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
							shapes.add(0, shapes.get(i));
							shapes.remove(i + 1);
							break;
						}
					}
					frame.getContentPane().repaint();
					
					addToArchive();
				} 

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (drawingTopic == "rectangle") {
					if (drawing == false) {
						//in this case, xTemp & yTemp pose as x1 and y1 for the shapes
						xTemp = e.getPoint().x;
						yTemp = e.getPoint().y;
						shapes.add(new Rectangle(xTemp, yTemp, xTemp, yTemp, tempColor));
						drawing = true;
					} 
				} else if (drawingTopic == "circle") {
					if (drawing == false) {
						//in this case, xTemp & yTemp pose as x1 and y1 for the shapes
						xTemp = e.getPoint().x;
						yTemp = e.getPoint().y;
						shapes.add(new Circle(xTemp, yTemp, xTemp, yTemp, tempColor));
						drawing = true;
					} 
				} else if (drawingTopic == "line") {
					if (drawing == false) {
						//in this case, xTemp & yTemp pose as x1 and y1 for the shapes
						xTemp = e.getPoint().x;
						yTemp = e.getPoint().y;
						shapes.add(new Line(xTemp, yTemp, xTemp, yTemp, tempColor, lineWidth));
						drawing = true;

					} 
				} else if (drawingTopic == "move") {
					if (drawing == false) {
						//in this case, xTemp & yTemp pose as the initial position of the shape. 
						xTemp = e.getPoint().x;
						yTemp = e.getPoint().y;
						for (int i = 0; i < shapes.size(); i++) {
							if (shapes.get(i).isOn(e.getPoint().x, e.getPoint().y) == true) {
								moving = true;
								movingIndex = i;
								break;
							}
						}
						//mouse position - shapes.get(i).x1/y1 = the displacement from x1/y1 of the shape
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
				
				//so that if there are changes made, they can be saved. 
				saved = false;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (drawingTopic == "rectangle") {
					
					//if the user was drawing, then the user is no longer drawing, and their change is added to the archive. 
					if (drawing == true) {
						drawing = false;
						
						addToArchive();
					}
				} else if (drawingTopic == "circle") {
					if (drawing == true) {
						drawing = false;
						
						addToArchive(); 
					}
				} else if (drawingTopic == "line") {
					if (drawing == true) {
						drawing = false;
						
						addToArchive();
					}
				} else if (drawingTopic == "move") {
					
					moving = false;
					
					addToArchive();
				} else if (drawingTopic == "pen") {
					drawing = false;
					
					addToArchive();
				}
				saved = false;
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
					shapes.get(shapes.size() - 1).resize(xTemp, yTemp, e.getPoint().x, e.getPoint().y);
				} else if (drawingTopic == "circle") {
					shapes.get(shapes.size() - 1).resize(xTemp, yTemp, e.getPoint().x, e.getPoint().y);
				} else if (drawingTopic == "line") {
					shapes.get(shapes.size() - 1).resize(xTemp, yTemp, e.getPoint().x, e.getPoint().y);
				} else if (drawingTopic == "pen") {
					//0's are extra values because resize for pen only takes two values. 
					shapes.get(shapes.size() - 1).resize(e.getPoint().x, e.getPoint().y, 0, 0);
				} else if (drawingTopic == "move") {
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
				//gets the value of lineWidthField and equates that to the lineWidth. Otherwise it is defaulted to 4. 
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
					Integer.parseInt(textSizeField.getText());
				} else {
					
				}
			}
		});
		
		chooseColor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//opens a JColorChooser to help choose the color. 
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
				//the temporary lineWidth is equated to the number in the lineWidthField otherwise it is defaulted t 4
				if (lineWidthField.getText().equals("") == false) {
					lineWidth = Integer.parseInt(lineWidthField.getText());
				} else {
					lineWidth = 4;
				}
				
			}
		});

		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    JFileChooser fileChooser= new JFileChooser();
			    
			    //("image") is the temporary name of the file that the user changes
			    fileChooser.setSelectedFile(new File("Image"));
			    //the window
			    int returnVal = fileChooser.showSaveDialog(null);
			    
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			        File fileToSave = fileChooser.getSelectedFile();
			        try{
			        	//getting the image of the g2
			        	BufferedImage image=new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
						Graphics2D g2=(Graphics2D)image.getGraphics();
			        	canvas.paint(g2);
			        	
						try {
							//writes the file to the location from the JFileChooser
							ImageIO.write(image, "png", new File(fileToSave.getAbsolutePath() + ".png"));
						} catch (Exception e1) {
							
						}
			        }
			        catch(Exception e1){
			        	
			        }
			    }
				 
				
				
			}
		});

		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//equates the current shapes to the topmost index of archives and deletes that index
				//catches bug when archive.size() == 1
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
				
				frame.getContentPane().repaint();
				
				addToArchive();
			}
		});
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (curFile == null) {
					//chooses a file name and location (default name "Save File")
				    JFileChooser fileChooser= new JFileChooser();
				    fileChooser.setSelectedFile(new File("Save File"));
				    int returnVal = fileChooser.showSaveDialog(null);
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				        File fileToSave = fileChooser.getSelectedFile();
				        curFile = fileToSave.getAbsolutePath() + ".txt";
				        try{
				        	FileWriter myWriter = new FileWriter(curFile);
				            for (int i = 0; i < shapes.size(); i++) {
				            	//calls the write function which is basically "toString" for shapes
								myWriter.write(shapes.get(i).write() + "s");
							}
				            myWriter.close();
				        }
				        catch(Exception e1){
				        	
				        }
				    }
				    saved = true;
				} else {
					try {
						new FileWriter(curFile, false).close();
						FileWriter myWriter = new FileWriter(curFile);
			            for (int i = 0; i < shapes.size(); i++) {
							myWriter.write(shapes.get(i).write() + "s");
						}
			            myWriter.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (saved == false && shapes.size() > 0) {
					//asks if the user wants to save
					int input = JOptionPane.showConfirmDialog(null, "Your current image is not saved. Are you Sure you want to load a new image?", "WARNING!", 2);
					if (input == 0) {
						JFileChooser j = new JFileChooser();
						// Open the save dialog
						int result = j.showOpenDialog(null);
						@SuppressWarnings("unused")
						Scanner sc = null;
						if (result == JFileChooser.APPROVE_OPTION) {
							File f = j.getSelectedFile();
							curFile = f.getAbsolutePath();
							try {
								sc = new Scanner(f);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
						}
						//the character "s" refers to a new shape
						String raw = sc.next();
						String[] shapesTemp = raw.split("s");
						shapes = new ArrayList<Shape>();
						for (int i = 0; i < shapesTemp.length; i++) {
							//the character "a" refers to an attribute of the shape (coordinates, color, etc. )
							String attributesTemp[] = shapesTemp[i].split("a");
							if (attributesTemp[0].equals("Circle")) {
								shapes.add(new Circle(Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
										Integer.valueOf(attributesTemp[3]), Integer.valueOf(attributesTemp[4]), 
										new Color(Integer.valueOf(attributesTemp[5]), Integer.valueOf(attributesTemp[6]), 
												Integer.valueOf(attributesTemp[7]))));
							} else if (attributesTemp[0].equals("Line")) {
								shapes.add(new Line(Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
										Integer.valueOf(attributesTemp[3]), Integer.valueOf(attributesTemp[4]), 
										new Color(Integer.valueOf(attributesTemp[5]), Integer.valueOf(attributesTemp[6]), 
												Integer.valueOf(attributesTemp[7])), Integer.valueOf(attributesTemp[8])));
							} else if (attributesTemp[0].equals("Pen")) {
								
								shapes.add(new Pen(new Color(Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
										Integer.valueOf(attributesTemp[3])), Integer.valueOf(attributesTemp[4]), new ArrayList<Pixel>()));
								
								String[] tempText = Arrays.copyOfRange(attributesTemp, 5, attributesTemp.length);
								
								//read function so that the function doesn't make changes outside to the ArrayList<PixeL> inside of the function
								shapes.get(i).read(tempText);
								
								//"a" taken out of rectangle to not be confused with the attribute splitter. 
							} else if (attributesTemp[0].equals("Rectngle")) {
								shapes.add(new Rectangle(Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
										Integer.valueOf(attributesTemp[3]), Integer.valueOf(attributesTemp[4]), 
										new Color(Integer.valueOf(attributesTemp[5]), Integer.valueOf(attributesTemp[6]), 
												Integer.valueOf(attributesTemp[7]))));
							} else if (attributesTemp[0].equals("Text")) {
								String tempText = "";
								//each character is sorted into one attribute and this turns them back into characters 
								for (int k = 7; k < attributesTemp.length; k++) {
									tempText += (char) Integer.valueOf(attributesTemp[k]).intValue(); 
								} 
								shapes.add(new Text(tempText, Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
										new Color(Integer.valueOf(attributesTemp[3]), Integer.valueOf(attributesTemp[4]), Integer.valueOf(attributesTemp[5])), 
										Integer.valueOf(attributesTemp[6])));
							}
							saved = true;
							frame.getContentPane().repaint();
						}
					}
				} else {
					JFileChooser j = new JFileChooser();
					// Open the save dialog
					int result = j.showOpenDialog(null);
					@SuppressWarnings("unused")
					Scanner sc = null;
					if (result == JFileChooser.APPROVE_OPTION) {
						File f = j.getSelectedFile();
						curFile = f.getAbsolutePath();
						try {
							sc = new Scanner(f);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					String raw = sc.next();
					String[] shapesTemp = raw.split("s");
					shapes = new ArrayList<Shape>();
					for (int i = 0; i < shapesTemp.length; i++) {
						String attributesTemp[] = shapesTemp[i].split("a");
						if (attributesTemp[0].equals("Circle")) {
							shapes.add(new Circle(Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
									Integer.valueOf(attributesTemp[3]), Integer.valueOf(attributesTemp[4]), 
									new Color(Integer.valueOf(attributesTemp[5]), Integer.valueOf(attributesTemp[6]), 
											Integer.valueOf(attributesTemp[7]))));
						} else if (attributesTemp[0].equals("Line")) {
							shapes.add(new Line(Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
									Integer.valueOf(attributesTemp[3]), Integer.valueOf(attributesTemp[4]), 
									new Color(Integer.valueOf(attributesTemp[5]), Integer.valueOf(attributesTemp[6]), 
											Integer.valueOf(attributesTemp[7])), Integer.valueOf(attributesTemp[8])));
						} else if (attributesTemp[0].equals("Pen")) {
							
							shapes.add(new Pen(new Color(Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
									Integer.valueOf(attributesTemp[3])), Integer.valueOf(attributesTemp[4]), new ArrayList<Pixel>()));
							
							String[] tempText = Arrays.copyOfRange(attributesTemp, 5, attributesTemp.length);
							shapes.get(i).read(tempText);
							
						} else if (attributesTemp[0].equals("Rectngle")) {
							shapes.add(new Rectangle(Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
									Integer.valueOf(attributesTemp[3]), Integer.valueOf(attributesTemp[4]), 
									new Color(Integer.valueOf(attributesTemp[5]), Integer.valueOf(attributesTemp[6]), 
											Integer.valueOf(attributesTemp[7]))));
						} else if (attributesTemp[0].equals("Text")) {
							String tempText = "";
							for (int k = 7; k < attributesTemp.length; k++) {
								tempText += (char) Integer.valueOf(attributesTemp[k]).intValue(); 
							} 
							shapes.add(new Text(tempText, Integer.valueOf(attributesTemp[1]), Integer.valueOf(attributesTemp[2]), 
									new Color(Integer.valueOf(attributesTemp[3]), Integer.valueOf(attributesTemp[4]), Integer.valueOf(attributesTemp[5])), 
									Integer.valueOf(attributesTemp[6])));
						}
						//System.out.println("shsdfas:" + attributesTemp[0]);
						saved = true;
						frame.getContentPane().repaint();
					}
				} 
			}
		});
		
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				//the three button options so that it can be displayed in the joptionpane
				String[] options = {"Save & Exit", "Don't Save", "Cancel"};
				int input = JOptionPane.showOptionDialog(null, "Your current image is not saved. Are you Sure you want exit?", "WARNING!", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		        if (input == 0) {
		        	//same as the save function
					if (curFile == null) {
					    JFileChooser fileChooser= new JFileChooser();
					    fileChooser.setSelectedFile(new File("Save File"));
					    int returnVal = fileChooser.showSaveDialog(null);
					    if (returnVal == JFileChooser.APPROVE_OPTION) {
					        File fileToSave = fileChooser.getSelectedFile();
					        curFile = fileToSave.getAbsolutePath() + ".txt";
					        try{
					        	FileWriter myWriter = new FileWriter(curFile);
					            for (int i = 0; i < shapes.size(); i++) {
									myWriter.write(shapes.get(i).write() + "s");
								}
					            myWriter.close();
					        }
					        catch(Exception e1){
					        	
					        }
					    }
					    saved = true;
					} else {
						try {
							new FileWriter(curFile, false).close();
							FileWriter myWriter = new FileWriter(curFile);
				            for (int i = 0; i < shapes.size(); i++) {
								myWriter.write(shapes.get(i).write() + "s");
							}
				            myWriter.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					System.exit(0);
		        } else if (input == 1) {
		        	System.exit(0);
		        } 
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		frame.add(mainContainer);

		frame.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//running the program
		new msPaint();

	}

}
