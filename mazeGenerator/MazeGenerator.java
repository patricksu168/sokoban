package mazeGenerator;

import maze.Maze;

public interface MazeGenerator {

	/**
	 * this function is use to generate a new level
	 * 
	 * @return a single level
	 */
	public Maze generate();
}
