package gameElement;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Property that the Object can be get Image form Texture class
 *
 */
public interface Drawable extends Serializable {
	/**
	 * This method is to get the textureID
	 * 
	 * @return static final TEXTUREID
	 */
	public short getTextureID();

	/**
	 * it use for getting the image from the texture class
	 * 
	 * @return BufferedImage
	 */
	public BufferedImage getImage();
}
