package mainPackage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
import javafx.stage.Stage;
import javafx.util.Duration;
import objectsPackage.Ghost;
import objectsPackage.Pacman;
import objectsPackage.Pellet;
import objectsPackage.UniqueObject;
import objectsPackage.Wall;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import com.google.inject.Singleton;

@Singleton
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
	final Timeline timeline = new Timeline();
	private CollisionContactListener contactListener;

	private ArrayList<Pellet> pellets = new ArrayList<Pellet>();
	private ArrayList<Pacman> pacmanArray = new ArrayList<Pacman>();
	private ArrayList<Label> pacmanLives1;
	private ArrayList<Label> pacmanLives2;

	private Label scoreValueLabel;
	private Label scoreValueLabel2;
	private Label gameOverLabel;
	private Label outLabel;

	private int life;
	private int life2;
	private ObservableList<Node> group;
	private final long timeStart = System.currentTimeMillis();
	private Timer timer;
	private MoveDir P2intendedMoveDir = MoveDir.NONE;
	private MoveDir P1intendedMoveDir = MoveDir.NONE;
	private MoveDir P1currentDir = MoveDir.RIGHT;
	private MoveDir P2currentDir = MoveDir.RIGHT;
	private final Vec2 tmpV1 = new Vec2();
	private final Vec2 tmpV2 = new Vec2();
	private boolean canMove;

	private ComponentSetup componentSetup;
	private LabelSetup labelSetup;

	public enum MoveDir {
		UP, DOWN, LEFT, RIGHT, NONE
	}

	@Override
	public void start(Stage stage) throws Exception {
		setStageProperties(stage);
		this.rootGroup = new Group();
		this.group = rootGroup.getChildren();
		// Create a group for holding all objects on the screen.
		this.ghosts = new ArrayList<Ghost>();

		this.pacmanLives1 = new ArrayList<Label>();
		this.pacmanLives2 = new ArrayList<Label>();

		this.componentSetup = new ComponentSetup(this);
		this.componentSetup.createShapes();

		this.labelSetup = new LabelSetup(this);
		this.labelSetup.setLabels();
	//	this.gameOverLabel = new Label("   GAME OVER" + "\n" + "Press R to restart");
		this.scene = new Scene(rootGroup, Properties.WIDTH, Properties.HEIGHT, Color.BLACK);

		this.contactListener = new CollisionContactListener(rootGroup, pellets, pacmanArray, ghosts);

		this.life = 0;
		this.life2 = 0;
		this.timer = new Timer();
		createWalls();
		world.setContactListener(contactListener);
		startSimulation();
		addKeyListeners(scene);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/pacmanIcon2.png")));

		stage.show();
	}

	private void setStageProperties(Stage stage) {
		stage.setWidth(Properties.WIDTH);
		stage.setHeight(Properties.HEIGHT);
		stage.setTitle("Pacman");
		stage.setResizable(false);
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
				createWalls();
				x++;
				removeFixturesAndPellets();
				scoreValueLabel.setText(String.valueOf(pacman1.getScore()));
				scoreValueLabel2.setText(String.valueOf(pacman2.getScore()));
				checkIntendedMove();

				// Move pacmans to the new position computed by JBox2D
				movePacman(pacman1);
				movePacman(pacman2);
				moveGhostsStep();
				onPacmanContact();
			}
		};
		/**
		 * Set ActionEvent and duration to the KeyFrame. The ActionEvent is
		 * trigged when KeyFrame execution is over.
		 */
		KeyFrame frame = new KeyFrame(duration, ae, null, null);
		timeline.getKeyFrames().add(frame);

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
		showLabelOnTimer(outLabel);
		componentSetup.createGhosts();

	}

	private void onPacmanContact() {
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
			if (pacman1.getLives() <= 0 || pacman2.getLives() <= 0 || pellets.isEmpty()) {
				gameOverLabel.setVisible(true);
				// showLabelOnTimer(gameOverLabel);
				timeline.stop();
			}
		}
	}

	public ArrayList<Label> getPacmanLives(){
		return pacmanLives2;
	}
	public ArrayList<Label> getPacmanLives1(){
		return pacmanLives1;
	}
	private void checkIntendedMove() {
		if (P1intendedMoveDir != MoveDir.NONE) {
			// automatic pacman movement based on input
			if (checkMovable(pacman1, P1intendedMoveDir)) {
				P1currentDir = P1intendedMoveDir;
				P1intendedMoveDir = MoveDir.NONE;
				movePacman(pacman1, P1currentDir);
			}
		}
		if (P2intendedMoveDir != MoveDir.NONE) {
			// automatic pacman movement based on input
			if (checkMovable(pacman2, P2intendedMoveDir)) {
				P2currentDir = P2intendedMoveDir;
				P2intendedMoveDir = MoveDir.NONE;
				movePacman(pacman2, P2currentDir);
			}
		}
	}

	private void showLabelOnTimer(Label label) {
		label.setVisible(true);
		timer.schedule(new ScheduledTask(label), 2 * 1000);
	}

	class ScheduledTask extends TimerTask {
		private Label newLabel;

		public ScheduledTask(Label label) {
			newLabel = label;
		}

		@Override
		public void run() {
			newLabel.setVisible(false);
		}
	}

	private void moveGhostsStep() {
		for (Ghost g : ghosts) {
			moveAGhost(g);
			if (x % 65 == 0) {
				g.changeDirection(System.currentTimeMillis());
			}
		}
	}

	private void moveAGhost(Ghost g) {
		animateGhost(g);
		Body body = (Body) g.getNode().getUserData();
		float xpos = Properties.jBoxToFxPosX(body.getPosition().x);
		float ypos = Properties.jBoxToFxPosY(body.getPosition().y);
		g.resetLayoutX(xpos);
		g.resetLayoutY(ypos);
	}

	private void animateGhost(Ghost ghost) {
		double time = (System.currentTimeMillis() - timeStart) / 1000.0;
		ghost.animateGhost(time);

	}

	private void movePacman(Pacman pacman) {

		animatePacman(timeStart, pacman);
		Body pacBody = (Body) pacman.getNode().getUserData();
		float xpos = Properties.jBoxToFxPosX(pacBody.getPosition().x);
		float ypos = Properties.jBoxToFxPosY(pacBody.getPosition().y);
		pacman.resetLayoutX(xpos);
		pacman.resetLayoutY(ypos);
		pacman.resetSpeed();
	}

	private void removeFixturesAndPellets() {
		for (Fixture b : contactListener.getFixturesToRemove()) {
			world.destroyBody(b.getBody());
		}

		for (Pellet p : contactListener.getPelletsToRemove()) {
			group.remove(p.getNode());
		}

		// clear for next step
		contactListener.getPelletsToRemove().clear();
		contactListener.getFixturesToRemove().clear();

	}

	// Reset the game
	private void restartGame() {
		resetPacmansAndGhosts();
		componentSetup.createPellets();
		componentSetup.createBonusPellets();
		pacman1.resetLives();
		pacman2.resetLives();
		pacman1.resetScore();
		pacman2.resetScore();
		pacmanLives1.clear();
		pacmanLives2.clear();
		life = 0;
		life2 = 0;
		gameOverLabel.setVisible(false);

		for (Label pac : pacmanLives1) {
			group.remove(pac.getNodeOrientation());
		}
		for (Label pac2 : pacmanLives2) {
			group.remove(pac2.getNodeOrientation());
		}
		timeline.playFromStart();
	}

	public ObservableList<Node> getGroup() {
		return group;
	}

	public WorldLogic getWorld() {
		return this.world;
	}

	public Pacman getPacman1() {
		return pacman1;
	}

	public Pacman getPacman2() {
		return pacman2;
	}

	public ArrayList<Ghost> getGhosts() {
		return ghosts;
	}

	public ArrayList<Pacman> getPacmanArray() {
		return pacmanArray;
	}

	public void setPacmanArray(ArrayList<Pacman> pacmanArray) {
		this.pacmanArray = pacmanArray;
	}

	public ArrayList<Pellet> getPellets() {
		return pellets;
	}

	public void setPellets(ArrayList<Pellet> pellets) {
		this.pellets = pellets;
	}

	private void animatePacman(final long timeStart, Pacman pacman) {
		double time = (System.currentTimeMillis() - timeStart) / 1000.0;
		pacman.animatePacman(time);
	}

	private void addKeyListeners(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case SHIFT:
					if (!gameOverLabel.isVisible()) {
						timeline.playFromStart();
						moveGhostsStep();
					}
					break;
				case UP:
					P1intendedMoveDir = MoveDir.UP;
					if (checkMovable(pacman1, MoveDir.UP)) {
						movePacman(pacman1, MoveDir.UP);
					}
					break;
				case DOWN:
					P1intendedMoveDir = MoveDir.DOWN;
					if (checkMovable(pacman1, MoveDir.DOWN)) {
						movePacman(pacman1, MoveDir.DOWN);
					}
					break;
				case LEFT:
					P1intendedMoveDir = MoveDir.LEFT;
					if (checkMovable(pacman1, MoveDir.LEFT)) {
						movePacman(pacman1, MoveDir.LEFT);
					}
					break;
				case RIGHT:
					P1intendedMoveDir = MoveDir.RIGHT;
					if (checkMovable(pacman1, MoveDir.RIGHT)) {
						movePacman(pacman1, MoveDir.RIGHT);
					}
					break;
				case S:// LEFT
					P2intendedMoveDir = MoveDir.LEFT;
					if (checkMovable(pacman2, MoveDir.LEFT)) {
						movePacman(pacman2, MoveDir.LEFT);
					}
					break;
				case D:// Down
					P2intendedMoveDir = MoveDir.DOWN;
					if (checkMovable(pacman2, MoveDir.DOWN)) {
						movePacman(pacman2, MoveDir.DOWN);
					}
					break;
				case F:// Right
					P2intendedMoveDir = MoveDir.RIGHT;
					if (checkMovable(pacman2, MoveDir.RIGHT)) {
						movePacman(pacman2, MoveDir.RIGHT);
					}
					break;
				case E:// Up
					P2intendedMoveDir = MoveDir.UP;
					if (checkMovable(pacman2, MoveDir.UP)) {
						movePacman(pacman2, MoveDir.UP);
					}
					break;
				case P: // Pause
					timeline.pause();
					break;
				case ENTER:
					timeline.play();
					break;
				case R:
					restartGame();
					break;
				default:
					break;
				}
			}
		});

	}

	private void movePacman(Pacman pacman, MoveDir dir) {
		switch (dir) {
		case UP:
			pacman.setDirection(0.0f, 20.0f, 270);
			break;
		case DOWN:
			pacman.setDirection(0.0f, -20.0f, 90);
			break;
		case LEFT:
			pacman.setDirection(-20.0f, 0.0f, 180);
			break;
		case RIGHT:
			pacman.setDirection(20.0f, 0.0f, 0);
			break;
		default:
			break;
		}
	}

	private boolean checkMovable(Pacman pacman, final MoveDir dir) {
		RayCastCallback rayCastCallback = new RayCastCallback() {
			public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
				if (((UniqueObject) fixture.getBody().getUserData()).getDescription() == "WALL") {
					canMove = false;
					return 0;
				}
				return 0;
			}
		};
		Body body = pacman.getFixture().getBody();
		canMove = true;
		tmpV1.set(body.getPosition());

		for (float i = -3f; i <= 3f; i += 3f) {
			switch (dir) {
			case UP:
				tmpV2.set(body.getPosition().x + i, body.getPosition().y + 3.1f);
				break;
			case DOWN:
				tmpV2.set(body.getPosition().x + i, body.getPosition().y - 3.1f);
				break;
			case LEFT:
				tmpV2.set(body.getPosition().x - 3.1f, body.getPosition().y + i);
				break;
			case RIGHT:
				tmpV2.set(body.getPosition().x + 3.1f, body.getPosition().y + i);
				break;
			default:
				break;
			}

			if (!canMove)
				break;
			world.raycast(rayCastCallback, tmpV1, tmpV2);
		}

		return canMove;
	}

	private void createWalls() {
		// WALLS
		// top wall
		createWall(0, 84, 100, 1);
		// bottom wall
		createWall(0, 4, 100, 1);
		// right wall
		createWall(99, 37, 1, 74);
		// left wall
		createWall(0, 37, 1, 74);

		// west
		createWall(11, 15, 3, 3);
		createWall(24, 15, 3, 3);
		createWall(11, 68, 3, 8);
		createWall(11, 39, 3, 14);
		createWall(24, 64, 3, 12);
		createWall(24, 35, 3, 10);

		// north
		createWall(37, 60, 3, 3);
		createWall(63, 60, 3, 3);
		createWall(50, 73, 16, 3);
		createWall(50, 65, 3, 8);

		// south
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
		group.add(new Wall(posX, posY, world, width, height, Color.BLUE).getNode());
	}

	public void setPacman1(Pacman p ){
		this.pacman1 = p;
	}
	
	public void setPacman2(Pacman p ){
		this.pacman2 = p;
	}
	
	public void play(String[] args) {
		Application.launch(args);
	}
}
