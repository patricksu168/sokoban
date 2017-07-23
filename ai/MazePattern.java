package ai;

import java.util.List;

import gameElement.gameObject.Box;
import gameElement.gameObject.Player;

/**
 * a class the use in the BFS AI search, which save the state of the Maze it is
 * use for having pattern match for the game which improve the performance a
 * little bit
 */
public class MazePattern implements Comparable<MazePattern> {
	private Player myplayer;
	private List<Box> myBox;
	private int myHCost;
	private int myGCost;

	public MazePattern(List<Box> boxs, Player player, int Gcost, int Hcost) {
		this.myplayer = player;
		this.myBox = boxs;
		this.myHCost = Hcost;
		this.myGCost = Gcost;
	}

	public Player getMyplayer() {
		return myplayer;
	}

	public void setMyplayer(Player myplayer) {
		this.myplayer = myplayer;
	}

	public List<Box> getMyBox() {
		return myBox;
	}

	public void setMyBox(List<Box> myBox) {
		this.myBox = myBox;
	}

	@Override
	public int compareTo(MazePattern o) {
		// TODO Auto-generated method stub
		int myCost = this.myGCost + this.myHCost;
		int oCost = o.myGCost + o.myHCost;
		if (myCost < oCost)
			return -1;
		else if (myCost == oCost)
			return 0;
		else
			return 1;
	}

	public boolean equals(Object o) {
		if (o instanceof MazePattern) {
			List<Box> obox = ((MazePattern) o).getMyBox();
			Player oplayer = ((MazePattern) o).getMyplayer();

			if (this.myplayer.equals(oplayer)) {
				for (Box b : obox) {

					// check if any box not inside myBoxList
					if (!this.myBox.contains(b)) {
						return false;
					}
				}
				// if all contain
				return true;
			}
		}
		return false;
	}
}
