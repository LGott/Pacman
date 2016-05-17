package groupProject.pacman;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.google.inject.Inject;
import com.google.inject.Singleton;

public class BoardGui extends JFrame implements KeyListener {
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

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case (KeyEvent.VK_UP): {
			maze.setLabelIcon(maze.getLabel1(), "blackSquare");
			maze.setLabelIcon(maze.getLabel2(), "pacman");
			break;
		}
		case (KeyEvent.VK_DOWN): {
			break;
		}
		case (KeyEvent.VK_LEFT): {
			break;
		}
		case (KeyEvent.VK_RIGHT): {
			break;
		}
		}
	}
}
