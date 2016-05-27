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
	private Color color;
	private final int groupIndex = 1;
	private final int maskBits = 1;
	private final int categoryBits = 1;

	public Wall(int posX, int posY, World world, float width, float height,
			Color c) {
		super(posX, posY, world, "WALL");
		ps = new PolygonShape();
		ps.setAsBox(width + .48f, height + .48f);
		this.height = height;
		this.width = width;
		this.color = c;
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
				.height(Properties.jBoxtoPixelHeight(height) * 2).fill(color)
				.build();
		body = createBodyAndFixture(bodyType, ps, maskBits, groupIndex,
				categoryBits);

		wall.setUserData(body);
		super.setUserData();

		return wall;
	}
}