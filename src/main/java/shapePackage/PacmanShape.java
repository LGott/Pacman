package shapePackage;

import mainPackage.MazeGui;
import mainPackage.Properties;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class PacmanShape {
	private Node node;
	private int posX;
	private int posY;
	// radius in pixels
	private final int radius = 20;
	private final BodyType bodyType = BodyType.DYNAMIC;

	public PacmanShape(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		node = create();
	}

	// This method creates a pacman by using Circle object from JavaFX and
	// CircleShape from JBox2D
	private Node create() {
		// Create an UI for ball - JavaFX code
		Circle pacman = new Circle();
		pacman.setRadius(radius);
		Image img = new Image("/pacman.png");
		ImagePattern imagePattern = new ImagePattern(img);
		pacman.setFill(imagePattern);

		// Set ball position on JavaFX scene. We need to convert JBox2D
		// coordinates to JavaFX coordinates which are in pixels.
		pacman.setLayoutX(Properties.toPixelPosX(posX));
		pacman.setLayoutY(Properties.toPixelPosY(posY));
		pacman.setCache(true); // Cache this object for better performance

		// Create an JBox2D body definition for ball.
		BodyDef bd = new BodyDef();
		bd.type = bodyType;
		bd.position.set(posX, posY);

		// create a jbox2D circle shape
		CircleShape cs = new CircleShape();
		cs.m_radius = radius * 0.1f; // We need to convert radius to JBox2D
										// equivalent

		// Create a fixture for pacman
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0f;
		fd.friction = 0f;
		fd.restitution = 0f;

		// Is there a better way to get world?
		MazeGui.world.createBody(bd).createFixture(fd);
		return pacman;
	}

	public Node getNode() {
		return node;
	}
}