package schoolstuff;

import java.util.Random;

public class Maze {

	private int[] posDir = { 0, 1, 2, 3 };
	private Random rnd = new Random();
	private int dir = posDir[rnd.nextInt(posDir.length - 1)];

	private void newDir() {

		int pos = rnd.nextInt(posDir.length - 1);
		dir = posDir[pos];

		int[] copy = new int[posDir.length - 1];

		for (int i = 0, j = 0; i < posDir.length; i++) {
			if (i != pos) {
				copy[j++] = posDir[i];
			}
		}

		posDir = copy;

	}

	private final static int SIZE = 50;
	private static boolean[][] maze = new boolean[SIZE][SIZE];
	private int tileX = 0;
	private int tileY = (int) (Math.random() * (SIZE - 1));

	// aMAZEing

	private void makeDaMaze() {

		while (true) {


			if (tileX == SIZE - 1) {
				maze[tileX][tileY] = true;
				System.out.println("DONE!");
				break;
			}

			if (posDir.length == 0) {
				maze = new boolean[SIZE][SIZE];
				tileX = 0;
				tileY = (int) (Math.random() * (SIZE - 1)) + 2;
				System.out.println("newMaze");
				makeDaMaze();
				break;
			}

			maze[tileX][tileY] = true;

			if (dir == 0) {
				
				if (tileX == 1) {
					newDir();
					continue;
				}
				
//				if (maze[tileX + 2][tileY] == true) {
//					newDir();
//					continue;
//				}
				
				if (maze[tileX + 1][tileY + 1] == true || maze[tileX + 1][tileY - 1]) {
					newDir();
					continue;
				}
				
				tileX++;
				
			} else if (dir == 1) {

				if (tileX == 1) {
					newDir();
					continue;
				}
				
				if (maze[tileX - 1][tileY + 1] == true || maze[tileX - 1][tileY - 1] == true) {
					newDir();
					continue;
				}
				
				tileX--;
			} else if (dir == 2) {

				if (tileY == SIZE - 2) {
					newDir();
					continue;
				}
				
				if (maze[tileX + 1][tileY + 1] == true || maze[tileX - 1][tileY + 1] == true) {
					newDir();
					continue;
				} 
				

				tileY++;

			} else if (dir == 3) {

				if (tileY == 1) {
					newDir();
					continue;
				}
				
				if (maze[tileX + 1][tileY - 1] == true || maze[tileX - 1][tileY - 1] == true) {
					newDir();
					continue;
				}
				
				tileY--;
			}

			System.out.println("X: " + tileX + " Y: " + tileY + " direction: " + dir);
			posDir = new int[4];
			for (int i = 0; i < 4; i++) {
				posDir[i] = i;
			}
			
			newDir();
		}
	}

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Maze runner = new Maze();
		runner.makeDaMaze();

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (maze[j][i] == true) {
					System.out.print("+ ");
				} else
					System.out.print("0 ");
			}
			System.out.println();
		}
	}

}
