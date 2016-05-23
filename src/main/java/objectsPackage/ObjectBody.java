package objectsPackage;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class ObjectBody extends Body {

	private int id;
	
	public ObjectBody(BodyDef bd, World world, int id) {
		super(bd, world);
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	
	
	public int getID(){
		return id;
	}

}
