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
import objectsPackage.Wall;
import objectsPackage.YellowPellet;
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
	private Ghost[] ghosts = new Ghost[4];
	// private int numBonusPellets; //I think they can be counted together, and
	// when they're both all finished - you finished the round!
	final Timeline timeline = new Timeline();
	private CollisionContactListener contactListener;

	private ArrayList<Pellet> pellets = new ArrayList<Pellet>();

	private ScorePanel scorePanel1 = new ScorePanel();
	private ScorePanel scorePanel2 = new ScorePanel();
	private ArrayList<Pacman> pacmanArray = new ArrayList<Pacman>();
	private Label scoreLabel;
	private Label scoreValueLabel;
	private Label scoreLabel2;
	private Label scoreValueLabel2;
	private ArrayList<Label> pacmanLives1;
	private ArrayList<Label> pacmanLives2;
	private Label gameOverLabel;
	private boolean gameOver;
	private int life;
	private ObservableList<Node> group;
	private final long timeStart = System.currentTimeMillis();

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		setStageProperties(stage);
		// Create a group for holding all objects on the screen.
	
		rootGroup = new Group();
		setScorePanels();
		group = rootGroup.getChildren();
		contactListener = new CollisionContactListener(rootGroup, pellets, scorePanel1, scorePanel2, pacmanArray);
		scene = new Scene(rootGroup, Properties.WIDTH, Properties.HEIGHT, Color.BLACK);

		pacmanLives1 = new ArrayList<Label>();
		pacmanLives2 = new ArrayList<Label>();
		life = 0;
		setPacmanLives();
		gameOverLabel = new Label("GAME OVER");
		gameOverLabel.setFont(new Font(90));
		gameOverLabel.setTranslateX(120);
		gameOverLabel.setTranslateY(150);
		gameOverLabel.setTextFill(Color.WHITE);
		gameOverLabel.setVisible(false);

		rootGroup.getChildren().add(gameOverLabel);
		gameOver = false;
		createShapes();
		world.setContactListener(contactListener);
		startSimulation();
		addKeyListeners(scene);
		stage.setScene(scene);
		stage.show();
	}

	private void setScorePanels() {
		scoreLabel = new Label("Score: ");
		scoreLabel.setTranslateX(25);
		scoreLabel.setTranslateY(25);
		scoreLabel.setTextFill(Color.YELLOW);
		scoreValueLabel = new Label();
		scoreValueLabel.setTextFill(Color.YELLOW);
		scoreValueLabel.setTranslateX(70);
		scoreValueLabel.setTranslateY(25);

		scoreLabel2 = new Label("Score: ");
		scoreLabel2.setTranslateX(25);
		scoreLabel2.setTranslateY(45);
		scoreLabel2.setTextFill(Color.YELLOW);
		scoreValueLabel2 = new Label();
		scoreValueLabel2.setTextFill(Color.YELLOW);
		scoreValueLabel2.setTranslateX(75);
		scoreValueLabel2.setTranslateY(45);

		rootGroup.getChildren().add(scoreLabel);
		rootGroup.getChildren().add(scoreValueLabel);

		rootGroup.getChildren().add(scoreLabel2);
		rootGroup.getChildren().add(scoreValueLabel2);

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
				removeFixturesAndPellets();
				scoreValueLabel.setText(String.valueOf(pacman1.getScore()));
				// Move pacmans to the new position computed by JBox2D
				movePacman(pacman1);
				movePacman(pacman2);
				moveGhostsStep();
				if (contactListener.isPacmanLost()) {
					contactListener.setPacmanLoss(false);
					pacmanLives1.get(life).setGraphic(null);
					if (life < 3) {
						//life++;
					}
				}
				if (pacman1.getLives() <= 0 || pacman2.getLives() <= 0) {
					gameOverLabel.setVisible(true);
					timeline.stop();
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

	private void moveGhostsStep() {
		// TODO Auto-generated method stub
		for (Ghost g : ghosts) {
			moveAGhost(g);
		}
	}

	private void moveAGhost(Ghost g) {
		// TODO Auto-generated method stub
		Body body = (Body) g.getNode().getUserData();
		body.setLinearVelocity(new Vec2(-20.0f, 0.0f));
		g.changeDirection();
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
		contactListener.getPacmanColliding().clear();
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
		// WALLS
		// top wall
		createWall(0, 85, 100, 1);
		// bottom wall
		createWall(0, 5, 100, 1);
		// right wall
		createWall(99, 37, 2, 100);
		// left wall
		createWall(0, 50, 2, 100);

		// west
		createWall(18, 16, 9, 3);
		createWall(12, 69, 3, 8);
		createWall(12, 40, 3, 14);
		createWall(25, 65, 3, 12);
		createWall(25, 36, 3, 10);

		// north
		createWall(34, 60, 6, 3);
		createWall(67, 60, 6, 3);
		createWall(50, 65, 3, 8);
		createWall(50, 74, 15, 3);

		// south
		createWall(50, 29, 15, 3);
		createWall(43, 23, 3, 3);
		createWall(57, 23, 3, 3);
		createWall(37, 9, 3, 3);
		createWall(50, 9, 3, 3);
		createWall(63, 9, 3, 3);

		// east
		createWall(81, 74, 8, 3);
		createWall(81, 47, 8, 3);
		createWall(81, 16, 8, 3);
		createWall(92, 60, 5, 3);
		createWall(92, 31, 5, 5);

		createWall(76, 67, 3, 10);
		createWall(76, 38, 3, 12);

		// center
		createWall(50, 49, 9, 1);
		createWall(50, 40, 9, 1);
	}

	private void createWall(int posX, int posY, int width, int height) {
		group.add(new Wall(posX, posY, world, width, height, Color.MAGENTA).getNode());
	}

	public void createPacmans() {
		pacmanArray.add(pacman1 = createPacman(50, 80, "pacman1"));
		pacmanArray.add(pacman2 = createPacman(50, 22, "pacman2"));
	}

	private Pacman createPacman(int x, int y, String name) {
		Pacman pacman = new Pacman(x, y, world, name);
		rootGroup.getChildren().add(pacman.getNode());
		return pacman;
	}

	private void createGhosts() {
		ghosts[0] = new Ghost(42, 44, world, "/blueGhost.png");
		ghosts[1] = new Ghost(47, 44, world, "/pinkGhost.png");
		ghosts[2] = new Ghost(53, 44, world, "/orangeGhost.png");
		ghosts[3] = new Ghost(58, 44, world, "/redGhost.png");

		for (Ghost g : ghosts) {
			group.add(g.getNode());
		}
	}

	private void createPellets() {
		//bottom line across
		for (int i = 13; i < 31; i += 8) {
			createYellowPellet(i, 9);
			}
		createYellowPellet(44, 9);
		createYellowPellet(56, 9);
		for (int i = 70; i < 90; i += 8) {
			createYellowPellet(i, 9);
		}
		
		//second to bottom across
		//createYellowPellet(6, 16);
		for (int i = 36; i < 68; i += 7) {
			createYellowPellet(i, 16);
		}	
		createYellowPellet(93, 16);
		
		//third to bottom across
		for (int i = 13; i < 40; i += 7) {
			createYellowPellet(i, 23);
		}
		for (int i = 65; i < 95; i += 7) {
			createYellowPellet(i, 23);
		}
		
		//vertical left column
		for (int i = 16; i < 75; i += 7) {
			createYellowPellet(6, i);
		}
		//second to left vertical column
		for (int i = 30; i < 75; i += 7) {
			createYellowPellet(18, i);
		}
		
		//top row across
		for (int i = 6; i < 45; i += 7) {
			createYellowPellet(i, 80);
		}
		for (int i = 58; i < 95; i += 7) {
			createYellowPellet(i, 80);
		}
		
		//left inner home down
		for (int i = 34; i < 60; i += 7) {
			createYellowPellet(31, i);
		}
		for (int i = 34; i < 60; i += 7) {
			createYellowPellet(38, i);
		}
		//right inner home down
		for (int i = 34; i < 60; i += 7) {
			createYellowPellet(62, i);
		}
		for (int i = 34; i < 60; i += 7) {
			createYellowPellet(69, i);
		}
		createYellowPellet(76, 55);
		
		//horizontal under home
		for (int i = 44; i < 62; i += 6) {
			createYellowPellet(i, 34);
		}
		
		//vertical above home
		for (int i = 55; i < 72; i += 6) {
			createYellowPellet(56, i);
		}
		for (int i = 55; i < 72; i += 6) {
			createYellowPellet(44, i);
		}
				
		//horizontal above home
		for (int i = 31; i < 45; i += 7) {
			createYellowPellet(i, 67);
		}
		for (int i = 62; i < 75; i += 7) {
			createYellowPellet(i, 67);
		}
		
		//vertical second to right column
		for (int i = 28; i < 45; i += 6) {
			createYellowPellet(83, i);
		}
		for (int i = 55; i < 70; i += 6) {
			createYellowPellet(83, i);
		}
		createYellowPellet(88,40);
		//createYellowPellet(88,55);
		createYellowPellet(88,67);
		
		//vertical right column
		for (int i = 48; i < 58; i += 7) {
			createYellowPellet(93, i);
		}
		for (int i = 67; i < 75; i += 6) {
			createYellowPellet(93, i);
		}
		
	}

	private void createYellowPellet(int posX, int posY) {
		Pellet p = new YellowPellet(posX, posY, world);
		addPellet(p);
	}

	private void createBonusPellets() {
		createBonusPellet(32, 74);
		createBonusPellet(70, 74);
		createBonusPellet(25, 49);
		createBonusPellet(12, 58);
		createBonusPellet(50, 54);
		createBonusPellet(93, 9);
		createBonusPellet(30, 16);
		createBonusPellet(70, 16);
		createBonusPellet(6, 9);
		createBonusPellet(93,40);
		
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
					pacman1.setDirection(0.0f, 20.0f, 270);
					break;
				case DOWN:
					pacman1.setDirection(0.0f, -20.0f, 90);
					break;
				case LEFT:
					pacman1.setDirection(-20.0f, 0.0f, 180);
					break;
				case RIGHT:
					pacman1.setDirection(20.0f, 0.0f, 0);
					break;
				case S:// LEFT
					pacman2.setDirection(-20.0f, 0.0f, 180);
					break;
				case D:// Down
					pacman2.setDirection(0.0f, -20.0f, 90);
					break;
				case F:// Right
					pacman2.setDirection(20.0f, 0.0f, 0);
					break;
				case E:// Up
					pacman2.setDirection(0.0f, 20.0f, 270);
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