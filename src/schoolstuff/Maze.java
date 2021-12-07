package schoolstuff;

import java.util.ArrayList;
import java.util.Arrays;

public class Maze {

	private ArrayList<String> posDir = new ArrayList<>(Arrays.asList("right", "left", "up", "down"));

	private String dir;

	private void newDir() {

		int pos = (int) (Math.random() * (posDir.size()));
		dir = posDir.get(pos);
		System.out.println(posDir.get(pos));

		posDir.remove(pos);

	}

	private final static int SIZE = 25;
	private static boolean[][] maze = new boolean[SIZE][SIZE];
	private int tileX;
	private int tileY;

	// aMAZEing

	private void makeDaMaze1() {

		dir = "right";
		tileX = 0;
		tileY = (int) (Math.random() * (SIZE - 1));
		
		while (true) {

			if (posDir.size() == 0) {
				maze = new boolean[SIZE][SIZE];
				tileX = 0;
				tileY = (int) (Math.random() * (SIZE - 1)) + 2;
				posDir = new ArrayList<>(Arrays.asList("right", "left", "up", "down"));
				System.out.println("newMaze");
				makeDaMaze1();
				break;
			}

			if (tileX == SIZE - 1) {
				maze[tileX][tileY] = true;
				System.out.println("DONE!");
				break;
			}


			maze[tileX][tileY] = true;

			if (dir.equals("right")) {
				
				if (tileX < SIZE - 2) {
					if (maze[tileX + 2][tileY] == true) {
						newDir();
						continue;
					}
				}
				
				if (tileY < SIZE - 1) {
					if (maze[tileX + 1][tileY + 1] == true) {
						newDir();
						continue;
					}
				}
				if (tileY > 0) {
					if (maze[tileX + 1][tileY - 1] == true) {
						newDir();
						continue;
					}
				}
				tileX++;
				
			} else if (dir.equals("left")) {

				if (tileX == 1) {
					newDir();
					continue;
				}
				
				if (maze[tileX - 2][tileY] == true) {
					newDir();
					continue;
				}
				
				if (maze[tileX - 1][tileY + 1] == true || maze[tileX - 1][tileY - 1] == true) {
					newDir();
					continue;
				}
				
				tileX--;
			} else if (dir.equals("up")) {

				if (tileY == SIZE - 2) {
					newDir();
					continue;
				}
				
				if (maze[tileX][tileY + 2] == true) {
					newDir();
					continue;
				}
				
				
				if (tileX > 0) {
					if (maze[tileX - 1][tileY + 1] == true) {
						newDir();
						continue;
					}
				}
				
				if (tileX < SIZE - 1) {
					if (maze[tileX + 1][tileY + 1] == true) {
						newDir();
						continue;
					}
				}
				

				tileY++;

			} else if (dir.equals("down")) {

				if (tileY <= 1) {
					newDir();
					continue;
				}
				
				if (tileX > 1) {
					if (maze[tileX][tileY - 2] == true) {
						newDir();
						continue;
					}
				}
				
				if (tileX > 0) {
					if (maze[tileX - 1][tileY - 1] == true) {
						newDir();
						continue;
					}
				}
				
				if (tileX < SIZE - 1) {
					if (maze[tileX + 1][tileY - 1] == true) {
						newDir();
						continue;
					}
				}
				
				
				tileY--;
			}


			if (posDir.size() == 0) {
				maze = new boolean[SIZE][SIZE];
				tileX = 0;
				tileY = (int) (Math.random() * (SIZE - 1)) + 2;
				posDir = new ArrayList<>(Arrays.asList("right", "left", "up", "down"));
				System.out.println("newMaze");
				makeDaMaze1();
				break;
			}
			
			System.out.println("X: " + tileX + " Y: " + tileY + " direction: " + dir);
			posDir = new ArrayList<>(Arrays.asList("right", "left", "up", "down"));
			newDir();
		}
	}

	private void makeDaMaze2() {
		
		while (true) {
			
			double trueTiles = 0;
			for (int i = 1; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (maze[i][j] == true) {
						trueTiles++;
					}
				}
			}
			if (trueTiles/(double) SIZE*SIZE > 0.6) break;
			
			
			for (int i = 1; i < SIZE - 2; i++) {
				for (int j = 0; j < SIZE - 1; j++) {
					if (maze[i][j] == true) {
						
						
						

						maze = new boolean[SIZE][SIZE];
						tileX = 0;
						tileY = (int) (Math.random() * (SIZE - 1)) + 2;
						posDir = new ArrayList<>(Arrays.asList("right", "left", "up", "down"));
						System.out.println("newMaze");
						makeDaMaze1();

						if (tileX == SIZE - 1) {
							maze[tileX][tileY] = true;
							System.out.println("DONE!");
							break;
						}


						maze[tileX][tileY] = true;

						if (dir.equals("right")) {
							
							if (tileX < SIZE - 2) {
								if (maze[tileX + 2][tileY] == true) {
									newDir();
									continue;
								}
							}
							
							if (tileY < SIZE - 1) {
								if (maze[tileX + 1][tileY + 1] == true) {
									newDir();
									continue;
								}
							}
							if (tileY > 0) {
								if (maze[tileX + 1][tileY - 1] == true) {
									newDir();
									continue;
								}
							}
							tileX++;
							
						} else if (dir.equals("left")) {

							if (tileX == 1) {
								newDir();
								continue;
							}
							
							if (maze[tileX - 2][tileY] == true) {
								newDir();
								continue;
							}
							
							if (maze[tileX - 1][tileY + 1] == true || maze[tileX - 1][tileY - 1] == true) {
								newDir();
								continue;
							}
							
							tileX--;
						} else if (dir.equals("up")) {

							if (tileY == SIZE - 2) {
								newDir();
								continue;
							}
							
							if (maze[tileX][tileY + 2] == true) {
								newDir();
								continue;
							}
							
							
							if (tileX > 0) {
								if (maze[tileX - 1][tileY + 1] == true) {
									newDir();
									continue;
								}
							}
							
							if (tileX < SIZE - 1) {
								if (maze[tileX + 1][tileY + 1] == true) {
									newDir();
									continue;
								}
							}
							

							tileY++;

						} else if (dir.equals("down")) {

							if (tileY <= 1) {
								newDir();
								continue;
							}
							
							if (tileX > 1) {
								if (maze[tileX][tileY - 2] == true) {
									newDir();
									continue;
								}
							}
							
							if (tileX > 0) {
								if (maze[tileX - 1][tileY - 1] == true) {
									newDir();
									continue;
								}
							}
							
							if (tileX < SIZE - 1) {
								if (maze[tileX + 1][tileY - 1] == true) {
									newDir();
									continue;
								}
							}
							
							
							tileY--;
						}
					}
				}
			}
		}
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Maze runner = new Maze();
		runner.makeDaMaze1();
		runner.makeDaMaze2();

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
