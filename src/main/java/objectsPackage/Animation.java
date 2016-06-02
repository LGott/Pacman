package objectsPackage;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Animation {

	private ArrayList<Image> images;
	private Image sprite;
	private volatile boolean running;
	private long previousTime, speed;
	private int imageAtPause, currentImage;

	public Animation(ArrayList<Image> images) {
		this.images = images;
	}

	public void setSpeed(long speed){
		this.speed = speed;
	}

	public void update(long time) {
		if(running){
			if(time - previousTime >= speed){
				//update animation
				currentImage++;
				try{
					sprite = images.get(currentImage);
				}catch(IndexOutOfBoundsException e){
					currentImage = 0;
					sprite = images.get(currentImage);
				}
				previousTime = time;
			}
		}
	}

	public void start() {
		running = true;
		previousTime = imageAtPause = currentImage = 0;
	}

	public void stop() {
		running = false;
		previousTime = imageAtPause = currentImage = 0;
	}

	public void pause() {
		running = false;
		imageAtPause = currentImage;
	}

	public void resume() {
		currentImage = imageAtPause;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setImages(ArrayList<Image> imageArray){
		this.images = imageArray;
	}
}
