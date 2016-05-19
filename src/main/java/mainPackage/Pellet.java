package mainPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Pellet extends Piece{
	private Node node;
	// radius in pixels
	private final int radius;
	private final BodyType bodyType = BodyType.STATIC;

	public Pellet(int posX, int posY, World world, int radius) {
		super(posX, posY, world);
		this.radius = radius;
		node = create();		
	}

	private Node create() {
		Circle pellet = new Circle();
		pellet.setRadius(radius);
		Image img = new Image("/pellet.png");
		ImagePattern imagePattern = new ImagePattern(img);
		pellet.setFill(imagePattern);

		pellet.setLayoutX(Properties.toPixelPosX(getPosX()));
		pellet.setLayoutY(Properties.toPixelPosY(getPosY()));
		pellet.setCache(true); // Cache this object for better performance

		// create a jbox2D circle shape
		CircleShape cs = new CircleShape();
		cs.m_radius = radius * 0.1f; // We need to convert radius to JBox2D
										// equivalent
		createBodyAndFixture(bodyType, cs);

		return pellet;
	}

	public Node getNode() {
		return node;
	}
}