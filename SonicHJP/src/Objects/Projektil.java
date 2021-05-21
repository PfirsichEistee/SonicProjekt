package Objects;

import app.CMath;
import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Projektil {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/misc/projectile.png");
	public static int imageZaehler = 0;
	
	private float x, y;
	private float speedX, speedY;
	public float timer;
	
	
	// KONSTRUKTOR //
	public Projektil(float px, float py, float pSpeedX, float pSpeedY) {
		x = px;
		y = py;
		speedX = pSpeedX;
		speedY = pSpeedY;
		timer = 0;
	}
	
	
	// METHODEN //
	public void update(float delta) {
		x += speedX * delta;
		y += speedY * delta;
		
		timer += delta;
	}
	
	public void draw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, imageZaehler * 10, 0, 10, 10, x - 0.2f, y - 0.2f, 0.4f, 0.4f);
	}
	

	public boolean isNextTo(float px, float py) {
		if (CMath.distance(px, py, x, y) < 0.8f)
			return true;
		
		return false;
	}
}
