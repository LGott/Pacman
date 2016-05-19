package mainPackage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objectsPackage.BonusPellet;
import objectsPackage.Ghost;
import objectsPackage.Pacman;
import objectsPackage.Pellet;
import objectsPackage.Wall;

import org.jbox2d.dynamics.Body;

import javafx.scene.Node;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import javafx.util.Duration;

import org.jbox2d.dynamics.BodyType;

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
	final Timeline timeline = new Timeline();

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
		world.setContactListener(new CollisionContactListener());
		startSimulation();
		addKeyListeners(scene);
		stage.setScene(scene);
		stage.show();
	}

	public void createGround() {
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(Properties.WIDTH, Properties.HEIGHT);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;

		BodyDef bd = new BodyDef();
		bd.position = new Vec2(0.0f, 0.0f);

		world.createBody(bd).createFixture(fd);
	}

	private void startSimulation() {
		timeline.setCycleCount(Timeline.INDEFINITE);

		Duration duration = Duration.seconds(1.0 / 60.0); // Set duration for
															// frame.

		// Create an ActionEvent, on trigger it executes a world time step and
		// moves the balls to new position
		EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// Create time step. Set Iteration count 8 for velocity and 3
				// for positions
				world.step(1.0f / 60.f, 8, 3);
				// Move balls to the new position computed by JBox2D
				for (Ghost g : ghosts) {
					Body body = (Body) g.getNode().getUserData();
					float xpos = Properties.toPixelPosX(body.getPosition().x);
					float ypos = Properties.toPixelPosY(body.getPosition().y);
					g.resetLayoutX(xpos);
					g.resetLayoutY(ypos);
				}
			}
		};

		/**
		 * Set ActionEvent and duration to the KeyFrame. The ActionEvent is
		 * trigged when KeyFrame execution is over.
		 */
		KeyFrame frame = new KeyFrame(duration, ae, null, null);

		timeline.getKeyFrames().add(frame);

		// TODO Auto-generated method stub
	}

	private void createShapes() {
		createWalls();
		createGround();
		pacman1 = createPacman(50, 80);
		pacman2 = createPacman(50, 20);
		createGhosts();
		createPellets();
		createBonusPellets(); // should createPellets call createBonusPellets?

	}

	private void createWalls() {
		rootGroup.getChildren().add(new Wall(60, 90, world, 10, 100).getNode());
		rootGroup.getChildren().add(new Wall(60, 90, world, 100, 10).getNode());
		//rootGroup.getChildren().add(
				//new Wall(0, 0, world, 5, Properties.HEIGHT).getNode());// left
																		// wall
		//rootGroup.getChildren().add(
				//new Wall(0, 0, world, Properties.WIDTH, 5).getNode());// ceiling
		//rootGroup.getChildren().add(
			//	new Wall(Properties.WIDTH, 0, world, 5, Properties.HEIGHT)
					//	.getNode());// right wall
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
		for (int i = 10; i < 100; i += 10) {
			rootGroup.getChildren().add(new Pellet(i, 15, world, 10).getNode());
		}
	}

	private void createBonusPellets() {
		for (int i = 30; i < 100; i += 10) {
			rootGroup.getChildren()
					.add(new BonusPellet(15, i, world).getNode());
		}
	}

	private void addKeyListeners(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				// need another 4 directional keys for other player. which ones?
				case SHIFT:
					System.out.println("Shift");
					timeline.playFromStart();
					break;
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