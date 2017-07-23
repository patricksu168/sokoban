/**
 * Each page of the game stand for one state, main menu, history, maze game etc.
 * 
 * each state has their own keyboard or mouse listener, can check how does it do on the game board class
 *  
 */
package gameState;

import java.awt.event.MouseEvent;

import sound.BackgroundMusic;

/**
 * the main game control unit of the game
 *
 */
public class GameStateManager {

	public static final short NUMGAMESTATES = 4;
	public static final short MAINMENU = 0;
	public static final short STARTGAMEMENU = 1;
	public static final short SINGLEPLAYER = 2;
	public static final short CREATIVEMODE = 3;
	private GameState gameState;
	private short currentState;
	protected String fileName;
	protected String loadType = "LOADFROMDB";
	protected boolean musicPause = false;

	private BackgroundMusic bgm = new BackgroundMusic();

	public GameStateManager() {

		gameState = null;
		currentState = MAINMENU;
		bgm.playMusic();
		loadState(currentState);
	}

	/**
	 * method that use to play music
	 */
	public void playMusic() {
		this.bgm.playMusic();
	}

	/**
	 * method that use to stop music
	 */
	public void stopMusic() {
		this.bgm.stopMusic();
	}

	/**
	 * acting containing unload and load GameState
	 * 
	 * @param state
	 *            GameState reference number
	 */
	public void setState(short state) {
		unloadState();
		currentState = state;
		loadState(currentState);
	}

	/**
	 * use for passing value to Single player
	 * 
	 * @param type
	 */
	public void setFileName(String name) {
		this.fileName = name;
	}

	/**
	 * use for passing value to Single player
	 * 
	 * @param type
	 */
	public void setLoadType(String type) {
		this.loadType = type;
	}

	/**
	 * change game state for options
	 * 
	 * @param state
	 *            GameState reference number
	 */
	private void loadState(short state) {
		if (state == MAINMENU)
			gameState = new MainMenu(this);
		if (state == SINGLEPLAYER)
			gameState = new SinglePlayer(this, this.fileName, this.loadType);
		if (state == STARTGAMEMENU)
			gameState = new StartGameMenu(this);
		if (state == CREATIVEMODE) {
			gameState = new CreativeMode(this);
		}
	}

	/**
	 * remove the existing GameState Object
	 */
	private void unloadState() {
		gameState = null;
	}

	/**
	 * draw game screen
	 * 
	 * @param g
	 *            current graphics
	 */
	public void draw(java.awt.Graphics2D g) {
		try {
			gameState.draw(g);
		} catch (Exception e) {
		}
	}

	/**
	 * user control
	 * 
	 * @param k
	 *            key press value
	 */
	public void keyPressed(int k) {
		gameState.keyPressed(k);
	}

	/**
	 * user control
	 * 
	 * @param k
	 *            key press value
	 */
	public void keyReleased(int k) {
		gameState.keyReleased(k);
	}

	/**
	 * user control
	 * 
	 * @param e
	 *            MouseEvent value
	 */
	public void mousePressed(MouseEvent e) {
		gameState.MousePressed(e.getX(), e.getY());
	}

	/**
	 * user control
	 * 
	 * @param e
	 *            MouseEvent value
	 */
	public void mouseReleased(MouseEvent e) {
		gameState.MouseReleased(e.getX(), e.getY());

	}

	/**
	 * user control
	 * 
	 * @param e
	 *            MouseEvent value
	 */
	public void MouseMoved(MouseEvent e) {
		gameState.MouseMoved(e.getX(), e.getY());
	}

}
