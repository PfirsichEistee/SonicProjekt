package Entities;

import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Affe extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage;
	private int imageZaehler;
	private int Zustand;
	private int Blickrichtung;
	private float start;
	private float speedY;
	
	// KONSTRUKTOR //
	public Affe(float px, float py) {
		super(px, py);
		
		x = px;
		y = py;
		
		start = py;
		speedY = -1f;
	}
	
	
	// METHODEN
	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public void fixedUpdate(float delta) {
		if(Zustand == 0){ //Bewegen
			y += speedY * delta;
			
			if(y >= start) {
				speedY = -speedY;
				Zustand = 1;
			}
			else if(y <= start - 3) {
				speedY = -speedY;
			}
		}
		else if(Zustand == 1) { //Projektil in Blickrichtung
			//dieSpielwelt.setdieProjektile(x, y, 2f, 0f);
			Zustand = 0;
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
