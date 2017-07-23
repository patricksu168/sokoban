package gameElement.floorObject;

/**
 * Game Element which represent the Area that block way
 *
 */
public class Wall extends FloorObject {

	public static final short TEXTUREID = 0;

	@Override
	public short getTextureID() {
		return TEXTUREID;
	}

	@Override
	public boolean enter(short direction) {
		return false;
	}

	@Override
	public boolean exit(short direction) {
		return false;
	}

	@Override
	public short[] getExtraMovement(short direction) {
		return null;
	}

}
