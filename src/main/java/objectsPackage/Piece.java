package objectsPackage;

import javafx.scene.Node;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class Piece{
	private int posX;
	private int posY;
	private World world;

	public Piece(int posX, int posY, World world) {
		this.posX = posX;
		this.posY = posY;
		this.world = world;
	}

	abstract Node getNode();

	public Body createBodyAndFixture2(BodyType bodyType, Shape shape, float width, float height) {

		// Create an JBox2D body definition
		BodyDef bd = new BodyDef();
		// set position to x and y coordinates
		bd.position.set(posX + width / 2, posY + height / 2);
		//bd.position.set(posX, posY);
		bd.type = bodyType;

		// Create a fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 0f;
		fd.friction = 0f;
		fd.restitution = 0f;

		Body body = world.createBody(bd);
		body.createFixture(fd);
		return body;
	}
	public Body createBodyAndFixture(BodyType bodyType, Shape shape) {

		// Create an JBox2D body definition
		BodyDef bd = new BodyDef();
		// set position to x and y coordinates
		bd.position.set(posX, posY);
		//bd.position.set(posX, posY);
		bd.type = bodyType;

		// Create a fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 0f;
		fd.friction = 0f;
		fd.restitution = 0f;

		Body body = world.createBody(bd);
		body.createFixture(fd);
		return body;
	} int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}