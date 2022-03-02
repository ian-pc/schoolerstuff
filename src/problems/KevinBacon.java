package problems;

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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.Scanner;

public class KevinBacon {

	//basic variables
	public HashMap<String, String> actors = new HashMap<>();
	public ArrayList<String> actorsString;
	public HashMap<String, String> movies = new HashMap<>();
	public HashMap<String, ArrayList<String>> tempMoviesActors = new HashMap<>();
	//map is my labeled graph
	public Labeledtime<String, String> map = new Labeledtime<>();

	//variables for the home screen
	public int Width = 750, Height = 500;
	JLabel menuLabel = new JLabel("The EXTREME Kevin Bacon Game");
	JButton menuClassicButton = new JButton("Classic");
	JButton menuZenButton = new JButton("Zen");
	JButton menuDistanceButton = new JButton("Distance");
	JButton backButton = new JButton("Back");
	
	//variables for the Classic game mode
	HashMap<JButton, Integer> gameButton = new HashMap<>();
	JLabel classicStartActorLabel = new JLabel();
	JLabel classicEndActorLabel = new JLabel();
	JLabel classicBothInMovie = new JLabel();
	String rActorStart = "", rActorEnd = "";
	ArrayList<AbstractMap.SimpleEntry<String, String>> BFS = new ArrayList<>();
	int correctActorIter = 1;
	int correctButtonr;
	int k = 0;

	//variables for the Zen game mode
	JTextField zenStartActorField = new JTextField();
	JTextField zenEndActorField = new JTextField();
	JButton zenEnterButton = new JButton("Enter!");
	JLabel zenResultLabel = new JLabel();
	
	//variables for the Distance game mode
	JButton distanceEnterButton = new JButton("Enter!");
	JTextField distanceGuessField = new JTextField();
	JLabel distanceFindActorLabel = new JLabel();
	JLabel distanceRemainingActorsLabel = new JLabel();
	JLabel distanceDistanceLabel = new JLabel();
	ArrayList<String> answers; //instantiated outside so that it can be accessed across different functions

	


	public KevinBacon() throws IOException {

		readActors("Files/actors.txt"); //reads the txt file of actors and writes it into a hashmap with the actor being the value and their ID# being the 
		//key as well as putting all the actors into a labeled graph (unconnected)
		readMovie("Files/movies.txt");  //reads the txt file of movies and writes it into a hashmap with the movies being the value and their ID# being the 
		//key
		readActorsinMovie("Files/movie-actors.txt"); //stores all the movie-actor pairs into a temporary save
		intoLabeltime(); //takes the temporary hashmap made in readActors in Movie and connects all the actors in each movie with the edge being called the movie they
		//are in together
		actorsString = new ArrayList(actors.values());//an array of the actors but only their string. 

		setup();
	}

	public void setup() {
		
		//window set up
		JFrame frame = new JFrame();		
		frame.setSize(Width, Height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(new Color(255, 0, 0));
			}
		};

		//formatting all the menu elements
		menuLabel.setFont(new Font("Helvetica", Font.PLAIN, 45));
		menuClassicButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
		menuZenButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
		menuDistanceButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
		//adding all the elements to my canvas
		canvas.add(backButton);
		canvas.add(menuLabel);
		canvas.add(menuClassicButton);
		canvas.add(menuZenButton);
		canvas.add(classicStartActorLabel);
		canvas.add(classicEndActorLabel);
		canvas.add(zenStartActorField);
		canvas.add(zenEndActorField);
		canvas.add(zenEnterButton);
		canvas.add(zenResultLabel);
		canvas.add(menuDistanceButton);
		canvas.add(classicBothInMovie);
		canvas.add(distanceFindActorLabel);
		canvas.add(distanceDistanceLabel);
		canvas.add(distanceRemainingActorsLabel);
		canvas.add(distanceGuessField);
		canvas.add(distanceEnterButton);
		
		//instantiating all the button elements in my game as well as set them up, and format them
		for (int i = 0; i < 4; i++) {
			gameButton.put(new JButton(), i);
		}
		for (JButton b : gameButton.keySet()) {
			canvas.add(b);
			b.setVisible(false);
		}
		
		setupElements();
		
		//first game: after given a starting actor and end actor, guess the actors they are connected by in their BFS
		menuClassicButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				menuVisible(false);
				classicPlay();
			}
		});
		//the user can enter the starting actor and end actor and they are told the BFS between them
		menuZenButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				menuVisible(false);
				zenPlay();
			}
		});
		//the user is given an actor and have to find all actors that are x amount of actors far away, x is always lesser than the actor furthest from them. 
		menuDistanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuVisible(false);
				distancePlay();
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				toMenu();
			}
		});
		
		canvas.setPreferredSize(new Dimension(Width, Height));
		frame.add(canvas);
		frame.setVisible(true);
	}

	//formatting all the elements. 
	public void setupElements() {
		backButton.setVisible(false);
		classicBothInMovie.setVisible(false);
		classicStartActorLabel.setVisible(false);
		classicEndActorLabel.setVisible(false);
		zenStartActorField.setVisible(false);
		zenStartActorField.setPreferredSize(new Dimension(100, 50));
		zenStartActorField.setEditable(true);
		zenEndActorField.setVisible(false);
		zenEndActorField.setPreferredSize(new Dimension(100, 50));
		zenStartActorField.setEditable(true);
		zenEnterButton.setVisible(false);
		distanceGuessField.setVisible(false);
		distanceGuessField.setPreferredSize(new Dimension(100, 50));
		distanceGuessField.setEditable(true);
		distanceEnterButton.setVisible(false);
		
		//in-game button actions are added outside so that multiple functions are  not called when clicking a button
		for (JButton b : gameButton.keySet()) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//if correct button -> next step in BFS; else -> restart
					if (b.getText().equals(BFS.get(correctActorIter - 1).getKey())) {
						correctButton();
					} else {
						correctActorIter = 1;
						correctButton();
					}
				}
			});
		}
		
		zenEnterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//if actors not valid or BFS = null then write error message
				//if both actors present -> find BFS and print
				if (!actorsString.contains(zenStartActorField.getText())) {
					zenResultLabel.setText("the start actor does not exist, Kevin Bacon is sad :(");
				} else if (!actorsString.contains(zenEndActorField.getText())) {
					zenResultLabel.setText("the end actor does not exist, Kevin Bacon is sad :(");
				} else {
					ArrayList<AbstractMap.SimpleEntry<String, String>> zenBFSPath;
					zenBFSPath = map.moviesBFS(zenStartActorField.getText(), zenEndActorField.getText());
					
					if ((zenBFSPath == null)) {
						zenResultLabel.setText("there is no path between the two actors, Kevin Bacon is sad :(");
					} else {
						zenResultLabel.setText(zenBFSPath.toString());
					}
				}
				
				
			}
		});
		
		distanceEnterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//if the answer exists in the array of answers, it remvoes it
				if (answers.contains(distanceGuessField.getText())) {
					answers.remove(distanceGuessField.getText());
				}
				distanceRemainingActorsLabel.setText(String.valueOf("Remaining: " + answers.size()));
				//when there are no more answers left, it ends the game. 
				if (answers.size() == 0) {
					toMenu();
					menuVisible(true);
				}
			}
		});
		
	}
	
	public void classicPlay() {
		//make  buttons visible
		for (JButton b : gameButton.keySet()) {
			b.setVisible(true);
			
		}

		classicBothInMovie.setVisible(true);
		
		//gets new actors until there is a valid path between them and they are not the same. 
		do {
			rActorStart = actorsString.get((int) (Math.random() * actorsString.size()));
			rActorEnd = actorsString.get((int) (Math.random() * actorsString.size()));
			BFS = map.moviesBFS(rActorEnd, rActorStart);
		} while (rActorStart == rActorEnd || BFS == null);
		
		classicStartActorLabel.setText("start: " + rActorStart);
		classicStartActorLabel.setVisible(true);
		classicEndActorLabel.setText("end: " + rActorEnd);
		classicEndActorLabel.setVisible(true);


		correctButton();
		
		
	}
	
	public void zenPlay() {

		zenStartActorField.setVisible(true);
		zenEndActorField.setVisible(true);
		zenResultLabel.setVisible(true);
		zenEnterButton.setVisible(true);
		zenStartActorField.setText("Start Actor");
		zenEndActorField.setText("End Actor");
		

	}
	
	public void distancePlay() {
		
		distanceEnterButton.setVisible(true);
		distanceGuessField.setVisible(true);
		distanceFindActorLabel.setVisible(true);
		distanceRemainingActorsLabel.setVisible(true);
		distanceDistanceLabel.setVisible(true);
		//gets a random actor with a distance that is lesser than the maximum distance
		String randActor = actorsString.get((int) (Math.random() * actorsString.size()));
		int furthestDist = map.BFS(randActor, map.findFurthest(randActor)).size();
		int randDist = (int) (Math.random() * furthestDist);
		answers = map.findDistAway(randDist, randActor);
		
		distanceRemainingActorsLabel.setText("Remaining: " + String.valueOf(answers.size()));
		distanceFindActorLabel.setText(randActor);
		distanceDistanceLabel.setText("Distance: " + String.valueOf(randDist));
	}
	
	//a sub-function for the classic gamemode which is played when a correct buttn is pressed
	public void correctButton() {

		//if all the actors are done, it will return to the menu
		if (correctActorIter == BFS.size()) {
			menuVisible(true);
			toMenu();
			correctActorIter = 1;
			
		} else {
			String[] randomButtonText = new String[4];

			//getting which button will be the correct index
			correctButtonr = (int) (Math.random() * 3);
			
			classicBothInMovie.setText("were both in: " + BFS.get(correctActorIter).getValue());
			for (int i = 0; i < 4; i++) {	
				//if > index is correct button, set button text variable to that variable, else get a random actor that is not equal to the correct answer
				if (i == correctButtonr) {
					randomButtonText[i] = BFS.get(correctActorIter).getKey();
				} else {
					do {
						randomButtonText[i] = actorsString.get((int) (Math.random() * actorsString.size()));
					} while (randomButtonText[i] == rActorStart);
				}
			}
			//sets the buttons to the vallues in the array randomButtonText which stores the text of the buttons. 
			int tem = 0;
			for (JButton b : gameButton.keySet()) {
				b.setText(randomButtonText[tem]);
				tem++;
			}

			correctActorIter++;
		}
		
		
	}
	
	//sets all the menu elements visible
	public void menuVisible(boolean visible) {
		//sets all the elements visible/invisible and the back button to be invisible. 
		menuClassicButton.setVisible(visible);
		menuZenButton.setVisible(visible);
		menuDistanceButton.setVisible(visible);
		menuLabel.setVisible(visible);
		backButton.setVisible(!visible);
	}
	
	//hidessall game elements
	public void toMenu() {
		menuVisible(true);
		for (JButton b : gameButton.keySet()) {
			b.setVisible(false);
		}
		classicStartActorLabel.setVisible(false);
		classicEndActorLabel.setVisible(false);
		classicBothInMovie.setVisible(false);
		zenStartActorField.setVisible(false);
		zenEndActorField.setVisible(false);
		zenEnterButton.setVisible(false);
		zenResultLabel.setVisible(false);
		distanceEnterButton.setVisible(false);
		distanceGuessField.setVisible(false);
		distanceFindActorLabel.setVisible(false);
		distanceRemainingActorsLabel.setVisible(false);
		distanceDistanceLabel.setVisible(false);
	}
	
	public void readActors(String fileloc) throws IOException {

		BufferedReader sc = new BufferedReader(new FileReader(fileloc));
		String line;
		while ((line = sc.readLine()) != null) {
			String[] temp = line.split("~");
			actors.put(temp[0], temp[1]);
			map.addVertex(temp[1]);
		}
		sc.close();
	}

	public void readMovie(String fileloc) throws IOException {
		FileReader fr = new FileReader(fileloc);
		BufferedReader sc = new BufferedReader(fr);
		String line;
		while ((line = sc.readLine()) != null) {
			String[] temp = line.split("~");
			movies.put(temp[0], temp[1]);
		}
		sc.close();
	}

	public void readActorsinMovie(String fileloc) throws IOException {
		FileReader fr = new FileReader(fileloc);
		BufferedReader sc = new BufferedReader(fr);
		String line;
		while ((line = sc.readLine()) != null) {

			String[] temp = line.split("~");
			if (tempMoviesActors.containsKey(movies.get(temp[0]))) {
				tempMoviesActors.get(movies.get(temp[0])).add(actors.get(temp[1]));
			} else {
				tempMoviesActors.put(movies.get(temp[0]), new ArrayList<String>());
				tempMoviesActors.get(movies.get(temp[0])).add(actors.get(temp[1]));
			}
		}
		sc.close();
	}

	public void intoLabeltime() {
		for (String s : tempMoviesActors.keySet()) {
			for (int i = 0; i < tempMoviesActors.get(s).size(); i++) {
				for (int j = i; j < tempMoviesActors.get(s).size(); j++) {
					map.connect(tempMoviesActors.get(s).get(i), tempMoviesActors.get(s).get(j), s);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new KevinBacon();
		// map.moviesBFS("Jackie Chan", "Jon Cook");
	}

}
