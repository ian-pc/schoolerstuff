package maze;

import java.awt.Color;

import problems.Bot;

public class BotFive extends Bot{
	private int mode = 0;
	private int count = 0;
	private Boolean tr = false;
	private int trc = 0;
	
	
	public BotFive(MazeRunner mr, Color c) {
		super(mr, c);
		mr.count = -500;
	}

	@Override
	public void move() {
		
		if (tr == true) {
			if (trc == 0 || trc == 1) {
				trc++;
				turnLeft();
			} else if (trc == 2){
				tr = false;
				trc = 0;
				turnLeft();
			}
		} else {
			if(count>0) {
				tr = true;
				count --;
			}else if(mode == 0) {
				tr = true;
				mode =1;
			}else if(mode ==2) {
				if(moveForward()) {
					mode = 0;
				}else {
					count = 3;
					mode=0;
				}
			}else if(mode== 1) { 
				if(moveForward()) {
					count = 3;
					mode = 3;
				}else {
					count = 3;
					mode = 2;
				}
			}else if(mode == 3) {
				count = 1;
				mode = 0;
			}
		}
	}

}