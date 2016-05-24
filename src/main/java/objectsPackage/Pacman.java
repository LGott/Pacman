package objectsPackage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import mainPackage.Properties;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Pacman extends Piece {
	private Node node;
	// radius in pixels
	private final int radius = 20;
	private final BodyType bodyType = BodyType.DYNAMIC;

	private Image imageClose = new Image(getClass().getResourceAsStream("/pacman-closed.png"));
	private Image imageOpen = new Image(getClass().getResourceAsStream("/pacman-opened.png"));
	private Image[] images = new Image[] {imageClose, imageOpen};
	private  int imgNum;
	private boolean colliding = false;


	public Pacman(int posX, int posY, World world) {
		super(posX, posY, world, "PACMAN");
		node = create();
	}

	// This method creates a pacman by using Circle object from JavaFX and
	// CircleShape from JBox2D
	private Node create() {
		// Create an UI for pacman - JavaFX code
		Circle pacman = new Circle();
		pacman.setRadius(radius);
		// can subtract 5 to add a 'border' around the pacman, so that there is
		// less overlap between pacman and pieces
		// pacman.setRadius(radius - 5);


		Image img = images[imgNum = 0];
		ImagePattern imagePattern = new ImagePattern(img);
		pacman.setFill(imagePattern);

		// Set ball position on JavaFX scene. We need to convert JBox2D
		// coordinates to JavaFX coordinates which are in pixels.
		pacman.setLayoutX(Properties.jBoxToFxPosX(getPosX()));
		pacman.setLayoutY(Properties.jBoxToFxPosY(getPosY()));
		pacman.setCache(true); // Cache this object for better performance

		// create a jbox2D circle shape
		CircleShape cs = new CircleShape();
		cs.m_radius = radius * 0.1f; // We need to convert radius to JBox2D
		// equivalent

		body = createBodyAndFixture(bodyType, cs);
		// body.setUserData("PACMAN");
		super.setUserData();

		pacman.setUserData(body);
		return pacman;
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

	public void setImage(Image image) {
		ImagePattern imagePattern = new ImagePattern(image);
		((Shape) node).setFill(imagePattern);
	}

	public void animatePacman(double time){
		double duration = 0.100;
		int index = (int)((time % (images.length * duration)) / duration);
		this.setImage(images[index]);
		//		if(++imgNum == 3){
		//			imgNum= 0;
		//			this.setImage(images[imgNum]);
		//		}else{
		//			this.setImage(images[imgNum++]);
		//		}
	}


	public void setOpenPacman(){
		this.setImage(imageOpen);
	}

	public void setColliding(boolean col){
		this.colliding = col;
	}
	public boolean isColliding(){
		return colliding ;
	}
}