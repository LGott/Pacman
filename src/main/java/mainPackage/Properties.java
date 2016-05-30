package mainPackage;

public class Properties {
	public static final int WIDTH = 700;
	public static final int HEIGHT = 800;

	
	//Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float jBoxToFxPosX(float posX) {
        float x = WIDTH*posX / 100.0f;
        return x;
    }
 
    //Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
    public static float fxToJboxPosX(float posX) {
        float x =   (posX*100.0f*1.0f)/WIDTH;
        return x;
    }
     
    //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float jBoxToFxPosY(float posY) {
        float y = HEIGHT - (1.0f*HEIGHT) * posY / 100.0f;
        return y;
    }
     
    //Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
    public static float fxToJboxPosY(float posY) {
        float y = 100.0f - ((posY * 100*1.0f) /HEIGHT) ;
        return y;
    }
     
    //Convert a JBox2D width to pixel width
    public static float jBoxtoPixelWidth(float width) {
        return WIDTH*width / 100.0f;
    }
     
    //Convert a JBox2D height to pixel height
    public static float jBoxtoPixelHeight(float height) {
        return HEIGHT*height/100.0f;
    }
}