package pacmanComponents;

import org.jbox2d.dynamics.World;

public class YellowPellet extends Pellet {

	private final static String imgName = "/pellet.png";
	private final static String description = "PELLET";

	public YellowPellet(int posX, int posY, World world) {
		super(posX, posY, world, description, imgName);
	}
}