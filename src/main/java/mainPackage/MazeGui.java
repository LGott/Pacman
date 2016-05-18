package mainPackage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import shapePackage.GhostShape;
import shapePackage.PacmanShape;

public class MazeGui extends Application {
	private static final int WIDTH = 700;
	private static final int HEIGHT = 700;
	Group rootGroup;
	Scene scene;
	World world;
	boolean doSleep = true;
	Vec2 gravity = new Vec2(0f, 0f);
	PacmanShape pacman1;
	PacmanShape pacman2;
	GhostShape[] ghosts;
	int numPellets;
	int numBonusPellets;

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);
		stage.setTitle("Pacman");

		stage.setResizable(false);

		// Create a group for holding all objects on the screen.
		rootGroup = new Group();

		Scene scene = new Scene(rootGroup, WIDTH, HEIGHT, Color.BLACK);

		createShapes();

		addKeyListeners(scene);
		stage.setScene(scene);
		stage.show();
	}

	private void createShapes() {
		createWalls();
		createPacman(0, 0, 0, 0); // pacman1
		createPacman(0, 0, 0, 0); // pacman2
		createGhosts();
		createPellets();
		createBonusPellets(); // should createPellets call createBonusPellets?
	}

	// create _______ shape
	// add to rootGroup
	// call world.createBody(x, y, bodyType)
	// call world.createFixture(shape)
	private void createWalls() {
	}

	private void createPacman(int x, int y, int width, int height) {
	}

	private void createGhosts() {
	}

	private void createPellets() {
	}

	private void createBonusPellets() {
	}

	private void addKeyListeners(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				// need another 4 directional keys for other player. which ones?
				case UP:
					break;
				case DOWN:
					break;
				case LEFT:
					break;
				case RIGHT:
					break;
				default:
					break;
				}
			}
		});
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

	public void play(String[] args) {
		Application.launch(args);
	}
}