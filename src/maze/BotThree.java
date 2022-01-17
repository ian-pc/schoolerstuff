package maze;

import java.awt.Color;
import java.util.ArrayList;

import problems.Bot;

public class BotThree extends Bot{


	public BotThree(MazeRunner mr, Color c) {
		// TODO Auto-generated constructor stub
		super(mr, c);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		double a = Math.random();
		if (a > 0.5) {
			turnLeft();
		} else {
			moveForward();
		}
		
	}

}
