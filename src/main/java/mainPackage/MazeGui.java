package mainPackage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.jbox2d.common.Vec2;

import shapePackage.GhostShape;
import shapePackage.PacmanShape;

public class MazeGui extends Application {
	Group rootGroup;
	Scene scene;
	static boolean doSleep = true;
	static Vec2 gravity = new Vec2(0f, 0f);
	public static WorldLogic world = new WorldLogic(gravity, doSleep);
	PacmanShape pacman1;	
	PacmanShape pacman2;
	GhostShape[] ghosts;
	int numPellets;
	int numBonusPellets;

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setWidth(Properties.WIDTH);
		stage.setHeight(Properties.HEIGHT);
		stage.setTitle("Pacman");

		stage.setResizable(false);

		// Create a group for holding all objects on the screen.
		rootGroup = new Group();

		Scene scene = new Scene(rootGroup, Properties.WIDTH, Properties.HEIGHT,
				Color.BLACK);

		createShapes();

		addKeyListeners(scene);
		stage.setScene(scene);
		stage.show();
	}

	private void createShapes() {
		createWalls();
		createPacman(50, 80); // pacman1
		createPacman(50, 20); // pacman2
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

	private void createPacman(int x, int y) {
		PacmanShape pacmanShape = new PacmanShape(x, y);
		rootGroup.getChildren().add(pacmanShape.getNode());
		// world.createBodyAndFixture(x, y, BodyType.DYNAMIC, pacmanShape);
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

	public void play(String[] args) {
		Application.launch(args);
	}
}