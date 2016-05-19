package mainPackage;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Wall extends Piece {
	private Node node;
	// radius in pixels
	private final int height;
	private final int width;
	private final BodyType bodyType = BodyType.DYNAMIC;

	public Wall(int posX, int posY, World world, int height, int width) {
		super(posX, posY, world);
		this.height = height;
		this.width = width;
		node = create();
	}

	private Node create() {
		Rectangle wall = new Rectangle(width, height);
		wall.setFill(Color.BLUE);

		// Set ball position on JavaFX scene. We need to convert JBox2D
		// coordinates to JavaFX coordinates which are in pixels.
		wall.setLayoutX(Properties.toPixelPosX(getPosX()));
		wall.setLayoutY(Properties.toPixelPosY(getPosY()));
		wall.setCache(true); // Cache this object for better performance

		PolygonShape ps = new PolygonShape();
		//ps.m_radius = radius * 0.1f; // We need to convert radius to JBox2D
										// equivalent
		//HOW DO YOU MAKE A POLYGON SHAPE???
		
		createBodyAndFixture(bodyType, ps);

		return wall;
	}

	public Node getNode() {
		return node;
	}
}