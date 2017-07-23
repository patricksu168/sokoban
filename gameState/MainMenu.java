package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * Game State which display the main menu of the game
 *
 */
public class MainMenu extends GameState {

	private boolean keyHold = false;
	private short currentChoice = -1;
	private String[] myOoptions = { "Start", "Creative", "Exit" };

	private Font myFont;

	public MainMenu(GameStateManager gsm) {

		this.gsm = gsm;
		System.err.println("main menu");

	}

	/**
	 * only action that execute action when button click which is use for
	 * passing into another state
	 */
	private void select() {
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.STARTGAMEMENU);
		}
		if (currentChoice == 1) {
			gsm.setState(GameStateManager.CREATIVEMODE);
		}
		if (currentChoice == 2) {
			System.exit(0);
		}
	}

	public void init() {
	}

	/*
	 * public void update() { // bg.update(); }
	 */
	public void draw(Graphics2D g) {
		// // draw bg
		Image background;
		ImageIcon img = new ImageIcon("res/menuIcon/MainBackground.jpg");
		background = img.getImage();
		g.drawImage(background, 0, 0, 1280, 900, null);
		//
		// //draw title
		Image title;
		img = new ImageIcon("res/menuIcon/GameTitle.png");
		title = img.getImage();
		g.drawImage(title, 400, 80, 500, 112, null); // 350, 50, 500, 112
		//
		//
		// draw menu options
		myFont = new Font("Arial", Font.PLAIN, 40);
		g.setFont(myFont);
		for (int i = 0; i < myOoptions.length; i++) {
			String png = ".png";
			String hover = "";
			if (i == currentChoice) {
				hover = "_hover";
			}
			img = new ImageIcon("res/menuIcon/" + myOoptions[i] + hover + png);
			Image button;
			button = img.getImage();
			g.drawImage(button, 520, 300 + i * 120, 255, 120, null);
		}

	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice <= -1) {
				currentChoice = (short) (myOoptions.length - 1);
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == myOoptions.length) {
				currentChoice = 0;
			}
		}
		if (k == KeyEvent.VK_P) {
			if (gsm.musicPause) {
				this.gsm.playMusic();
				this.gsm.musicPause = false;
			} else {
				this.gsm.stopMusic();
				this.gsm.musicPause = true;
			}
		}
	}

	public void keyReleased(int k) {
	}

	@Override
	public void MousePressed(double x, double y) {
		this.keyHold = true;

	}

	@Override
	public void MouseReleased(double x, double y) {
		select();
		keyHold = false;
	}

	public void MouseMoved(double x, double y) {
		if (keyHold) {

		} else {
			if (x > 520 && x < 770) {
				if (y >= 303 && y < 416) {// 113
					currentChoice = 0;
				} else if (y >= 422 && y < 535) {
					currentChoice = 1;
				} else if (y >= 541 && y < 654) {
					currentChoice = 2;
				} else {
					currentChoice = -1;
				}
			} else {
				currentChoice = -1;
			}
		}
	}

}
