package gameElement.floorObject;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import gameElement.GameElement;
import texture.texture;

/**
 * a class enclose all the game Element that should be act as floor decoration
 *
 */
public abstract class FloorObject extends GameElement {
	@Override
	public abstract short getTextureID();

	/**
	 * return the property of the Tile if have any restriction when exiting the
	 * tile, it can very with direction
	 * 
	 * @param direction
	 * @return true if the object is allow to enter this type of tile
	 */
	public abstract boolean enter(short direction);

	/**
	 * return the property of the Tile if have any restriction when entering the
	 * tile, it can very with direction
	 * 
	 * @param direction
	 * @return true if the object is allow to leave this type of tile
	 */
	public abstract boolean exit(short direction);

	/**
	 * refer to the floor property like the one in GameObject *NOTE* not yet put
	 * in the logic loop
	 * 
	 * @param direction
	 *            stopping location int short[] {x,y}
	 * @return
	 */
	public abstract short[] getExtraMovement(short direction);

	@Override
	public BufferedImage getImage() {
		return texture.getInstance().getImage(getTextureID());

	}
}
