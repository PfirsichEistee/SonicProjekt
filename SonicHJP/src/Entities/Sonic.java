package Entities;

import app.Kamera;
import javafx.scene.image.Image;

public class Sonic {
	// ATTRIBUTE //
	private float x, y;
	private float speed;
	private float speedX, speedY;
	private float rotation;
	
	private Image dasImage;
	private int imageZaehler;
	
	private Kamera dieKamera;
	
	
	// KONSTRUKTOR //
	public Sonic(float px, float py, Kamera pKamera) {
		x = px;
		y = py;
		dieKamera = pKamera;
		
		speed = 0;
		speedX = 0;
		speedY = 0;
		rotation = 0;
	}
	
	
	// METHODEN //
	public void update(float delta) {
		
	}
	
	
	public void fixedUpdate(float delta) {
		
	}
	
	
	public void draw() {
		
	}
}
