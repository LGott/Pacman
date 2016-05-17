package groupProject.pacman;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.google.inject.Inject;

public class MazeGui extends JPanel {
	private Pacman pacman1, pacman2;
	private Ghost redGhost, pinkGhost, orangeGhost, blueGhost;
	private ArrayList<Pellet> pellets;
	private BonusPellet bonus1, bonus2;
	private MazeTile[][] maze;

	@Inject
	public MazeGui(Pacman pacman1, Pacman pacman2, Ghost redGhost, Ghost pinkGhost, Ghost orangeGhost, Ghost blueGhost,
			ArrayList<Pellet> pellets, BonusPellet bonus1, BonusPellet bonus2) {
		
		this.pacman1 = pacman1;
		this.pacman2 = pacman2;
		this.redGhost = redGhost;
		this.pinkGhost = pinkGhost;
		this.orangeGhost = orangeGhost;
		this.blueGhost = blueGhost;
		this.pellets = pellets;
		this.bonus1 = bonus1;
		this.bonus2 = bonus2;
		this.maze = new MazeTile[15][15];
		
		setComponents();
	}

	private void setComponents(){
		setLayout(new GridLayout(15,15));
		//TODO
	}
	
	private void initializeMaze(){
		//TODO - Set up 2D matrix with specified MazeTiles 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("IN GRAPHICS");
		pacman1.draw(getGraphics());
		repaint();
	}
}
