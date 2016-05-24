package objectsPackage;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import mainPackage.Properties;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

@SuppressWarnings("deprecation")
public class Wall extends Piece {

	private final float height;
	private final float width;
	private final BodyType bodyType = BodyType.STATIC;
	private Rectangle wall;
	private PolygonShape ps;

	public Wall(int posX, int posY, World world, int width, int height) {
		super(posX, posY, world, "WALL");
		ps = new PolygonShape();
		ps.setAsBox(width, height);
		this.height = height;
		this.width = width;
		node = create();
	}

	private Node create() {
		wall = RectangleBuilder
				.create()
				.x(Properties.jBoxToFxPosX(getPosX())
						- Properties.jBoxtoPixelWidth(width))
				.y(Properties.jBoxToFxPosY(getPosY())
						- Properties.jBoxtoPixelHeight(height))
				.width(Properties.jBoxtoPixelWidth(width) * 2)
				.height(Properties.jBoxtoPixelHeight(height) * 2)
				.fill(Color.BLUE).build();
		body = createBodyAndFixture(bodyType, ps);
		wall.setUserData(body);
		super.setUserData();
		return wall;
	}
}