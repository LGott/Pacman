package mainPackage;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import objectsPackage.BonusPellet;
import objectsPackage.Ghost;
import objectsPackage.Pacman;
import objectsPackage.Pellet;
import objectsPackage.UniqueObject;
import objectsPackage.Wall;
import objectsPackage.YellowPellet;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class MazeGui extends Application {
	private Group rootGroup;
	private Scene scene;
	private boolean doSleep = true;
	private Vec2 gravity = new Vec2(0.0f, 0.0f);
	private WorldLogic world = new WorldLogic(gravity, doSleep);
	private Pacman pacman1;
	private Pacman pacman2;
	private ArrayList<Ghost> ghosts;
	private int x = 0;

	public enum MoveDir {
		UP, DOWN, LEFT, RIGHT, NONE
	}

	private MoveDir P2intendedMoveDir = MoveDir.NONE;
	private MoveDir P1intendedMoveDir = MoveDir.NONE;
	private MoveDir P1currentDir = MoveDir.RIGHT;
	private MoveDir P2currentDir = MoveDir.RIGHT;

	private final Vec2 tmpV1 = new Vec2();
	private final Vec2 tmpV2 = new Vec2();
	private boolean canMove;

	// private int numBonusPellets; //I think they can be counted together, and
	// when they're both all finished - you finished the round!
	final Timeline timeline = new Timeline();
	private CollisionContactListener contactListener;

	private ArrayList<Pellet> pellets = new ArrayList<Pellet>();
	private ArrayList<Pacman> pacmanArray = new ArrayList<Pacman>();
	private Label scoreLabel;
	private Label scoreValueLabel;
	private Label scoreLabel2;
	private Label scoreValueLabel2;
	private ArrayList<Label> pacmanLives1;
	private ArrayList<Label> pacmanLives2;
	private Label gameOverLabel;
	private Label outLabel;
	private Label pacmanLife1;
	private Label pacmanLife2;
	private int life;
	private int life2;
	private ObservableList<Node> group;
	private final long timeStart = System.currentTimeMillis();

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		setStageProperties(stage);
		// Create a group for holding all objects on the screen.

		rootGroup = new Group();
		setScoreLabels();

		group = rootGroup.getChildren();
		this.ghosts = new ArrayList<Ghost>();
		contactListener = new CollisionContactListener(rootGroup, pellets,
				pacmanArray, ghosts);
		scene = new Scene(rootGroup, Properties.WIDTH, Properties.HEIGHT,
				Color.BLACK);
		pacmanLives1 = new ArrayList<Label>();
		pacmanLives2 = new ArrayList<Label>();

		life = 0;
		life2 = 0;
		setPacmanLives();
		setLabels();
		createShapes();
		world.setContactListener(contactListener);
		startSimulation();
		addKeyListeners(scene);
		stage.setScene(scene);
		stage.show();
	}

	private void setLabels() {
		gameOverLabel = new Label("GAME OVER");
		gameOverLabel.setFont(new Font(90));
		gameOverLabel.setTranslateX(120);
		gameOverLabel.setTranslateY(150);
		gameOverLabel.setTextFill(Color.WHITE);
		gameOverLabel.setVisible(false);

		outLabel = new Label("BOOM!!"); // Subject to change lol
		outLabel.setFont(new Font(90));
		outLabel.setTranslateX(155);
		outLabel.setTranslateY(300);
		outLabel.setTextFill(Color.WHITE);
		outLabel.setVisible(false);

		group.add(gameOverLabel);
		group.add(outLabel);

	}

	private void setScoreLabels() {
		scoreLabel = new Label("Pacman 1 Score: ");
		setLabel(scoreLabel, 25, 25);
		scoreValueLabel = new Label();
		setLabel(scoreValueLabel, 140, 25);

		scoreLabel2 = new Label("Pacman 2 Score: ");
		setLabel(scoreLabel2, 25, 45);

		scoreValueLabel2 = new Label();
		setLabel(scoreValueLabel2, 140, 45);

		pacmanLife1 = new Label("Pacman 1");
		pacmanLife2 = new Label("Pacman 2");
		setLabel(pacmanLife1, 575, 50);
		setLabel(pacmanLife2, 285, 50);
		rootGroup.getChildren().add(scoreLabel);
		rootGroup.getChildren().add(scoreValueLabel);

		rootGroup.getChildren().add(scoreLabel2);
		rootGroup.getChildren().add(scoreValueLabel2);
		rootGroup.getChildren().add(pacmanLife1);
		rootGroup.getChildren().add(pacmanLife2);

	}

	private void setLabel(Label label, int x, int y) {

		label.setTranslateX(x);
		label.setTranslateY(y);
		label.setTextFill(Color.YELLOW);

	}

	private void setStageProperties(Stage stage) {
		// TODO Auto-generated method stub
		stage.setWidth(Properties.WIDTH);
		stage.setHeight(Properties.HEIGHT);
		stage.setTitle("Pacman");
		stage.setResizable(false);
	}

	// Display the pacman labels in the correct position
	private void setPacmanLives() {
		int x = 630;
		int y = 25;

		for (int i = 0; i < 3; i++) {
			pacmanLives1.add(new Label(""));
			pacmanLives2.add(new Label(""));
		}

		for (Label pac : pacmanLives1) {
			pacLives(pac, x, y);
			x -= 45;
		}
		x = 250;
		for (Label pac2 : pacmanLives2) {
			pacLives(pac2, x, y);
			x += 45;
		}
	}

	// Set the attributes
	private void pacLives(Label pac, int x, int y) {

		Image image = new Image(getClass().getResourceAsStream("/pacman.png"));
		ImageView img = new ImageView(image);
		img.setFitWidth(25);
		img.setPreserveRatio(true);
		pac.setGraphic(img);
		pac.setTranslateX(x);
		pac.setTranslateY(y);
		rootGroup.getChildren().add(pac);

	}

	private void startSimulation() {

		timeline.setCycleCount(Timeline.INDEFINITE);

		Duration duration = Duration.seconds(1.0 / 60.0); // Set duration for
		// frame.

		// Create an ActionEvent, on trigger it executes a world time step and
		// moves the objects to new position

		EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				world.step(1.0f / 60.f, 8, 3);
				x++;
				removeFixturesAndPellets();
				scoreValueLabel.setText(String.valueOf(pacman1.getScore()));
				scoreValueLabel2.setText(String.valueOf(pacman2.getScore()));

				if (P1intendedMoveDir != MoveDir.NONE) {
					// automatic pacman movement based on input
					if (isValidMove(pacman1, P1intendedMoveDir)) {
						System.out.println("pacman1 change direction");
						P1currentDir = P1intendedMoveDir;
						P1intendedMoveDir = MoveDir.NONE;
						pacman1.setDirection(P1currentDir);
					}
				}
				if (P2intendedMoveDir != MoveDir.NONE) {
					if (isValidMove(pacman2, P2intendedMoveDir)) {
						P2currentDir = P2intendedMoveDir;
						P2intendedMoveDir = MoveDir.NONE;
						pacman2.setDirection(P2currentDir);
					}
				}
				// Move pacmans to the new position computed by JBox2D
				movePacman(pacman1);
				movePacman(pacman2);
				moveGhostsStep();

				if (contactListener.isPacmanLost()) {
					timeline.pause();
					resetPacmansAndGhosts();

					// If pacman1 hit a ghost, decrement its score
					if (contactListener.determinePacman() == "Pacman1") {
						pacmanLives1.get(life).setGraphic(null);
						contactListener.setPacmanLoss(false);
						contactListener.setPacmans("Neutral"); // reset
						if (life < 2) {
							life++;
						}
					}
					// If pacman2 hit a ghost decrement its score
					if (contactListener.determinePacman() == "Pacman2") {
						pacmanLives2.get(life2).setGraphic(null);
						contactListener.setPacmanLoss(false);
						contactListener.setPacmans("Neutral"); // reset
						if (life2 < 2) {
							life2++;
						}
					}
					if (pacman1.getLives() <= 0 || pacman2.getLives() <= 0) {
						gameOverLabel.setVisible(true);
						timeline.stop();
					}
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

	private void resetPacmansAndGhosts() {

		// reset pacman1
		group.remove(pacman1.getNode());
		world.destroyBody(pacman1.getFixture().getBody());
		pacman1 = new Pacman(pacman1);
		group.add(pacman1.getNode());

		// reset pacman2
		group.remove(pacman2.getNode());
		world.destroyBody(pacman2.getFixture().getBody());
		pacman2 = new Pacman(pacman2);
		group.add(pacman2.getNode());

		pacmanArray = new ArrayList<Pacman>();
		pacmanArray.add(pacman1);
		pacmanArray.add(pacman2);
		contactListener.resetPacmanArray(pacmanArray);

		// reset ghosts
		for (Ghost g : ghosts) {
			group.remove(g.getNode());
			world.destroyBody(g.getFixture().getBody());
		}
		ghosts.clear();
		outLabel.setVisible(true);
		createGhosts();

	}

	private void moveGhostsStep() {
		// TODO Auto-generated method stub
		for (Ghost g : ghosts) {
			moveAGhost(g);
			if (x % 65 == 0) {
				g.changeDirection();
			}
		}
	}

	private void moveAGhost(Ghost g) {
		// TODO Auto-generated method stub
		Body body = (Body) g.getNode().getUserData();
		// body.setLinearVelocity(new Vec2(-20.0f, 0.0f));
		float xpos = Properties.jBoxToFxPosX(body.getPosition().x);
		float ypos = Properties.jBoxToFxPosY(body.getPosition().y);
		g.resetLayoutX(xpos);
		g.resetLayoutY(ypos);
	}

	private void movePacman(Pacman pacman) {

		animatePacman(timeStart, pacman);
		// TODO Auto-generated method stub
		Body pacBody = (Body) pacman.getNode().getUserData();
		float xpos = Properties.jBoxToFxPosX(pacBody.getPosition().x);
		float ypos = Properties.jBoxToFxPosY(pacBody.getPosition().y);
		pacman.resetLayoutX(xpos);
		pacman.resetLayoutY(ypos);
		pacman.resetSpeed();
	}

	private void removeFixturesAndPellets() {
		// TODO Auto-generated method stub
		for (Fixture b : contactListener.getFixturesToRemove()) {
			world.destroyBody(b.getBody());
			// rootGroup.getChildren().remove(b);
		}

		for (Pellet p : contactListener.getPelletsToRemove()) {
			group.remove(p.getNode());
		}

		// clear for next step
		// contactListener.getPacmanColliding().clear();
		contactListener.getPelletsToRemove().clear();
		contactListener.getFixturesToRemove().clear();

	}

	private void animatePacman(final long timeStart, Pacman pacman) {
		if (contactListener.isCollidingWithWall() && pacman.isColliding()) {
			pacman.setOpenPacman();
		} else {
			double time = (System.currentTimeMillis() - timeStart) / 1000.0;
			pacman.animatePacman(time);
		}
	}

	private void createShapes() {
		createWalls();
		createGhosts();
		createPacmans();
		createPellets();
		createBonusPellets();
	}

	private void createWalls() {
		// wall (x,y) = 1/2 from edge (x,y)
		// WALLS DON'T TOUCH!
		// top wall
		createWall(0, 84, 100, 1);
		// bottom wall
		createWall(0, 4, 100, 1);
		// right wall
		createWall(99, 37, 1, 74);
		// left wall
		createWall(0, 37, 1, 74);

		// west DON'T TOUCH
		createWall(11, 15, 3, 3);
		createWall(24, 15, 3, 3);
		createWall(11, 68, 3, 8);
		createWall(11, 39, 3, 14);
		createWall(24, 64, 3, 12);
		createWall(24, 35, 3, 10);

		// north DON'T TOUCH
		createWall(37, 60, 3, 3);
		createWall(63, 60, 3, 3);
		createWall(50, 73, 16, 3);
		createWall(50, 65, 3, 8);

		// south DON'T TOUCH
		createWall(50, 28, 16, 3);
		createWall(37, 8, 3, 3);
		createWall(50, 15, 3, 3);
		createWall(63, 8, 3, 3);

		// east
		createWall(95, 69, 3, 7);
		createWall(95, 40, 3, 9);
		createWall(82, 15, 9, 3);
		createWall(82, 59, 3, 3);
		createWall(82, 28, 3, 3);

		createWall(76, 66, 3, 10);
		createWall(76, 37, 3, 12);

		// center
		createWall(50, 48, 9, 1);
		createWall(50, 39, 9, 1);

	}

	private void createWall(int posX, int posY, int width, int height) {
		group.add(new Wall(posX, posY, world, width, height, Color.BLUE)
		.getNode());
	}

	public void createPacmans() {
		pacmanArray.add(pacman1 = createPacman(50, 80, "Pacman1"));
		pacmanArray.add(pacman2 = createPacman(50, 22, "Pacman2"));
	}

	private Pacman createPacman(int x, int y, String name) {
		Pacman pacman = new Pacman(x, y, world, name);
		rootGroup.getChildren().add(pacman.getNode());
		return pacman;
	}

	private void createGhosts() {
		ghosts.add(new Ghost(42, 44, world, "/blueGhost.png"));
		ghosts.add(new Ghost(47, 44, world, "/pinkGhost.png"));
		ghosts.add(new Ghost(53, 44, world, "/orangeGhost.png"));
		ghosts.add(new Ghost(58, 44, world, "/redGhost.png"));

		for (Ghost g : ghosts) {
			group.add(g.getNode());
		}
	}

	private void createPellets() {
		// bottom line across
		for (int i = 5; i < 31; i += 8) {
			createYellowPellet(i, 8);
		}
		createYellowPellet(43, 8);
		createYellowPellet(50, 8);
		createYellowPellet(57, 8);
		for (int i = 70; i < 95; i += 8) {
			createYellowPellet(i, 8);
		}
		// second to bottom across
		createYellowPellet(29, 15);
		createYellowPellet(36, 15);
		createYellowPellet(43, 15);

		createYellowPellet(57, 15);
		createYellowPellet(63, 15);
		createYellowPellet(70, 15);
		// third to bottom across
		for (int i = 22; i < 50; i += 7) {
			createYellowPellet(i, 22);
		}
		for (int i = 57; i < 90; i += 7) {
			createYellowPellet(i, 22);
		}
		createYellowPellet(12, 22);
		createYellowPellet(18, 15);
		// vertical left column
		for (int i = 16; i < 75; i += 7) {
			createYellowPellet(5, i);
		}
		// second to left vertical column
		for (int i = 30; i < 75; i += 7) {
			createYellowPellet(18, i);
		}
		// top row across
		for (int i = 5; i < 45; i += 7) {
			createYellowPellet(i, 80);
		}
		for (int i = 59; i < 95; i += 7) {
			createYellowPellet(i, 80);
		}
		// left inner home down
		for (int i = 34; i < 60; i += 7) {
			createYellowPellet(31, i);
		}
		for (int i = 34; i < 60; i += 7) {
			createYellowPellet(38, i);
		}
		createYellowPellet(31, 61);
		// right inner home down
		for (int i = 34; i < 60; i += 7) {
			createYellowPellet(62, i);
		}
		for (int i = 34; i < 60; i += 7) {
			createYellowPellet(69, i);
		}
		createYellowPellet(69, 61);
		// horizontal under home
		for (int i = 44; i < 62; i += 6) {
			createYellowPellet(i, 34);
		}
		// vertical above home
		for (int i = 55; i < 72; i += 6) {
			createYellowPellet(56, i);
		}
		for (int i = 55; i < 72; i += 6) {
			createYellowPellet(44, i);
		}
		// horizontal above home
		for (int i = 31; i < 45; i += 7) {
			createYellowPellet(i, 67);
		}
		for (int i = 62; i < 75; i += 7) {
			createYellowPellet(i, 67);
		}
		// vertical third to right column
		for (int i = 35; i < 58; i += 8) {
			createYellowPellet(82, i);
		}
		for (int i = 66; i < 75; i += 7) {
			createYellowPellet(82, i);
		}
		// right vertical
		createYellowPellet(94, 51);
		createYellowPellet(94, 59);
		for (int i = 14; i < 30; i += 7) {
			createYellowPellet(94, i);
		}
		// second to right vertical
		for (int i = 59; i < 75; i += 7) {
			createYellowPellet(87, i);
		}
		for (int i = 35; i < 58; i += 8) {
			createYellowPellet(87, i);
		}
	}

	private void createYellowPellet(int posX, int posY) {
		Pellet p = new YellowPellet(posX, posY, world);
		addPellet(p);
	}

	private void createBonusPellets() {
		createBonusPellet(31, 74);
		createBonusPellet(70, 74);
		createBonusPellet(11, 57);
		createBonusPellet(50, 54);
		createBonusPellet(24, 49);
		createBonusPellet(88, 28);
		createBonusPellet(17, 22);
		createBonusPellet(70, 28);
		createBonusPellet(31, 28);
		createBonusPellet(76, 53);
	}

	private void createBonusPellet(int posX, int posY) {
		// TODO Auto-generated method stub
		Pellet bp = new BonusPellet(posX, posY, world);
		addPellet(bp);
	}

	private void addPellet(Pellet p) {
		pellets.add(p);
		group.add(p.getNode());
	}

	private void addKeyListeners(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case SHIFT:
					timeline.playFromStart();
					moveGhostsStep();
					break;
				case UP:
					P1intendedMoveDir = MoveDir.UP;
					// if (checkMovable(pacman1.getFixture().getBody(),
					// MoveDir.UP)) {
					// pacman1.setDirection(0.0f, 20.0f, 270);
					// }
					break;
				case DOWN:
					P1intendedMoveDir = MoveDir.DOWN;
					// if (checkMovable(pacman1.getFixture().getBody(),
					// MoveDir.DOWN)) {
					// //pacman1.getFixture().getBody().applyLinearImpulse(tmpV1.set(3.6f,
					// 0).scl(pacman1.getFixture().getBody().getMass()),
					// pacman1.getFixture().getBody().getWorldCenter(), true);
					// pacman1.setDirection(0.0f, -20.0f, 90);
					// }
					break;
				case LEFT:
					P1intendedMoveDir = MoveDir.LEFT;
					// if (checkMovable(pacman1.getFixture().getBody(),
					// MoveDir.LEFT)) {
					// pacman1.setDirection(-20.0f, 0.0f, 180);
					// break;
					// }
				case RIGHT:
					P1intendedMoveDir = MoveDir.RIGHT;
					// if (checkMovable(pacman1.getFixture().getBody(),
					// MoveDir.RIGHT)) {
					// pacman1.setDirection(20.0f, 0.0f, 0);
					// }
					break;
				case S:// LEFT
					P2intendedMoveDir = MoveDir.LEFT;
					// if (checkMovable(pacman2.getFixture().getBody(),
					// MoveDir.LEFT)) {
					// pacman2.setDirection(-20.0f, 0.0f, 180);
					// }
					break;
				case D:// Down
					P2intendedMoveDir = MoveDir.DOWN;
					// if (checkMovable(pacman2.getFixture().getBody(),
					// MoveDir.DOWN)) {
					// pacman2.setDirection(0.0f, -20.0f, 90);
					// }
					break;
				case F:// Right
					P2intendedMoveDir = MoveDir.RIGHT;
					// if (checkMovable(pacman2.getFixture().getBody(),
					// MoveDir.RIGHT)) {
					// pacman2.setDirection(20.0f, 0.0f, 0);
					// }
					break;
				case E:// Up
					P2intendedMoveDir = MoveDir.UP;
					// if (checkMovable(pacman2.getFixture().getBody(),
					// MoveDir.UP)) {
					// pacman2.setDirection(0.0f, 20.0f, 270);
					// }
					break;

				default:
					break;
				}
			}
		});
	}

	private boolean isValidMove(Pacman pacman, MoveDir dir) {
		Pacman p = new Pacman(pacman);
		p.setDirection(dir);
		if (!p.isColliding()) {
			world.destroyBody(p.getFixture().getBody());
			return true;
		}
		return false;

	}

	private boolean checkMovable(Body body, MoveDir dir) {
		canMove = true;
		RayCastCallback rayCastCallback = new RayCastCallback() {
			public float reportFixture(Fixture fixture, Vec2 point,
					Vec2 normal, float fraction) {
				if (((UniqueObject) fixture.getBody().getUserData())
						.getDescription() == "WALL") {
					canMove = false;
					return 0;
				}
				return 0;
			}
		};

		tmpV1.set(body.getPosition());
		MoveDir direction = dir;
		switch (direction) {
		case UP:
			tmpV2.set(20.0f, 0.0f);
			break;
		case DOWN:
			tmpV2.set(0.0f, -20.0f);
			break;
		case LEFT:
			tmpV2.set(-20.0f, 0.0f);
			break;
		case RIGHT:
			tmpV2.set(20.0f, 0.0f);
			break;
		case NONE:
			canMove = false;
			break;
		}

		world.raycast(rayCastCallback, tmpV1, tmpV2);

		return canMove;
	}

	public void play(String[] args) {
		Application.launch(args);
	}
}
