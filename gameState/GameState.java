package gameState;

/**
 * a class that enclose all the State that will process on the screen
 *
 */
public abstract class GameState {

	protected GameStateManager gsm;

	public abstract void init();

	/**
	 * Main method of drawing element on the screen
	 * 
	 * @param g
	 */
	public abstract void draw(java.awt.Graphics2D g);

	public abstract void keyPressed(int k);

	public abstract void keyReleased(int k);

	public abstract void MousePressed(double x, double y);

	public abstract void MouseReleased(double x, double y);

	public abstract void MouseMoved(double x, double y);

}
