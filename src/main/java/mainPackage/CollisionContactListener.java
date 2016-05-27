package mainPackage;

import java.util.ArrayList;

import javafx.scene.Group;
import objectsPackage.Ghost;
import objectsPackage.Pacman;
import objectsPackage.Pellet;
import objectsPackage.UniqueObject;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
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
	private boolean pacmanLost;
private ArrayList<Ghost> ghosts;
	public boolean isColliding() {
		return colliding;
	}

	public boolean isPacmanLost() {
		return this.pacmanLost;
	}

	public CollisionContactListener(ArrayList<Pellet> pellet,
			ScorePanel scorePanel, ArrayList<Pacman> pacmanArray, ArrayList<Ghost> ghosts) {

		colliding = false;
		this.pellets = pellet;
		this.scorePanel = scorePanel;
		fixturesToRemove = new ArrayList<Fixture>();
		pelletsToRemove = new ArrayList<Pellet>();
		this.pacmanArray = pacmanArray;
		pacmanColliding = new ArrayList<Integer>();
		pacmanLost = false;
		this.ghosts=ghosts;
	}

	public void beginContact(Contact contact) {
		colliding = true;
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		UniqueObject obj1 = (UniqueObject) f1.getBody().getUserData();
		UniqueObject obj2 = (UniqueObject) f2.getBody().getUserData();
		System.out.println("contacts " + obj1.getDescription() + " and "
				+ obj2.getDescription());
		if (obj1.getDescription() == "PACMAN"
				&& obj2.getDescription() == "PELLET") {
			removePellet(f2, obj2);
			scorePanel.incrementScore(10);

		} else if (obj1.getDescription() == "PACMAN"
				&& obj2.getDescription() == "BONUS_PELLET") {
			
			removePellet(f2, obj2);
			scorePanel.incrementScore(50);
		}
		else if (obj1.getDescription() == "WALL"
				&& obj2.getDescription() == "GHOST") {

//			float xpos = Properties.jBoxToFxPosX(f2.getBody().getPosition().x);
//			float ypos = Properties.jBoxToFxPosY(f2.getBody().getPosition().y);
//			f2.getBody().setAngularVelocity(xpos);
//			f2.getBody().applyTorque(20);
//			f2.getBody().setAngularVelocity(20);
//			f2.getBody().setLinearVelocity(new Vec2(20.0f, 0.0f));
			turnGhost(obj2);
			
			

		}else if(obj2.getDescription() == "WALL"
				&& obj1.getDescription() == "GHOST"){
			turnGhost(obj1);
		}
		else if(obj1.getDescription() == "GHOST" && obj2.getDescription() == "GHOST"){
			
		}

		else if (obj1.getDescription() == "PACMAN"
				&& obj2.getDescription() == "GHOST"
				|| (obj1.getDescription() == "GHOST" && obj2.getDescription() == "GHOST")
				|| (obj1.getDescription() == "GHOST" && obj2.getDescription() == "PACMAN")) {
			// remove an extra pacman
			// System.out.println("here");
			scorePanel.decrementLives();
			pacmanLost = true;
			System.out.println("pacman-ghost   are colliding");

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

	private void turnGhost(UniqueObject obj) {
		// TODO Auto-generated method stub
		for(Ghost g: ghosts){
			if(g.getObjectDescription().getID()==obj.getID()){
				g.turnGhost();
				break;
			}
		}
		
		
		
	}

	public void setPacmanLoss(boolean lost) {
		pacmanLost = lost;
	}

	public ArrayList<Integer> getPacmanColliding() {
		return pacmanColliding;
	}

	private void removePellet(Fixture f2, UniqueObject obj2) {
		fixturesToRemove.add(f2);
		removePelletShape(obj2);
	}

	private void removePelletShape(UniqueObject obj2) {
		// TODO Auto-generated method stub
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
			setPacmanColliding(obj2);
		}
	}

	private void setPacmanColliding(UniqueObject obj2) {
		// TODO Auto-generated method stub
		for (int i = 0; i < pacmanArray.size(); i++) {
			if (pacmanArray.get(i).getObjectDescription().getID() == obj2
					.getID()) {
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
}