package Entities;

import Objects.Projektil;
import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Affe extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/enemies/affe.png");
	private int imageZaehler;
	private int Zustand;
	private int Blickrichtung;
	private float start;
	private float speedY;
	
	private float testShit = 0;
	
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
		testShit += Math.random() * delta * 3;
		if (testShit > 1) {
			testShit = 0;
			dieSpielwelt.addProjectile(new Projektil(x, y, 0, -15));
		}
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
		
		dieKamera.drawImageSection(dasImage, 0, 0, 32, 48, x - 0.5f, y - 0.75f, 1f, 1.5f);
	}
	
	
	public static void setImage(Image pImage) {
		dasImage = pImage;
	}
}
