package gameElement;

import java.awt.image.BufferedImage;

/**
 * super class that enclose all object in the maze
 * 
 */
public abstract class GameElement implements Drawable {

	public GameElement() {
	}

	@Override
	public abstract short getTextureID();

	@Override
	public abstract BufferedImage getImage();
}
