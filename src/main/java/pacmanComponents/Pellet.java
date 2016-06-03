package pacmanComponents;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import pacmanControllers.Properties;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Pellet extends Piece {

	// radius in pixels
	protected int radius;
	protected final BodyType bodyType = BodyType.STATIC;
	protected Circle pellet;
	protected String imgName;
	private CircleShape cs;
	private final int groupIndex = -1;
	private final int maskBits = -1;
	private final int categoryBits = -1;

	public Pellet(int posX, int posY, World world, String description,
			String imgName) {
		super(posX, posY, world, description);
		this.radius = 5;
		this.imgName = imgName;
		this.pellet = new Circle();
		this.node = create();
	}

	public Pellet(int posX, int posY, World world, String description,
			String imgName, int radius) {
		super(posX, posY, world, description);
		this.radius = radius;
		this.imgName = imgName;
		this.pellet = new Circle();
		this.node = create();
	}

	private Node create() {
		setPelletProperties();
		// create a jbox2D circle shape
		this.cs = new CircleShape();
		this.cs.m_radius = radius * 0.1f; // We need to convert radius to JBox2D
		// equivalent
		this.body = createBodyAndFixture(bodyType, cs, maskBits, groupIndex,
				categoryBits);
		super.setUserData();
		this.pellet.setUserData(body);
		return pellet;
	}

	private void setPelletProperties() {
		pellet.setRadius(radius);
		pellet.setLayoutX(Properties.jBoxToFxPosX(getPosX()));
		pellet.setLayoutY(Properties.jBoxToFxPosY(getPosY()));
		pellet.setCache(true); // Cache this object for better performance
		setPelletImage();
	}

	private void setPelletImage() {
		Image img = new Image(imgName);
		ImagePattern imagePattern = new ImagePattern(img);
		pellet.setFill(imagePattern);
	}

	public void setRadius(int radius){
		this.radius = radius;
	}

}
