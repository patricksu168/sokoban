package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import gameMain.GamePanel;
import maze.MazeIO;

public class StartGameMenu extends GameState {
	private boolean subMenu = false;
	private short currentChoice;
	private String[] myOoptions = { "Select", "Load", "Back" };
	private Font myFont;
	private int myPage = 0;
	private List<Rectangle> myButton;
	private List<String> mapPool;
	private int mazeBuffer = MazeIO.getNumberOfMaze();
	private int selectedLevel = -1;
	private String loadLevel = "";
	private int displayNumber = 0;
	private boolean keyHold = false;

	public StartGameMenu(GameStateManager gsm) {
		System.err.println("Start Game Menu");
		this.gsm = gsm;

	}

	/**
	 * game control for passing to next game state
	 */
	private void select() {

		if (currentChoice == 0) {// load game from database
			if (selectedLevel == -9) {
				myPage--;
				if (myPage < 0) {
					myPage = 0;
				}
				subMenu();
			} else if (selectedLevel == -10) {
				subMenu = false;
				myButton = null;
				myPage = 0;
				selectedLevel = -1;
				loadLevel = "";
			} else if (selectedLevel == -11) {
				myPage++;
				subMenu();
			} else if (selectedLevel == -1) {
				// do nothing
			} else {
				gsm.setFileName(Integer.toString(selectedLevel));
				gsm.setLoadType("LOADFROMDB");
				gsm.setState(GameStateManager.SINGLEPLAYER);
			}
		} else {// load game from user file
			if (loadLevel.equals("home")) {
				subMenu = false;
				myButton = null;
				myPage = 0;
				selectedLevel = -1;
				loadLevel = "";
			} else if (loadLevel.equals("next")) {
				myPage++;
				subMenu();
			} else if (loadLevel.equals("previous")) {
				myPage--;
				if (myPage < 0) {
					myPage = 0;
				}
				subMenu();
			} else if (loadLevel.equals("")) {

			} else {
				gsm.setFileName(loadLevel);
				gsm.setLoadType("LOADSAVE");
				gsm.setState(GameStateManager.SINGLEPLAYER);
			}
		}
	}

	/**
	 * in class control which change and create different object for control
	 * purpose
	 */
	private void subMenu() {
		if (currentChoice == 0) {
			mazeBuffer = MazeIO.getNumberOfMaze();
			selectLevelMenu();
		}
		if (currentChoice == 1) {
			loadLevelMenu();
		}
		if (currentChoice == 2) {
			gsm.setState(GameStateManager.MAINMENU);

		}
	}

	/**
	 * help method which draw the load level screen
	 * 
	 * @param g
	 */
	private void drawLoadLevel(Graphics2D g) {
		boolean overflow = false;
		displayNumber = 30;

		int posX = 100;
		int posY = 50;
		int fontSize = 26;
		g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		Rectangle rec = new Rectangle();
		rec.setBounds(100, 50, 800, 838);
		g.setColor(new Color((float) 192 / 255, (float) 192 / 255, (float) 192 / 255));
		g.fill(rec);
		g.setColor(Color.black);
		int i = 0;
		for (Rectangle rec1 : myButton) {
			int value = myPage * displayNumber + 1 + i;
			if (value > this.mapPool.size()) {
				overflow = true;
			}

			if (!overflow && i < displayNumber) {
				if (loadLevel.equals(this.mapPool.get(value - 1))) {
					g.setColor(new Color((float) 102 / 255, (float) 102 / 255, (float) 255 / 255));
					g.fill(rec1);
					g.setColor(Color.black);
				}
				g.drawString(this.mapPool.get(value - 1), posX, posY + fontSize);
				posY += fontSize;
			} else if (((myPage + 1) * displayNumber <= this.mapPool.size() && i == this.myButton.size() - 3
					&& myPage != 0)
					|| ((myPage + 1) * displayNumber > this.mapPool.size() && i == this.myButton.size() - 2
							&& myPage != 0)) {

				if (loadLevel == "previous") {
					g.setColor(new Color((float) 102 / 255, (float) 102 / 255, (float) 255 / 255));
					g.fill(rec1);
				}
				Image ic = new ImageIcon("res/icon/rewind.png").getImage();
				g.drawImage(ic, ((Double) rec1.getX()).intValue(), ((Double) rec1.getY()).intValue(),
						((Double) rec1.getWidth()).intValue(), ((Double) rec1.getHeight()).intValue(), null);

			} else if (((myPage + 1) * displayNumber <= this.mapPool.size() && i == this.myButton.size() - 1)
					|| ((myPage + 1) * displayNumber > this.mapPool.size() && i == this.myButton.size())
					|| ((myPage + 1) * displayNumber > this.mapPool.size() && i + 1 == this.myButton.size())) {

				if (loadLevel == "home") {
					g.setColor(new Color((float) 102 / 255, (float) 102 / 255, (float) 255 / 255));
					g.fill(rec1);
				}

				Image ic = new ImageIcon("res/icon/replay.png").getImage();
				g.drawImage(ic, ((Double) rec1.getX()).intValue(), ((Double) rec1.getY()).intValue(),
						((Double) rec1.getWidth()).intValue(), ((Double) rec1.getHeight()).intValue(), null);

			} else if (((myPage + 1) * displayNumber <= this.mapPool.size() && i + 2 == this.myButton.size())) {

				if (loadLevel == "next") {
					g.setColor(new Color((float) 102 / 255, (float) 102 / 255, (float) 255 / 255));
					g.fill(rec1);
				}

				Image ic = new ImageIcon("res/icon/fast-forward.png").getImage();
				g.drawImage(ic, ((Double) rec1.getX()).intValue(), ((Double) rec1.getY()).intValue(),
						((Double) rec1.getWidth()).intValue(), ((Double) rec1.getHeight()).intValue(), null);
			}

			i++;
		}

	}

	/**
	 * help method which draw the select level screen
	 * 
	 * @param g
	 */
	private void drawSelectLevel(Graphics2D g) {
		int i = 0;
		boolean overflow = false;
		for (Rectangle p : this.myButton) {
			g.setColor(Color.LIGHT_GRAY);
			int value = myPage * displayNumber + 1 + i;
			g.fill(p);

			if (this.mazeBuffer < value) {
				overflow = true;
			}
			if (selectedLevel == value) {
				g.setColor(Color.cyan);
			} else {
				g.setColor(Color.BLACK);
			}
			if (!overflow && i < displayNumber) {
				g.drawString("" + value, (float) (p.getWidth() / 2 + p.getMinX() - 20),
						(float) (p.getMinY() + p.getHeight() / 2 + 10));
			} else if (((myPage + 1) * displayNumber <= this.mazeBuffer && i == this.myButton.size() - 3 && myPage != 0)
					|| ((myPage + 1) * displayNumber > this.mazeBuffer && i == this.myButton.size() - 2
							&& myPage != 0)) {
				if (selectedLevel == -9)
					g.setColor(Color.cyan);
				else
					g.setColor(Color.BLACK);
				g.drawString("Prev", (float) (p.getWidth() / 2 + p.getMinX() - 40),
						(float) (p.getMinY() + p.getHeight() / 2 + 10));
			} else if (((myPage + 1) * displayNumber <= this.mazeBuffer && i == this.myButton.size() - 1)
					|| ((myPage + 1) * displayNumber > this.mazeBuffer && i == this.myButton.size())
					|| ((myPage + 1) * displayNumber > this.mazeBuffer && i + 1 == this.myButton.size())) {
				if (selectedLevel == -10)
					g.setColor(Color.cyan);
				else
					g.setColor(Color.BLACK);
				g.drawString("Home", (float) (p.getWidth() / 2 + p.getMinX() - 40),
						(float) ((p.getHeight() / 2) + p.getMinY() + 10));
			} else if (((myPage + 1) * displayNumber <= this.mazeBuffer && i + 2 == this.myButton.size())) {
				if (selectedLevel == -11)
					g.setColor(Color.cyan);
				else
					g.setColor(Color.BLACK);
				g.drawString("Next", (float) (p.getWidth() / 2 + p.getMinX() - 40),
						(float) (p.getMinY() + p.getHeight() / 2 + 10));
			}
			i++;
		}
	}

	/**
	 * help method which draw the menu screen
	 * 
	 * @param g
	 */
	private void drawThisMenu(Graphics2D g) {
		// draw menu options
		ImageIcon img = null;
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

	/**
	 * Initialize method for sub menu in this class
	 */
	private void selectLevelMenu() {
		boolean overflow = false;
		displayNumber = 25;
		this.myButton = new ArrayList<Rectangle>();
		subMenu = true;
		// pop up window
		// System.out.println(MazeIO.getNumberOfMaze());
		int Y = 110;
		int X = 155;

		for (int i = 1; i <= displayNumber; i++) {
			int value = (myPage * displayNumber + i);
			if (this.mazeBuffer < value) {
				overflow = true;
				break;
			}
			int x1 = X;
			int y1 = Y;
			X += 205;
			if (i % 5 == 0) {
				Y += 100;
				X = 155;
			}
			Rectangle p = new Rectangle();
			p.setBounds(x1, y1, 150, 90);
			myButton.add(p);
		}
		// add page button
		if (myPage != 0) {
			Rectangle p1 = new Rectangle();
			p1.setBounds(360, 610, 150, 90);
			myButton.add(p1);
		}
		if (!overflow) {
			Rectangle p1 = new Rectangle();
			p1.setBounds(770, 610, 150, 90);
			myButton.add(p1);
		}
		Rectangle p1 = new Rectangle();
		p1.setBounds(565, 610, 150, 90);
		myButton.add(p1);

	}

	/**
	 * Initialize method for sub menu in this class
	 */
	private void loadLevelMenu() {
		this.displayNumber = 30;
		this.myButton = new ArrayList<>();
		this.mapPool = new ArrayList<>();
		Rectangle rec = new Rectangle();
		subMenu = true;
		// load dir
		setMapPool("./res/out");
		// make button
		int x = 100;
		int y = 55;
		int height = 26;
		int width = 700;
		boolean overflow = false;

		for (int i = 0; i < displayNumber; i++) {
			if (myPage * displayNumber + i + 1 > this.mapPool.size()) {
				overflow = true;
				break;
			}
			rec.setBounds(x, y + height * i, width, height);
			myButton.add((Rectangle) rec.clone());
		}
		if (myPage != 0) {
			rec.setBounds(630, 845, 40, 40);
			myButton.add((Rectangle) rec.clone());
		}

		if (!overflow) {
			rec.setBounds(770, 845, 40, 40);
			myButton.add((Rectangle) rec.clone());
		}
		rec.setBounds(700, 845, 40, 40);
		myButton.add((Rectangle) rec.clone());

		System.out.println("page: " + myPage);
		System.out.println("button size: " + this.myButton.size());
		System.out.println("pool size:" + this.mapPool.size());
	}

	@Override
	public void init() {

	}

	@Override
	public void draw(Graphics2D g) {
		g.clearRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// // draw bg
		Image background;
		ImageIcon img = new ImageIcon("res/menuIcon/MainBackground.jpg");
		background = img.getImage();
		g.drawImage(background, 0, 0, 1280, 900, null);

		if (!subMenu) {
			// this menu
			drawThisMenu(g);
		} else if (currentChoice == 0) {
			// sub menu for select level
			drawSelectLevel(g);
		} else if (currentChoice == 1) {
			// sub menu for load level
			drawLoadLevel(g);
		} else {
			drawThisMenu(g);
		}

	}

	public void keyPressed(int k) {
		if (!subMenu) {
			if (k == KeyEvent.VK_ENTER) {
				subMenu();
			}
			if (k == KeyEvent.VK_UP) {
				currentChoice--;
				if (currentChoice == -1) {
					currentChoice = (short) (myOoptions.length - 1);
				}
			}
			if (k == KeyEvent.VK_DOWN) {
				currentChoice++;
				if (currentChoice == myOoptions.length) {
					currentChoice = 0;
				}
			}
			if (k == KeyEvent.VK_ESCAPE) {
				currentChoice = 2;
				subMenu();
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
		} else {
			if (currentChoice == 0) {
				if (k == KeyEvent.VK_ESCAPE) {
					selectedLevel = -10;
					select();
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
			} else {
				if (k == KeyEvent.VK_ESCAPE) {
					loadLevel = "home";
					select();
				}
				if (k == KeyEvent.VK_HOME) {
					try {
						System.getProperties().list(System.out);
						System.out.println(System.getProperty("os.name"));
						if (System.getProperty("os.name").startsWith("Window")) {
							Runtime.getRuntime().exec("explorer.exe " + ".\\res\\out\\.");
						} else {

						}
					} catch (IOException e) {
						e.printStackTrace();
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
		// TODO Auto-generated method stub
		if (!subMenu) {
			subMenu();
		} else {
			select();
		}
		this.keyHold = false;
	}

	public void MouseMoved(double x, double y) {
		if (this.keyHold) {
		} else {
			// System.out.println(x +" " +y);
			if (!subMenu) {
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
			} else {
				if (currentChoice == 0) {
					for (int i = 0; i < this.myButton.size(); i++) {
						if (this.myButton.get(i).contains(x, y)) {
							selectedLevel = myPage * displayNumber + i + 1;
							if (((myPage + 1) * displayNumber <= this.mazeBuffer && i == this.myButton.size() - 3
									&& myPage != 0)
									|| ((myPage + 1) * displayNumber > this.mazeBuffer && i == this.myButton.size() - 2
											&& myPage != 0)) {
								selectedLevel = -9;// back button
							} else if (((myPage + 1) * displayNumber <= this.mazeBuffer
									&& i == this.myButton.size() - 1)
									|| ((myPage + 1) * displayNumber > this.mazeBuffer && i == this.myButton.size())
									|| ((myPage + 1) * displayNumber > this.mazeBuffer
											&& i + 1 == this.myButton.size())) {
								selectedLevel = -10;// home
							} else if (((myPage + 1) * displayNumber <= this.mazeBuffer
									&& i + 2 == this.myButton.size())) {
								selectedLevel = -11;// next
							}
							break;
						}
						selectedLevel = -1;
					}
				} else {
					for (int i = 0; i < this.myButton.size(); i++) {
						if (this.myButton.get(i).contains(x, y)) {

							if (((myPage + 1) * displayNumber <= this.mapPool.size() && i == this.myButton.size() - 3
									&& myPage != 0)
									|| ((myPage + 1) * displayNumber > this.mapPool.size()
											&& i == this.myButton.size() - 2 && myPage != 0)) {
								loadLevel = "previous";// back button
							} else if (((myPage + 1) * displayNumber <= this.mapPool.size()
									&& i == this.myButton.size() - 1)
									|| ((myPage + 1) * displayNumber > this.mapPool.size() && i == this.myButton.size())
									|| ((myPage + 1) * displayNumber > this.mapPool.size()
											&& i + 1 == this.myButton.size())) {
								loadLevel = "home";// home
							} else if (((myPage + 1) * displayNumber <= this.mapPool.size()
									&& i + 2 == this.myButton.size())) {
								loadLevel = "next";// next
							} else {
								loadLevel = this.mapPool.get(myPage * displayNumber + i);
							}
							break;
						}
						loadLevel = "";
					}
				}
			}
		}
	}

	public void setMapPool(String directory) {
		if (this.mapPool == null)
			this.mapPool = new ArrayList<String>();
		try {
			File folder = new File(directory);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			// System.out.println(folder.getAbsolutePath());
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					if (fileEntry.getName().equals(".."))
						continue;
					setMapPool(fileEntry.toString());
					// System.out.println(fileEntry.getName());
				} else {
					this.mapPool.add(fileEntry.getName());
				}
			}
			// this.mapPool.add("Home");
		} catch (NullPointerException e) {

		}

	}

}
