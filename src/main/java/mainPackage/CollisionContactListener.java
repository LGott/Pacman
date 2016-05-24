package mainPackage;

import java.util.ArrayList;

import javafx.scene.Group;
import objectsPackage.YellowPellet;
import objectsPackage.UniqueObject;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionContactListener implements ContactListener {

	private boolean colliding;
	private Group rootGroup;
	private ScorePanel scorePanel;
	private YellowPellet[] pellets;
	private ArrayList<Fixture> fixturesToRemove;
	private ArrayList<YellowPellet> pelletsToRemove;

	public boolean isColliding() {
		return colliding;
	}

	public CollisionContactListener(Group rootGroup, YellowPellet[] pellets,
			ScorePanel scorePanel) {
		colliding = false;
		this.rootGroup = rootGroup;
		this.pellets = pellets;
		this.scorePanel = scorePanel;
		this.fixturesToRemove = new ArrayList<Fixture>();
		this.pelletsToRemove = new ArrayList<YellowPellet>();
	}

	public void beginContact(Contact contact) {
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		UniqueObject obj1= (UniqueObject)f1.getBody().getUserData();
		UniqueObject obj2= (UniqueObject)f2.getBody().getUserData();
		System.out.println("contacts "+ obj1.getDescription() +" and "+ obj2.getDescription());
		if (obj1.getDescription() == "PACMAN"
				&&obj2.getDescription() == "PELLET") {
			colliding = true;

			fixturesToRemove.add(f2);

			 //remove the pellet
			for(int i=0; i<pellets.length; i++){
				if(pellets[i].getObjectDescription().getID() == obj2.getID()){
					pelletsToRemove.add(pellets[i]);
					break;
				}
			}
			
			scorePanel.incrementScore(10);
			System.out.println("pacman-pellet");
		} else if (obj1.getDescription()== "PACMAN"
				&& obj2.getDescription() == "BONUS_PELLET") {
			// remove the bonus pellet
			colliding = true;
			scorePanel.incrementScore(50);

			System.out.println("pacman-bonus pellet");
		}

		else if (obj1.getDescription() == "PACMAN"
				&& obj2.getDescription() == "GHOST"
				|| (f2.getBody().getUserData() == "GHOST" && f1.getBody()
				.getUserData() == "GHOST")) {
			// remove an extra pacman
			System.out.println("here");
			scorePanel.decrementLives();
			colliding = true;
			System.out.println("pacman-ghost");
		}
	}

	public void endContact(Contact contact) {
		colliding = false;
		System.out.println("Contact removed");
	}

	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	public ArrayList<YellowPellet> getPelletsToRemove() {
		return pelletsToRemove;
	}

	public ArrayList<Fixture> getFixturesToRemove() {
		return fixturesToRemove;
	}
}
