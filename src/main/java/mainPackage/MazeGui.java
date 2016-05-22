package mainPackage;

import java.awt.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objectsPackage.BonusPellet;
import objectsPackage.Ghost;
import objectsPackage.Pacman;
import objectsPackage.Pellet;
import objectsPackage.Wall;

import org.jbox2d.dynamics.Body;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import javafx.util.Duration;

public class MazeGui extends Application {
	private Group rootGroup;
	private Scene scene;
	private boolean doSleep = true;
	private Vec2 gravity = new Vec2(0.0f, 0.0f);
	private WorldLogic world = new WorldLogic(gravity, doSleep);
	private Pacman pacman1;
	private Pacman pacman2;
	private Ghost[] ghosts = new Ghost[4]; // HOW MANY??
	private int numPellets;
	private int numBonusPellets;
	final Timeline timeline = new Timeline();
	private int x = 0;
	private CollisionContactListener contactListener;
	Pellet[] pellets= new Pellet[10];
	private ScorePanel scorePanel= new ScorePanel();
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setWidth(Properties.WIDTH);
		stage.setHeight(Properties.HEIGHT);
		stage.setTitle("Pacman");

		stage.setResizable(false);

	    
		// Create a group for holding all objects on the screen.
		rootGroup = new Group();
		contactListener=new CollisionContactListener(rootGroup, pellets, scorePanel);

		Scene scene = new Scene(rootGroup, Properties.WIDTH, Properties.HEIGHT,
				Color.BLACK);

		createShapes();
		world.setContactListener(contactListener);
		startSimulation();
		addKeyListeners(scene);
		stage.setScene(scene);
		stage.show();
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
			//	if(!contactListener.isColliding()){
				world.step(1.0f / 60.f, 8, 3);
				x++;
				// Move pacman1 to the new position computed by JBox2D
				Body pacBody1 = (Body) pacman1.getNode().getUserData();
				float xpos1 = Properties.toPixelPosX(pacBody1.getPosition().x);
				float ypos1 = Properties.toPixelPosY(pacBody1.getPosition().y);
				pacman1.resetLayoutX(xpos1);
				pacman1.resetLayoutY(ypos1);
				
				// Move pacman2 to the new position computed by JBox2D
				Body pacBody2 = (Body) pacman2.getNode().getUserData();
				float xpos2 = Properties.toPixelPosX(pacBody2.getPosition().x);
				float ypos2 = Properties.toPixelPosY(pacBody2.getPosition().y);
				pacman2.resetLayoutX(xpos2);
				pacman2.resetLayoutY(ypos2);
				
				//move ghosts
				for (Ghost g : ghosts) {
					
					Body body = (Body) g.getNode().getUserData();
					float xpos = Properties.toPixelPosX(body.getPosition().x);
					float ypos = Properties.toPixelPosY(body.getPosition().y);
					g.resetLayoutX(xpos);
					g.resetLayoutY(ypos);
				
				}
			//}else{
			//	System.out.println("Collision");}
			if(scorePanel.isGameOver()){
				System.out.println("Game over");
				System.exit(0);
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
		//createGround();
		pacman1 = createPacman(50, 80);
		pacman2 = createPacman(50, 20);
		createGhosts();
		createPellets();
		createBonusPellets(); // should createPellets call createBonusPellets?

	}

	private void createWalls() {
		//rootGroup.getChildren().add(new Wall(60, 90, world, 5, 100).getNode());
		//rootGroup.getChildren().add(new Wall(60, 90, world, 100, 5).getNode());
		rootGroup.getChildren().add(
				new Wall(0, 5, world, 5, Properties.WIDTH).getNode());// bottom wall
		rootGroup.getChildren().add(
				new Wall(0, 100, world, 5, Properties.WIDTH).getNode());// ceiling
		rootGroup.getChildren().add(
				new Wall(98, 100, world,  Properties.HEIGHT,5)
						.getNode());// right wall
		rootGroup.getChildren().add(
				new Wall(0, 100, world,  Properties.HEIGHT,5)
						.getNode());// right wall
	}

	private Pacman createPacman(int x, int y) {
		Pacman pacman = new Pacman(x, y, world);
		rootGroup.getChildren().add(pacman.getNode());
		return pacman;
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
		Pellet[] pellets= new Pellet[10];
		for (int j=0, i = 10; i < 100; j++, i += 10) {
			pellets[j]= new Pellet(i, 15, world, 10);
			rootGroup.getChildren().add(pellets[j].getNode());
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
					((Body) pacman1.getNode().getUserData()).setLinearVelocity(new Vec2(0.0f, 20.0f));
					//gravity = new Vec2(0.0f, 10.0f);
					//world.setGravity(gravity);
					break;
				case DOWN:
					((Body) pacman1.getNode().getUserData()).setLinearVelocity(new Vec2(0.0f, -20.0f));
					//gravity = new Vec2(0.0f, -10.0f);
					//world.setGravity(gravity);
					break;
				case LEFT:
					((Body) pacman1.getNode().getUserData()).setLinearVelocity(new Vec2(-20.0f, 0.0f));
					//gravity = new Vec2(-10.0f, 0.0f);
					//world.setGravity(gravity);
					break;
				case RIGHT:
					((Body) pacman1.getNode().getUserData()).setLinearVelocity(new Vec2(20.0f, 0.0f));
					//gravity = new Vec2(10.0f, 0.0f);
					//world.setGravity(gravity);
					break;
				case S://LEFT
					((Body) pacman2.getNode().getUserData()).setLinearVelocity(new Vec2(-20.0f, 0.0f));
					break;
				case D://Down
					((Body) pacman2.getNode().getUserData()).setLinearVelocity(new Vec2(0.0f, -20.0f));
					break;
				case F://Right
					((Body) pacman2.getNode().getUserData()).setLinearVelocity(new Vec2(20.0f, 0.0f));
					break;
				case E://Up
					((Body) pacman2.getNode().getUserData()).setLinearVelocity(new Vec2(0.0f, 20.0f));
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