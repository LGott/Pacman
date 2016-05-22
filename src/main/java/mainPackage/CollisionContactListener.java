package mainPackage;

import javafx.scene.Group;
import objectsPackage.Ghost;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.*;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.*;

public class CollisionContactListener implements ContactListener {

	private boolean colliding;
	private Group rootGroup;

	public boolean isColliding() {
		return colliding;
	}

	public CollisionContactListener(Group rootGroup) {
		colliding = false;
		this.rootGroup = rootGroup;
	}

	public void beginContact(Contact contact) {
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		if (f1.getBody().getUserData() == "PACMAN"
				&& f2.getBody().getUserData() == "PELLET") {
			colliding = true;
			rootGroup.getChildren().clear();
			System.out.println("here");
		}

		System.out.println("PACMAN AND PELLET detected");
		System.out.println(f1.getBody().getUserData());
		System.out.println(f2.getBody().getUserData());

		System.out.println("Contact detected");
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
}
