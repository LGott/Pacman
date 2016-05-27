package objectsPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import mainPackage.Properties;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Pellet extends Piece {

	// radius in pixels
	protected final int radius;
	protected final BodyType bodyType = BodyType.STATIC;
	protected Circle pellet;
	protected String imgName;
	private CircleShape cs;

	public Pellet(int posX, int posY, World world, String description,
			String imgName) {
		super(posX, posY, world, description);
		// TODO Auto-generated constructor stub
		this.radius = 10;
		this.imgName = imgName;
		pellet = new Circle();
		node = create();
	}

	private Node create() {
		setPelletProperties();
		// create a jbox2D circle shape
		cs = new CircleShape();
		cs.m_radius = radius * 0.1f; // We need to convert radius to JBox2D
		// equivalent
		body = createBodyAndFixture(bodyType, cs, -1, -1, -1);
		super.setUserData();
		pellet.setUserData(body);
		return pellet;
	}

	private void setPelletProperties() {
		// TODO Auto-generated method stub
		pellet.setRadius(radius);
		pellet.setLayoutX(Properties.jBoxToFxPosX(getPosX()));
		pellet.setLayoutY(Properties.jBoxToFxPosY(getPosY()));
		pellet.setCache(true); // Cache this object for better performance
		setPelletImage();
	}

	private void setPelletImage() {
		// TODO Auto-generated method stub
		Image img = new Image(imgName);
		ImagePattern imagePattern = new ImagePattern(img);
		pellet.setFill(imagePattern);
	}

}
