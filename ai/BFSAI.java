package ai;

import java.util.LinkedList;
import java.util.PriorityQueue;

import gameElement.gameObject.Player;
import maze.Maze;

/**
 * This AI is use beast-first search to search all possible movement can be done
 * by player object This AI storing 2 type of state(i.e. Object State and AI
 * State), which tracking the object movement in maze and Player movement
 * respectively Object State are use to save possible game State which may be
 * form during maze search and try to making compare before keep search on that
 * tree AI state are use to track the path that AI taken and it only contain the
 * direction that player get.
 */
public class BFSAI {

	private Maze maze;
	private PriorityQueue<MazePattern> Pattern;
	private LinkedList<AIState> searchList;

	public BFSAI(Maze maze) {
		this.maze = maze;
		maze.setBackground(true);
		this.Pattern = new PriorityQueue<MazePattern>();
		this.searchList = new LinkedList<AIState>();
	}

	/**
	 * A method that start the AI
	 * 
	 * @return true if the game is solvable
	 */
	public boolean solve() {
		boolean check = false;
		this.searchList = createAIState(null);
		this.Pattern.add(new MazePattern(maze.getMyBox(), maze.getMyplayer(), 0, maze.MisPlaceHeuristic()));
		while (this.searchList.size() != 0) {
			AIState curr = this.searchList.poll();
			try {
				check = this.search(curr, this.maze.copy());
			} catch (PathNotFoundExecption e) {
				continue;
			}

			if (check)
				break;
			if (curr.getMyPath() != null) {
				this.searchList.addAll(this.createAIState(curr));
			}

		}
		if (!check && this.searchList.size() == 0) {
			System.out.println("*No solution*");
		}

		return check;
	}

	/**
	 * method that create the next state
	 * 
	 * @param currentState
	 * @return a list of AIState that will be run in the search
	 */
	private LinkedList<AIState> createAIState(AIState currentState) {
		LinkedList<AIState> list = new LinkedList<AIState>();
		if (currentState == null) {
			list.add(new AIState(Player.UP));
			list.add(new AIState(Player.DOWN));
			list.add(new AIState(Player.LEFT));
			list.add(new AIState(Player.RIGHT));
		} else {
			list.add(new AIState(currentState.copyAllAndAdd(Player.UP)));
			list.add(new AIState(currentState.copyAllAndAdd(Player.DOWN)));
			list.add(new AIState(currentState.copyAllAndAdd(Player.LEFT)));
			list.add(new AIState(currentState.copyAllAndAdd(Player.RIGHT)));
		}
		return list;
	}

	/**
	 * Help method that handle the main loop of searching
	 * 
	 * @param currentState
	 * @param mazeCopy
	 * @return true if the game is completed else false
	 * @throws PathNotFoundExecption
	 *             if there are no path
	 */
	private boolean search(AIState currentState, Maze mazeCopy) throws PathNotFoundExecption {
		boolean move = mazeCopy.executePath(currentState.getMyPath());
		if (!move) {
			throw new PathNotFoundExecption();
		}
		MazePattern newState = new MazePattern(mazeCopy.getMyBox(), mazeCopy.getMyplayer(),
				currentState.getMyPath().size(), mazeCopy.ManhattanDistanceHeuristic());

		boolean ifexist = false;
		for (MazePattern oldState : this.Pattern) {
			if (oldState.equals(newState)) {
				currentState.setMyPath(null);
				ifexist = true;
				break;
			}
		}
		if (!ifexist) {
			this.Pattern.add(newState);
		}

		boolean ifdone = mazeCopy.ifSolved();
		mazeCopy.clean();
		return ifdone;
	}

}
