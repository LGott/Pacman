package groupProject.pacman;

import javax.swing.JFrame;

public class BoardGui extends JFrame {
	/*
	 * it will hold all gui components and an instance of the logic class
	 */

	private GameLogic gameLogic;
	private Pacman pacman1;
	private Pacman pacman2;

	public BoardGui() {
		setSize(600, 600);
		setTitle("Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
