package groupProject.pacman;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.google.inject.Inject;
import com.google.inject.Singleton;

public class BoardGui extends JFrame {
	/*
	 * it will hold all gui components and an instance of the logic class
	 */

	private MazeGui maze;
	private GameLogic gameLogic;
	private ScorePanel scorePanel;

	@Inject
	public BoardGui(MazeGui maze, GameLogic gameLogic, ScorePanel scorePanel) {
		setSize(600, 600);
		setTitle("Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		this.maze = maze;
		this.gameLogic = gameLogic;
		this.scorePanel = scorePanel;

		add(maze, BorderLayout.CENTER);
		add(scorePanel, BorderLayout.SOUTH);
		getContentPane();
	}
}
