package mazeGenerator;

import java.util.Random;
import java.util.Vector;

import gameElement.floorObject.Goal;
import gameElement.floorObject.PassWay;
import gameElement.floorObject.Wall;
import gameElement.gameObject.Box;
import gameElement.gameObject.Player;
import maze.Maze;
import maze.Tile;

public class MazeGeneratorTwo implements MazeGenerator {

	public Maze generate() {
		Maze m = new Maze();
		Random rand = new Random();
		short length = (short) (rand.nextInt(12) + 6);
		short width = (short) (rand.nextInt(12) + 6);
		short count, counter;

		short mapSize[] = { (short) length, (short) width };
		Tile[][] map = new Tile[mapSize[0]][mapSize[1]];
		for (count = 0; count < width; count++) {
			for (counter = 0; counter < length; counter++) {
				map[counter][count] = new Tile();
			}
		}
		m.setMySize(mapSize);
		m.setMyMap(map);

		short x, y;
		int i, j;
		short[][] vacancy = new short[mapSize[0]][mapSize[1]];
		short boxNum = (short) (Math.max(length, width) / 3);
		short goalXPos[] = new short[boxNum];
		short goalYPos[] = new short[boxNum];
		boolean valid = false;
		for (count = 0; count < boxNum; count++) {
			valid = false;
			while (valid == false) {
				// flag = 0;
				x = (short) (rand.nextInt(length - 3) + 1);
				y = (short) (rand.nextInt(width - 3) + 1);
				goalXPos[count] = x;
				goalYPos[count] = y;
				if (vacancy[x][y] != 1) {
					valid = true;
					for (j = -1; j < 2; j++) {
						for (i = -1; i < 2; i++) {
							if (vacancy[x + i][y + j] != 1) {
								vacancy[x + i][y + j] = 2;
							}
						}
					}
					vacancy[x][y] = 1;
				}
			}
		}

		int movement = 0;
		int oldMovement = 0;
		int firstMove = 0;

		// number of moves in the same direction before changing direction
		short boxXPos[] = new short[boxNum];
		short boxYPos[] = new short[boxNum];
		int direction;
		for (count = 0; count < boxNum; count++) {
			direction = getDirection(goalXPos[count], goalYPos[count], length, width);
			x = goalXPos[count];
			y = goalYPos[count];
			firstMove = 0;
			if (direction == 0) {
				while (x > 2 && y > 2) {
					movement = rand.nextInt(2);
					if (vacancy[x - 1][y] == 1 && vacancy[x][y - 1] == 1) {
						if (vacancy[x + 1][y] == 1) {
							y++;
						} else {
							x++;
						}
						break;
					} else if (vacancy[x - 1][y] == 1) {
						y--;
					} else if (vacancy[x][y - 1] == 1) {
						x--;
					} else {
						if (movement == 0) {
							x--;
						} else {
							y--;
						}
					}
					if (oldMovement != movement && firstMove == 1) {
						if (movement == 0) {
							if (vacancy[x - 1][y - 2] != 1) {
								vacancy[x - 1][y - 2] = 2;
							}
							if (vacancy[x][y - 2] != 1) {
								vacancy[x][y - 2] = 2;
							}
						} else {
							if (vacancy[x - 1][y] != 1) {
								vacancy[x - 1][y] = 2;
							}
							if (vacancy[x - 1][y - 1] != 1) {
								vacancy[x - 1][y - 1] = 2;
							}

						}
					}
					if (vacancy[x][y] == 0) {
						vacancy[x][y] = 2;
					}
					oldMovement = movement;
					firstMove = 1;
					for (counter = 0; counter < boxNum; counter++) {
						if (goalXPos[counter] == x && goalYPos[counter] == y) {
							vacancy[x][y] = 1;
							while (vacancy[x][y] == 1) {
								if (x == 2) {
									y--;
								} else {
									x++;
								}
								// vacancy[x][y] = 1;
							}
							break;
						}
					}
				}
			} else if (direction == 2) {
				while (x < length - 3 && y > 2) {
					movement = rand.nextInt(2);
					if (vacancy[x + 1][y] == 1 && vacancy[x][y - 1] == 1) {
						if (vacancy[x][y + 1] == 1) {
							x--;
						} else {
							y++;
						}
						break;
					} else if (vacancy[x + 1][y] == 1) {
						y--;
					} else if (vacancy[x][y - 1] == 1) {
						x++;
					} else {
						if (movement == 0) {
							x++;
						} else {
							y--;
						}
					}
					if (oldMovement != movement && firstMove == 1) {
						if (movement == 0) {
							if (vacancy[x - 1][y - 1] != 1) {
								vacancy[x - 1][y - 1] = 2;
							}
							if (vacancy[x][y - 1] != 1) {
								vacancy[x][y - 1] = 2;
							}
						} else {
							if (vacancy[x + 1][y] != 1) {
								vacancy[x + 1][y] = 2;
							}
							if (vacancy[x + 1][y + 1] != 1) {
								vacancy[x + 1][y + 1] = 2;
							}

						}
					}
					if (vacancy[x][y] == 0) {
						vacancy[x][y] = 2;
					}
					oldMovement = movement;
					firstMove = 1;
				}
				for (counter = 0; counter < boxNum; counter++) {
					if (goalXPos[counter] == x && goalYPos[counter] == y) {
						vacancy[x][y] = 1;
						while (vacancy[x][y] == 1) {
							if (x == length - 3) {
								y++;
							} else {
								x--;
							}
							// vacancy[x][y] = 1;
						}
						break;
					}
				}

			} else if (direction == 3) {
				while (x > 2 && y < width - 3) {
					movement = rand.nextInt(2);
					if (vacancy[x - 1][y] == 1 && vacancy[x][y + 1] == 1) {
						if (vacancy[x][y - 1] == 1) {
							x++;
						} else {
							y--;
						}
						break;
					} else if (vacancy[x - 1][y] == 1) {
						y++;
					} else if (vacancy[x][y + 1] == 1) {
						x--;
					} else {
						if (movement == 0) {
							x--;
						} else {
							y++;
						}
					}
					if (oldMovement != movement && firstMove == 1) {
						if (movement == 0) {
							if (vacancy[x][y + 1] != 1) {
								vacancy[x][y + 1] = 2;
							}
							if (vacancy[x + 1][y + 1] != 1) {
								vacancy[x + 1][y + 1] = 2;
							}
						} else {
							if (vacancy[x - 1][y - 1] != 1) {
								vacancy[x - 1][y - 1] = 2;
							}
							if (vacancy[x - 1][y] != 1) {
								vacancy[x - 1][y] = 2;
							}

						}
					}
					if (vacancy[x][y] == 0) {
						vacancy[x][y] = 2;
					}
					oldMovement = movement;
					firstMove = 1;
				}
				for (counter = 0; counter < boxNum; counter++) {
					if (goalXPos[counter] == x && goalYPos[counter] == y) {
						vacancy[x][y] = 1;
						while (vacancy[x][y] == 1) {
							if (x == 2) {
								y--;
							} else {
								x++;
							}
							// vacancy[x][y] = 1;
						}
						break;
					}
				}
			} else if (direction == 5) {
				while (x < length - 3 && y < width - 3) {
					movement = rand.nextInt(2);

					if (vacancy[x + 1][y] == 1 && vacancy[x][y + 1] == 1) {
						if (vacancy[x][y - 1] == 1) {
							x--;
						} else {
							y--;
						}
						break;
					} else if (vacancy[x + 1][y] == 1) {
						y++;
					} else if (vacancy[x][y + 1] == 1) {
						x++;
					} else {
						if (movement == 0) {
							x++;
						} else {
							y++;
						}
					}
					if (oldMovement != movement && firstMove == 1) {
						if (movement == 0) {
							if (vacancy[x][y + 2] != 1) {
								vacancy[x][y + 2] = 2;
							}
							if (vacancy[x + 1][y + 2] != 1) {
								vacancy[x + 1][y + 2] = 2;
							}
						} else {
							if (vacancy[x + 1][y - 1] != 1) {
								vacancy[x + 1][y - 1] = 2;
							}
							if (vacancy[x + 1][y] != 1) {
								vacancy[x + 1][y] = 2;
							}

						}
					}
					if (vacancy[x][y] == 0) {
						vacancy[x][y] = 2;
					}
					oldMovement = movement;
					firstMove = 1;
				}

				for (counter = 0; counter < boxNum; counter++) {
					if (goalXPos[counter] == x && goalYPos[counter] == y) {
						vacancy[x][y] = 1;
						while (vacancy[x][y] == 1) {
							if (x == length - 3) {
								y--;
							} else {
								x--;
							}
							// vacancy[x][y] = 1;
						}
						break;
					}
				}
			}
			boxXPos[count] = x;
			boxYPos[count] = y;

			for (j = -1; j < 2; j++) {
				for (i = -1; i < 2; i++) {
					if (vacancy[x + i][y + j] != 1) {
						vacancy[x + i][y + j] = 2;
					}
				}
			}
			vacancy[x][y] = 1;
		}

		valid = false;
		short playerX = 0;
		short playerY = 0;
		while (valid == false) {
			playerX = (short) (rand.nextInt(length - 3) + 1);
			playerY = (short) (rand.nextInt(width - 3) + 1);
			if (vacancy[playerX][playerY] != 1) {
				vacancy[playerX][playerY] = 1;
				valid = true;
				break;
			}
		}
		Player p = new Player((short) playerX, (short) playerY);
		x = playerX;
		y = playerY;
		int a = boxXPos[0];
		int c = boxYPos[0];

		direction = decideDirection(a, c, x, y);

		while (x != a || y != c) {
			movement = rand.nextInt(2);
			if (direction == 0) {
				if (y == c) {
					x--;
				} else if (x == a) {
					y--;
				} else if (movement == 0) {
					x--;
				} else if (movement == 1) {
					y--;
				}
			} else if (direction == 2) {
				if (y == c) {
					x++;
				} else if (x == a) {
					y--;
				} else if (movement == 0) {
					x++;
				} else if (movement == 1) {
					y--;
				}
			} else if (direction == 3) {

				if (y == c) {
					x--;
				} else if (x == a) {
					y++;
				} else if (movement == 0) {
					x--;
				} else if (movement == 1) {
					y++;
				}
			} else if (direction == 5) {
				if (y == c) {
					x++;
				} else if (x == a) {
					y++;
				} else if (movement == 0) {
					x++;
				} else if (movement == 1) {
					y++;
				}
			} else if (direction == 1) {
				y--;
			} else if (direction == 4) {
				y++;
			} else if (direction == 6) {
				x++;
			} else if (direction == 7) {
				x--;
			}
			if (x >= length || y >= width) {

			}
			vacancy[x][y] = 2;
		}
		int walls = 0;

		// calulate the amount of available space to place inner walls
		for (y = 1; y < width - 1; y++) {
			for (x = 1; x < length - 1; x++) {
				if (vacancy[x][y] == 0) {
					walls++;
				}
			}
		}
		short wallXPos[] = new short[walls];
		short wallYPos[] = new short[walls];
		count = 0;
		for (y = 1; y < width - 1; y++) {
			for (x = 1; x < length - 1; x++) {
				if (vacancy[x][y] == 0) {
					wallXPos[count] = x;
					wallYPos[count] = y;
					count++;
				}
			}
		}

		Vector<Box> box_list = new Vector<Box>();
		Vector<Goal> goal_list = new Vector<Goal>();

		for (y = 0; y < width; y++) {
			for (x = 0; x < length; x++) {
				if (y == 0 || y == width - 1) {
					m.getMyMap()[x][y].setMyFloor(new Wall());
				} else if (x == 0 || x == length - 1) {
					m.getMyMap()[x][y].setMyFloor(new Wall());
				} else {
					m.getMyMap()[x][y].setMyFloor(new PassWay());
					for (count = 0; count < boxNum; count++) {
						if (x == boxXPos[count] && y == boxYPos[count]) {
							Box b = new Box((short) x, (short) y);
							m.getMyMap()[x][y].setStanding(b);
							box_list.add(b);
						} else if (x == goalXPos[count] && y == goalYPos[count]) {
							Goal g = new Goal((short) x, (short) y);
							m.getMyMap()[x][y].setMyFloor(g);
							goal_list.add(g);
						}
					}
					for (count = 0; count < walls; count++) {
						if (x == wallXPos[count] && y == wallYPos[count]) {
							m.getMyMap()[x][y].setMyFloor(new Wall());
						}
					}
					if (x == playerX && y == playerY) {
						m.getMyMap()[x][y].setStanding(p);
						m.setMyplayer(p);
					}
				}
			}
		}
		m.setMyBox(box_list);
		m.setMyGoals(goal_list);

		return m;
	}

	/**
	 * return direction to which the box will be pushed from goal to spawn point
	 * direction: 0: top-left 1: top 2: top-right 3: bottom left 4: bottom 5:
	 * bottom-right 6: right 7: left
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static int getDirection(int x, int y, int length, int width) {
		Random r = new Random();
		int d;

		if (length % 2 == 1 && width % 2 == 1) {
			if (x == length / 2 && y == width / 2) {
				int[] directions = { 0, 2, 3, 5 };
				d = r.nextInt(4);
				return directions[d];
			}
		}
		if (length % 2 == 1) {
			d = r.nextInt(2);
			if (x == length / 2 && y < width / 2) {
				if (d == 0) {
					return 3;
				} else {
					return 5;
				}
			} else if (x == length / 2 && y >= width / 2) {
				if (d == 0) {
					return 0;
				} else {
					return 2;
				}
			}
		}
		if (width % 2 == 1) {
			d = r.nextInt(2);
			if (y == width / 2 && x < length / 2) {
				if (d == 0) {
					return 2;
				} else {
					return 5;
				}
			} else if (y == width / 2 && x >= length / 2) {
				if (d == 0) {
					return 0;
				} else {
					return 3;
				}
			}
		}
		if (x < length / 2) {
			if (y < width / 2) {
				return 5;
			} else {
				return 2;
			}
		} else {
			if (y < width / 2) {
				return 3;
			} else {
				return 0;
			}
		}
	}

	/**
	 * A decision switch
	 * 
	 * @param x
	 * @param y
	 * @param a
	 * @param b
	 * @return
	 */
	private static int decideDirection(int x, int y, int a, int b) {
		if (x > a && y > b) {
			return 5;
		} else if (x < a && y < b) {
			return 0;
		} else if (x > a && y < b) {
			return 2;
		} else if (x == a && y < b) {
			return 1;
		} else if (x == a && y > b) {
			return 4;
		} else if (x > a && y == b) {
			return 6;
		} else if (x < a && y == b) {
			return 7;
		} else {
			return 3;
		}
	}
}
