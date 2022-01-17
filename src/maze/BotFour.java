package maze;

import java.awt.Color;
import java.util.ArrayList;

import problems.Bot;

public class BotFour extends Bot{


	public BotFour(MazeRunner mr, Color c) {
		// TODO Auto-generated constructor stub
		super(mr, c);
	}

	private Boolean cf = true; //checking fork
	private int cfc = 0; //checking fork count
	private ArrayList<Character> posdir = new ArrayList<>();
	private Boolean tr = true;
	private int trc = 0;
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		if (cf == true) {
			System.out.println("ye2");
				if (cfc == 0) { if (moveForward() == true) { posdir.add('f');}} 
				else if (cfc == 1) {turnLeft();}
				else if (cfc == 2) { if (moveForward() == true) {posdir.add('l');}}
				else if (cfc == 3) {turnLeft();}
				else if (cfc == 4) {turnLeft();}
				else if (cfc == 5) { if (moveForward() == true) {posdir.add('r');}}
				else if (cfc == 6) {turnLeft();}
				else if (cfc == 7) {
					char dir = posdir.get((int) (posdir.size() * Math.random()));
					if (dir == 'f') {
						cfc = 0;
						posdir = new ArrayList<Character>();
						moveForward();
					} else if (dir == 'l') {
						cfc = 0;
						posdir = new ArrayList<Character>();
						turnLeft();
					} else if (dir == 'r') {
						tr = true;
						cf = false;
						cfc = 0;
						posdir = new ArrayList<Character>();
					}
				}
		}
		if (tr == true) {
			System.out.println("ye");
			if (trc == 0 || trc == 1) {
				trc++;
				turnLeft();
			} else if (trc == 2){
				tr = false;
				trc = 0;
				cf = true;
				cfc = 0;
				turnLeft();
			}
		}
		
		
	}

}
