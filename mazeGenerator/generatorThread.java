package mazeGenerator;

import ai.BFSAI;
import maze.Maze;
import maze.MazeIO;

/**
 * this class is create a new thread for auto generate new level and save it
 * into the level database, for every level generated it will be verified by AI
 *
 */
public class generatorThread implements Runnable {

	private BFSAI ai;
	private Maze maze;
	private int nextId = MazeIO.getNumberOfMaze() + 1;

	private void init() {
		maze = new MazeGeneratorTwo().generate();
		ai = new BFSAI(maze);
	}

	public void run() {
		while (true) {
			System.out.print("");
			init();
			if (ai.solve()) {
				MazeIO.saveMaze(nextId, maze);
				nextId++;
			} else {
				maze.clean();
			}
		}
	}

}
