package Entities;

import app.Kamera;
import javafx.scene.image.Image;

public class Fisch extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage;
	private int imageZaehler;
	
	
	// KONSTRUKTOR //
	public Fisch(float px, float py) {
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
		
	}
	
	
	public static void setImage(Image pImage) {
		dasImage = pImage;
	}
}
