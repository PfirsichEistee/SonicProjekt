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
	private int animationsZaehler;
	private float animationTimer;
	
	private final int[][] anim = {
			{ 0, 1 }, //Klettern
			{ 0, 2, 1} //Projektil werfen
	};
	
	// KONSTRUKTOR //
	public Affe(float px, float py) {
		super(px, py);
		
		x = px;
		y = py;
		
		start = py;
		speedY = -2f;
	}
	
	
	// METHODEN
	@Override
	public void update(float delta) {
		animationTimer += delta;
		
		imageZaehler = anim[0][animationsZaehler];
		
		if (animationTimer >= 0.15f) {
			animationTimer = 0;
			if(animationsZaehler == 1) {
				animationsZaehler = 0;
			}
			else {
				animationsZaehler ++;
			}
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
			if(derSpieler.getX() > x) {
				dieSpielwelt.addProjectile(new Projektil(x, y, 15, 0));
			}
			else if(derSpieler.getX() < x) {
				dieSpielwelt.addProjectile(new Projektil(x, y, -15, 0));
			}
			Zustand = 0;
		}		
	}
	
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		dieKamera.setLineWidth(0.05f);
		
		dieKamera.drawImageSection(dasImage, imageZaehler * 32, 0, 32, 48, x - 0.5f, y - 0.75f, 1f, 1.5f);
	}
	
	
	public static void setImage(Image pImage) {
		dasImage = pImage;
	}
}
