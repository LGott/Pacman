package mainPackage;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
	private Body pacmanBody1;
	private Body pacmanBody2;
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
	private ArrayList<Label> pacmanLives;
	private Label gameOverLabel;
	private boolean gameOver;
	private int life;

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		setStageProperties(stage);
		// Create a group for holding all objects on the screen.
		rootGroup = new Group();
		setScorePanels();
		contactListener = new CollisionContactListener(rootGroup, pellets, scorePanel1, scorePanel2, pacmanArray);
		scene = new Scene(rootGroup, Properties.WIDTH, Properties.HEIGHT, Color.BLACK);

	
		pacmanLives = new ArrayList<Label>();
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

	private void setPacmanLives() {
		for (int i = 0; i < 3; i++) {
			pacmanLives.add(new Label(""));
		}
		int value = 630;
		for (Label pac : pacmanLives) {
			Image image = new Image(getClass().getResourceAsStream("/pacman.png"));
			ImageView img = new ImageView(image);
			img.setFitWidth(25);
			img.setPreserveRatio(true);
			pac.setGraphic(img);
			pac.setTranslateX(value);
			pac.setTranslateY(25);
			rootGroup.getChildren().add(pac);
			value -= 45;
		}
	}

	private void startSimulation() {

		timeline.setCycleCount(Timeline.INDEFINITE);

		Duration duration = Duration.seconds(1.0 / 60.0); // Set duration for
		// frame.

		// Create an ActionEvent, on trigger it executes a world time step and
		// moves the objects to new position

		final long timeStart = System.currentTimeMillis();

		EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				world.step(1.0f / 60.f, 8, 3);
				removeFixturesAndPellets();
				scoreValueLabel.setText(String.valueOf(scorePanel1.getScore()));
				scoreValueLabel2.setText(String.valueOf(scorePanel2.getScore()));
				// Move pacmans to the new position computed by JBox2D
				movePacman(pacman1);
				movePacman(pacman2);
				// move ghosts
				moveGhostsStep();

				if (contactListener.isPacmanLost()) {
					contactListener.setPacmanLoss(false);
					pacmanLives.get(life).setGraphic(null);
					if (life < 3) {
						life++;
					}
				}

				if (scorePanel1.isGameOver()) {
					gameOverLabel.setVisible(true);
					timeline.stop();
				}

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
					rootGroup.getChildren().remove(p.getNode());
				}

				// clear for next step
				contactListener.getPacmanColliding().clear();
				contactListener.getPelletsToRemove().clear();
				contactListener.getFixturesToRemove().clear();

			}

			private void animatePacman(final long timeStart, Pacman pacman) {
				if (contactListener.isCollidingWithWall() && pacman.isColliding()) {
					pacman.setOpenPacman();
					System.out.println("touching walls");
				} else {
					double time = (System.currentTimeMillis() - timeStart) / 1000.0;
					pacman.animatePacman(time);
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
		createGhosts();
		createPacmans();
		createPellets();
		createBonusPellets(); // should createPellets call createBonusPellets?
	}

	/*
	 * private void createWalls() { createWall(50, 60, 5, 10); createWall(50,
	 * 60, 10, 5); createWall(25, 80, 5, 2); createWall(40, 80, 5, 2); // left
	 * wall createWall(1, 100, 1, 100); // right wall createWall(99, 100, 1,
	 * 100); // bottom wall createWall(0, 5, 100, 1); // top wall createWall(0,
	 * 100, 100, 1);
	 * 
	 * }
	 */
	private void createWalls() {
		// vertical center
		// rootGroup.getChildren().add(new Wall(50, 52, world, 1,
		// 48).getNode());
		// horizontal center
		// rootGroup.getChildren().add(new Wall(50, 50, world, 50,
		// 1).getNode());

		// south of center box // verticals
		rootGroup.getChildren().add(new Wall(50, 9, world, 1, 6, Color.ORANGE).getNode());
		rootGroup.getChildren().add(new Wall(30, 18, world, 1, 5, Color.ORANGE).getNode());
		rootGroup.getChildren().add(new Wall(10, 18, world, 1, 5, Color.ORANGE).getNode());
		rootGroup.getChildren().add(new Wall(50, 36, world, 1, 5, Color.ORANGE).getNode());
		rootGroup.getChildren().add(new Wall(89, 18, world, 1, 5, Color.ORANGE).getNode());

		// south of center box //horizontal
		rootGroup.getChildren().add(new Wall(15, 23, world, 6, 1, Color.YELLOW).getNode());
		rootGroup.getChildren().add(new Wall(30, 14, world, 12, 1, Color.YELLOW).getNode());
		rootGroup.getChildren().add(new Wall(50, 23, world, 12, 1, Color.YELLOW).getNode());

		rootGroup.getChildren().add(new Wall(84, 23, world, 6, 1, Color.YELLOW).getNode());
		rootGroup.getChildren().add(new Wall(70, 14, world, 11, 1, Color.YELLOW).getNode());

		// box
		// center left vertical
		rootGroup.getChildren().add(new Wall(39, 54, world, 1, 4, Color.RED).getNode());
		// center right vertical
		rootGroup.getChildren().add(new Wall(61, 54, world, 1, 4, Color.RED).getNode());
		// center bottom horizontal
		rootGroup.getChildren().add(new Wall(50, 49, world, 12, 1, Color.RED).getNode());
		// center top left horizontal
		rootGroup.getChildren().add(new Wall(42, 59, world, 4, 1, Color.RED).getNode());
		// center top left horizontal
		rootGroup.getChildren().add(new Wall(58, 59, world, 4, 1, Color.RED).getNode());

		// WALLS! DON'T TOUCH!
		// left wall
		rootGroup.getChildren().add(new Wall(1, 100, world, 1, 100, Color.BLUE).getNode());
		// right wall
		rootGroup.getChildren().add(new Wall(98, 100, world, 1, 100, Color.BLUE).getNode());
		// bottom wall
		rootGroup.getChildren().add(new Wall(0, 5, world, 100, 1, Color.BLUE).getNode());
		// top wall
		rootGroup.getChildren().add(new Wall(0, 100, world, 100, 1, Color.BLUE).getNode());

	}

	public void createWall(int posX, int posY, int width, int height) {
		rootGroup.getChildren().add(new Wall(posX, posY, world, width, height, Color.MAGENTA).getNode());
	}

	public void createPacmans() {
		pacmanArray.add(pacman1 = createPacman(50, 80));
		pacmanArray.add(pacman2 = createPacman(50, 20));
		pacmanBody1 = (Body) pacman1.getNode().getUserData();
		pacmanBody2 = (Body) pacman2.getNode().getUserData();
	}

	private Pacman createPacman(int x, int y) {
		Pacman pacman = new Pacman(x, y, world);
		rootGroup.getChildren().add(pacman.getNode());
		return pacman;
	}

	private void createGhosts() {
		ghosts[0] = new Ghost(30, 30, world, "/blueGhost.png");
		ghosts[1] = new Ghost(10, 10, world, "/pinkGhost.png");
		ghosts[2] = new Ghost(80, 80, world, "/orangeGhost.png");
		ghosts[3] = new Ghost(70, 70, world, "/redGhost.png");

		for (Ghost g : ghosts) {
			rootGroup.getChildren().add(g.getNode());
		}
	}

	private void createPellets() {
		for (int i = 10; i < 100; i += 10) {
			createYellowPellet(i, 15);
		}
	}

	private void createYellowPellet(int posX, int posY) {
		Pellet p = new YellowPellet(posX, posY, world);
		addPellet(p);
	}

	private void createBonusPellets() {
		for (int i = 30; i < 100; i += 10) {
			createBonusPellet(15, i);
		}

	}

	private void createBonusPellet(int posX, int posY) {
		// TODO Auto-generated method stub
		Pellet bp = new BonusPellet(posX, posY, world);
		addPellet(bp);
	}

	private void addPellet(Pellet p) {
		pellets.add(p);
		rootGroup.getChildren().add(p.getNode());
	}

	private void moveGhosts() {
		for (Ghost g : ghosts) {
			((Body) g.getNode().getUserData()).setLinearVelocity(new Vec2(0.0f, 20.0f));
			if (contactListener.isColliding()) {
				float xpos1 = Properties.jBoxToFxPosX(g.getPosX());
				float ypos1 = Properties.jBoxToFxPosY(g.getPosY());
				g.resetLayoutX(xpos1);
				g.resetLayoutY(ypos1);

			}
		}
	}

	private void addKeyListeners(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case SHIFT:
					timeline.playFromStart();
					moveGhosts();
					break;
				case UP:
					pacman1.setDirection(new Vec2(0.0f, 20.0f), 270);
					break;
				case DOWN:
					pacman1.setDirection(new Vec2(0.0f, -20.0f), 90);
					break;
				case LEFT:
					pacman1.setDirection(new Vec2(-20.0f, 0.0f), 180);
					break;
				case RIGHT:
					pacman1.setDirection(new Vec2(20.0f, 0.0f), 0);
					break;
				case S:// LEFT
					pacman2.setDirection(new Vec2(-20.0f, 0.0f), 180);
					break;
				case D:// Down
					pacman2.setDirection(new Vec2(0.0f, -20.0f), 90);
					break;
				case F:// Right
					pacman2.setDirection(new Vec2(20.0f, 0.0f), 0);
					break;
				case E:// Up
					pacman2.setDirection(new Vec2(0.0f, 20.0f), 270);
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