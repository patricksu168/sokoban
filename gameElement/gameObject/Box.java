package gameElement.gameObject;

import java.awt.image.BufferedImage;

import texture.texture;

/**
 * Game Element which represent a type of Object that require to push to goal in
 * order to finish a game
 *
 */
public class Box extends GameObject {

	public static short TEXTUREID = 3;

	public Box(short X, short Y) {
		super(X, Y);
	}

	public Box(short[] pos) {
		super(pos);
	}

	public boolean equals(Object o) {

		if (o instanceof Box) {
			if (this.position[0] == ((Box) o).getPosition()[0] && this.position[1] == ((Box) o).getPosition()[1]) {
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
	public short[] getDest(short direction) {
		short[] next = new short[2];
		next[0] = position[0];
		next[1] = position[1];
		switch (direction) {
		case Player.UP:
			next[1]--;
			break;
		case Player.DOWN:
			next[1]++;
			break;
		case Player.LEFT:
			next[0]--;
			break;
		case Player.RIGHT:
			next[0]++;
			break;
		}
		return next;
	}

	@Override
	public BufferedImage getImage() {
		return texture.getInstance().getImage(TEXTUREID);
	}

	public void setTextureID(short direction) {
		this.TEXTUREID = direction;
	}
}
