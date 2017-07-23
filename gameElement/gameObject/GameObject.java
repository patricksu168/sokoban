package gameElement.gameObject;

import java.awt.image.BufferedImage;
import java.util.Stack;

import gameElement.GameElement;
import gameElement.Trackable;

/**
 * a class enclose all the game element which is standing on the floor
 *
 */
public abstract class GameObject extends GameElement implements Trackable {
	protected short[] position;
	// refer to the property that the object will keep on moving if no addition
	// force act on it
	protected boolean rollable;
	protected Stack path = new Stack();

	public GameObject(short[] pos) {
		super();
		this.rollable = false;
		this.position = pos;
	}

	public GameObject(short X, short Y) {
		super();
		short[] pos = { X, Y };
		this.setPosition(pos);
	}

	/**
	 * the the last action code into the path stack
	 * 
	 * @param path
	 */
	public void addPath(short[] path) {
		this.path.push((short[]) path);
	}

	/**
	 * take out the last element in the path stack
	 * 
	 * @return action code in short
	 */
	public short[] getLast() {
		return (short[]) this.path.pop();
	}

	/**
	 * return the path stack size
	 * 
	 * @return size of stack
	 */
	public int getPathSize() {
		return this.path.size();
	}

	/**
	 * get the property of object
	 * 
	 * @return return true if the object is rolling after impact
	 */
	public boolean isRollable() {
		return rollable;
	}

	/**
	 * print out the stack into console
	 */
	public void printPath() {
		for (Object e : this.path) {
			short[] temp = (short[]) e;
			System.out.print(temp[0] + ", " + temp[1] + "\n");
		}
	}

	/**
	 * put the last position into the path stack
	 */
	public void saveCurr() {
		this.path.push(this.getPosition());
	}

	/**
	 * change the property of the object
	 * 
	 * @param rollable
	 */
	public void setRollable(boolean rollable) {
		this.rollable = rollable;
	}

	/**
	 * Allow Game Object changes its texture
	 * 
	 * @param direction
	 */
	public abstract void setTextureID(short direction);

	/**
	 * This method is different Object by Object It return the Expected stopping
	 * position if this instance is move for more detail compare Ball.class and
	 * Box.class *NOTE* must be override
	 * 
	 * @param direction
	 * @return
	 */
	public abstract short[] getDest(short direction);

	@Override
	public abstract short getTextureID();

	@Override
	public abstract boolean equals(Object o);

	@Override
	public short[] getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(short[] position) {
		this.position = position;
	}

	@Override
	public abstract BufferedImage getImage();

}
