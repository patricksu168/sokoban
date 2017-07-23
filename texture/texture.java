package texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * A class which handle all the maze texture
 */
public class texture {
	private static texture instance = new texture();
	private HashMap<Short, BufferedImage> save;

	private texture() {
		init();
	}

	private void init() {
		String temp = null;
		this.save = new HashMap<Short, BufferedImage>();
		BufferedImage img = null;
		for (short i = 0; i <= 20; i++) {
			switch (i) {
			case 0: // wall
				temp = "res/img3/castle.png";
				break;
			case 1: // finish
				temp = "res/img/Crate_Yellow.png";
				break;
			case 2:// goal
				temp = "res/img/GroundGravel_Grass.png";
				break;
			case 3:// box
				temp = "res/img/CrateDark_Blue.png";
				break;
			case 4:// player
				temp = "res/img/Boss_Down_Stand.png";
				break;
			case 5:// player
				temp = "res/img/Boss_Up_Stand.png";
				break;
			case 6:// player
				temp = "res/img/Boss_Down_WalkLeft.png";
				break;
			case 7:// player
				temp = "res/img/Boss_Left_Stand.png";
				break;
			case 8:// player
				temp = "res/img/Boss_Right_Stand.png";
				break;
			case 9: // tile
				temp = "res/img/white_background.jpg";
				break;
			case 10: // tile
				temp = "res/img/black_tile.jpg";
				break;
			case 11: // ball
				temp = "res/img3/coin.png";
				break;
			case 12:
				temp = "res/img3/pick.png";
				break;
			}
			try {
				img = ImageIO.read(new File(temp));
				if (img == null)
					throw new IOException();
			} catch (IOException e) {
			}
			save.put(i, img);
		}
	}

	public static texture getInstance() {
		return instance;
	}

	/**
	 * Get the image object with given ID
	 * 
	 * @precondition reference ID should be within the range of the image table
	 * @param ref
	 *            gameElement texture ID
	 * @return Buffer Image that ready for draw on screen
	 */
	public BufferedImage getImage(short ref) {
		return save.get(ref);
	}
}