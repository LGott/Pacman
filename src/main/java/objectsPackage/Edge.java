package objectsPackage;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Edge {

	public Edge(Vec2 vec1, Vec2 vec2, World world) {

		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;

		// define shape of the body.
		PolygonShape ps = new PolygonShape();
		ps.setAsEdge(vec1, vec2);

		// define fixture of the body.
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0f;
		fd.friction = 0f;
		fd.restitution = 0f;
//		fd.filter.categoryBits=-1;
//		fd.filter.maskBits= -1;
//		fd.filter.groupIndex = -1;
	//	fixture = body.createFixture(fd);
		// create the body and add fixture to it
		Body body = world.createBody(bd);
		body.createFixture(fd);
	}
}