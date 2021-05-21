package Entities;

import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Kaefer extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/enemies/kaefer.png");
	private int imageZaehler;
	
	// KONSTRUKTOR //
	public Kaefer(float px, float py) {
		super(px, py);
	}
	
	
	// METHODEN
	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public void fixedUpdate(float delta) {
		
	}
	
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, 0, 0, 39, 30, x - 0.5f, y - 0.355f, 1f, 0.76f);
	}
	
	
	public static void setImage(Image pImage) {
		dasImage = pImage;
	}
}
