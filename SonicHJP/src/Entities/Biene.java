package Entities;

import Objects.Projektil;
import app.Kamera;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Biene extends Gegner {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/enemies/biene.png");
	private int imageZaehler;
	private int Zustand;
	private int Blickrichtung;
	private int timer;
	private int animationsZaehler;
	private float animationTimer;
	
	private final int[][] anim = {
			{ 0, 1 }, //fliegen
			{ 2, 3, 4, 3, 2 } //Projektil schießen
	};
	
	// KONSTRUKTOR //
	public Biene(float px, float py) {
		super(px, py);
		
		x = px;
		y = py;
	}
	
	
	// METHODEN
	@Override
	public void update(float delta) {
		animationTimer += delta;
		
		imageZaehler = anim[0][animationsZaehler];
		
		if (animationTimer >= 0.05f) {
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
			dieSpielwelt.addProjectile(new Projektil(x, y, -5, -5));
			
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
		
		dieKamera.drawImageSection(dasImage, imageZaehler * 48, 0, 48, 48, x - 0.8f, y - 0.8f, 1.6f, 1.6f);
	}
	
	
	public static void setImage(Image pImage) {
		dasImage = pImage;
	}
}
