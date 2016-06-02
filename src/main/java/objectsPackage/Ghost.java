package objectsPackage;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
	private ImagePattern imagePattern;
	private final int groupIndex = -1;
	private final int maskBits = -1;
	private final int categoryBits = -1;
	private Vec2 currDirection;
	private ArrayList<Image> upImages = new ArrayList<Image>(),
			downImages = new ArrayList<Image>(),
			rightImages = new ArrayList<Image>(),
			leftImages = new ArrayList<Image>(),
			invincible = new ArrayList<Image>();
	private ArrayList<Image> images;
	private boolean isInvincible;

	public Ghost(int posX, int posY, World world, Image[] images) {
		super(posX, posY, world, "GHOST");
		ps = new PolygonShape();
		ps.setAsBox(width, height);
		imagePattern = new ImagePattern(images[0]);
		node = create();

		// images
		downImages.add(images[0]);
		downImages.add(images[1]);
		upImages.add(images[2]);
		upImages.add(images[3]);
		leftImages.add(images[4]);
		leftImages.add(images[5]);
		rightImages.add(images[6]);
		rightImages.add(images[7]);
		invincible.add(images[8]);
		invincible.add(images[9]);

		this.images = downImages;
	}

	private Node create() {
		ghost = new Rectangle((Properties.jBoxtoPixelWidth(width) * 2),
				(Properties.jBoxtoPixelHeight(height) * 2));
		setGhostProperties();
		body = createBodyAndFixture(bodyType, ps, maskBits, groupIndex,
				categoryBits);
		super.setUserData();
		ghost.setUserData(body);
		return ghost;
	}

	private void setGhostProperties() {
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

	public void changeDirection(long time) {
		Vec2 oldDir = currDirection;
		Random randomGen = new Random();
		while (oldDir == currDirection) {
			int dir = randomGen.nextInt(4);
			switch (dir) {
			case 0: // UP
				currDirection = new Vec2(0.0f, 30.0f);
				System.out.println("up");
				if(!isInvincible){
					images = upImages;
				}
				break;
			case 1: // DOWN
				currDirection = new Vec2(0.0f, -30.0f);
				System.out.println("down");
				if(!isInvincible){
					images = downImages;
				}
				break;
			case 2: // LEFT
				currDirection = new Vec2(-30.0f, 0.0f);
				System.out.println("left");
				if(!isInvincible){
					images = leftImages;
				}
				break;
			case 3: // RIGHT
				currDirection = new Vec2(30.0f, 0.0f);
				System.out.println("right");
				if(!isInvincible){
					images = rightImages;
				}
				break;
			}
		}

		resetSpeed();

	}

	public void setImage(Image image) {
		imagePattern = new ImagePattern(image);
		((Shape) node).setFill(imagePattern);
	}

	public void animateGhost(double time) {
		double duration = 0.100;
		int index = (int) ((time % (images.size() * duration)) / duration);
		this.setImage(images.get(index));
	}

	public void resetSpeed() {
		body.setLinearVelocity(currDirection);
	}

	public void turnBlue() {
		isInvincible = true;
		images = invincible;
		this.setImage(images.get(0));
	}

	public void resetColor() {
		isInvincible = false;
		images = upImages;
		//this.setImage(images.get(0));
	}

}