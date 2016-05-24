package objectsPackage;

import org.jbox2d.dynamics.World;

public class BonusPellet extends Pellet{

	public BonusPellet(int posX, int posY, World world) {
		super(posX, posY, world, 15, "BONUS_PELLET");
		
		
	}
}
