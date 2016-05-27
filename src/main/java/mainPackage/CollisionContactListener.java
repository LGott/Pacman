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
	private ScorePanel scorePanel2;
	private ArrayList<Pellet> pellets;
	private ArrayList<Fixture> fixturesToRemove;
	private ArrayList<Pellet> pelletsToRemove;
	private boolean collidingWithWall;
	private ArrayList<Integer> pacmanColliding;
	private ArrayList<Pacman> pacmanArray;
	private ArrayList<Pacman> deadPacmans;
	private boolean pacmanLost;

	public boolean isColliding() {
		return colliding;
	}

	public boolean isPacmanLost() {
		return this.pacmanLost;
	}

	public CollisionContactListener(Group rootGroup, ArrayList<Pellet> pellet, ScorePanel scorePanel,
			ScorePanel scorePanel2, ArrayList<Pacman> pacmanArray) {

		colliding = false;
		this.pellets = pellet;
		this.scorePanel = scorePanel;

		this.scorePanel2 = scorePanel2;
		this.fixturesToRemove = new ArrayList<Fixture>();
		this.pelletsToRemove = new ArrayList<Pellet>();
		this.pacmanArray = pacmanArray;
		pacmanColliding = new ArrayList<Integer>();
		pacmanLost = false;
		deadPacmans = new ArrayList<Pacman>();
	}

	public void beginContact(Contact contact) {
		colliding = true;
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		UniqueObject obj1 = (UniqueObject) f1.getBody().getUserData();
		UniqueObject obj2 = (UniqueObject) f2.getBody().getUserData();
		System.out.println("contacts " + obj1.getDescription() + " and " + obj2.getDescription());
		if (obj1.getDescription() == "PACMAN" && obj2.getDescription() == "PELLET") {

			removePellet(f2, obj2);

			// Find the correct pacman and increment it's score
			Pacman pac = identifyPacman(obj1);
			pac.incrementScore(10);

			System.out.println("pacman-pellet");

		} 
		else if (obj2.getDescription() == "PACMAN" && obj1.getDescription() == "PELLET") {

			removePellet(f2, obj1);

			// Find the correct pacman and increment it's score
			Pacman pac = identifyPacman(obj2);
			pac.incrementScore(10);
			System.out.println("pacman-pellet");
		}else if (obj2.getDescription() == "PACMAN" && obj1.getDescription() == "BONUS_PELLET") {

			// remove the bonus pellet
			removePellet(f1, obj1);
			Pacman pac = identifyPacman(obj2);
			pac.incrementScore(50);

		} else if (obj1.getDescription() == "PACMAN" && obj2.getDescription() == "BONUS_PELLET") {
			removePellet(f2, obj2);
			Pacman pac = identifyPacman(obj1);
			pac.incrementScore(50);

			System.out.println("BONUS PELLET DETECTED");

		} else if (obj1.getDescription() == "WALL" && obj2.getDescription() == "GHOST"
				|| (obj1.getDescription() == "GHOST" && obj2.getDescription() == "GHOST")) {
			// float xpos =
			// Properties.jBoxToFxPosX(f2.getBody().getPosition().x);
			// float ypos =
			// Properties.jBoxToFxPosY(f2.getBody().getPosition().y);
			// f2.getBody().setAngularVelocity(xpos);
			// f2.getBody().applyTorque(20);
			// f2.getBody().setAngularVelocity(20);
			// f2.getBody().setLinearVelocity(new Vec2(20.0f, 0.0f));

		}

		else if (obj1.getDescription() == "PACMAN" && obj2.getDescription() == "GHOST"
				|| (obj1.getDescription() == "GHOST" && obj2.getDescription() == "PACMAN")) {
			// remove an extra pacman
			// System.out.println("here");

			pacmanLost = true;

			if (obj1.getDescription() == "PACMAN") {
				Pacman pac = identifyPacman(obj1);
				if (pac.getLives() > 0) {
					pac.decrementLives();
					deadPacmans.add(pac);
					System.out.println(pac.getName() + " added to dead list");
				}
			} else if (obj2.getDescription() == "PACMAN") {
				Pacman pac = identifyPacman(obj2);
				if (pac.getLives() > 0) {
					pac.decrementLives();
					deadPacmans.add(pac);
					System.out.println(pac.getName() + " added to dead list");
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

			System.out.println("pacman-wall");
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

	public void resetPacmanArray(ArrayList<Pacman> newPacmans){
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
		// TODO Auto-generated method stub
		for (int i = 0; i < pellets.size(); i++) {
			if (pellets.get(i).getObjectDescription().getID() == obj.getID()) {
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

		if (obj1.getDescription() == "WALL" && obj2.getDescription() == "PACMAN") {
			collidingWithWall = false;
			setPacmanColliding(obj2);
		}
	}

	private void setPacmanColliding(UniqueObject obj) {
		// TODO Auto-generated method stub
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

	public ArrayList<Pacman> getDeadPacmans(){
		return deadPacmans;
	}
}