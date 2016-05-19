package mainPackage;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.*;
import org.jbox2d.dynamics.contacts.*;

public class CollisionContactListener implements ContactListener {

	private boolean colliding;

	public boolean isColliding() {
		return colliding;
	}

	public CollisionContactListener() {
		colliding = false;
	}

	public void beginContact(Contact contact) {

		if (contact.getFixtureA().getBody().getUserData() == "body1"
				&& contact.getFixtureB().getBody().getUserData() == "body2")
			colliding = true;
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
