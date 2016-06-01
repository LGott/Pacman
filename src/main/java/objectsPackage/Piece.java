package objectsPackage;

import javafx.scene.Node;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class Piece {
	private int posX;
	private int posY;
	protected World world;
	protected Fixture fixture;
	private static int lastID = 0;
	protected int id;
	private UniqueObject objectDescription;
	protected Body body;
	protected Node node;
	protected FixtureDef fd;
	protected BodyDef bd;

	public UniqueObject getObjectDescription() {
		return objectDescription;
	}

	public Piece(int posX, int posY, World world, String description) {
		this.posX = posX;
		this.posY = posY;
		this.world = world;
		this.id = ++lastID;
		this.objectDescription = new UniqueObject(this.id, description);
		this.fd = new FixtureDef();

	}

	public Body createBodyAndFixture(BodyType bodyType, Shape shape, int maskBits, int groupIndex, int categoryBits) {

		// Create an JBox2D body definition
		bd = new BodyDef();
		// set position to x and y coordinates
		bd.position.set(posX, posY);
		// bd.position.set(posX, posY);
		bd.type = bodyType;

		// Create a fixture

		fd.shape = shape;
		fd.density = 0f;
		fd.friction = 0f;
		fd.restitution = 0f;
		fd.filter.categoryBits = -categoryBits;
		fd.filter.maskBits = maskBits;
		fd.filter.groupIndex = groupIndex;
		body = world.createBody(bd);
		fixture = body.createFixture(fd);

		return body;
	}

	public int getPosX() {
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

	public Fixture getFixture() {
		return fixture;
	}

	public void setUserData() {
		body.setUserData(objectDescription);
	}

	public void setUserData(String newDescription) {
		objectDescription = new UniqueObject(this.id, newDescription);
	}

	public Node getNode() {
		return node;
	}
}