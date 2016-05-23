package objectsPackage;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mainPackage.Properties;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Wall extends Piece {
	private Node node;
	// radius in pixels
	private final int height;
	private final int width;
	private final BodyType bodyType = BodyType.STATIC;

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
		ps.setAsBox(width/2f, height/2f);

		Body body = createBodyAndFixture(bodyType, ps);
		wall.setUserData(body);
		return wall;
	}

	@Override
	public Node getNode() {
		return node;
	}
}