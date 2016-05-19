package groupProject.pacman;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class PacmanRectangle extends Rectangle {
	public PacmanRectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
		Image img = new Image("/pacman.png");
		ImagePattern imagePattern = new ImagePattern(img);
		setFill(imagePattern);
		
	}

}
