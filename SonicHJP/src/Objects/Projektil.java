package Objects;

import app.Kamera;
import javafx.scene.image.Image;

public class Projektil {
	// ATTRIBUTE //
	private static Image dasImage;
	public static int imageZaehler;
	
	private float x, y;
	private float speedX, speedY;
	
	
	// KONSTRUKTOR //
	public Projektil(float px, float py, float pSpeedX, float pSpeedY) {
		x = px;
		y = py;
		speedX = pSpeedX;
		speedY = pSpeedY;
	}
	
	
	// METHODEN //
	public void update(float delta) {
		
	}
	
	public void draw(Kamera dieKamera) {
		
	}
	
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}
