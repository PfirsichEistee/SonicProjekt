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
	private int animationsZaehler;
	private float animationTimer;
	
	private final int[][] anim = {
			{ 0, 1 }, //Bewegen
	};
	
	
	// KONSTRUKTOR //
	public Fisch(float px, float py) {
		super(px, py);
		
		x = px;
		y = py;
		
		start = py;
		speedY = 5f;
	}
	
	
	// METHODEN
	@Override
	public void start() {
		
	}
	@Override
	public void update(float delta) {
		animationTimer += delta;
		
		imageZaehler = anim[0][animationsZaehler];
		
		if (animationTimer >= 0.1f) {
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
		y += speedY * delta;
		
		if(y >= start + 5) {
			speedY = -speedY;
		}
		else if(y <= start) {
			speedY = -speedY;
		}
	}
	
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		dieKamera.setLineWidth(0.05f);
		
		if(speedY > 0) {
			dieKamera.drawImageSection(dasImage, imageZaehler * 32, 0, 32, 23, x - 0.5f, y - 0.355f, 1f, 0.71f, (float)Math.PI / 2);// Rotiert wird mit Bogenmaß. PI == 180°
		}
		else if(speedY < 0) {
			dieKamera.drawImageSection(dasImage, 32, 0, 32, 23, x - 0.5f, y - 0.355f, 1f, 0.71f, (float)Math.PI / -2);
		}
	}
}
