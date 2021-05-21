package Entities;

import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Fisch extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/enemies/fisch.png");
	private int imageZaehler;
	private float start;
	private float speedY;
	
	
	// KONSTRUKTOR //
	public Fisch(float px, float py) {
		super(px, py);
		
		x = px;
		y = py;
		
		start = py;
		speedY = -3f;
	}
	
	
	// METHODEN
	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public void fixedUpdate(float delta) {
		y += speedY * delta;
		
		if(y >= start) {
			speedY = -speedY;
		}
		else if(y <= start - 10) {
			speedY = -speedY;
		}
	}
	
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		dieKamera.setLineWidth(0.05f);
		
		dieKamera.drawImageSection(dasImage, 0, 0, 32, 23, x - 0.5f, y - 0.355f, 1f, 0.71f, (float)Math.PI / 2); // Rotiert wird mit Bogenmaß. PI == 180°
	}
	
	
	public static void setImage(Image pImage) {
		dasImage = pImage;
	}
}
