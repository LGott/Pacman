package pacmanComponents;

import org.jbox2d.dynamics.World;

public class BonusPellet extends Pellet {
	private final static String imgName = "/cherries.png";
	private final static String description = "BONUS_PELLET";

	public BonusPellet(int posX, int posY, World world, int radius) {
		super(posX, posY, world, description, imgName, radius);
	}

}
