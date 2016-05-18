package mainPackage;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class WorldLogic extends World {

	public WorldLogic(Vec2 gravity, boolean doSleep) {
		super(gravity, doSleep);
	}

	/*public void createBodyAndFixture(int x, int y, BodyType bodyType,
			Shape shape) {
		BodyDef bd = new BodyDef();
		// set position to x and y coordinates
		bd.position.set(x, y);
		bd.type = bodyType;

		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 0f;
		fd.friction = 0f;
		fd.restitution = 0f;

		createBody(bd).createFixture(fd);
	}*/
}