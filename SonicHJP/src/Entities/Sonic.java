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
	private float x, y;
	private float speed;
	private float speedX, speedY;
	private float rotation;
	
	private float radHor, radVer; // radius horizontal/vertikal
	private float modeRotation; // nur in 90° Schritten
	private int modeCounter; // jeder modus muss fuer mind. 6 Updates bestehen
	private float lastNormalX, lastNormalY;
	
	private float jumpTimer;
	private boolean jumpLock;
	
	private boolean grounded;
	private boolean rolling;
	private float spinDash;
	
	private SpezialStrecke dieSpezStrecke;
	private float spezialProzent;
	private float spezialZiel;
	private boolean physicsLock;
	
	
	private Kamera dieKamera;
	
	
	// Animation
	private Image dasImage;
	private boolean imageFlip;
	private final int[][] anim = {
			{ 0 }, // Idle
			{ 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49 }, // Walk
			{ 50, 51, 52, 53, 54, 55, 56, 57 }, // Run
			{ 58, 59, 60, 61, 62, 63, 64, 65, 66 }, // Ball
			{ 67, 68, 69, 70, 71, 72, 73 }, // Spin-Dash
	};
	private int animationZustand, animationZaehler;
	private float animationTimer;
	
	
	
	// Constants
	private final float MAX_SPEED = 20f;
	private final float ACCELERATION = 7f;
	private final float DECELERATION = 50f;
	private final float JUMP_FORCE = 10f;
	private final float GRAVITY = 30f;
	
	
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
		lastNormalX = 0;
		lastNormalY = 1;
		
		jumpTimer = -1;
		jumpLock = false;
		
		grounded = true;
		rolling = false;
		spinDash = 0;
		
		dieSpezStrecke = null;
		physicsLock = false;
		
		
		dasImage = new Image("file:files/textures/hdsonicsheet.png");
		imageFlip = false;
		animationZustand = 0;
		animationZaehler = 0;
		animationTimer = 0;
	}
	
	
	// METHODEN //
	public void update(float delta) {
		animationTimer += delta;
		
		if (animationTimer >= 0.05f) {
			animationTimer = 0;
			
			// Animation updaten
			int zustand = 0;
			/* Zustaende:
			0 = Idle
			1 = Gehen
			2 = Rennen
			3 = Springen/Luft/Rollen
			4 = Spin Dash
			*/
			if (grounded && !rolling) {
				if (speed > 0)
					imageFlip = true;
				else if (speed < 0)
					imageFlip = false;
				
				if (Math.abs(speed) <= 0.1f)
					zustand = 0;
				else if (Math.abs(speed) <= (MAX_SPEED * 0.6f))
					zustand = 1;
				else
					zustand = 2;
				
			} else {
				if (speedX > 0)
					imageFlip = true;
				else if (speedX < 0)
					imageFlip = false;
				
				zustand = 3;
			}
			if (physicsLock)
				zustand = 3;
			
			if (grounded && spinDash > 0)
				zustand = 4;
			
			if (zustand != animationZustand)
				animationZaehler = 0;
			
			animationZustand = zustand;
			
			
			animationZaehler++;
			animationZaehler %= anim[animationZustand].length;
		}
	}
	
	
	public void fixedUpdate(float delta) {
		if (physicsLock) {
			spezialUpdate(delta);
		} else {
			int repeat = (int)CMath.min((int)Math.ceil((Math.abs(speed) * delta) / 0.05f), 1); // fuer alle 0.05 LE updaten
			//System.out.println("Repeat " + repeat + "x");
			for (int i = 0; i < repeat; i++) {
				if (!Eingabe.isKeyDown("W") && grounded)
					jumpLock = false;
				
				groundUpdate(delta / repeat);
			}
		}
		
		// Kamera bewegen
		dieKamera.setX((x - dieKamera.getX()) * delta * 10 + dieKamera.getX());
		dieKamera.setY((y - dieKamera.getY()) * delta * 10 + dieKamera.getY());
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
			if (angle > 45 || speedY > 0.2f) {
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
				
				if (CMath.angleBetweenDirs(lastNormalX, lastNormalY, groundHit.normalX, groundHit.normalY) > 45) {
					grounded = false;
					return;
				}
			}
			
			lastNormalX = groundHit.normalX;
			lastNormalY = groundHit.normalY;
			
			
			// Update velocity
			
			// Did sonic ground JUST NOW?
			if (!grounded) {
				rolling = false;
				
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
			
			
			// Rolling
			if (Eingabe.isKeyDown("S") && !rolling)
				rolling = true;
			
			if (rolling && Math.abs(speed) < 0.25f)
				spinDash = CMath.max(spinDash + delta * 2, 1.5f);
			
			if (rolling && Math.abs(speed) < 0.25f && !Eingabe.isKeyDown("S")) {
				if (spinDash < 0.25f) {
					rolling = false;
				} else {
					if (imageFlip) {
						// right
						speed = spinDash * MAX_SPEED;
					} else {
						// left
						speed = spinDash * -MAX_SPEED;
					}
					
					spinDash = 0;
				}
			}
			
			if (!rolling)
				spinDash = 0;
			
			
			// Update speed
			float inputX = (Eingabe.isKeyDown("D") ? 1 : 0);
			inputX = (Eingabe.isKeyDown("A") ? (inputX - 1) : inputX);
			inputX *= (rolling ? 0 : 1);
			
			if (inputX != 0) {
				if (speed == 0 || Math.signum(speed) == inputX) {
					speed = CMath.move(speed, MAX_SPEED * inputX, ACCELERATION * delta);
				} else {
					speed = CMath.move(speed, 0, DECELERATION * delta);
				}
			} else {
				speed = CMath.move(speed, 0, DECELERATION * delta * (rolling ? 0.1f : 0.5f));
			}
			
			// TODO gravitation
			
			
			// Jump
			if (Eingabe.isKeyDown("W") && !jumpLock) {
				x += groundHit.normalX * 0.1f;
				y += groundHit.normalY * 0.1f;
				
				grounded = false;
				speedX += groundHit.normalX * JUMP_FORCE;
				speedY = groundHit.normalY * JUMP_FORCE;
				jumpTimer = 0.5f;
				
				jumpLock = true;
				
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
				float minSpeed = MAX_SPEED * 0.5f;
				
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
			
			if (Math.abs(speed) < MAX_SPEED * 0.05f)
				angle = 0;
			
			rotation = CMath.slerp(rotation, angle, 10f * delta);
		} else {
			// Sonic ungrounded
			modeRotation = 0;
			modeCounter = 99;
			lastNormalX = 0;
			lastNormalY = 1;
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
		
		
		// Update velocity
		
		speedY = CMath.move(speedY, -MAX_SPEED, delta * (jumpTimer > 0 ? (GRAVITY * 0.4f) : GRAVITY));
		
		
		float inputX = (Eingabe.isKeyDown("D") ? 1 : 0);
		inputX = (Eingabe.isKeyDown("A") ? (inputX - 1) : inputX);
		speedX = CMath.clamp(speedX + inputX * delta * 10f, -MAX_SPEED, MAX_SPEED);
		
		
		// Apply velocity
		x += speedX * delta;
		y += speedY * delta;
	}
	
	
	private void spezialUpdate(float delta) {
		rotation = 0;
		spezialProzent = CMath.move(spezialProzent, spezialZiel, delta);
		
		x = dieSpezStrecke.getSonicX(spezialProzent);
		y = dieSpezStrecke.getSonicY(spezialProzent);
		
		if (spezialProzent == spezialZiel) {
			unlockPhysics();
		}
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
				boolean wasHit = (hitD1 != null || hitD2 != null);
				hitD1 = Kollision.raycast(x - radHor * 0.98f, y, 0, radVer, false);
				hitD2 = Kollision.raycast(x + radHor * 0.98f, y, 0, radVer, false);
				
				if (hitD1 != null || hitD2 != null) {
					RaycastHit hit = hitD1;
					if (hitD1 == null || hitD1 != null && hitD2 != null && Math.abs(hitD2.hitY - y) < Math.abs(hitD1.hitY - y))
						hit = hitD2;
					
					y = hit.hitY - radVer;
					
					if (!wasHit) {
						speedY *= Math.sin(CMath.angleBetweenDirs(0, 1, hit.normalX, hit.normalY) * (Math.PI / 180));
						
						//y = hit.hitY - radVer;
					}
				}
			}
		}
	}
	
	
	public void draw() {
		dieKamera.setFarbe(new Color(1f, 0.5f, 0, 0.2f));
		
		int i = anim[animationZustand][animationZaehler];
		dieKamera.drawImageSection(dasImage, 256 * (i % 8), 280 * (i / 8), 256, 280, 
				x - 0.9f + (imageFlip ? 1.8f : 0), y + 0.9f + (rolling ? -0.05f : 0),
				1.8f * (imageFlip ? -1 : 1), 1.8f, rotation);
		
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
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	public float getSpeedX() {
		return speedX;
	}
	public float getSpeedY() {
		return speedY;
	}
	
	public boolean getGrounded() {
		return grounded;
	}
	
	public void setSpezialProzent(float pProzent) {
		spezialProzent = pProzent;
	}
	public void setSpezialZiel(float pZiel) {
		spezialZiel = pZiel;
	}
	
	public boolean isDeadly() {
		if (rolling || !grounded) {
			return true;
		}
		return false;
	}
	
	public void setForce(float px, float py) {
		speedX = px;
		speedY = py;
	}
	
	
	private void debugDrawRay(float px, float py, float dx, float dy) {
		dieKamera.drawLine(px, py, px + dx, py + dy);
	}
}
