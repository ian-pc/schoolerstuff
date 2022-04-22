package GPSstue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import problems.Distancetime.Vertex;

public class GPS {

	private int Width = 820, Height = 820;
	// Width and Size of the canvas (20 pixels larger than image for safe measure)

	private int tempX, tempY;
	private int tempMouseX, tempMouseY;
	// temporary variables for storing the mouse location when creating new
	// locations

	HashSet<Location[]> highlighted = new HashSet<>();
	// a HashSet of Locations (size 2) to indicate the edges that are highlighted

	private boolean selecting;
	private int selectingIndex;
	// selecting indicates if one of the Location is currently being linked to
	// selectingIndex indicates the index of the Location which is currently being
	// connected.

	private DistancetimeGPS graph = new DistancetimeGPS("GPSstuff.txt");
	// The main Distance Graph that is constructed from the file "GPSstuff.txt"

	private void save() { // function for saving data
		try {
			new FileWriter("Files/GPSstuff.txt", false).close();
			// resetting the File (second parameter "false")
			FileWriter myWriter = new FileWriter("Files/GPSstuff.txt");
			// the FileWriter that writes onto the location ("Files/GPSstuff.txt")

			ArrayList<Location> tempA = graph.toArray();
			// temporary Arraylist of the distance graph in arrayform
			for (int i = 0; i < tempA.size(); i++) {
				myWriter.write(tempA.get(i).x + " " + tempA.get(i).y + " " + tempA.get(i).name
						+ graph.get(tempA.get(i)).neighborsToString());
				// goes through Location in the graph and writes out it's values in the order
				// which is later read.
			}

			myWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private GPS() throws IOException {

		JFrame frame = new JFrame("main");
		// main canvas that has the map

		frame.setSize(Width, Height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		// setting the dimensions of the frame and makes sure the frame closes

		JFrame PtopBox = new JFrame("ptopbox");
		// the frame which the user will type into when making a new point

		PtopBox.setSize(new Dimension(250, 75));
		PtopBox.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		PtopBox.setLocationRelativeTo(null);
		// setting the frame (similar to the "main" frame)

		JTextField TFlocation = new JTextField();
		// textfield for where the user inputs the name of the point

		TFlocation.addActionListener(new ActionListener() { // when enter is pressed in the textField
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Location tempL = new Location(tempX, tempY, TFlocation.getText());
				// temporary Location that stores the value of the point
				graph.add(tempL);
				// adds the temporary location to the graph
				for (int i = 0; i < TFlocation.getText().length(); i++) {
					if (TFlocation.getText().charAt(i) == ' ') {
						graph.remove(tempL);
						System.out.println("please no spaces in the name");
					}
				}
				// goes through each value and makes sure that they do not have space in them
				// because it interferes with the saving.

				PtopBox.setVisible(false);
				TFlocation.setText("");
				// resetting the frame.

				frame.getContentPane().repaint();
				// updates changes to the map
			}

		});

		Font bigFont = TFlocation.getFont().deriveFont(Font.PLAIN, 40);
		// Font
		TFlocation.setFont(bigFont);
		// implementing above font

		JFrame PDijkstrasBox = new JFrame("Dijkstras");
		// the frame that the user will be able to enter two values and get the
		// djiakstras

		PDijkstrasBox.setVisible(true);
		PDijkstrasBox.setSize(new Dimension(250, 250));
		PDijkstrasBox.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		PDijkstrasBox.setLocationRelativeTo(null);
		// initializing the Frame similar to Ptopbox and main frame.

		JButton BDijkstras = new JButton("back");
		BDijkstras.setVisible(false);
		// creating a back button which is hidden at first.

		JTextField TLDijkstrasP1 = new JTextField();
		JTextField TLDijkstrasP2 = new JTextField();
		// textFields for the first and second location to be used in Djiakstras

		JLabel LDijkstras = new JLabel();
		// where the Djiakstras will be shown
		LDijkstras.setVisible(false);
		// hides the result of the djiakstras in the beginning

		JLabel LDijkstras1 = new JLabel("Location 1");
		JLabel LDijkstras2 = new JLabel("Location 2");
		// Labels to indicate what the textfields are to the user

		TLDijkstrasP2.addActionListener(new ActionListener() {// when enter is pressed
			@Override
			public void actionPerformed(ActionEvent e) {
				BDijkstras.setVisible(true); // unhiding the back button
				Location p1 = null, p2 = null;
				// resetts the values of the first and second location for the djiakstras
				ArrayList<Location> tempG = graph.toArray();
				// temporary arraylist of the graph
				for (int i = 0; i < tempG.size(); i++) {
					if (tempG.get(i).name.equals(TLDijkstrasP1.getText())) {
						p1 = tempG.get(i);
					}
					if (tempG.get(i).name.equals(TLDijkstrasP2.getText())) {
						p2 = tempG.get(i);
					}
				}
				// traverses the graph in search of a matching vertex which has the names

				if (p1 == null) {
					LDijkstras.setText("first point is invalid");
					// if the name in the Label cannot be found in the graph, do nothing
				} else if (p2 == null) {
					LDijkstras.setText("second point is invalid");
					// if the name in the Label cannot be found in the graph, do nothing
				} else {
					String tempS = "";
					for (int i = 0; i < graph.djiaktras(p1, p2).size(); i++) {
						tempS += graph.djiaktras(p1, p2).get(i).info.name + " ";
					}
					// TEMPORARILY sets tempS to the string of the djiakstras of the graph.

					String[] tempS2 = tempS.split(" ");
					// splits it at the spaces

					tempS = "<html>";
					// reusing tempS as the string to hold the values of the Djiakstra
					for (int i = 0; i < tempS2.length; i++) {
						tempS += " " + Integer.toString(i + 1) + ") " + tempS2[i] + "<br>";
					}
					tempS += "</html>";
					// entering + formatting the djiakstras into tempS

					LDijkstras.setText(tempS);
					// set text
				}

				LDijkstras1.setVisible(false);
				LDijkstras2.setVisible(false);
				TLDijkstrasP1.setText("");
				TLDijkstrasP2.setText("");
				TLDijkstrasP1.setVisible(false);
				TLDijkstrasP2.setVisible(false);
				LDijkstras.setVisible(true);
				// hiding/showing the items that need to be changed.

				ArrayList<DistancetimeGPS.Vertex> tempG2 = graph.djiaktras(p1, p2);
				for (int i = 0; i < tempG2.size() - 1; i++) {
					highlighted.add(new Location[] { tempG2.get(i).info, tempG2.get(i + 1).info });
				}
				// traverses the graph and if there is an edge with adjacent points in the
				// djiakstras, it is added to the "highlighted" arraylist
				frame.getContentPane().repaint();
				// update the frame
			}

		});

		TLDijkstrasP1.setFont(bigFont);
		TLDijkstrasP2.setFont(bigFont);
		TLDijkstrasP1.setColumns(125);
		TLDijkstrasP2.setColumns(125);
		// change the and format the Textfields

		JPanel PDijkstrasHV = new JPanel();
		PDijkstrasHV.setLayout(new BoxLayout(PDijkstrasHV, BoxLayout.Y_AXIS));
		// a canvas that is formatted vertically that will hold everything.

		PDijkstrasHV.add(LDijkstras1);
		PDijkstrasHV.add(TLDijkstrasP1);
		PDijkstrasHV.add(LDijkstras2);
		PDijkstrasHV.add(TLDijkstrasP2);
		PDijkstrasHV.add(LDijkstras);
		PDijkstrasHV.add(BDijkstras);
		// adding all elements to the canvas

		PDijkstrasBox.add(PDijkstrasHV);
		// adding canvas to the frame.

		BDijkstras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { // if back button is pressed
				// TODO Auto-generated method stub
				BDijkstras.setVisible(false);
				TLDijkstrasP1.setVisible(true);
				TLDijkstrasP2.setVisible(true);
				LDijkstras.setVisible(false);
				LDijkstras1.setVisible(true);
				LDijkstras2.setVisible(true);
				// resetting everything

				highlighted.clear();
				// highliting reset as well

				frame.getContentPane().repaint();
				// update the map
			}

		});

		JFrame menu = new JFrame("save");
		// frame that holds save button

		menu.setSize(250, 250);
		menu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		menu.setLocationRelativeTo(null);
		// same initializing process as main, ptopbox, and djiakstras

		JButton buttonsave = new JButton("save");
		// the save button
		buttonsave.addActionListener(new ActionListener() { // if the save button is pressed

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					FileWriter fw = new FileWriter(new File("Files/GPSstuff.txt"), false);
					for (Location tempL : graph.toArray()) {
						fw.write(Integer.toString(tempL.x) + " ");
						fw.write(Integer.toString(tempL.y) + " ");
						// writes x and y values of the location
						fw.write(tempL.name);
						// writes the name of the location
						for (int i = 0; i < graph.get(tempL).neighborsToArray().size(); i++) {
							fw.write(" " + graph.get(tempL).neighborsToArray().get(i).name);
							fw.write(" " + (int) (Math.hypot(
									Math.abs(tempL.x - graph.get(tempL).neighborsToArray().get(i).x),
									Math.abs(tempL.y - graph.get(tempL).neighborsToArray().get(i).y))));
						}
						// goes through each neighbor of each location and prints the neighbors name and
						// distance
						fw.write("\n");
					}
					fw.close();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		menu.add(buttonsave);
		buttonsave.setVisible(true);
		// more setup

		BufferedImage mapImage = ImageIO.read(new File("Files/FortniteMap.jpg"));
		JLabel map = new JLabel(new ImageIcon(mapImage));
		// drawing the map

		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.RED);
				// using red
				AffineTransform affinetransform = new AffineTransform();
				FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
				// tools used to find the width and height of the text
				ArrayList<Location> tempG = graph.toArray();
				// temporary arraylist of the graph
				for (int i = 0; i < tempG.size(); i++) {
					g.fillPolygon(
							new int[] { tempG.get(i).x, tempG.get(i).x + 10, tempG.get(i).x + 10, tempG.get(i).x - 10,
									tempG.get(i).x - 10 },
							new int[] { tempG.get(i).y, tempG.get(i).y - 5, tempG.get(i).y - 15, tempG.get(i).y - 15,
									tempG.get(i).y - 5 },
							5);
					// drawing a pin-like shape at each location
					g.drawString(tempG.get(i).name, tempG.get(i).x
							- (int) ((new JLabel().getFont()).getStringBounds(tempG.get(i).name, frc).getWidth()) / 2,
							tempG.get(i).y - 20);
					// write the point's text that is centered.
				}
				// traversing the graph and drawing each point with it's name

				for (Location temp : graph.vertices.keySet()) { // traversing the graph printing each one's edge to it's
																// neighbor as well as their distance
					ArrayList<Location> tempA = graph.get(temp).neighborsToArray();
					// temporary arraylist of the current location's neighbors
					for (int i = 0; i < tempA.size(); i++) {
						for (Location[] tempL : highlighted) {
							if ((tempL[0] == temp && tempL[1] == tempA.get(i))
									|| (tempL[0] == tempA.get(i) && tempL[1] == temp)) {
								g.setColor(Color.blue);
								// if the edge is adjacent between two highlighted points, set color to blue
							}
						}
						g.drawLine(temp.x, temp.y, tempA.get(i).x, tempA.get(i).y);
						// draw the line
						g.drawString(
								(int) (Math.hypot(Math.abs(temp.x - tempA.get(i).x), Math.abs(temp.y - tempA.get(i).y)))
										+ "",
								(tempA.get(i).x + temp.x) / 2, (tempA.get(i).y + temp.y) / 2 - 10);
						// draw the distance between the two points
						g.setColor(Color.red);
						// reset color to red
					}

				}

				if (selecting == true) {
					g.drawLine(tempG.get(selectingIndex).x, tempG.get(selectingIndex).y, tempMouseX, tempMouseY);
					g.drawString(
							(int) (Math.hypot(Math.abs(tempG.get(selectingIndex).x - tempMouseX),
									Math.abs(tempG.get(selectingIndex).y - tempMouseY))) + "",
							tempMouseX, tempMouseY - 10);
					// if selecting is true, draw a moving line from the mouse to the selected
					// point, as well as indicate the distance
				}

			}
		};

		canvas.add(map);
		// add the map to the canvas

		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				boolean temp = false;
				ArrayList<Location> tempG = graph.toArray();
				// temporary arraylist of the graph
				for (int i = 0; i < tempG.size(); i++) {
					if (e.getX() > tempG.get(i).x - 10 && e.getX() < tempG.get(i).x + 10) {
						if (e.getY() > tempG.get(i).y - 15 && e.getY() < tempG.get(i).y) {
							temp = true;
						}
					}
				}
				// if not clicking over a point, begin drawing a new point
				if (temp == false) {
					PtopBox.setVisible(true);
					tempX = e.getX();
					tempY = e.getY();
				}
				// show ptopbox and set the temporary x and y values to the mouse' location

				for (int j = 0; j < tempG.size(); j++) {
					if (e.getX() > tempG.get(j).x - 10 && e.getX() < tempG.get(j).x + 10) {
						if (e.getY() > tempG.get(j).y - 15 && e.getY() < tempG.get(j).y) {
							graph.remove(tempG.get(j));
						}
					}
				}
				// if the Location is clicked, remove it.
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				ArrayList<Location> tempG = graph.toArray();
				for (int i = 0; i < tempG.size(); i++) {
					if (e.getX() > tempG.get(i).x - 10 && e.getX() < tempG.get(i).x + 10) {
						if (e.getY() > tempG.get(i).y - 15 && e.getY() < tempG.get(i).y) {
							selecting = true;
							selectingIndex = i;
						}
					}
				}
				// traverses the graph, and if the point is clicked the selecting is true.
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				ArrayList<Location> tempG = graph.toArray();
				if (selecting == true) {
					for (int i = 0; i < tempG.size(); i++) {
						if (e.getX() > tempG.get(i).x - 10 && e.getX() < tempG.get(i).x + 10) {
							if (e.getY() > tempG.get(i).y - 15 && e.getY() < tempG.get(i).y) {
								graph.connect(tempG.get(selectingIndex), tempG.get(i),
										(int) (Math.hypot(Math.abs(tempG.get(selectingIndex).x - tempMouseX),
												Math.abs(tempG.get(selectingIndex).y - tempMouseY))));
							}
						}
					}
				}
				// if the mouse is hovering over another location, and the user is currently in
				// selecting mode, connect the two
				selecting = false;
				frame.getContentPane().repaint();
				// refresh
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
				if (selecting == true) {
					tempMouseX = e.getX();
					tempMouseY = e.getY();
					// update tempMousX and tempMouseY if selecting
					frame.getContentPane().repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		canvas.setPreferredSize(new Dimension(Width, Height));

		frame.add(canvas);
		frame.setVisible(true);
		menu.setVisible(true);
		// making frame visible

		PtopBox.add(TFlocation);

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new GPS();
	}
}
