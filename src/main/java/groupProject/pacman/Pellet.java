package groupProject.pacman;

import javax.swing.ImageIcon;

public class Pellet extends MazeTile {

	public Pellet(){
		super();
		setIcon(getClass().getResource("/resources/pellet.png").toString());
	}
}