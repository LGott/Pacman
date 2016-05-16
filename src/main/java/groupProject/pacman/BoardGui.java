package groupProject.pacman;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.google.inject.Inject;
import com.google.inject.Singleton;

public class BoardGui extends JFrame {
	/*
	 * it will hold all gui components and an instance of the logic class
	 */

	private GameLogic gameLogic;
	private Maze maze;
	private ScorePanel scorePanel;

	@Inject
	public BoardGui(Maze maze) {
		setSize(600, 600);
		setTitle("Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.maze = maze;
		add(maze, BorderLayout.CENTER);
		getContentPane();
	}
}
