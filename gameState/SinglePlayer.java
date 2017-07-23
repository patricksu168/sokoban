package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import gameElement.floorObject.*;
import gameElement.gameObject.*;
import maze.*;
import mazeGenerator.MazeGenerator;
import mazeGenerator.MazeGeneratorTwo;
import texture.texture;

/**
 * Generate the single player game board
 */
public class SinglePlayer extends GameState implements ActionListener {
	private Maze myMaze;
	private String fileName;
	private int tileLength = 0;
	private boolean pauseFlag = false;
	private int menuSelection = 0;
	private List<Integer> keyInput;
	private String loadType;
	private Maze tempMaze;
	private boolean keyPress;

	/**
	 * constructor
	 * 
	 * @param gsm
	 *            current GameStateManager
	 */
	public SinglePlayer(GameStateManager gsm, String name, String type) {
		System.err.println("Single player");
		this.keyInput = new ArrayList<>();
		this.gsm = gsm;
		this.loadType = type;
		this.fileName = name;
		this.keyPress = false;
		init();
		tips();
	}

	/**
	 * initial the game board
	 */
	public void init(int i) {
		this.fileName = "" + i;
		this.loadType = "NEWGAME";
		init();
	}

	/**
	 * check if box type object place on goal tile
	 * 
	 * @param position
	 * @return
	 */
	public boolean boxOnGoal(short[] position) {
		for (Goal g : this.myMaze.getMyGoals()) {
			if (g.getPosition()[1] == position[1] && g.getPosition()[0] == position[0]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * main method that draw element on screen
	 * 
	 * @param g
	 * @return Graphics2D g
	 */
	private Graphics2D drawnewmaze(Graphics2D g) {
		Image maze = this.myMaze.getmazemap();
		int iniX = this.myMaze.getDrawSetting()[0];
		int iniY = this.myMaze.getDrawSetting()[1];

		g.drawImage(maze, this.myMaze.getDrawSetting()[0], this.myMaze.getDrawSetting()[1], null);

		short X = this.myMaze.getMyplayer().getPosition()[0];
		short Y = this.myMaze.getMyplayer().getPosition()[1];

		BufferedImage playerImg = this.myMaze.getMyplayer().getImage();
		g.drawImage(playerImg, X * this.tileLength + iniX, Y * this.tileLength + iniY,
				X * this.tileLength + this.tileLength + iniX, Y * this.tileLength + this.tileLength + iniY, 0, 0,
				playerImg.getWidth(), playerImg.getHeight(), null);

		BufferedImage boxImg = null;
		for (short i = 0; i < this.myMaze.getMyBox().size(); i++) {
			X = this.myMaze.getMyBox().get(i).getPosition()[0];
			Y = this.myMaze.getMyBox().get(i).getPosition()[1];
			short[] pos = { X, Y };
			if (!boxOnGoal(pos)) {
				boxImg = this.myMaze.getMyBox().get(i).getImage();
			} else {
				boxImg = texture.getInstance().getImage((short) 1);
			}
			g.drawImage(boxImg, X * this.tileLength + iniX, Y * this.tileLength + iniY,
					X * this.tileLength + this.tileLength + iniX, Y * this.tileLength + this.tileLength + iniY, 0, 0,
					boxImg.getWidth(), boxImg.getHeight(), null);

		}

		BufferedImage ballImg = null;
		X = 0;
		Y = 0;
		return g;
	}

	/**
	 * pop up a instruction manuel
	 */
	private void tips() {
		String s = "Controls:" + "\n\n\n" + "" + "W-A-S-D | Player 1 Movement\n"
				+ "E              | Player 1 Place tracer / Pick up tracer\n"
				+ "Z              | Activates/Deactivates Zoom mode\n" + "ESC         | Pause Menu\n"
				+ "SPACE    | Bring up / close this menu\n\n\n" + "" + "Gamplay:\n\n"
				+ "Your goal is to reach the safe zone (Green Square) while avoiding the ghosts.\n"
				+ "The ability to finish the map is only unlocked when all Maps are collected.\n\n"
				+ "A Sword will sometimes spawn for you in a random area.\n"
				+ "This will grant you the ability to survive one ghost and eliminate it from the current map.\n"
				+ "Be warned, the enemies in this game have the ability to mimic your every action!\n\n"
				+ "Use this knowledge to your advantage as you encounter one in your path.";
		JOptionPane.showMessageDialog(null, s, "Instruction", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * method that draw slide board on screen
	 * 
	 * @param g
	 * @return Graphics2D g
	 */
	private Graphics2D slideBoard(Graphics2D g) {
		Image graph;
		ImageIcon img;
		if (this.menuSelection == 1) {
			img = new ImageIcon("res/icon/stop_hover.png");
		} else {
			img = new ImageIcon("res/icon/stop.png");
		}
		graph = img.getImage();
		g.drawImage(graph, 950, 200, 70, 70, null);
		if (this.menuSelection == 2) {
			img = new ImageIcon("res/icon/replay_hover.png");
		} else {
			img = new ImageIcon("res/icon/replay.png");
		}
		graph = img.getImage();
		g.drawImage(graph, 1050, 200, 70, 70, null);
		if (this.menuSelection == 3) {
			img = new ImageIcon("res/icon/rewind_hover.png");
		} else {
			img = new ImageIcon("res/icon/rewind.png");
		}
		graph = img.getImage();
		g.drawImage(graph, 1150, 200, 70, 70, null);
		if (this.menuSelection == 4) {
			img = new ImageIcon("res/icon/save_hover.png");
		} else {
			img = new ImageIcon("res/icon/save.png");
		}
		graph = img.getImage();
		g.drawImage(graph, 950, 100, 70, 70, null);

		if (this.menuSelection == 5) {
			img = new ImageIcon("res/icon/speaker_hover.png");
		} else {
			img = new ImageIcon("res/icon/speaker.png");
		}
		graph = img.getImage();
		g.drawImage(graph, 1050, 100, 70, 70, null);

		if (this.menuSelection == 6) {
			img = new ImageIcon("res/icon/next_hover.png");
		} else {
			img = new ImageIcon("res/icon/next.png");
		}
		graph = img.getImage();
		g.drawImage(graph, 1150, 100, 70, 70, null);

		String playerName1 = "Player 1";
		String ss = "Steps";

		Font font = new Font("Black", Font.BOLD, 24);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(playerName1, 1280 - 270, 320);
		g.drawImage(texture.getInstance().getImage((short) 4), 1280 - 160, 275, 50, 50, null);
		g.drawString(ss, 1280 - 215, 500);
		g.drawString(Integer.toString(this.myMaze.getMyplayer().getStep()), 1280 - 193, 525);

		// repaint();
		return g;
	}

	/**
	 * method that use for save current state as save data
	 */
	private void saveFile() {

		JFrame test = new JFrame();
		String filename = JOptionPane.showInputDialog(test, "Enter save file name(maxium 10 character):", "Filename");
		if (filename == null || filename.length() == 0)
			return;
		MazeIO.saveMaze(filename, this.myMaze);
	}

	public void init() {
		if (this.loadType == "RESTART") {
			System.out.println(".......");
			this.myMaze = tempMaze;
			tempMaze = this.myMaze.copy();
			myMaze.printmazeonlywithmazetile();
			myMaze.setDrawSetting();
			this.tileLength = this.myMaze.getTileImagelength();
			myMaze.buildmazeImage();
			this.loadType = "REGEN";
		} else {
			if (this.loadType != "LOADSAVE") {
				MazeGenerator a = new MazeGeneratorTwo();
				System.err.println("init maze");
				this.myMaze = MazeIO.getMaze(Integer.parseInt(this.fileName));
				this.tempMaze = myMaze.copy();
				while (!myMaze.ifValidMaze()) {
					myMaze = a.generate();
				}
				myMaze.setDrawSetting();
				this.tileLength = this.myMaze.getTileImagelength();
				myMaze.buildmazeImage();
			} else {
				this.myMaze = MazeIO.loadMaze(this.fileName);
				this.tempMaze = myMaze.copy();
				myMaze.setDrawSetting();
				this.tileLength = this.myMaze.getTileImagelength();
				myMaze.buildmazeImage();
			}
		}
	}

	// }
	/**
	 * draw the game board
	 */

	@Override
	public void draw(Graphics2D g) {

		String s = myMaze.getMyMap().toString();
		Image graph;
		ImageIcon img = new ImageIcon("res/img/white_background.jpg");
		graph = img.getImage();
		g.drawImage(graph, 0, 0, 1440, 900, null);

		g = drawnewmaze(g);
		g = slideBoard(g);

		if (this.myMaze.ifSolved()) {
			try {
				this.fileName = "" + (Integer.parseInt(fileName) + 1);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				this.init(new Random().nextInt(MazeIO.getNumberOfMaze()) + 1);
			}
			this.init();
			System.out.println("complete");

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * user button control
	 */

	@Override
	public void keyPressed(int k) {
		if (!this.keyPress) {
			this.keyInput.add(k);
			this.keyPress = true;
		}

	}

	@Override
	public void keyReleased(int k) {
		// this.m.getMyplayer().setTextureID((short) 4);
		if (this.keyInput.size() > 1) {
			this.keyInput.clear();
		}
		try {
			this.keyPress = false;
			switch (this.keyInput.get(0)) {
			case KeyEvent.VK_W:
				myMaze.saveAll();
				this.myMaze.getMyplayer().setTextureID((short) 5);
				myMaze.movePlayer(Player.UP);
				break;
			case KeyEvent.VK_S:
				myMaze.saveAll();
				this.myMaze.getMyplayer().setTextureID((short) 6);
				myMaze.movePlayer(Player.DOWN);
				break;
			case KeyEvent.VK_A:
				myMaze.saveAll();
				this.myMaze.getMyplayer().setTextureID((short) 7);
				myMaze.movePlayer(Player.LEFT);
				break;
			case KeyEvent.VK_D:
				myMaze.saveAll();
				this.myMaze.getMyplayer().setTextureID((short) 8);
				myMaze.movePlayer(Player.RIGHT);
				break;
			case KeyEvent.VK_R:
				myMaze.reverse();
				break;
			case KeyEvent.VK_ESCAPE:
				this.pauseFlag = true;
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to exit to the main menu?",
						"Warning", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					this.gsm.setState((short) 0);
				} else {
					// Do nothing;
					this.pauseFlag = false;
				}
				break;
			case KeyEvent.VK_G:
				this.init();
				break;
			case KeyEvent.VK_K:
				saveFile();
				this.loadType = "LOADSAVE";
				break;
			case KeyEvent.VK_P:
				if (gsm.musicPause) {
					this.gsm.playMusic();
					this.gsm.musicPause = false;
				} else {
					this.gsm.stopMusic();
					this.gsm.musicPause = true;
				}
				break;
			}
		} catch (Exception e) {

		} finally {
			this.keyInput.clear();
		}
	}

	/**
	 * user button control
	 */
	@Override
	public void MousePressed(double x, double y) {
		if (!this.pauseFlag) {
			if (950 < x && x < 950 + 70 && 200 < y && y < 200 + 70) {
				gsm.setState(GameStateManager.MAINMENU);
			}
			if (950 < x && x < 1050 + 70 && 200 < y && y < 200 + 70) {
				System.out.println("restaring ");
				this.loadType = "RESTART";
				this.init();
			}
			if (1150 < x && x < 1150 + 70 && 200 < y && y < 200 + 70) {
				this.myMaze.reverse();
			}
			if (950 < x && x < 950 + 70 && 100 < y && y < 100 + 70) {
				saveFile();
			}
			if (1050 < x && x < 1050 + 70 && 100 < y && y < 100 + 70) {
				System.out.println("mute");
			}
			if (1150 < x && x < 1150 + 70 && 100 < y && y < 100 + 70) {
				this.init();
			}
		}

	}

	/**
	 * user button control
	 */

	@Override
	public void MouseReleased(double x, double y) {
	}

	/**
	 * user mouse button control
	 */
	// adding the hover effect under the mousemoved method

	@Override
	public void MouseMoved(double x, double y) {
		if (!this.pauseFlag) {
			if (950 < x && x < 950 + 70 && 200 < y && y < 200 + 70) {
				this.menuSelection = 1;
			} else if (1050 < x && x < 1050 + 70 && 200 < y && y < 200 + 70) {
				this.menuSelection = 2;
			} else if (1150 < x && x < 1150 + 70 && 200 < y && y < 200 + 70) {
				this.menuSelection = 3;
			} else if (950 < x && x < 950 + 70 && 100 < y && y < 100 + 70) {
				this.menuSelection = 4;
			} else if (1050 < x && x < 1050 + 70 && 100 < y && y < 100 + 70) {
				this.menuSelection = 5;
			} else if (1150 < x && x < 1150 + 70 && 100 < y && y < 100 + 70) {
				this.menuSelection = 6;
			} else {
				this.menuSelection = 0;
			}
		}

	}

}