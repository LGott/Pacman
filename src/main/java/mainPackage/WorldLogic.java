package mainPackage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.google.inject.Singleton;

@Singleton
public class WorldLogic extends World {

	public WorldLogic(Vec2 gravity, boolean doSleep) {
		super(gravity, doSleep);
	}
}