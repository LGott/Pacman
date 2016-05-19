package objectsPackage;

import mainPackage.Properties;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Pacman extends Piece {
	private Node node;
	// radius in pixels
	private final int radius = 20;
	private final BodyType bodyType = BodyType.DYNAMIC;

	public Pacman(int posX, int posY, World world) {
		super(posX, posY, world);
		node = create();
	}

	// This method creates a pacman by using Circle object from JavaFX and
	// CircleShape from JBox2D
	private Node create() {
		// Create an UI for pacman - JavaFX code
		Circle pacman = new Circle();
		pacman.setRadius(radius);
		Image img = new Image("/pacman.png");
		ImagePattern imagePattern = new ImagePattern(img);
		pacman.setFill(imagePattern);

		// Set ball position on JavaFX scene. We need to convert JBox2D
		// coordinates to JavaFX coordinates which are in pixels.
		pacman.setLayoutX(Properties.toPixelPosX(getPosX()));
		pacman.setLayoutY(Properties.toPixelPosY(getPosY()));
		pacman.setCache(true); // Cache this object for better performance

		// create a jbox2D circle shape
		CircleShape cs = new CircleShape();
		cs.m_radius = radius * 0.1f; // We need to convert radius to JBox2D
										// equivalent
		
		Body body = createBodyAndFixture(bodyType, cs);
		pacman.setUserData(body);
		return pacman;
	}
	
	public void resetLayoutX(float x) {
		node.setLayoutX(x);
	}

	public void resetLayoutY(float y) {
		node.setLayoutY(y);
	}

	public Node getNode() {
		return node;
	}
}