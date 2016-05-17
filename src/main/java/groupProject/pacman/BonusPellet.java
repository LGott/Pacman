package groupProject.pacman;

import javax.swing.ImageIcon;

public class BonusPellet extends Pellet{

	public BonusPellet(){
		super();
		setIcon(getClass().getResource("/resources/cherries.png").toString());
	}
}
