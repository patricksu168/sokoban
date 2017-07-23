/**
 * Main class to set up JFrame
 */
package gameMain;

import javax.swing.JFrame;

import gameMain.GamePanel;

/**
 * entrance point of the game
 *
 */
public class MazeGame {

	public static void main(String[] args) {
		JFrame window = new JFrame("Maze Game");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);

	}
}
