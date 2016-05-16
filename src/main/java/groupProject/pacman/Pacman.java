package groupProject.pacman;

import java.awt.Color;
import java.awt.Graphics;

public class Pacman extends MovablePiece {

	@Override
	public void draw(Graphics g) {
		System.out.println("before draw");
		g.setColor(Color.YELLOW);
		g.fillOval(50, 50, 100, 100);
		g.fillArc(100, 100, 50, 50, 25, 40);
		System.out.println("after draw");
	}

}
