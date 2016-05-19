package mainPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Ghost extends Tile {
	private Node node;
	private final int width = 40; // square - same width and height
	private final BodyType bodyType = BodyType.DYNAMIC;
	private String image;

	public Ghost(int posX, int posY, World world, String image) {
		super(posX, posY, world);
		this.image = image;
		node = create();
	}

	private Node create() {
		Rectangle ghost = new Rectangle(width, width);
		Image img = new Image(image);
		ImagePattern imagePattern = new ImagePattern(img);
		ghost.setFill(imagePattern);

		ghost.setLayoutX(Properties.toPixelPosX(getPosX()));
		ghost.setLayoutY(Properties.toPixelPosY(getPosY()));
		ghost.setCache(true); // Cache this object for better performance

		PolygonShape cs = new PolygonShape();
		cs.m_radius = width * 0.1f;

		createBodyAndFixture(bodyType, cs);

		return ghost;
	}

	@Override
	Node getNode() {
		return node;
	}
}