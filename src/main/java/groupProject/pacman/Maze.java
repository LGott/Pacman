package groupProject.pacman;

import java.awt.Graphics;
import javax.swing.JComponent;

import com.google.inject.Inject;

public class Maze extends JComponent {
	private Pacman pacman1;
	private Pacman pacman2;

	@Inject
	public Maze(Pacman pacman1) {
		this.pacman1 = pacman1;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("IN GRAPHICS");
		pacman1.draw(getGraphics());
		repaint();
	}
}
