package objectsPackage;

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

public class Pacman extends Piece {
	private Node node;
	private final int width = 3; // square - same width and height
	private final int height = 3;
	private final BodyType bodyType = BodyType.DYNAMIC;

	private Image imageClose = new Image(getClass().getResourceAsStream("/pacman-closed.png"));
	private Image imageOpen = new Image(getClass().getResourceAsStream("/pacman-opened.png"));
	private Image[] images = new Image[] { imageClose, imageOpen };
	private boolean colliding = false;
	private Image image;
	// starts moving to the left
	private Vec2 currDirection = new Vec2(-20.0f, 0.0f);
	private int currDegree = 180;
	private String name;
	private int score;
	private int lives;
	private int initialX, initialY;

	public Pacman(int posX, int posY, World world, String name) {
		super(posX, posY, world, "PACMAN");
		this.initialX = posX;
		this.initialY = posY;
		this.node = create();
		this.name = name;
		this.score = 0;
		this.lives = 3;
	}

	public Pacman(Pacman p) {
		super(p.initialX, p.initialY, p.world, "PACMAN");
		this.node = create();
		this.id = p.id;
		this.initialX = p.initialX;
		this.initialY = p.initialY;
		this.name = p.name;
		this.score = p.score;
		this.lives = p.lives;
	}

	private Node create() {
		Image img = images[0];
		ImagePattern imagePattern = new ImagePattern(img);

		Rectangle pacman = new Rectangle((Properties.jBoxtoPixelWidth(width) * 2),
				(Properties.jBoxtoPixelHeight(height) * 2));
		pacman.setFill(imagePattern);
		pacman.setLayoutX(Properties.jBoxToFxPosX(getPosX()) - Properties.jBoxtoPixelWidth(width));
		pacman.setLayoutY(Properties.jBoxToFxPosY(getPosY()) - Properties.jBoxtoPixelHeight(height));
		pacman.setCache(true); // Cache this object for better performance

		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width, height);

		body = createBodyAndFixture(bodyType, ps, 1, 1, 1);
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

	@Override
	public Node getNode() {
		return node;
	}

	public void setDirection(float horizontal, float vertical, int degree) {
		Vec2 newDirection = new Vec2(horizontal, vertical);
		currDirection = newDirection;
		currDegree = degree;
		resetSpeed();
	}

	public void resetSpeed() {
		body.setLinearVelocity(currDirection);
		node.setRotate(currDegree);
	}

	public void setImage(Image image) {
		Image img = image;
		ImagePattern imagePattern = new ImagePattern(img);
		((Shape) node).setFill(imagePattern);
	}

	public void animatePacman(double time) {
		double duration = 0.100;
		int index = (int) ((time % (images.length * duration)) / duration);
		this.setImage(images[index]);
		// if(++imgNum == 3){
		// imgNum= 0;
		// this.setImage(images[imgNum]);
		// }else{
		// this.setImage(images[imgNum++]);
		// }
	}

	public int getScore() {
		return this.score;
	}

	public void incrementScore(int score) {
		this.score += score;
	}

	public void decrementLives() {
		lives--;
		System.out.println("lives decremented");
		System.out.println("decremented");

	}

	public int getLives() {
		return this.lives;
	}

	public void setOpenPacman() {
		this.setImage(imageOpen);
	}

	public void setColliding(boolean col) {
		this.colliding = col;
	}

	public boolean isColliding() {
		return colliding;
	}

	public String getName() {
		return this.name;
	}
	public void resetLives(){
		this.lives = 3;
	}
	public  void resetScore(){
		this.score = 0;
	}
}