package Objects;

import app.Kamera;
import javafx.scene.image.Image;

public class Ring {
	// ATTRIBUTE //
	private static Image dasImage;
	public static int imageZaehler;
	
	private float x, y;
	
	
	// KONSTRUKTOR //
	public Ring(float px, float py) {
		x = px;
		y = py;
	}
	
	
	// METHODEN //
	public void draw(Kamera dieKamera) {
		
	}
	
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}
