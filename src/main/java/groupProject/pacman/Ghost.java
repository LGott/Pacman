package groupProject.pacman;

import java.awt.Graphics;

public class Ghost extends MazeTile {

	
	public Ghost(String img){
		super();
		setIcon(getClass().getResource(img).toString());
	}
}
