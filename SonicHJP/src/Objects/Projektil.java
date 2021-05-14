package Objects;

import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
		x += speedX;
		y += speedY;
	}
	
	public void draw(Kamera dieKamera) {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		dieKamera.setLineWidth(0.05f);
		
		dieKamera.drawImage(dasImage, x, y, 256, 280, 0);
	}
	
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}
