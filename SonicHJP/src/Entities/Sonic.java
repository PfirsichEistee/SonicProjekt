package Entities;

import Objects.SpezialStrecke;
import app.CMath;
import app.Eingabe;
import app.Kamera;
import app.Kollision;
import app.RaycastHit;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Sonic {
	// ATTRIBUTE //
	public float x, y;
	private float speed;
	private float speedX, speedY;
	private float rotation;
	
	public float progx;
	public float specialziel;
	
	private float radHor, radVer; // radius horizontal/vertikal
	private float modeRotation; // nur in 90° Schritten
	private int modeCounter; // jeder modus muss fuer mind. 6 Updates bestehen
	
	private float jumpTimer;
	private boolean jumpLock;
	
	private boolean grounded;
	
	private SpezialStrecke dieSpezStrecke;
	private boolean physicsLock;
	
	
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
		modeCounter = 0;
		
		jumpTimer = -1;
		jumpLock = false;
		
		grounded = true;
		
		dieSpezStrecke = null;
		physicsLock = false;
		
		
		dasImage = new Image("file:files/textures/testsonic.png");
	}
	
	
	// METHODEN //
	public void update(float delta) {
		// Kamera bewegen
		dieKamera.setX((x - dieKamera.getX()) * delta * 10 + dieKamera.getX());
		dieKamera.setY((y - dieKamera.getY()) * delta * 10 + dieKamera.getY());
		
		
		// Animation updaten
		int zustand = 0;
		/* Zustaende:
		0 = Idle
		1 = Gehen
		2 = Rennen
		3 = Springen/Luft
		*/
		if (grounded) {
			if (Math.abs(speed) <= 0.1f)
				zustand = 0;
			else if (Math.abs(speed) <= 6f)
				zustand = 1;
			else
				zustand = 2;
		} else {
			zustand = 3;
		}
	}
	
	
	public void fixedUpdate(float delta) {
		if (physicsLock) {
			spezialUpdate(delta);
			return;
		}
		
		int repeat = (int)CMath.min((int)Math.ceil((Math.abs(speed) * delta) / 0.1f), 1); // fuer alle 0.1 LE updaten
		//System.out.println("Repeat " + repeat + "x");
		for (int i = 0; i < repeat; i++) {
			groundUpdate(delta / repeat);
		}
	}
	
	
	private void groundUpdate(float delta) {
		float mSin = (float)Math.sin(modeRotation);
		float mCos = (float)Math.cos(modeRotation);
		
		float distMult = (grounded ? 2f : 1.25f);
		//distMult = ((!grounded && speedY > 0) ? 1f : distMult);
		
		hitB1 = Kollision.raycast(x - radHor * 0.98f * mCos, y - radHor * 0.98f * mSin, radVer * mSin * distMult, -radVer * mCos * distMult, true);
		hitB2 = Kollision.raycast(x + radHor * 0.98f * mCos, y + radHor * 0.98f * mSin, radVer * mSin * distMult, -radVer * mCos * distMult, true);
		//hitD1 = Kollision.raycast(x - radHor * 0.98f, y, 0, radVer);
		//hitD2 = Kollision.raycast(x + radHor * 0.98f, y, 0, radVer);
		
		
		// Find groundHit
		RaycastHit groundHit = hitB1;
		if (hitB1 == null && hitB2 != null) {
			groundHit = hitB2;
		} else if (hitB1 != null && hitB2 != null) {
			if (modeRotation == 0 || modeRotation == (float)Math.PI)
				groundHit = (Math.abs(y - hitB1.hitY) < Math.abs(y - hitB2.hitY) ? hitB1 : hitB2);
			else
				groundHit = (Math.abs(x - hitB1.hitX) < Math.abs(x - hitB2.hitX) ? hitB1 : hitB2);
		}
		
		
		// Check if can ground
		if (!grounded && groundHit != null) {
			float angle = CMath.angleBetweenDirs(0, 1, groundHit.normalX, groundHit.normalY);
			if (angle > 45 || speedY > 0.1f) {
				groundHit = null;
			}
		}
		
		
		if (groundHit != null) {
			// Stick to ground
			boolean vertical = ((modeRotation == 0 || modeRotation == (float)Math.PI) ? true : false);
			if (vertical)
				y = groundHit.hitY + radVer * (modeRotation == 0 ? 1 : -1);
			else
				x = groundHit.hitX + radVer * (modeRotation == (float)(Math.PI / 2) ? -1 : 1);
			
			
			// Update rotation mode
			modeCounter++;
			float angle = CMath.angleBetweenDirs(0, 1, groundHit.normalX, groundHit.normalY);
			if (modeCounter >= 6) {
				float lastModeRot = modeRotation;
				
				if (angle <= 45)
					modeRotation = 0;
				else if (angle <= 135)
					modeRotation = (float)(Math.PI / 2) * (groundHit.normalX >= 0 ? -1 : 1);
				else
					modeRotation = (float)Math.PI;
				
				if (modeRotation != lastModeRot) {
					modeCounter = 0;
				}
			}
			
			
			// Update velocity
			float acceleration = 30f;
			float deceleration = 50f;
			float maxSpeed = 22f;
			
			
			// Did sonic ground JUST NOW?
			if (!grounded) {
				if (Eingabe.isKeyDown("W"))
					Eingabe.onKeyUp("W");
				
				if (angle <= 25) {
					// Shallow
					speed = speedX;
					//System.out.println("grounded SHALLOW");
				} else if (angle <= 45) {
					// Half Steep
					if (Math.abs(speedX) > -speedY) {
						speed = speedX;
					} else {
						speed = speedY * (float)Math.signum(Math.sin(angle * (Math.PI / 180))) * 0.5f;
					}
					//System.out.println("grounded HALF STEEP");
				} else {
					// Full Steep
					if (Math.abs(speedX) > -speedY) {
						speed = speedX;
					} else {
						speed = speedY * (float)Math.signum(Math.sin(angle * (Math.PI / 180)));
					}
					//System.out.println("grounded FULL STEEP");
				}
			}
			
			
			// Grounded
			grounded = true;
			
			
			// Update speed
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
			
			
			// Jump
			if (!Eingabe.isKeyDown("W") && jumpLock)
				jumpLock = false;
			
			if (Eingabe.isKeyDown("W") && !jumpLock) {
				x += groundHit.normalX * 0.1f;
				y += groundHit.normalY * 0.1f;
				
				grounded = false;
				float jumpForce = 7f;
				speedX += groundHit.normalX * jumpForce;
				speedY = groundHit.normalY * jumpForce;
				jumpTimer = 0.5f;
				
				return;
			} else {
				jumpTimer = -1f;
			}
			
			
			// Apply velocity
			if (groundHit.normalX < 0) angle *= -1;
			angle *= (float)(Math.PI / 180);
			angle += (Math.PI / 2);
			
			speedX = (float)Math.sin(angle) * speed;
			speedY = (float)Math.cos(angle) * speed;
			
			if (modeRotation != 0) {
				float minSpeed = 5f;
				
				if ((float)Math.abs(modeRotation) == (float)(Math.PI / 2)) {
					speedY -= 10f * delta;
					
					if (Math.abs(speedY) < minSpeed) {
						grounded = false;
						modeRotation = 0;
					}
				} else {
					if (Math.abs(speedX) < minSpeed) {
						grounded = false;
						modeRotation = 0;
					}
				}
			}
			
			
			// Move
			x += speedX * delta;
			y += speedY * delta;
			
			
			// Update rotation
			angle = CMath.angleBetweenDirs(0, 1, groundHit.normalX, groundHit.normalY);
			if (CMath.angleBetweenDirs(1, 0, groundHit.normalX, groundHit.normalY) > 90)
				angle *= -1;
			
			
			angle *= (Math.PI / 180.0);
			
			rotation = CMath.slerp(rotation, angle, 10f * delta);
		} else {
			// Sonic ungrounded
			modeRotation = 0;
			modeCounter = 99;
			airUpdate(delta);
			
			grounded = false;

			rotation = CMath.slerp(rotation, 0, 10f * delta);
		}
		
		collide();
	}
	
	
	private void airUpdate(float delta) {
		// Update Jump
		jumpTimer -= delta;
		
		if (!Eingabe.isKeyDown("W"))
			jumpTimer = -1f;
		
		if (jumpTimer < 0 || !Eingabe.isKeyDown("W"))
			jumpLock = true;
		
		
		// Update velocity
		float gravity = 20f;
		float maxSpeed = 25f;
		
		speedY = CMath.move(speedY, -maxSpeed, delta * (jumpTimer > 0 ? (gravity * 0.25f) : gravity));
		

		float inputX = (Eingabe.isKeyDown("D") ? 1 : 0);
		inputX = (Eingabe.isKeyDown("A") ? (inputX - 1) : inputX);
		speedX += inputX * delta * 10f;
		
		
		// Apply velocity
		x += speedX * delta;
		y += speedY * delta;
	}
	
	
	private void spezialUpdate(float delta) {
		
		//x = dieSpezStrecke.getSonicX(progx);
		//y = dieSpezStrecke.getSonicY(progx);
		
		if(specialziel == 1) {
			if(progx < 1) {
				progx = progx + 0.02f;
				
				x = dieSpezStrecke.getSonicX(progx);
				y = dieSpezStrecke.getSonicY(progx);
			}
			else {
				unlockPhysics();
				
				speedX = -20f;
			    speedY = 0;
			    progx = 0;
			    }
		}
		else if(specialziel == 0) {
			if(progx < 1) {
				progx = progx + 0.02f;
				
				x = dieSpezStrecke.getSonicX(progx); 
				y = dieSpezStrecke.getSonicY(progx);
			}
			else {
				unlockPhysics();
				
				speedX = 20f;
			    speedY = 0;
			    progx = 0;			    
			    }
		}
			
		// TODO: bewege Sonic entlang einer Spezial-Strecke
		// Am Ende physic wieder entsperren
	}
	
	
	private void collide() {
		float mSin = (float)Math.sin(modeRotation);
		float mCos = (float)Math.cos(modeRotation);
		
		boolean horizontal = ((modeRotation == 0 || modeRotation == (float)Math.PI) ? true : false);
		hitL = Kollision.raycast(x, y, -radHor * mCos, -radHor * mSin, true);
		hitR = Kollision.raycast(x, y, radHor * mCos, radHor * mSin, true);
		if (hitL != null) {
			if (horizontal) {
				x = hitL.hitX + radHor * mCos;
				speedX = 0;
			} else {
				y = hitL.hitY + radHor * mSin;
				speedY = 0;
			}
		}
		if (hitR != null) {
			if (horizontal) {
				x = hitR.hitX - radHor * mCos;
				speedX = 0;
			} else {
				y = hitR.hitY - radHor * mSin;
				speedY = 0;
			}
		}
		
		
		if (!grounded) {
			if (speedY > 0) {
				hitD1 = Kollision.raycast(x - radHor * 0.98f, y, 0, radVer, false);
				hitD2 = Kollision.raycast(x + radHor * 0.98f, y, 0, radVer, false);
				
				if (hitD1 != null || hitD2 != null) {
					speedY = 0;
					
					if (hitD1 != null)
						y = hitD1.hitY - radVer;
					if (hitD2 != null)
						y = hitD2.hitY - radVer;
				}
			}
		}
	}
	
	
	public void draw() {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		
		dieKamera.drawImage(dasImage, x - 0.9f, y + 0.9f, 1.8f, 1.8f, rotation);

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
	
	
	public void lockPhysics() {
		physicsLock = true;
	}
	public void unlockPhysics() {
		physicsLock = false;
	}
	public boolean isPhysicsLocked() {
		return physicsLock;
	}
	
	public void setSpezialStrecke(SpezialStrecke pStrecke) {
		dieSpezStrecke = pStrecke;
	}
	
	
	private void debugDrawRay(float px, float py, float dx, float dy) {
		dieKamera.drawLine(px, py, px + dx, py + dy);
	}
}
