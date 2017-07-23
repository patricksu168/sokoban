package maze;

import java.io.Serializable;

import gameElement.floorObject.FloorObject;
import gameElement.floorObject.Goal;
import gameElement.floorObject.PassWay;
import gameElement.floorObject.Wall;
import gameElement.gameObject.Box;
import gameElement.gameObject.GameObject;
import gameElement.gameObject.Player;

public class Tile implements Serializable {

	protected static final short WALL = Wall.TEXTUREID;
	protected static final short PASSWAY = PassWay.TEXTUREID;
	protected static final short GOAL = Goal.TEXTUREID;
	protected static final short BOX = Box.TEXTUREID;
	protected static final short PLAYER = Player.TEXTUREID;

	private FloorObject myFloorDecoration; // store immobile GameObject (*goal
											// is not movable)
	private GameObject myStanding; // Store movable GameObject

	public Tile() {
		myFloorDecoration = null;
		myStanding = null;
	}

	/**
	 * To get any Tile Effect about the movement of the GameObject should be
	 * applied
	 * 
	 * @param direction
	 * @return
	 */
	public boolean allowExit(short direction) {
		return this.myFloorDecoration.exit(direction);
	}

	/**
	 * To get any Tile Effect about the movement of the GameObject should be
	 * applied
	 * 
	 * @param direction
	 * @return
	 */
	public boolean allowEnter(short direction) {
		return this.myFloorDecoration.enter(direction);
	}

	/**
	 * To get any Tile Effect about the movement of the GameObject should be
	 * applied
	 * 
	 * @param direction
	 * @return pos.xy which GameObject will be moved to (i.e. the place that the
	 *         Object should be stop)
	 * @return null if and only if no Addition movement will be applied
	 */
	public short[] getExtraMovement(short direction) {
		return this.myFloorDecoration.getExtraMovement(direction);
	}

	/**
	 * return the Object that stand on this tile
	 * 
	 * @return
	 */
	public FloorObject getMyFloor() {
		return myFloorDecoration;
	}

	/**
	 * set a floor Object to the tile
	 * 
	 * @param myType
	 */
	public void setMyFloor(FloorObject myType) {
		this.myFloorDecoration = myType;
	}

	/**
	 * return the Object that stand on this tile
	 * 
	 * @return
	 */
	public GameObject getStanding() {
		return myStanding;
	}

	/**
	 * set a game Object to stand this tile
	 * 
	 * @param standing
	 */
	public void setStanding(GameObject standing) {
		this.myStanding = standing;
	}
}
