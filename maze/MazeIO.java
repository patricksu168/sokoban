package maze;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import gameElement.floorObject.*;
import gameElement.gameObject.*;

/**
 * class that handle the IO of all maze including the game database as well as
 * creative mode product into game file
 *
 */
public class MazeIO {
	private static final String FILE = "res/MapDataBase";

	/**
	 * get the number of level in the database
	 * 
	 * @return
	 */
	public static int getNumberOfMaze() {
		Scanner sc = null;
		int number = 0;
		try {
			sc = new Scanner(new FileReader(MazeIO.FILE));
		} catch (FileNotFoundException e) {
			System.out.println("can't open file :" + MazeIO.FILE);
		} finally {
			String line;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.startsWith("maze")) {
					number++;
				} else {
					continue;
				}
			}
		}
		return number;
	}

	/**
	 * read the level out from the database with given id
	 * 
	 * @param ID
	 * @return Maze
	 */
	public static Maze getMaze(int ID) {
		Maze m = null;

		Scanner sc = null;
		String line = null;
		String Maze_String = "maze " + ID + "$";
		boolean isFound = false;
		try {
			sc = new Scanner(new FileReader(MazeIO.FILE));
		} catch (FileNotFoundException e) {
			System.out.println("can't open file :" + MazeIO.FILE);
		} finally {
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				String[] parts = line.split(" ");
				// Find the maze that looking at
				if (line.startsWith(Maze_String) || isFound) {
					if (line.startsWith(Maze_String)) {
						isFound = true;
						m = new Maze();

						System.out.println("Maze Found:\n" + line);
					} else if (line.startsWith("size")) {

						short[] size = { Short.parseShort(parts[1]), Short.parseShort(parts[2]) };
						Tile[][] map = new Tile[size[0]][size[1]];
						m.setMySize(size);
						m.setMyMap(map);

					} else if (line.startsWith("map")) {
						String[] tile_codes_list = parts[1].split(",");
						for (int Y = 0; Y < m.getMySize()[1]; Y++) {
							for (int X = 0; X < m.getMySize()[0]; X++) {

								Tile temp = new Tile();
								if (Short.parseShort(tile_codes_list[(Y * m.getMySize()[0] + X)]) == Tile.WALL) {
									temp.setMyFloor(new Wall());
								} else {
									temp.setMyFloor(new PassWay());
								}
								m.getMyMap()[X][Y] = temp;
							}
						}

					} else if (line.startsWith("box")) {
						Vector<Box> box_list = new Vector<Box>();
						String[] box_array = parts[1].split(",");

						for (int i = 0; i < box_array.length - 1; i += 2) {
							Box temp = new Box(Short.parseShort(box_array[i]), Short.parseShort(box_array[i + 1]));
							box_list.add(temp);
							m.getMyMap()[Short.parseShort(box_array[i])][Short.parseShort(box_array[i + 1])]
									.setStanding(temp);
						}

						m.setMyBox(box_list);

					} else if (line.startsWith("position")) {

						Vector<Goal> goal_list = new Vector<Goal>();
						String[] goal_array = parts[1].split(",");

						for (int i = 0; i < goal_array.length - 1; i += 2) {
							// Goal is not movable so not adding into
							// Tile.standing
							Goal temp = new Goal(Short.parseShort(goal_array[i]), Short.parseShort(goal_array[i + 1]));
							m.getMyMap()[Short.parseShort(goal_array[i])][Short.parseShort(goal_array[i + 1])]
									.setMyFloor(temp);
							goal_list.add(temp);
						}

						m.setMyGoals(goal_list);

					} else if (line.startsWith("player")) {
						String[] player_pos = parts[1].split(",");
						Player temp = new Player(Short.parseShort(player_pos[0]), Short.parseShort(player_pos[1]));
						m.setMyplayer(temp);
						m.getMyMap()[Short.parseShort(player_pos[0])][Short.parseShort(player_pos[1])]
								.setStanding(temp);
						isFound = false;
					}

				} else {
					// if not, goto next line
					continue;
				}

			}
			if (sc != null)
				sc.close();

		}

		if (m == null) {
			System.err.println("Cannot find " + Maze_String);
		}
		return m;
	}

	/**
	 * method that load a custom maze level
	 * 
	 * @param filename
	 * @return Maze
	 */
	public static Maze loadMaze(String filename) {
		String PATH = "res/out/";
		PATH += filename;
		Maze m = null;

		Scanner sc = null;
		String line = null;
		try {
			sc = new Scanner(new FileReader(PATH));
		} catch (FileNotFoundException e) {
			System.out.println("can't open file :" + filename);
		} finally {
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				String[] parts = line.split(" ");
				// Find the maze that looking at
				if (line.startsWith("maze")) {
					m = new Maze();

					System.out.println("Maze Found:\n" + line);
				} else if (line.startsWith("size")) {

					short[] size = { Short.parseShort(parts[1]), Short.parseShort(parts[2]) };
					Tile[][] map = new Tile[size[0]][size[1]];
					m.setMySize(size);
					m.setMyMap(map);

				} else if (line.startsWith("map")) {
					String[] tile_codes_list = parts[1].split(",");
					for (int Y = 0; Y < m.getMySize()[1]; Y++) {
						for (int X = 0; X < m.getMySize()[0]; X++) {

							Tile temp = new Tile();
							if (Short.parseShort(tile_codes_list[(Y * m.getMySize()[0] + X)]) == Tile.WALL) {
								temp.setMyFloor(new Wall());
							} else {
								temp.setMyFloor(new PassWay());
							}
							m.getMyMap()[X][Y] = temp;
						}
					}

				} else if (line.startsWith("box")) {
					Vector<Box> box_list = new Vector<Box>();
					String[] box_array = parts[1].split(",");

					for (int i = 0; i < box_array.length - 1; i += 2) {
						Box temp = new Box(Short.parseShort(box_array[i]), Short.parseShort(box_array[i + 1]));
						box_list.add(temp);
						m.getMyMap()[Short.parseShort(box_array[i])][Short.parseShort(box_array[i + 1])]
								.setStanding(temp);
					}

					m.setMyBox(box_list);

				} else if (line.startsWith("position")) {

					Vector<Goal> goal_list = new Vector<Goal>();
					String[] goal_array = parts[1].split(",");

					for (int i = 0; i < goal_array.length - 1; i += 2) {
						// Goal is not movable so not adding into Tile.standing
						Goal temp = new Goal(Short.parseShort(goal_array[i]), Short.parseShort(goal_array[i + 1]));
						m.getMyMap()[Short.parseShort(goal_array[i])][Short.parseShort(goal_array[i + 1])]
								.setMyFloor(temp);
						goal_list.add(temp);
					}

					m.setMyGoals(goal_list);

				} else if (line.startsWith("player")) {
					String[] player_pos = parts[1].split(",");
					Player temp = new Player(Short.parseShort(player_pos[0]), Short.parseShort(player_pos[1]));
					m.setMyplayer(temp);
					m.getMyMap()[Short.parseShort(player_pos[0])][Short.parseShort(player_pos[1])].setStanding(temp);
				}

			}
			if (sc != null)
				sc.close();

		}
		return m;
	}

	/**
	 * Save current map status into file or save created map into file
	 * 
	 * @param filename
	 * @param m
	 *            Maze
	 */
	public static void saveMaze(String filename, Maze m) {
		String name = "./res/out/" + filename;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss-SSS");
		Date dt = new Date();
		String timeStamp = sdf.format(dt);
		name += "_" + timeStamp;
		System.out.println(name);

		try (FileWriter fw = new FileWriter(name, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			boolean start_element = true;
			out.println();

			// print maze and id
			String temp = "maze 0$";
			out.println(temp);

			// print size line
			temp = "size " + m.getMySize()[0] + " " + m.getMySize()[1];
			out.println(temp);

			// print map line
			StringBuilder map = new StringBuilder();
			map.append("map ");
			start_element = true;
			for (int Y = 0; Y < m.getMySize()[1]; Y++) {
				for (int X = 0; X < m.getMySize()[0]; X++) {
					Tile t = m.getMyMap()[X][Y];
					if (start_element) {
						map.append(t.getMyFloor().getTextureID());
						start_element = false;
					} else {
						map.append("," + t.getMyFloor().getTextureID());
					}
				}
			}
			out.println(map.toString());

			// print box line
			StringBuilder box = new StringBuilder();
			box.append("box ");
			start_element = true;
			for (Box b : m.getMyBox()) {
				if (start_element) {
					box.append(b.getPosition()[0] + "," + b.getPosition()[1]);
					start_element = false;
				} else {
					box.append("," + b.getPosition()[0] + "," + b.getPosition()[1]);
				}
			}
			out.println(box);

			// print position line
			StringBuilder pos = new StringBuilder();
			pos.append("position ");
			start_element = true;
			for (Goal g : m.getMyGoals()) {
				if (start_element) {
					pos.append(g.getPosition()[0] + "," + g.getPosition()[1]);
					start_element = false;
				} else {
					pos.append("," + g.getPosition()[0] + "," + g.getPosition()[1]);
				}
			}
			out.println(pos);

			// print player line
			temp = "player " + m.getMyplayer().getPosition()[0] + "," + m.getMyplayer().getPosition()[1];
			out.println(temp);

			// end
			out.close();
			bw.close();

		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}

	}

	/**
	 * save Maze with given id into database
	 * 
	 * @param ID
	 * @param m
	 *            Maze
	 */
	public static void saveMaze(int ID, Maze m) {

		try (FileWriter fw = new FileWriter("res/MapDataBase", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			boolean start_element = true;
			// out.println();

			// print maze and id
			String temp = "maze " + ID + "$";
			out.println(temp);

			// print size line
			temp = "size " + m.getMySize()[0] + " " + m.getMySize()[1];
			out.println(temp);

			// print map line
			StringBuilder map = new StringBuilder();
			map.append("map ");
			start_element = true;
			for (int Y = 0; Y < m.getMySize()[1]; Y++) {
				for (int X = 0; X < m.getMySize()[0]; X++) {
					Tile t = m.getMyMap()[X][Y];
					if (start_element) {
						map.append(t.getMyFloor().getTextureID());
						start_element = false;
					} else {
						map.append("," + t.getMyFloor().getTextureID());
					}
				}
			}
			out.println(map.toString());

			// print box line
			StringBuilder box = new StringBuilder();
			box.append("box ");
			start_element = true;
			for (Box b : m.getMyBox()) {
				if (start_element) {
					box.append(b.getPosition()[0] + "," + b.getPosition()[1]);
					start_element = false;
				} else {
					box.append("," + b.getPosition()[0] + "," + b.getPosition()[1]);
				}
			}
			out.println(box);

			// print position line
			StringBuilder pos = new StringBuilder();
			pos.append("position ");
			start_element = true;
			for (Goal g : m.getMyGoals()) {
				if (start_element) {
					pos.append(g.getPosition()[0] + "," + g.getPosition()[1]);
					start_element = false;
				} else {
					pos.append("," + g.getPosition()[0] + "," + g.getPosition()[1]);
				}
			}
			out.println(pos);

			// print player line
			temp = "player " + m.getMyplayer().getPosition()[0] + "," + m.getMyplayer().getPosition()[1];
			out.println(temp);

			// end
			out.close();
			bw.close();

		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}
}
