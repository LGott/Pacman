package objectsPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import mainPackage.Properties;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Ghost extends Piece {
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

		ghost.setLayoutX(Properties.jBoxToFxPosX(getPosX()));
		ghost.setLayoutY(Properties.jBoxToFxPosY(getPosY()));
		ghost.setCache(true); // Cache this object for better performance

		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width * 0.1f, width * 0.1f);

		Body body = createBodyAndFixture(bodyType, ps);
		ghost.setUserData(body);
		return ghost;
	}

	public void resetLayoutX(float x) {
		node.setLayoutX(x);
	}

	public void resetLayoutY(float y) {
		node.setLayoutY(y);
	}

	@Override
	public Node getNode() {
		return node;
	}
}