package ai;

import java.util.LinkedList;

/**
 * A class the Create Search State for AI to run through the game
 */
public class AIState {

	private LinkedList<Short> myPath;

	/**
	 * create state when the state is expanding
	 * 
	 * @param path
	 */
	public AIState(LinkedList<Short> path) {
		this.myPath = path;
	}

	/**
	 * create the first state for the search
	 * 
	 * @param direction
	 */
	public AIState(short direction) {
		LinkedList<Short> actionList = new LinkedList<Short>();
		actionList.add(direction);
		this.myPath = actionList;
	}

	/**
	 * return a new instance of List of Path and adding a new direction into the
	 * list
	 * 
	 * @param direction
	 * @return new LinkedList<Short> direction
	 */
	public LinkedList<Short> copyAllAndAdd(short direction) {
		LinkedList<Short> temp = new LinkedList<Short>();
		temp.addAll(this.myPath);
		temp.add(direction);
		return temp;
	}

	public LinkedList<Short> getMyPath() {
		return myPath;
	}

	public void setMyPath(LinkedList<Short> myPath) {
		this.myPath = myPath;
	}
}
