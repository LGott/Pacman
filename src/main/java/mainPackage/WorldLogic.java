package mainPackage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class WorldLogic extends World {

	public WorldLogic(Vec2 gravity, boolean doSleep) {
		super(gravity, doSleep);
	}
}