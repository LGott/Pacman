package groupProject.pacman;

import java.awt.Color;
import java.awt.Graphics;

public class Pacman extends MovablePiece {

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillArc(100, 100, 50, 50, 25, 40);
	}

}
