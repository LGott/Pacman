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

@SuppressWarnings("deprecation")
public class Ghost extends Piece {
	private Node node;
	private final int width = 3; // square - same width and height
	private final int height = 3;
	private final BodyType bodyType = BodyType.DYNAMIC;
	private String image;

	public Ghost(int posX, int posY, World world, String image) {
		super(posX, posY, world, "GHOST");
		this.image = image;
		node = create();
	}

	private Node create() {
		Image img = new Image(image);
		ImagePattern imagePattern = new ImagePattern(img);
		
		Rectangle ghost = new Rectangle((Properties.jBoxtoPixelWidth(width) * 2), 
				(Properties.jBoxtoPixelHeight(height) * 2));
		ghost.setFill(imagePattern);
		ghost.setLayoutX(Properties.jBoxToFxPosX(getPosX()) - Properties.jBoxtoPixelWidth(width));
		ghost.setLayoutY(Properties.jBoxToFxPosY(getPosY()) - Properties.jBoxtoPixelHeight(height));
		ghost.setCache(true); // Cache this object for better performance

		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width, height);
	
		body = createBodyAndFixture(bodyType, ps);
		//body.setUserData("GHOST");
		super.setUserData();
		ghost.setUserData(body);
		return ghost;
	}

	public void resetLayoutX(float x) {
		node.setLayoutX(x - Properties.jBoxtoPixelWidth(width));		
	}

	public void resetLayoutY(float y) {
		node.setLayoutY(y - Properties.jBoxtoPixelWidth(height));
	}

	@Override
	public Node getNode() {
		return node;
	}
}