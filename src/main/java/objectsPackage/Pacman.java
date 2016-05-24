package objectsPackage;

import mainPackage.Properties;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Pacman extends Piece {
	private Node node;
	private final int width = 3; // square - same width and height
	private final int height = 3;
	private final BodyType bodyType = BodyType.DYNAMIC;
	private String image;

	public Pacman(int posX, int posY, World world, String image) {
		super(posX, posY, world, "PACMAN");
		this.image = image;
		node = create();
	}

	private Node create() {
		Image img = new Image(image);
		ImagePattern imagePattern = new ImagePattern(img);

		Rectangle pacman = new Rectangle((Properties.jBoxtoPixelWidth(width) * 2),
				(Properties.jBoxtoPixelHeight(height) * 2));
		pacman.setFill(imagePattern);
		pacman.setLayoutX(Properties.jBoxToFxPosX(getPosX()) - Properties.jBoxtoPixelWidth(width));
		pacman.setLayoutY(Properties.jBoxToFxPosY(getPosY()) - Properties.jBoxtoPixelHeight(height));
		pacman.setCache(true); // Cache this object for better performance

		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width, height);

		body = createBodyAndFixture(bodyType, ps);
		super.setUserData();

		pacman.setUserData(body);
		return pacman;

	}

	public void resetLayoutX(float x) {
		node.setLayoutX(x - Properties.jBoxtoPixelWidth(width));
	}

	public void resetLayoutY(float y) {
		node.setLayoutY(y - Properties.jBoxtoPixelWidth(height));
	}

	public Node getNode() {
		return node;
	}

	public void setImage(String image) {
		Image img = new Image(image);
		ImagePattern imagePattern = new ImagePattern(img);
		((Shape) node).setFill(imagePattern);
	}
}