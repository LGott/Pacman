package mainPackage;

import java.util.ArrayList;

import javafx.scene.Group;
import objectsPackage.Pacman;
import objectsPackage.Pellet;
import objectsPackage.UniqueObject;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionContactListener implements ContactListener {

	private boolean colliding;
	private ScorePanel scorePanel;
	private ArrayList<Pellet> pellets;
	private ArrayList<Fixture> fixturesToRemove;
	private ArrayList<Pellet> pelletsToRemove;
	private boolean collidingWithWall;
	private ArrayList<Integer> pacmanColliding;
	private ArrayList<Pacman> pacmanArray;

	public boolean isColliding() {
		return colliding;
	}

	public CollisionContactListener(Group rootGroup, ArrayList<Pellet> pellet,
			ScorePanel scorePanel, ArrayList<Pacman> pacmanArray) {

		colliding = false;
		this.pellets = pellet;
		this.scorePanel = scorePanel;
		this.fixturesToRemove = new ArrayList<Fixture>();
		this.pelletsToRemove = new ArrayList<Pellet>();
		this.pacmanArray = pacmanArray;
		this.pacmanColliding = new ArrayList<Integer>();
	}

	public void beginContact(Contact contact) {
		colliding = true;
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		UniqueObject obj1 = (UniqueObject) f1.getBody().getUserData();
		UniqueObject obj2 = (UniqueObject) f2.getBody().getUserData();

		// System.out.println("contacts " + obj1.getDescription() + " and " +
		// obj2.getDescription());

		if (obj1.getDescription() == "PACMAN"
				&& obj2.getDescription() == "PELLET") {

			System.out.println("contacts " + obj1.getDescription() + " and "
					+ obj2.getDescription());
			colliding = true;

			removePellet(f2, obj2);

			scorePanel.incrementScore(10);
			System.out.println("pacman-pellet");
		} else if (obj1.getDescription() == "PACMAN"
				&& obj2.getDescription() == "BONUS_PELLET") {
			// remove the bonus pellet
			colliding = true;

			removePellet(f2, obj2);

			scorePanel.incrementScore(50);

			// System.out.println("pacman-bonus pellet");
		}

		else if (obj1.getDescription() == "WALL"
				&& obj2.getDescription() == "GHOST"
				|| (f2.getBody().getUserData() == "GHOST" && f1.getBody()
						.getUserData() == "GHOST")) {

			colliding = true;
			System.out.println("contacts " + obj1.getDescription() + " and "
					+ obj2.getDescription());

			float xpos = Properties.jBoxToFxPosX(f2.getBody().getPosition().x);
			float ypos = Properties.jBoxToFxPosY(f2.getBody().getPosition().y);
			f2.getBody().setAngularVelocity(xpos);
			f2.getBody().setAngularVelocity(ypos);

		}

		else if (obj1.getDescription() == "PACMAN"
				&& obj2.getDescription() == "GHOST"
				|| (obj1.getDescription() == "GHOST" && obj2.getDescription() == "GHOST")
				|| (obj1.getDescription() == "GHOST" && obj2.getDescription() == "PACMAN")) {
			// remove an extra pacman
			// System.out.println("here");
			//scorePanel.decrementLives();
			for (int i = 0; i < pacmanArray.size(); i++) {
				if (pacmanArray.get(i).getObjectDescription().getID() == obj1
						.getID() || pacmanArray.get(i).getObjectDescription().getID() == obj2
						.getID()) {
					pacmanArray.get(i).initialPosition();
					break;
				}
			}
			System.out.println("pacman-ghost            are colliding");
		} else if (obj1.getDescription() == "WALL"
				&& obj2.getDescription() == "PACMAN") {
			collidingWithWall = true;
			for (int i = 0; i < pacmanArray.size(); i++) {
				if (pacmanArray.get(i).getObjectDescription().getID() == obj2
						.getID()) {
					pacmanArray.get(i).setColliding(true);
					break;
				}
			}

			System.out.println("pacman-wall");
		}

	}

	public ArrayList<Integer> getPacmanColliding() {
		return pacmanColliding;
	}

	private void removePellet(Fixture f2, UniqueObject obj2) {
		fixturesToRemove.add(f2);

		// remove the pellet
		for (int i = 0; i < pellets.size(); i++) {
			if (pellets.get(i).getObjectDescription().getID() == obj2.getID()) {
				pelletsToRemove.add(pellets.get(i));
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

		if (obj1.getDescription() == "WALL"
				&& obj2.getDescription() == "PACMAN") {

			collidingWithWall = false;
			for (int i = 0; i < pacmanArray.size(); i++) {
				if (pacmanArray.get(i).getObjectDescription().getID() == obj2
						.getID()) {
					pacmanArray.get(i).setColliding(false);
					break;
				}
			}
			System.out.println("Contact removed with wall and pacman");
		}

		// System.out.println("Contact removed");
	}

	public boolean isCollidingWithWall() {
		return collidingWithWall;
	}

	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Pellet> getPelletsToRemove() {
		return pelletsToRemove;
	}

	public ArrayList<Fixture> getFixturesToRemove() {
		return fixturesToRemove;
	}
}
