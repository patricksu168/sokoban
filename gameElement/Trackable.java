package gameElement;

/**
 * this interface is implement when the Game ElEMENT is require keep tracking by
 * the maze, usually implement for all GAMEOBJECT
 * 
 */
public interface Trackable {

	/**
	 * GET the position (ie. Tile[X][Y]) of this instance position should be
	 * having length 2
	 * 
	 * @return maze tile position in short[]
	 */
	public short[] getPosition();

	/**
	 * SET the position (ie. Tile[X][Y]) of this instance position should be
	 * having length 2
	 * 
	 */
	public void setPosition(short[] position);
}
