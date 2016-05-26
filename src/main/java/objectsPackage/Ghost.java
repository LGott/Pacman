package objectsPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import mainPackage.Properties;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Ghost extends Piece {

	private final int width = 3; // square - same width and height
	private final int height = 3;
	private final BodyType bodyType = BodyType.DYNAMIC;
	private Rectangle ghost;
	private PolygonShape ps;
	private Image img;
	private ImagePattern imagePattern;
	private int i;

	private enum Status {
		RIGHT, LEFT, UP, DOWN
	}

	private Status status = Status.UP;

	public Ghost(int posX, int posY, World world, String image) {
		super(posX, posY, world, "GHOST");
		ps = new PolygonShape();
		ps.setAsBox(width, height);
		img = new Image(image);
		imagePattern = new ImagePattern(img);
		node = create();
		status = Status.UP;
	}

	private Node create() {
		ghost = new Rectangle((Properties.jBoxtoPixelWidth(width) * 2),
				(Properties.jBoxtoPixelHeight(height) * 2));
		setGhostProperties();
		body = createBodyAndFixture(bodyType, ps);
		super.setUserData();
		ghost.setUserData(body);
		return ghost;
	}

	private void setGhostProperties() {
		// TODO Auto-generated method stub
		ghost.setFill(imagePattern);
		ghost.setLayoutX(Properties.jBoxToFxPosX(getPosX())
				- Properties.jBoxtoPixelWidth(width));
		ghost.setLayoutY(Properties.jBoxToFxPosY(getPosY())
				- Properties.jBoxtoPixelHeight(height));
		ghost.setCache(true); // Cache this object for better performance
	}

	public void resetLayoutX(float x) {
		node.setLayoutX(x - Properties.jBoxtoPixelWidth(width));
	}

	public void resetLayoutY(float y) {
		node.setLayoutY(y - Properties.jBoxtoPixelWidth(height));
	}

	public void turnGhost() {
		// TODO Auto-generated method stub
		if (status.equals(Status.UP)) {
			System.out.println("turning right " + objectDescription.getID());
			body.applyLinearImpulse(new Vec2(0.0f, 20.0f),
					body.getWorldCenter());
			status = Status.RIGHT;
		}
		else if (status.equals(Status.RIGHT)) {
			System.out.println("turning down");
			body.applyLinearImpulse(new Vec2(-20.0f, 0.0f),
					body.getWorldCenter());
			status = Status.DOWN;
		}
		else if (status.equals(Status.DOWN)) {
			System.out.println("turning left");
			body.applyLinearImpulse(new Vec2(0.0f, -20.0f),
					body.getWorldCenter());
			status = Status.LEFT;
		}
		else if (status.equals(Status.LEFT)) {
			System.out.println("turning up");
			body.applyLinearImpulse(new Vec2(20.0f, 0.0f),
					body.getWorldCenter());
			status = Status.UP;

		}
	}

}