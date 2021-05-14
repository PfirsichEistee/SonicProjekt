package Entities;

import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Fisch extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage;
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
		
		dasImage = new Image("file:files/textures/testsonic.png");
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
		
		dieKamera.drawImage(dasImage, x, y, 256, 280, 0);
	}
	
	
	public static void setImage(Image pImage) {
		dasImage = pImage;
	}
}
