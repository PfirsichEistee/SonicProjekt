package Entities;

import app.Eingabe;
import app.Kamera;
import app.Kollision;
import app.RaycastHit;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Sonic {
	// ATTRIBUTE //
	private float x, y;
	private float speed;
	private float speedX, speedY;
	private float rotation;
	
	private float radHor, radVer; // radius horizontal/vertikal
	
	
	private Image dasImage;
	private int imageZaehler;
	
	private Kamera dieKamera;
	
	
	// DEBUG
	private RaycastHit hitB1, hitB2; // Boden
	private RaycastHit hitD1, hitD2; // Decke
	private RaycastHit hitL, hitR;
	
	
	// KONSTRUKTOR //
	public Sonic(float px, float py, Kamera pKamera) {
		x = px;
		y = py;
		dieKamera = pKamera;
		
		speed = 0;
		speedX = 0;
		speedY = 0;
		rotation = 0;
		
		radHor = 0.3f;
		radVer = 0.5f;
	}
	
	
	// METHODEN //
	public void update(float delta) {
		// Kamera bewegen
		dieKamera.setX((x - dieKamera.getX()) * delta * 10 + dieKamera.getX());
		dieKamera.setY((y - dieKamera.getY()) * delta * 10 + dieKamera.getY());
	}
	
	
	public void fixedUpdate(float delta) {
		if (Eingabe.isKeyDown("W"))
			y += 0.07;
		if (Eingabe.isKeyDown("S"))
			y -= 0.07;
		if (Eingabe.isKeyDown("D"))
			x += 0.07;
		if (Eingabe.isKeyDown("A"))
			x -= 0.07;
		
		hitB1 = Kollision.raycast(x - radHor * 0.98f, y, 0, -radVer);
		hitB2 = Kollision.raycast(x + radHor * 0.98f, y, 0, -radVer);
		hitD1 = Kollision.raycast(x - radHor * 0.98f, y, 0, radVer);
		hitD2 = Kollision.raycast(x + radHor * 0.98f, y, 0, radVer);
		hitL = Kollision.raycast(x, y, -radHor, 0);
		hitR = Kollision.raycast(x, y, radHor, 0);
	}
	
	
	public void draw() {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		dieKamera.drawRect(x - radHor, y - radVer, radHor * 2, radVer * 2);

		dieKamera.setLineWidth(0.05f);
		dieKamera.setFarbe(Color.RED);
		dieKamera.drawLine(x, y, x + radHor, y);
		dieKamera.setFarbe(Color.PURPLE);
		dieKamera.drawLine(x, y, x - radHor, y);
		dieKamera.setFarbe(Color.GREEN);
		dieKamera.drawLine(x - radHor, y, x - radHor, y + radVer);
		dieKamera.setFarbe(Color.DARKGREEN);
		dieKamera.drawLine(x + radHor, y, x + radHor, y + radVer);
		dieKamera.setFarbe(Color.BLUE);
		dieKamera.drawLine(x - radHor, y, x - radHor, y - radVer);
		dieKamera.setFarbe(Color.AQUA);
		dieKamera.drawLine(x + radHor, y, x + radHor, y - radVer);
		
		dieKamera.setFarbe(Color.RED);
		if (hitB1 != null)
			dieKamera.drawRect(hitB1.hitX - 0.1f, hitB1.hitY - 0.1f, 0.2f, 0.2f);
		if (hitB2 != null)
			dieKamera.drawRect(hitB2.hitX - 0.1f, hitB2.hitY - 0.1f, 0.2f, 0.2f);
		if (hitD1 != null)
			dieKamera.drawRect(hitD1.hitX - 0.1f, hitD1.hitY - 0.1f, 0.2f, 0.2f);
		if (hitD2 != null)
			dieKamera.drawRect(hitD2.hitX - 0.1f, hitD2.hitY - 0.1f, 0.2f, 0.2f);
		if (hitL != null)
			dieKamera.drawRect(hitL.hitX - 0.1f, hitL.hitY - 0.1f, 0.2f, 0.2f);
		if (hitR != null)
			dieKamera.drawRect(hitR.hitX - 0.1f, hitR.hitY - 0.1f, 0.2f, 0.2f);
	}
}
