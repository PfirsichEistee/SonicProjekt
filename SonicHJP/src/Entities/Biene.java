package Entities;

import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Biene extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage;
	private int imageZaehler;
	private int Zustand;
	private int Blickrichtung;
	private int timer;
	
	// KONSTRUKTOR //
	public Biene(float px, float py) {
		super(px, py);
		
		x = px;
		y = py;
	}
	
	
	// METHODEN
	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public void fixedUpdate(float delta) {
		if(Zustand == 0){ //Bewegen
			
			if(Blickrichtung == 0) {
				x -= 3f * delta;
				
				timer ++;
			}
			else if(Blickrichtung == 1) {
				x += 3f * delta;
				
				timer++;
			}
		}
		else if(Zustand == 1) { //Projectil in Blickrichtung
			//dieSpielwelt.setdieProjektile(x, y, 2f, 0f);
			
			timer = 0;
			
			Zustand = 0;
		}
		
		if(timer == 100) {
			Zustand = 1;
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
