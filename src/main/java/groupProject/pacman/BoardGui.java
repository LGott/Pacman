package groupProject.pacman;

import java.awt.Graphics;

import javax.swing.JFrame;

import com.google.inject.Inject;

public class BoardGui extends JFrame {
	/*
	 * it will hold all gui components and an instance of the logic class
	 */

	private GameLogic gameLogic;
	private Pacman pacman1;
	private Pacman pacman2;

	public BoardGui(Pacman pacman1) {
		setSize(600, 600);
		setTitle("Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pacman1 = pacman1;
		repaint();
	}
	
	public void draw(Graphics g){
		System.out.println("IN GRAPHICS");
		pacman1.draw(g);
	}
}
