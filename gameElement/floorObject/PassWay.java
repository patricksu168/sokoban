package gameElement.floorObject;

/**
 * Game Element that represent the walkable area of the maze
 *
 */
public class PassWay extends FloorObject {

	public static final short TEXTUREID = 1;

	@Override
	public short getTextureID() {
		return TEXTUREID;
	}

	@Override
	public boolean enter(short direction) {
		return true;
	}

	@Override
	public boolean exit(short direction) {
		return true;
	}

	@Override
	public short[] getExtraMovement(short direction) {
		return null;
	}
}
