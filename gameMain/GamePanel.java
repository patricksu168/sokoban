/**
 * set up JPanel, also set up multi-thread to process display, keyboard, mouse respond 
 */
package gameMain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.JPanel;

import gameState.GameStateManager;
import mazeGenerator.generatorThread;
import sound.Sound;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	// dimensions
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 900;
	public static final int SCALE = 1;
	// game thread
	private Thread gameThread;
	private Thread generatorThread;
	private Thread soundThread;
	private int FPS = 60;
	private Graphics2D g;
	// game state manager
	private GameStateManager gsm;
	// image
	private BufferedImage image;
	private boolean ifRunning;
	private long targetTime = 1000 / FPS;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (gameThread == null) {
			gameThread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			gameThread.start();
		}
	}

	private void init() {
		gsm = new GameStateManager();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		ifRunning = true;
		// initgenerator();

	}

	public void initSound() {
		soundThread = new Thread(Sound.getInstance());
		soundThread.setDaemon(true);
		soundThread.setPriority(Thread.MIN_PRIORITY);
		soundThread.start();
	}

	public void initGenerator() {
		generatorThread = new Thread(new generatorThread());
		generatorThread.setDaemon(true);
		generatorThread.setPriority(Thread.MIN_PRIORITY);
		generatorThread.start();
	}

	public void run() {

		init();
		initSound();
		long start;
		long elapsed;
		long wait;
		// game loop
		while (ifRunning) {

			start = System.nanoTime();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (generatorThread == null || !generatorThread.isAlive()) {
				initGenerator();
			}
		}

	}

	private void draw() {
		gsm.draw(g);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key) {
	}

	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		gsm.mousePressed(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		gsm.mouseReleased(e);

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gsm.MouseMoved(e);

	}

}
