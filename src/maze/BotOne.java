package maze;

import java.awt.Color;

import problems.Bot;

public class BotOne extends Bot{
	private int mode = 0;
	private int recursive = 0;
	
	public BotOne(MazeRunner mr, Color c) {
		super(mr, c);
		mr.count = -500;
	}

	@Override
	public void move() {
		if(recursive>0) {
			turnLeft();
			recursive --;
//			System.out.println("turnLeft");	
		}else if(mode == 0) {
			turnLeft(); //turn left to check there is wall on the left
			mode =1;
		}else if(mode== 1 && !moveForward()) { //there is wall on the left
			recursive = 3;
			mode = 2;
		}else if(mode==1 && moveForward()) {//there isnt wall on the left
			recursive = 3;
			mode = 3;
		}else if(mode ==2) {
			if(moveForward()) {
//				System.out.println("1 There is wall on the left. move Forward");
				//moveForward();
				mode = 0;
//				System.out.println("test1");
			}else {
//				System.out.println("2 There is wall on the left. cannot MoveForward");
				recursive = 3;
				mode=0;
//				System.out.println("test2");
			}
		}else if(mode == 3) {
//			System.out.println("3 There isnt a wall on the left");
			moveForward();
			recursive = 1;
			mode = 0;
		}
	}

}