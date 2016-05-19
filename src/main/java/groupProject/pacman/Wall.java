package groupProject.pacman;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {
	public Wall(int x, int y, int width, int height) {
		super(x,y, width, height);
		this.setFill(Paint.valueOf("BLUE"));
	}

}
