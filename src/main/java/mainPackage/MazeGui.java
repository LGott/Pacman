package mainPackage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.jbox2d.common.Vec2;

public class MazeGui extends Application {
	private Group rootGroup;
	private Scene scene;
	private boolean doSleep = true;
	private Vec2 gravity = new Vec2(0f, 0f);
	private WorldLogic world = new WorldLogic(gravity, doSleep);
	private Pacman pacman1;
	private Pacman pacman2;
	private Ghost[] ghosts = new Ghost[4]; // HOW MANY??
	private int numPellets;
	private int numBonusPellets;

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
		pacman1 = createPacman(50, 80);
		pacman2 = createPacman(50, 20);
		createGhosts();
		createPellets();
		createBonusPellets(); // should createPellets call createBonusPellets?
	}

	// create _______ shape
	// add to rootGroup

	private void createWalls() {
	}

	private Pacman createPacman(int x, int y) {
		Pacman pacmanShape = new Pacman(x, y, world);
		rootGroup.getChildren().add(pacmanShape.getNode());
		return pacmanShape;
	}

	private void createGhosts() {
		ghosts[0] = new Ghost(30, 30, world, "/blueGhost.png");
		ghosts[1] = new Ghost(50, 50, world, "/pinkGhost.png");
		ghosts[2] = new Ghost(80, 80, world, "/orangeGhost.png");
		ghosts[3] = new Ghost(60, 60, world, "/redGhost.png");

		for (Ghost g : ghosts) {
			rootGroup.getChildren().add(g.getNode());
		}
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