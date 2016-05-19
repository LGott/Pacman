package groupProject.pacman;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.paint.ImagePattern;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class Box2D extends Application {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 700;
	// first gravity paramenter is horizontal gravity and the second is vertical
	// gravity
	Vec2 gravity = new Vec2(0f, 0f);
	boolean doSleep = true;
	World world = new World(gravity, doSleep);
	private PacmanRectangle pacman;
	private Group root;

	public static void main(String[] args) {

		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);
		stage.setTitle("Pacaman");

		stage.setResizable(false);

		root = new Group(); // Create a group for holding all objects on
							// the screen
		Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

		Button button = new Button("Click Here");
		createPacman(250, 250, 30, 30);
		

		createWall(200, 150, 5, 200);
		createWall(350, 250, 170, 5);

		root.getChildren().add(button);
		root.getChildren().add(pacman);

		//setWalls();
		addKeyListeners(scene);
		stage.setScene(scene);
		stage.show();
	}

	private void createPacman(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		pacman = new PacmanRectangle(x, y, width, height);
		createBody(x, y, BodyType.DYNAMIC);
		//createFixture(pacman);
	}

	public void createWall(int x, int y, int width, int height) {
		Wall wall = new Wall(x, y, width, height);
		root.getChildren().add(wall);
		createBody(x, y, BodyType.STATIC);
		//createFixture((wall);
	}

	// body type can be static(ground, walls), dynamic(ghosts, pacman) or kinametic
	private void createBody(int x, int y, BodyType bodyType) {
		// TODO Auto-generated method stub
		BodyDef bd = new BodyDef();
		// set position to x and y coordinates
		bd.position.set(x, y);
		bd.type = bodyType;
	}
	private void createFixture(Shape shape){
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 0.5f;
		fd.friction = 0.3f;        
		fd.restitution = 0.5f;
	}

	private void addKeyListeners(Scene scene) {
		// TODO Auto-generated method stub
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					System.out.println("up");
					moveUp();
					break;
				case DOWN:
					System.out.println("down");
					moveDown();
					break;
				case LEFT:
					System.out.println("left");
					moveLeft();
					break;
				case RIGHT:
					System.out.println("right");
					moveRight();
					break;
				case SHIFT:
					System.out.println("shift");
					break;
				}
			}
		});
	}

	private void moveUp() {
		// TODO Auto-generated method stub

		Body body = (Body) pacman.getUserData();
		//float x = body.getPosition().x;
		// float xpos = toPixelPosX(body.getPosition().x);
		// float ypos = toPixelPosY(body.getPosition().y);
		// pacman.setLayoutX(xpos);
		// pacman.setLayoutY(ypos);
		pacman.setLayoutX(350);
		pacman.setLayoutY(200);
	}

	public void moveDown() {
		pacman.setLayoutX(400);
		pacman.setLayoutY(400);
	}

	public void moveRight() {
		pacman.setLayoutX(0);
		pacman.setLayoutY(100);
	}

	private void moveLeft() {
		pacman.setLayoutX(toPixelPosX(0));
		pacman.setLayoutY(350);
	}

	// Convert a JBox2D x coordinate to a JavaFX pixel y coordinate
	private float toPixelPosX(float posX) {
		float x = WIDTH * posX / 100.0f;
		return x;
	}

	// Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	protected float toPixelPosY(float posY) {
		float y = HEIGHT - (1.0f * HEIGHT) * posY / 100.0f;
		return y;
	}

//	private void setWalls() {
//		// TODO Auto-generated method stub
//		addGround(100, 10);
//		//
//		// //Add left and right walls so balls will not move outside the viewing
//		// area.
//		addWall(0, 100, 1, 100); // Left wall
//		addWall(99, 100, 1, 100); // Right wall
//	}

//	private void addWall(float posX, float posY, float width, float height) {
//
//		PolygonShape wall = new PolygonShape();
//		wall.setAsBox(width, height);
//
//		FixtureDef fd = new FixtureDef();
//		fd.shape = wall;
//		fd.density = 1.0f;
//		fd.friction = 0.3f;
//
//		BodyDef bd = new BodyDef();
//		bd.position.set(posX, posY);
//
//		world.createBody(bd).createFixture(fd);
//	}
//
//	public void addGround(float width, float height) {
//		PolygonShape ps = new PolygonShape();
//		ps.setAsBox(width, height);
//
//		FixtureDef fd = new FixtureDef();
//		fd.shape = ps;
//
//		BodyDef bd = new BodyDef();
//		bd.position = new Vec2(0.0f, -10f);
//
//		world.createBody(bd).createFixture(fd);
//	}

}