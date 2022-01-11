package maze;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import problems.Bot;

public class BotTwo extends Bot{


	public BotTwo(MazeRunner mr, Color c) {
		// TODO Auto-generated constructor stub
		super(mr, c);
	}

	private class Node {
		Node prev;
		String curDirection; //way back to previous node
		ArrayList<String> directions = new ArrayList<>();
		
		public Node() {
			this.directions.add("front");
		}
	}
	
	private Boolean checkFork = true;
	private int checkForkCount = 0;
	private Node curNode = new Node();
	private Boolean[] goBack = {false, false, false};
	private Boolean[] go = {false, false, false};
	private int goBackCount = 0, goCount = 0;
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		//System.out.println(curNode.curDirection);
		if (checkFork == true) {
			if (checkForkCount == 0) {
				if (moveForward() == true) {
					curNode.directions.add("front");
				}
				checkForkCount++;
				turnLeft();
			} else if (checkForkCount == 1) {
				if (moveForward() == true) {
					curNode.directions.add("left");
				}
				checkForkCount++;
				turnLeft();
			} else if (checkForkCount == 2) {
				checkForkCount++;
				turnLeft();
			} else if (checkForkCount == 3) {
				if (moveForward() == true) {
					curNode.directions.add("right");
				}
				checkForkCount++;
				turnLeft();
			} else if (checkForkCount == 4) {
				if (curNode.directions.size() == 0) {
					System.out.println("yeye");
					
					if (curNode.curDirection.equals("left")) goBack[0] = true;
					if (curNode.curDirection.equals("front")) goBack[1] = true;
					if (curNode.curDirection.equals("right")) goBack[2] = true;
					
					curNode.prev.directions.remove(0);
					curNode = curNode.prev;
					
					checkForkCount = 0;
					checkFork = false;
					
				} else {
					if (curNode.curDirection == "left") {
						curNode.prev = curNode;
						curNode.curDirection = "left";
						
						checkForkCount = 0;
						checkFork = false;
						go[0] = true;
					} else if (curNode.curDirection == "front") {
						curNode.prev = curNode;
						curNode.curDirection = "front";
						
						checkForkCount = 0;
						checkFork = false;
						go[1] = true;
					} else if (curNode.curDirection == "right") {
						curNode.prev = curNode;
						curNode.curDirection = "right";
						
						checkForkCount = 0;
						checkFork = false;
						go[2] = true;
					}
				}
			}
		}
		
		if (goBack[0] == true) {
			if (goBackCount == 0) {
				goBackCount++;
				turnLeft();
			} else if (goBackCount == 1) {
				goBackCount++;
				turnLeft();
			} else if (goBackCount == 2) {
				goBackCount++;
				moveForward();
			} else if (goBackCount == 3) {
				
				goBackCount = 0;
				goBack[0] = false;
				checkFork = true;
				turnLeft();
			}
		} else if (goBack[1] == true) {
			if (goBackCount == 0) {
				goBackCount++;
				turnLeft();
			} else if (goBackCount == 1) {
				goBackCount++;
				turnLeft();
			} else if (goBackCount == 2) {
				goBackCount++;
				moveForward();
			} else if (goBackCount == 3) {
				goBackCount++;
				turnLeft();
			} else if (goBackCount == 4) {
				
				goBackCount = 0;
				goBack[1] = false;
				checkFork = true;
				turnLeft();
			} else if (goBack[2] == true) {
				if (goBackCount == 0) {
					goBackCount++;
					turnLeft();
				} else if (goBackCount == 1) {
					goBackCount++;
					turnLeft();
				} else if (goBackCount == 2) {
					goBackCount++;
					moveForward();
				} else if (goBackCount == 3) {
					goBackCount++;
					turnLeft();
				} else if (goBackCount == 4) {
					goBackCount++;
					turnLeft();
				} else if (goBackCount == 5) {
					goBackCount++;
					turnLeft();
				} else if (goBackCount == 6) {
					
					goBackCount = 0;
					goBack[2] = false;
					checkFork = true;
					turnLeft();
				} 
			} 
			
			if (go[0] == true) {
				if (goCount == 0) {
					goCount++;
					turnLeft();
				} else if (goCount == 1) {
					goCount = 0;
					go[0] = false;
					checkFork = true;
					moveForward();
				}
			} else if (go[1] == true) {
				go[1] = false;
				checkFork = true;
				moveForward();
			} else if (go[2] == true) {
				if (goCount == 0) {
					goCount++;
					turnLeft();
				} else if (goCount == 1) {
					goCount++;
					turnLeft();
				} else if (goCount == 2) {
					goCount++;
					turnLeft();
				}else if (goCount == 3) {
					goCount = 0;
					go[2] = false;
					checkFork = true;
					moveForward();
				}
			}
			
		} 
		
	}

}
