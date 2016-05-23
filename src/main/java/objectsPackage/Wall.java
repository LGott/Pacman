package objectsPackage;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import mainPackage.Properties;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

@SuppressWarnings("deprecation")
public class Wall extends Piece {
	private Node node;
	private final float height;
	private final float width;
	private final BodyType bodyType = BodyType.STATIC;

	public Wall(int posX, int posY, World world, float width, float height) {
		super(posX, posY, world);
		this.height = height;
		this.width = width;
		node = create();
	}

	private Node create() {
		Rectangle wall = RectangleBuilder.create()
         .x(Properties.jBoxToFxPosX(getPosX()) - Properties.jBoxtoPixelWidth(width))
         .y(Properties.jBoxToFxPosY(getPosY()) - Properties.jBoxtoPixelHeight(height))
         .width(Properties.jBoxtoPixelWidth(width) * 2)
         .height(Properties.jBoxtoPixelHeight(height) * 2)
         .fill(Color.BLUE)
         .build();
         
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width, height);

		Body body = createBodyAndFixture(bodyType, ps);
		wall.setUserData(body);
		return wall;
	}

	public Node getNode() {
		return node;
	}
}