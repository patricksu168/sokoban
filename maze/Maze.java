package maze;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import gameElement.floorObject.Goal;
import gameElement.floorObject.Wall;
import gameElement.gameObject.Box;
import gameElement.gameObject.GameObject;
import gameElement.gameObject.Player;
import sound.Sound;
import texture.texture;

/**
 * entire maze level with all functional method
 *
 */
public class Maze implements Serializable, Transparency {

	private static final long serialVersionUID = 1L;
	private boolean background = false;
	private int[] drawSetting = new int[2];
	private BufferedImage entireMaze;
	private short[] mySize;
	private Tile[][] myMap;
	private Vector<Box> myBox;
	// Goal object will not show in Tile.contain but in Tile.type
	private Vector<Goal> myGoals;
	private Player myPlayer;
	private int tileImagelength = 0;

	public Maze() {
	}

	/**
	 * This function is only use by AI, For human player DO NOT call this
	 * function calling this function will remove this instance totally
	 * 
	 * CAUTION : CALLING THIS METHOD WILL CHANGE THE STATE OF THE MAZE!!!
	 * 
	 * @param path
	 */
	public boolean executePath(List<Short> path) {
		for (short direction : path) {
			boolean check = movePlayer(direction);
			if (!check) {
				return false;
			}
		}
		return true;
	}

	/**
	 * build the basic map image
	 */
	public void buildmazeImage() {
		BufferedImage temp = new BufferedImage(this.tileImagelength * this.mySize[0],
				this.tileImagelength * this.mySize[1], TRANSLUCENT);
		Graphics2D g;
		g = (Graphics2D) temp.getGraphics();
		int Y = 0;
		int iy = 0;
		for (short H = 0; H < this.mySize[1]; H++) {
			int ix = 0;
			int X = 0;
			for (short W = 0; W < this.mySize[0]; W++) {
				BufferedImage i = null;

				if (this.myMap[W][H].getMyFloor() != null && (int) this.myMap[W][H].getMyFloor().getTextureID() != 0) {
					if (ix % 2 == 0 && iy % 2 != 0) {
						i = texture.getInstance().getImage((short) 9);
					} else if (ix % 2 != 0 && iy % 2 == 0) {
						i = texture.getInstance().getImage((short) 9);
					} else if (ix % 2 != 0 && iy % 2 != 0) {
						i = texture.getInstance().getImage((short) 10);
					} else if (ix % 2 == 0 && iy % 2 == 0) {
						i = texture.getInstance().getImage((short) 10);
					}
				} else {
					i = texture.getInstance().getImage((short) 0);
				}
				g.drawImage(i, X, Y, X + this.tileImagelength, Y + this.tileImagelength, 0, 0, i.getWidth(),
						i.getHeight(), null);
				if ((int) this.myMap[W][H].getMyFloor().getTextureID() != 1) {
					i = this.myMap[W][H].getMyFloor().getImage();
					g.drawImage(i, X, Y, X + this.tileImagelength, Y + this.tileImagelength, 0, 0, i.getWidth(),
							i.getHeight(), null);
				}
				X += this.tileImagelength;
				ix++;
			}
			Y += this.tileImagelength;
			iy++;
		}
		this.entireMaze = temp;

	}

	/**
	 * *CAUTION* this function should not be call this method is only use in AI
	 * to clear the cloned version of maze
	 * 
	 * @return true if success clean up
	 */
	public boolean clean() {
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * A deep copy of the maze
	 * 
	 * @return clone maze
	 */
	public Maze copy() {
		return (Maze) Maze.deepClone(this);
	}

	/**
	 * the method od deep clone *NOT* all object need to be Serializable
	 * 
	 * @param object
	 * @return cloned object
	 */
	public static Object deepClone(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this method is use to check if the puzzle is done or not *NOTE* if new
	 * type of "box" or "goal" is added, this method must be edit to fit in the
	 * new Object
	 * 
	 * @return true if puzzle is done
	 */
	public boolean ifSolved() {
		List<short[]> box_Locations = new ArrayList<short[]>();
		List<short[]> ball_Locations = new ArrayList<short[]>();
		// System.err.println(box_Locations.size() + " " +
		// ball_Locations.size());
		if (this.myBox != null) {
			for (Box b : this.getMyBox()) {
				box_Locations.add(b.getPosition());
			}
		}
		GOALS: for (Goal g : this.getMyGoals()) {
			short[] goal_pos = g.getPosition();

			// check if goal pos match box pos
			for (short[] box_pos : box_Locations) {
				if (box_pos[0] == goal_pos[0] && box_pos[1] == goal_pos[1])
					continue GOALS;
			}
			for (short[] ball_pos : ball_Locations) {
				if (ball_pos[0] == goal_pos[0] && ball_pos[1] == goal_pos[1])
					continue GOALS;
			}
			// if none of it match return false
			return false;
		}
		// after all goals is checked, enter here and return true
		System.out.println("Maze Complete !! U are AWESOME");
		return true;

	}

	/**
	 * basic method of checking the maze have same location of game Object
	 * 
	 * @return true if all match
	 */
	public boolean ifValidMaze() {
		ArrayList<short[]> refPosList = new ArrayList<short[]>();
		for (Box b : this.myBox) {
			short[] refPos = b.getPosition();
			for (short[] p : refPosList) {
				// Meaning the two boxes overlap each other;
				if (p == refPos) {
					return false;
				}
			}
			refPosList.add(refPos);
		}
		for (Goal g : this.myGoals) {
			short[] refPos = g.getPosition();
			for (short[] p : refPosList) {
				// Meaning the two goals overlap each other;
				if (p == refPos) {
					return false;
				}
			}
			refPosList.add(refPos);
		}
		if (this.myBox.size() != this.myGoals.size()) {
			return false;
		}

		return true;
	}

	/**
	 * a handy function to get the next position after moving in given direction
	 * 
	 * @param from
	 * @param direction
	 * @return
	 * @throws GameControlError
	 */
	public static short[] nextPos(short[] from, short direction) throws GameControlError {
		short[] next = new short[2];
		next[0] = from[0];
		next[1] = from[1];
		switch (direction) {
		case Player.UP:
			next[1]--;
			break;
		case Player.DOWN:
			next[1]++;
			break;
		case Player.LEFT:
			next[0]--;
			break;
		case Player.RIGHT:
			next[0]++;
			break;
		default:
			GameControlError err = new GameControlError();
			System.err.println(" Invalid Player movement");
			throw err;
		}
		return next;
	}

	/**
	 * ONLY USE THIS IN GAME movement and it should location in GameState
	 * 
	 * @param direction
	 *            player movement direction
	 * @return true if move success
	 */
	public boolean movePlayer(short direction) {
		short[] location = this.reachDestination(this.myPlayer, this.myPlayer.getDest(direction), direction, false);
		if (location == null) {
			printMazeWithTileOnly();
			return false;
		} else {
			this.moveObject(this.getTile(this.myPlayer.getPosition()), this.getTile(location));
			this.myPlayer.setPosition(location);
			this.myPlayer.addStep();
			return true;
		}
	}

	/**
	 * add goals in creative mode
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void putDestination(int x, int y) {
		Goal g = new Goal((short) x, (short) y);
		this.getMyGoals().addElement(g);
	}

	/**
	 * add player in creative mode
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void putPlayer(int x, int y) {
		Player p = new Player((short) x, (short) y);
		this.myMap[(short) x][(short) y].setStanding(p);
		this.setMyplayer(p);
	}

	/**
	 * add box in creative mode
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void putBox(int x, int y) {
		Box b = new Box((short) x, (short) y);
		this.getMyBox().add(b);
	}

	/**
	 * add wall in creative mode
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void putWall(int x, int y) {
		this.myMap[(short) x][(short) y].setMyFloor(new Wall());
	}

	/**
	 * newer print maze method, only track the Tile Object; *NOTE* add content
	 * if new GAME ELEMENT added
	 */
	public void printMazeWithTileOnly() {
		System.out.println("");
		for (short y = 0; y < this.mySize[1]; y++) {
			for (short x = 0; x < this.mySize[0]; x++) {
				Tile t = this.myMap[x][y];
				short tile_code = t.getMyFloor().getTextureID();

				if (tile_code == Tile.WALL) {
					System.out.print("#");
				} else if (t.getStanding() instanceof Box) {
					if (tile_code == Tile.GOAL) {
						System.out.print("O");
					} else {
						System.out.print("B");
					}
				} else if (tile_code == Tile.GOAL) {
					System.out.print("X");
				} else if (t.getStanding() instanceof Player) {
					System.out.print("U");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}

	}

	/**
	 * old print maze method, can track the GameObject location *NOTE* add
	 * content if new GAME ELEMENT added
	 */
	public void printmazeonlywithmazetile() {
		System.out.println("");
		for (short y = 0; y < this.mySize[1]; y++) {
			X_axis: for (short x = 0; x < this.mySize[0]; x++) {
				Tile t = this.myMap[x][y];
				short tile_code = t.getMyFloor().getTextureID();
				if (t.getStanding() == null) {
					// print floor
					if (tile_code == Tile.WALL) {
						System.out.print("#");
						continue X_axis;
					}
					if (tile_code == Tile.PASSWAY) {
						System.out.print(".");
						continue X_axis;
					}
					if (tile_code == Tile.GOAL) {
						System.out.print("X");
						continue X_axis;
					}
				} else {
					tile_code = t.getStanding().getTextureID();
					if (tile_code == this.getMyplayer().getTextureID()) {
						System.out.print("U");
					} else if (tile_code == this.getMyBox().get(0).getTextureID()) {
						System.out.print("B");
					}

				}
			}
		}
	}

	/**
	 * another print maze method
	 */
	public void printMaze() {
		System.out.println("");
		for (short y = 0; y < this.mySize[1]; y++) {
			X_axis: for (short x = 0; x < this.mySize[0]; x++) {
				short[] curr = { x, y };
				boolean goals = false;
				short tile_code = this.myMap[x][y].getMyFloor().getTextureID();

				// check if goals exist
				if (tile_code == Tile.GOAL) {
					goals = true;
				}

				// print player location
				if (this.myPlayer.getPosition()[0] == curr[0] && this.myPlayer.getPosition()[1] == curr[1]) {
					System.out.print("U");
					continue X_axis;
				}

				// check if box exist, then print out
				if (this.myBox != null) {
					for (Box b : this.myBox) {
						if (b.getPosition()[0] == curr[0] && b.getPosition()[1] == curr[1]) {

							if (goals) {
								System.out.print("O");
							} else {
								System.out.print("B");
							}

							continue X_axis;
						}
					}
				}

				if (tile_code == Tile.WALL) {
					System.out.print("#");
					continue X_axis;
				}
				if (tile_code == Tile.PASSWAY) {
					System.out.print(".");
					continue X_axis;
				}
				if (tile_code == Tile.GOAL) {
					System.out.print("X");
					continue X_axis;
				}

			}
			System.out.println();
		}

	}

	/**
	 * the game logic take place, which handle all the Game Element restriction
	 * and movement this Logic should fit to all additional content if Object is
	 * correctly implement *NOTE* IF u NOT fully understand this messy method,
	 * ignore it. *NOTE* DO NOT try to edit it if GameObject movement is not as
	 * expected, give the console log to "@THOMAS" by group facebook
	 * 
	 * @param o
	 *            GameObject that perform move action
	 * @param Dest
	 *            Destination that GameObject predict to go by it property
	 * @return location that object stop
	 * @return null is object does not move
	 */
	public short[] reachDestination(GameObject o, short[] Dest, short direction, boolean stoploop) {

		short[] Stop_location = null;
		boolean done = false;
		short[] from = o.getPosition();
		short[] next_pos;
		// get next pos of the GameObject
		try {
			next_pos = nextPos(from, direction);
		} catch (GameControlError e) {
			// wrong input of movement
			return Stop_location;
		}

		while (!done) {
			Tile next_tile = this.getTile(next_pos);
			// check current standing Tile property

			Tile curr_tile = this.getTile(from);
			if (!curr_tile.allowExit(direction)) {
				// not allow to exit in this direction
				return Stop_location;
			}

			// check if Object o can enter to the next tile (not checking If any
			// Object blocking the way)
			if (!next_tile.allowEnter(direction)) {
				return Stop_location;
			}

			// get Object that standing in the way if any
			GameObject next_Object = next_tile.getStanding();

			if (next_Object != null) {
				// some Object will be move
				// try to move the blocking object
				if (next_Object.isRollable()) {
					if (stoploop && o.isRollable()) {
						Dest = next_pos;
					}
					// Object or player meet a rolling object
					short[] moved_to = this.reachDestination(next_Object, next_Object.getDest(direction), direction,
							true);
					if (moved_to == null) {
						// next_object cannot move
						return Stop_location;
					} else {
						// next_object can be move
						// move tile representation
						this.moveObject(next_tile, this.getTile(moved_to));
						// move object
						next_Object.setPosition(moved_to);
						// TODO apply tile effect
					}
				} else if (!stoploop) {
					// player moving object
					short[] moved_to = this.reachDestination(next_Object, next_Object.getDest(direction), direction,
							true);
					if (moved_to == null) {
						// next_object cannot move
						return Stop_location;
					} else {
						// next_object can be move
						// move tile representation
						this.moveObject(next_tile, this.getTile(moved_to));
						// move object
						next_Object.setPosition(moved_to);
						// TODO apply tile effect
					}
				} else {
					// object meet not rolling object, which should stop every
					// movement
					return Stop_location;
				}
			}

			// Path is clear and ready to move
			Stop_location = next_pos;
			from = Stop_location;
			try {
				next_pos = nextPos(next_pos, direction);
			} catch (GameControlError e) {
				e.printStackTrace();
			}
			if (Stop_location[0] == Dest[0] && Stop_location[1] == Dest[1]) {
				done = true;
			}
			// TODO apply tile effect?
		}
		if (!this.background) {
			Sound.getInstance().addQueue((short) o.getTextureID());
		}
		return Stop_location;
	}

	/**
	 * reverse a step
	 */
	public void reverse() {
		if (this.myPlayer.getPathSize() > 0) {
			this.myPlayer.reverseStep();
			short[] curr = null;
			short[] prev = null;
			curr = this.getMyplayer().getPosition();
			prev = this.getMyplayer().getLast();
			moveObject(this.getTile(curr), this.getTile(prev));
			this.getMyplayer().setPosition(prev);
			for (Box b : this.getMyBox()) {
				curr = b.getPosition();
				prev = b.getLast();
				moveObject(this.getTile(curr), this.getTile(prev));
				b.setPosition(prev);
			}
			printMazeWithTileOnly();

		}
	}

	/**
	 * save map status
	 */
	public void saveAll() {
		for (Box b : this.getMyBox()) {
			b.saveCurr();
		}
		this.getMyplayer().saveCurr();
	}

	/**
	 * this method is use to move object from Start Tile to the Destination Tile
	 * 
	 * @param from
	 * @param to
	 */
	private void moveObject(Tile from, Tile to) {

		if (!from.equals(to)) {
			to.setStanding(from.getStanding()); // update map location
			from.setStanding(null); // remove old location
		}
	}

	/**
	 * a handy method to get Tile from pos *NOTE* it can be further edit to have
	 * animation
	 * 
	 * @param pos
	 * @return
	 */
	private Tile getTile(short[] pos) {
		return this.myMap[pos[0]][pos[1]];
	}

	/**
	 * A type of heuristic calculation *NOTE* Using Step remaining will be more
	 * accurate, in most situation (ie. sum of remaining step)
	 * 
	 * @return cost
	 */
	public int MisPlaceHeuristic() {
		int value = 0;
		Outter: for (Box b : this.myBox) {
			for (Goal g : this.myGoals) {
				if (b.getPosition()[0] == g.getPosition()[0] && b.getPosition()[1] == g.getPosition()[1])
					continue Outter;
			}
			value++;
		}
		return value;
	}

	/**
	 * A type of heuristic calculation
	 * 
	 * @return cost
	 */
	public int ManhattanDistanceHeuristic() {
		List<Short[]> goals = new ArrayList<>();
		List<Short[]> boxs = new ArrayList<>();
		int cost = 0;
		for (Goal g : this.myGoals) {
			Short[] i = { g.getPosition()[0], g.getPosition()[1] };
			goals.add(i);
		}
		for (Box b : this.myBox) {
			Short[] i = { b.getPosition()[0], b.getPosition()[1] };
			boxs.add(i);
		}
		while (!goals.isEmpty()) {
			int step = 999;
			int number_goal = -1;
			int number_box = -1;
			for (int i = 0; i < goals.size(); i++) {
				for (int j = 0; j < boxs.size(); j++) {
					Short[] g = goals.get(i);
					Short[] b = boxs.get(j);
					int dis = Math.abs(g[0] - b[0]) + Math.abs(g[1] - b[1]);
					if (dis < step) {
						step = dis;
						number_goal = i;
						number_box = j;
					}
				}
			}
			cost += step;
			goals.remove(number_goal);
			boxs.remove(number_box);
		}
		return cost;
	}

	// Getters and Setters
	public short[] getMySize() {
		return mySize;
	}

	public void setMySize(short[] mySize) {
		this.mySize = mySize;
	}

	public Tile[][] getMyMap() {
		return myMap;
	}

	public void setMyMap(Tile[][] myMap) {
		this.myMap = myMap;
	}

	public Vector<Box> getMyBox() {
		return myBox;
	}

	public void setMyBox(Vector<Box> myBox) {
		this.myBox = myBox;
	}

	public Vector<Goal> getMyGoals() {
		return myGoals;
	}

	public void setMyGoals(Vector<Goal> goals) {
		this.myGoals = goals;
	}

	public Player getMyplayer() {
		return myPlayer;
	}

	public void setMyplayer(Player myplayer) {
		this.myPlayer = myplayer;
	}

	public short getMaxBorder() {
		if (this.getMySize()[0] > this.getMySize()[1]) {
			this.tileImagelength = 900 / this.getMySize()[0];
			return this.getMySize()[0];
		} else {
			this.tileImagelength = 900 / this.getMySize()[1];
			return this.getMySize()[1];
		}
	}

	public void setDrawSetting() {
		if (this.getMySize()[0] == this.getMaxBorder() && this.getMySize()[1] != this.getMaxBorder()) {
			this.drawSetting[1] = 450 - (this.getMySize()[1] / 2 * this.tileImagelength);
			this.drawSetting[0] = 0;
		} else if (this.getMySize()[1] == this.getMaxBorder() && this.getMySize()[0] != this.getMaxBorder()) {
			this.drawSetting[0] = 450 - (this.getMySize()[0] / 2 * this.tileImagelength);
			this.drawSetting[1] = 0;
		} else {
			this.drawSetting[0] = 0;
			this.drawSetting[1] = 0;
		}
	}

	public int[] getDrawSetting() {
		return this.drawSetting;
	}

	public void setBackground(boolean background) {
		this.background = background;
	}

	public int getTileImagelength() {
		return this.tileImagelength;
	}

	public BufferedImage getmazemap() {
		BufferedImage bi = this.entireMaze;
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	@Override
	public int getTransparency() {
		return OPAQUE;
	}
}
