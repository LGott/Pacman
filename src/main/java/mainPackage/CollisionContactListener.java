package mainPackage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.util.Duration;
import objectsPackage.Ghost;
import objectsPackage.Pacman;
import objectsPackage.Pellet;
import objectsPackage.UniqueObject;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Distance;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionContactListener implements ContactListener {

	
	private ArrayList<Pellet> pellets;
	private ArrayList<Fixture> fixturesToRemove;
	private ArrayList<Pellet> pelletsToRemove;
	private ArrayList<Integer> pacmanColliding;
	private ArrayList<Pacman> pacmanArray;
	private ArrayList<Pacman> deadPacmans;
	private ArrayList<Ghost> ghosts;
	private String determinePacman;
	private boolean isInvincible;
	private boolean pacmanLost;
	private boolean colliding;
	private boolean collidingWithWall;

	public CollisionContactListener(Group rootGroup, ArrayList<Pellet> pellet, ArrayList<Pacman> pacmanArray,
			ArrayList<Ghost> ghosts) {
		this.ghosts = ghosts;
		this.colliding = false;
		this.pellets = pellet;
		this.fixturesToRemove = new ArrayList<Fixture>();
		this.pelletsToRemove = new ArrayList<Pellet>();
		this.pacmanArray = pacmanArray;
		this.pacmanColliding = new ArrayList<Integer>();
		this.pacmanLost = false;
		this.deadPacmans = new ArrayList<Pacman>();
		this.determinePacman = "Neutral";
		this.isInvincible = false;
	}

	public void beginContact(Contact contact) {
		this.colliding = true;
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		UniqueObject obj1 = (UniqueObject) f1.getBody().getUserData();
		UniqueObject obj2 = (UniqueObject) f2.getBody().getUserData();

		if (obj1.getDescription() == "PACMAN" && obj2.getDescription() == "PELLET") {
			removePellet(f2, obj2);
			// Find the correct pacman and increment it's score
			Pacman pac = identifyPacman(obj1);
			pac.incrementScore(10);
		} else if (obj2.getDescription() == "PACMAN" && obj1.getDescription() == "PELLET") {
			removePellet(f2, obj1);
			// Find the correct pacman and increment it's score
			Pacman pac = identifyPacman(obj2);
			pac.incrementScore(10);

		} else if (obj2.getDescription() == "PACMAN" && obj1.getDescription() == "BONUS_PELLET") {
			// remove the bonus pellet
			removePellet(f1, obj1);
			Pacman pac = identifyPacman(obj2);
			if (isInvincible) { // If pacman eats a cherry while he is
								// invincible he gets 500 points
				pac.incrementScore(500);
			}
			pac.incrementScore(50);
			ghostEffects();
			setInvincible();

		} else if (obj1.getDescription() == "PACMAN" && obj2.getDescription() == "BONUS_PELLET") {
			removePellet(f2, obj2);
			Pacman pac = identifyPacman(obj1);
			if (isInvincible) {
				// If pacman eats a cherry while he is invincible
				// he gets 500 points
				pac.incrementScore(500);
			}
			pac.incrementScore(50);
			ghostEffects();
			setInvincible();

		} else if (obj1.getDescription() == "WALL" && obj2.getDescription() == "GHOST") {
			System.out.println("Bang");
			turnGhost(obj2);

		} else if (obj2.getDescription() == "WALL" && obj1.getDescription() == "GHOST") {
			System.out.println("Bang");
			turnGhost(obj1);

		}
		// else if (obj1.getDescription() == "GHOST" && obj2.getDescription() ==
		// "GHOST") {
		// turnGhost(obj1);
		// }

		else if (obj1.getDescription() == "PACMAN" && obj2.getDescription() == "GHOST"
				|| (obj1.getDescription() == "GHOST" && obj2.getDescription() == "PACMAN")) {

			if (!isInvincible) {
				pacmanLost = true;

				if (obj1.getDescription() == "PACMAN") {
					Pacman pac = identifyPacman(obj1);
					if (pac.getLives() > 0) {
						this.determinePacman = pac.getName();
						pac.decrementLives();
						deadPacmans.add(pac);
						System.out.println(pac.getName() + " added to dead list");
					}
				} else if (obj2.getDescription() == "PACMAN") {
					Pacman pac = identifyPacman(obj2);
					if (pac.getLives() > 0) {
						this.determinePacman = pac.getName();
						pac.decrementLives();
						deadPacmans.add(pac);
						System.out.println(pac.getName() + " added to dead list");
					}
				}
			}
			System.out.println("pacman-ghost   are colliding");

		} else if (obj1.getDescription() == "WALL" && obj2.getDescription() == "PACMAN") {
			collidingWithWall = true;
			for (int i = 0; i < pacmanArray.size(); i++) {
				if (pacmanArray.get(i).getObjectDescription().getID() == obj2.getID()) {
					pacmanArray.get(i).setColliding(true);
					break;
				}
			}
		}
	}

	private void setInvincible() {
		isInvincible = true;
		Timer timer = new java.util.Timer();
		timer.schedule(new DelayTask(), 10 * 1000);
		// Allow pacman to be invincible for 10 seconds
	}

	class DelayTask extends TimerTask {
		public void run() {
			isInvincible = false;
			for (Ghost ghost : ghosts) {
				ghost.resetColor();
			}
		}
	}

	private void ghostEffects() {
		for (Ghost ghost : ghosts) {
			ghost.turnBlue();
			FadeTransition ft = new FadeTransition(Duration.millis(1000), ghost.getNode());
			ft.setFromValue(3.0);
			ft.setToValue(0.2);
			ft.setCycleCount(10);
			ft.setAutoReverse(true);
			ft.play();
		}
	}

	public boolean isColliding() {
		return colliding;
	}

	public boolean isPacmanLost() {
		return this.pacmanLost;
	}

	public String determinePacman() {
		return this.determinePacman;
	}

	public void setPacmans(String name) {
		this.determinePacman = name;
	}

	private void turnGhost(UniqueObject obj) {
		for (Ghost g : ghosts) {
			if (g.getObjectDescription().getID() == obj.getID()) {
				System.out.println("TURN GHOST");
				g.changeDirection();
				break;
			}
		}
	}

	private Pacman identifyPacman(UniqueObject obj1) {
		Pacman pac = null;
		for (int i = 0; i < pacmanArray.size(); i++) {
			if (pacmanArray.get(i).getObjectDescription().getID() == obj1.getID()) {
				pac = pacmanArray.get(i);
				break;
			}
		}
		return pac;
	}

	public void resetPacmanArray(ArrayList<Pacman> newPacmans) {
		this.pacmanArray = newPacmans;
	}

	public void setPacmanLoss(boolean lost) {
		pacmanLost = lost;
	}

	public ArrayList<Integer> getPacmanColliding() {
		return pacmanColliding;
	}

	private void removePellet(Fixture f, UniqueObject obj) {
		fixturesToRemove.add(f);
		removePelletShape(obj);
	}

	private void removePelletShape(UniqueObject obj) {
		for (int i = 0; i < pellets.size(); i++) {
			if (pellets.get(i).getObjectDescription().getID() == obj.getID()) {
				pelletsToRemove.add(pellets.get(i));
				pellets.remove(i);
				break;
			}
		}
	}

	public void endContact(Contact contact) {
		colliding = false;
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		UniqueObject obj1 = (UniqueObject) f1.getBody().getUserData();
		UniqueObject obj2 = (UniqueObject) f2.getBody().getUserData();

		if (obj1.getDescription() == "WALL" && obj2.getDescription() == "PACMAN") {
			collidingWithWall = false;
			setPacmanColliding(obj2);
		}
	}

	private void setPacmanColliding(UniqueObject obj) {
		for (int i = 0; i < pacmanArray.size(); i++) {
			if (pacmanArray.get(i).getObjectDescription().getID() == obj.getID()) {
				pacmanArray.get(i).setColliding(false);
				break;
			}
		}
	}

	public boolean isCollidingWithWall() {
		return collidingWithWall;
	}

	public void preSolve(Contact arg0, Manifold arg1) {
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

	public ArrayList<Pellet> getPelletsToRemove() {
		return pelletsToRemove;
	}

	public ArrayList<Fixture> getFixturesToRemove() {
		return fixturesToRemove;
	}

	public ArrayList<Pacman> getDeadPacmans() {
		return deadPacmans;
	}
}