package Entities;

import app.CMath;
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
	private float modeRotation; // nur in 90° Schritten
	
	
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
		modeRotation = 0;
	}
	
	
	// METHODEN //
	public void update(float delta) {
		// Kamera bewegen
		dieKamera.setX((x - dieKamera.getX()) * delta * 10 + dieKamera.getX());
		dieKamera.setY((y - dieKamera.getY()) * delta * 10 + dieKamera.getY());
	}
	
	
	public void fixedUpdate(float delta) {
		int repeat = (int)CMath.min((int)Math.ceil((Math.abs(speed) * delta) / 0.1f), 1); // fuer alle 0.1 LE updaten
		//System.out.println("Repeat " + repeat + "x");
		for (int i = 0; i < repeat; i++) {
			groundUpdate(delta / repeat);
		}
	}
	
	
	private void groundUpdate(float delta) {
		float mSin = (float)Math.sin(modeRotation);
		float mCos = (float)Math.cos(modeRotation);
		
		// WICHTIG: Wenn sonic vorher bereits grounded war, raycast mit 2 multiplizieren. Wenn vorher air: raycast * 1.5
		hitB1 = Kollision.raycast(x - radHor * 0.98f * mCos, y - radHor * 0.98f * mSin, radVer * 1.5f * mSin, -radVer * 1.5f * mCos);
		hitB2 = Kollision.raycast(x + radHor * 0.98f * mCos, y + radHor * 0.98f * mSin, radVer * 1.5f * mSin, -radVer * 1.5f * mCos);
		//hitD1 = Kollision.raycast(x - radHor * 0.98f, y, 0, radVer);
		//hitD2 = Kollision.raycast(x + radHor * 0.98f, y, 0, radVer);
		//hitL = Kollision.raycast(x, y, -radHor, 0);
		//hitR = Kollision.raycast(x, y, radHor, 0);
		
		RaycastHit groundHit = hitB1;
		if (hitB1 == null && hitB2 != null) {
			groundHit = hitB2;
		} else if (hitB1 != null && hitB2 != null) {
			if (modeRotation == 0 || modeRotation == Math.PI)
				groundHit = (Math.abs(y - hitB1.hitY) < Math.abs(y - hitB2.hitY) ? hitB1 : hitB2);
			else
				groundHit = (Math.abs(x - hitB1.hitX) < Math.abs(x - hitB2.hitX) ? hitB1 : hitB2);
		}
		
		
		if (groundHit != null) {
			// Update rotation mode
			float angle = CMath.angleBetweenDirs(0, 1, groundHit.normalX, groundHit.normalY);
			if (angle <= 45)
				modeRotation = 0;
			else if (angle <= 135)
				modeRotation = (float)(Math.PI / 2) * (groundHit.normalX >= 0 ? -1 : 1);
			else
				modeRotation = (float)Math.PI;
			
			
			// Stick to ground
			boolean vertical = ((modeRotation == 0 || modeRotation == Math.PI) ? true : false);
			if (vertical) {
				y = groundHit.hitY + radVer * (modeRotation == 0 ? 1 : -1);
			} else {
				x = groundHit.hitX + radVer * (modeRotation == (Math.PI / 2) ? 1 : -1);
			}
			
			
			// Update velocity
			float acceleration = 30f;
			float deceleration = 50f;
			float maxSpeed = 25f;
			
			float inputX = (Eingabe.isKeyDown("D") ? 1 : 0);
			inputX = (Eingabe.isKeyDown("A") ? (inputX - 1) : inputX);
			
			if (inputX != 0) {
				if (speed == 0 || Math.signum(speed) == inputX) {
					speed = CMath.move(speed, maxSpeed * inputX, acceleration * delta);
				} else {
					speed = CMath.move(speed, 0, deceleration * delta);
				}
			} else {
				speed = CMath.move(speed, 0, deceleration * 0.25f * delta);
			}
			
			
			// Apply velocity
			angle *= (float)(Math.PI / 180);
			
			if (groundHit.normalX < 0) angle *= -1;
			
			angle += (Math.PI / 2);
			
			speedX = (float)Math.sin(angle) * speed;
			speedY = (float)Math.cos(angle) * speed;
			
			x += speedX * delta;
			y += speedY * delta;
		} else {
			// Sonic ungrounded
			modeRotation = 0;
			airUpdate(delta);
		}
	}
	
	
	private void airUpdate(float delta) {
		// Update velocity
		float gravity = 10;
		float maxSpeed = 25f;
		
		speedY = CMath.move(speedY, -maxSpeed, gravity * delta);
		
		
		// Apply velocity
		x += speedX * delta;
		y += speedY * delta;
	}
	
	
	public void draw() {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		dieKamera.drawRect(x - radHor, y - radVer, radHor * 2, radVer * 2);

		dieKamera.setLineWidth(0.05f);
		
		// LR
		dieKamera.setFarbe(Color.RED);
		debugDrawRay(x, y, radHor * (float)Math.cos(modeRotation), radHor * (float)Math.sin(modeRotation));
		
		dieKamera.setFarbe(Color.PURPLE);
		debugDrawRay(x, y, -radHor * (float)Math.cos(modeRotation), -radHor * (float)Math.sin(modeRotation));
		
		// Decke
		dieKamera.setFarbe(Color.GREEN);
		debugDrawRay(x - radHor * (float)Math.cos(modeRotation), y - radHor * (float)Math.sin(modeRotation),
				-radVer * (float)Math.sin(modeRotation), radVer * (float)Math.cos(modeRotation));
		
		dieKamera.setFarbe(Color.DARKGREEN);
		debugDrawRay(x + radHor * (float)Math.cos(modeRotation), y + radHor * (float)Math.sin(modeRotation),
				-radVer * (float)Math.sin(modeRotation), radVer * (float)Math.cos(modeRotation));
		
		// Boden
		dieKamera.setFarbe(Color.BLUE);
		debugDrawRay(x - radHor * (float)Math.cos(modeRotation), y - radHor * (float)Math.sin(modeRotation),
				radVer * (float)Math.sin(modeRotation), -radVer * (float)Math.cos(modeRotation));
		
		dieKamera.setFarbe(Color.AQUA);
		debugDrawRay(x + radHor * (float)Math.cos(modeRotation), y + radHor * (float)Math.sin(modeRotation),
				radVer * (float)Math.sin(modeRotation), -radVer * (float)Math.cos(modeRotation));
		
		
		dieKamera.setFarbe(Color.RED);
		if (hitB1 != null)
			//dieKamera.drawRect(hitB1.hitX - 0.1f, hitB1.hitY - 0.1f, 0.2f, 0.2f);
		if (hitB2 != null)
			//dieKamera.drawRect(hitB2.hitX - 0.1f, hitB2.hitY - 0.1f, 0.2f, 0.2f);
		if (hitD1 != null)
			dieKamera.drawRect(hitD1.hitX - 0.1f, hitD1.hitY - 0.1f, 0.2f, 0.2f);
		if (hitD2 != null)
			dieKamera.drawRect(hitD2.hitX - 0.1f, hitD2.hitY - 0.1f, 0.2f, 0.2f);
		if (hitL != null)
			dieKamera.drawRect(hitL.hitX - 0.1f, hitL.hitY - 0.1f, 0.2f, 0.2f);
		if (hitR != null)
			dieKamera.drawRect(hitR.hitX - 0.1f, hitR.hitY - 0.1f, 0.2f, 0.2f);
	}
	
	
	private void debugDrawRay(float px, float py, float dx, float dy) {
		dieKamera.drawLine(px, py, px + dx, py + dy);
	}
}
