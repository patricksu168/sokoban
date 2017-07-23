package gameElement.gameObject;

import java.awt.image.BufferedImage;

import texture.texture;

/**
 * Game Element which represent the agent that control by Player
 *
 */
public class Player extends GameObject {

	public static final short UP = 1;
	public static final short DOWN = 2;
	public static final short LEFT = 3;
	public static final short RIGHT = 4;
	public static short TEXTUREID = 4;
	protected int step = 0;

	public Player(short X, short Y) {
		super(X, Y);
		// TODO Auto-generated constructor stub
	}

	public Player(short[] pos) {
		super(pos);
	}

	/**
	 * increase the step counter by one
	 */
	public void addStep() {
		this.step++;
	}

	public int getStep() {
		return this.step;
	}

	/**
	 * decrease the step counter by one
	 */
	public void reverseStep() {
		this.step--;
	}

	public boolean equals(Object o) {
		if (o instanceof Player) {
			if (this.position[0] == ((Player) o).getPosition()[0]
					&& this.position[1] == ((Player) o).getPosition()[1]) {
				return true;
			}
		}
		return false;
	}

	public void setTextureID(short direction) {
		this.TEXTUREID = direction;
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
		return texture.getInstance().getImage(getTextureID());
	}

}
