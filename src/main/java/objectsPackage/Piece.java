package objectsPackage;

import javafx.scene.Node;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class Piece{
	private int posX;
	private int posY;
	private World world;
	private Fixture fixture;
	private static int lastID = 0;
	private int id;
	protected UniqueObject objectDescription;
	protected Body body;
	protected Node node;
	public UniqueObject getObjectDescription() {
		return objectDescription;
	}

	public Piece(int posX, int posY, World world, String description) {
		this.posX = posX;
		this.posY = posY;
		this.world = world;
		this.id = ++lastID;
		objectDescription=new UniqueObject(this.id, description);
		
		
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


		Body body = new Body(bd, world);
		body= world.createBody(bd);
		fixture = body.createFixture(fd);

		return body;
	} public int getPosX() {
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

	public Fixture getFixture(){
		return fixture;
	}

	public void setUserData() {
		// TODO Auto-generated method stub
		body.setUserData(objectDescription);
	}

	public void setUserData(String newDescription) {
		// TODO Auto-generated method stub
		objectDescription=new UniqueObject(this.id, newDescription);
	}
	
	public Node getNode() {
		return node;
	}
}