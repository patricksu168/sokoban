package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gameElement.floorObject.Goal;
import gameElement.floorObject.PassWay;
import gameElement.floorObject.Wall;
import gameElement.gameObject.Box;
import gameElement.gameObject.Player;
import maze.Maze;
import maze.MazeIO;
import maze.Tile;
import texture.texture;

/**
 * A game status which allow player to create their own map and save into file
 *
 */
public class CreativeMode extends GameState {

	private Maze myMaze = new Maze();
	private int myLength;
	private int myWidth;
	private boolean pauseFlag = false;
	private boolean loadMode = false;
	private String fileName;
	private int tileLength = 0;
	private int menuSelection = 0;
	private String myType;
	private List<Integer> keyInput = new ArrayList<>();
	private short[] cursorPos = { (short) 1, (short) 1 };
	private Vector<Wall> newWallList = new Vector<Wall>();

	public CreativeMode(GameStateManager gsm) {
		this.gsm = gsm;
		this.myMaze.setMyBox(new Vector<Box>());
		this.myMaze.setMyGoals(new Vector<Goal>());
		JFrame lengthFrame = new JFrame();
		this.myLength = Integer
				.valueOf(JOptionPane.showInputDialog(lengthFrame, "Enter the length of your maze", "16"));
		if (this.myLength == JOptionPane.YES_NO_CANCEL_OPTION) {
			this.gsm.setState((short) 3);
		}
		JFrame widthFrame = new JFrame();
		this.myWidth = Integer.valueOf(JOptionPane.showInputDialog(widthFrame, "Enter the width of your maze", "16"));
		if (this.myWidth == JOptionPane.YES_NO_CANCEL_OPTION) {
			this.gsm.setState((short) 3);
		}
		if (this.myLength <= 0 || this.myWidth <= 0) {
			JFrame debugFrame = new JFrame();
			JOptionPane.showConfirmDialog(debugFrame, "Invalid Maze!");
			this.gsm.setState((short) 3);
		}
		System.out.println("Reached here!");
		short[] mySize = { (short) this.myLength, (short) this.myWidth };
		Tile[][] map = new Tile[this.myLength][this.myWidth];
		for (int count = 0; count < myWidth; count++) {
			for (int counter = 0; counter < myLength; counter++) {
				map[counter][count] = new Tile();
			}
		}
		myMaze.setMyMap(map);
		this.myMaze.setMySize(mySize);
		int xIndex = 0;

		/*
		 * Init the map;
		 */
		while (xIndex < this.myLength) {
			this.myMaze.getMyMap()[xIndex][0].setMyFloor(new Wall());
			xIndex++;
		}
		xIndex = 0;
		int yIndex = 1;
		while (yIndex < this.myWidth - 1) {
			while (xIndex < this.myLength) {
				if (xIndex == 0 || xIndex == this.myLength - 1) {
					this.myMaze.getMyMap()[xIndex][yIndex].setMyFloor(new Wall());
				} else {
					this.myMaze.getMyMap()[xIndex][yIndex].setMyFloor(new PassWay());
				}
				xIndex++;
			}
			xIndex = 0;
			yIndex++;
		}
		while (xIndex < this.myLength) {
			this.myMaze.getMyMap()[xIndex][yIndex].setMyFloor(new Wall());
			xIndex++;
		}

		Player p = new Player((short) 1, (short) 1);
		this.myMaze.getMyMap()[1][1].setStanding(p);
		this.myMaze.setMyplayer(p);

		this.init();
	}

	/**
	 * move player cursor
	 * 
	 * @param direction
	 */
	public void moveCursor(short direction) {
		int xLimit = this.myLength - 1;
		int yLimit = this.myWidth - 1;
		short[] next = new short[2];
		next[0] = cursorPos[0];
		next[1] = cursorPos[1];
		switch (direction) {
		case 1:
			next[1]--;
			break;
		case 2:
			next[1]++;
			break;
		case 3:
			next[0]--;
			break;
		case 4:
			next[0]++;
			break;
		}
		if (next[0] >= 1 && next[0] <= xLimit && next[1] >= 1 && next[1] <= yLimit) {
			this.cursorPos = next;
		} else {
			// do nothing;
		}
	}

	/**
	 * main method handling draw element on screen
	 * 
	 * @param g
	 * @return
	 */
	private Graphics2D drawnewmaze(Graphics2D g) {
		// System.err.println("draw new maze");
		Image maze = this.myMaze.getmazemap();
		int iniX = this.myMaze.getDrawSetting()[0];
		int iniY = this.myMaze.getDrawSetting()[1];
		// Graphics2D filled maze = (Graphics2D) maze.getGraphics();
		g.drawImage(maze, this.myMaze.getDrawSetting()[0], this.myMaze.getDrawSetting()[1], null);
		short X = this.myMaze.getMyplayer().getPosition()[0];
		short Y = this.myMaze.getMyplayer().getPosition()[1];
		BufferedImage playerImg = this.myMaze.getMyplayer().getImage();
		g.drawImage(playerImg, X * this.tileLength + iniX, Y * this.tileLength + iniY,
				X * this.tileLength + this.tileLength + iniX, Y * this.tileLength + this.tileLength + iniY, 0, 0,
				playerImg.getWidth(), playerImg.getHeight(), null);
		BufferedImage wallImg = null;
		X = 0;
		Y = 0;
		while (X < this.myLength) {
			while (Y < this.myWidth) {
				if (this.myMaze.getMyMap()[X][Y].getMyFloor().getTextureID() == (short) 0) {
					wallImg = texture.getInstance().getImage((short) 0);
					g.drawImage(wallImg, X * this.tileLength + iniX, Y * this.tileLength + iniY,
							X * this.tileLength + this.tileLength + iniX, Y * this.tileLength + this.tileLength + iniY,
							0, 0, wallImg.getWidth(), wallImg.getHeight(), null);
				}
				Y++;
			}
			X++;
		}
		BufferedImage boxImg = null;
		for (short i = 0; i < this.myMaze.getMyBox().size(); i++) {
			X = this.myMaze.getMyBox().get(i).getPosition()[0];
			Y = this.myMaze.getMyBox().get(i).getPosition()[1];
			boxImg = this.myMaze.getMyBox().get(i).getImage();
			g.drawImage(boxImg, X * this.tileLength + iniX, Y * this.tileLength + iniY,
					X * this.tileLength + this.tileLength + iniX, Y * this.tileLength + this.tileLength + iniY, 0, 0,
					boxImg.getWidth(), boxImg.getHeight(), null);
		}

		BufferedImage destImg = null;
		for (short i = 0; i < this.myMaze.getMyGoals().size(); i++) {
			X = this.myMaze.getMyGoals().get(i).getPosition()[0];
			Y = this.myMaze.getMyGoals().get(i).getPosition()[1];
			destImg = this.myMaze.getMyGoals().get(i).getImage();
			g.drawImage(destImg, X * this.tileLength + iniX, Y * this.tileLength + iniY,
					X * this.tileLength + this.tileLength + iniX, Y * this.tileLength + this.tileLength + iniY, 0, 0,
					destImg.getWidth(), destImg.getHeight(), null);
		}

		/*
		 * BufferedImage WallImg = null; for (short i=0; i < this.m.getMy; i++){
		 * X = this.m.getMyBox().get(i).getPosition()[0]; Y =
		 * this.m.getMyBox().get(i).getPosition()[1]; boxImg =
		 * this.m.getMyBox().get(i).getImage(); g.drawImage(boxImg,
		 * X*this.tileLength + iniX, Y*this.tileLength + iniY,
		 * X*this.tileLength+this.tileLength + iniX,
		 * Y*this.tileLength+this.tileLength + iniY, 0,0,boxImg.getWidth(),
		 * boxImg.getHeight(),null); }
		 */
		return g;
	}

	/**
	 * draw slider on screen
	 * 
	 * @param g
	 * @return
	 */
	private Graphics2D slideBoard(Graphics2D g) {
		String instruction = "W-A-S-D: Control";
		String instruction2 = "B: Put box";
		String instruction3 = "X: Put wall";
		String instruction4 = "Z: Put destination";
		String instruction5 = "P: Put player";
		String ss = "K: Save maze";
		String ss2 = "ESC: Exit";

		Font font = new Font("Black", Font.BOLD, 24);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(instruction, 1280 - 270, 320);
		g.drawString(instruction2, 1280 - 270, 350);
		g.drawString(instruction3, 1280 - 270, 380);
		g.drawString(instruction4, 1280 - 270, 410);
		g.drawString(instruction5, 1280 - 270, 440);
		g.drawString(ss, 1280 - 270, 500);
		g.drawString(ss2, 1280 - 215, 530);
		return g;
	}

	/**
	 * save maze into file
	 */
	private void saveFile() {
		if (this.myMaze.getMyGoals().size() != this.myMaze.getMyBox().size()) {
			JFrame conditionCheck = new JFrame();
			int dialogResult = JOptionPane.showConfirmDialog(conditionCheck, "Invalid maze!");
			return;
		}
		JFrame test = new JFrame();
		String filename = JOptionPane.showInputDialog(test, "Enter save file name(maxium 10 character):", "Filename");
		if (filename == null || filename.length() == 0)
			return;
		MazeIO.saveMaze(filename, this.myMaze);
	}

	@Override
	public void init() {
		myMaze.setDrawSetting();
		this.tileLength = this.myMaze.getTileImagelength();
		myMaze.buildmazeImage();
	}

	@Override
	public void draw(Graphics2D g) {
		// String s = m.getMyMap().toString();
		Image graph;
		ImageIcon img = new ImageIcon("res/img/white_background.jpg");
		graph = img.getImage();
		g.drawImage(graph, 0, 0, 1440, 900, null);

		g = drawnewmaze(g);
		BufferedImage cursorImage = texture.getInstance().getImage((short) 12);
		int iniX = this.myMaze.getDrawSetting()[0];
		int iniY = this.myMaze.getDrawSetting()[1];
		short X = this.cursorPos[0];
		short Y = this.cursorPos[1];
		g.drawImage(cursorImage, X * this.tileLength + iniX, Y * this.tileLength + iniY,
				X * this.tileLength + this.tileLength + iniX, Y * this.tileLength + this.tileLength + iniY, 1, 1,
				cursorImage.getWidth(), cursorImage.getHeight(), null);
		g = slideBoard(g);

		// Image background;
		// ImageIcon img = new ImageIcon("res/img/WallRound_Gray.png");
		// background = img.getImage();
		// g.drawImage(background, 0, 0, 1280, 900, null);
		if (this.myMaze.ifSolved()) {
			// g.drawString("completed", 450, 450);
			System.out.println("complete");
			this.init();
		}
	}

	@Override
	public void keyPressed(int k) {
		this.keyInput.add(k);
	}

	@Override
	public void keyReleased(int k) {
		if (this.pauseFlag == false) {
			switch (k) {
			case KeyEvent.VK_W:
				this.moveCursor((short) 1);
				break;
			case KeyEvent.VK_S:
				this.moveCursor((short) 2);
				break;
			case KeyEvent.VK_A:
				this.moveCursor((short) 3);
				break;
			case KeyEvent.VK_D:
				this.moveCursor((short) 4);
				break;
			case KeyEvent.VK_ESCAPE:
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to exit to the main menu?",
						"Warning", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					this.gsm.setState((short) 0);
				} else {
					// Do nothing;
				}
				this.pauseFlag = true;
				break;
			case KeyEvent.VK_B:
				myMaze.putBox(cursorPos[0], cursorPos[1]);
				break;
			case KeyEvent.VK_X:
				myMaze.putWall(cursorPos[0], cursorPos[1]);
				break;
			case KeyEvent.VK_P:
				myMaze.putPlayer(cursorPos[0], cursorPos[1]);
				break;
			case KeyEvent.VK_Z:
				myMaze.putDestination(cursorPos[0], cursorPos[1]);
				break;
			case KeyEvent.VK_G:
				this.init();
				break;
			case KeyEvent.VK_K:
				saveFile();
				this.loadMode = true;
			}
		} else {
			switch (k) {
			case KeyEvent.VK_ESCAPE:
				this.pauseFlag = false;
				break;
			}
		}

	}

	@Override
	public void MousePressed(double x, double y) {
	}

	@Override
	public void MouseReleased(double x, double y) {
	}

	@Override
	public void MouseMoved(double x, double y) {
	}

}