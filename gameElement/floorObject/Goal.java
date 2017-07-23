package gameElement.floorObject;

import gameElement.Trackable;

/**
 * final position/ target location that the box should be place to finish the
 * game
 *
 */
public class Goal extends FloorObject implements Trackable {

	public static final short TEXTUREID = 2;
	private short[] position;

	public Goal(short X, short Y) {
		short[] pos = { X, Y };
		this.setPosition(pos);
	}

	public Goal(short[] pos) {
		this.setPosition(pos);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Goal) {
			if (this.position[0] == ((Goal) o).getPosition()[0] && this.position[1] == ((Goal) o).getPosition()[1]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public short getTextureID() {
		return TEXTUREID;
	}

	@Override
	public short[] getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(short[] position) {
		this.position = position;
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
