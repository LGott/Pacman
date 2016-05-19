package mainPackage;

public class Properties {
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;

	// Convert a JBox2D x coordinate to a JavaFX pixel y coordinate
	public static float toPixelPosX(float posX) {
		float x = WIDTH * posX / 100.0f;
		return x;
	}

	// Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
	public static float toPixelPosY(float posY) {
		float y = HEIGHT - (1.0f * HEIGHT) * posY / 100.0f;
		return y;
	}
}