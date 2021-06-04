package Entities;

import app.CMath;
import app.Kamera;
import app.Spielwelt;

public abstract class Gegner {
	// ATTRIBUTE //
	protected static Spielwelt dieSpielwelt;
	protected static Sonic derSpieler;
	
	protected float x, y;
	
	
	// KONSTRUKTOR //
	public Gegner(float px, float py) {
		x = px;
		y = py;
	}
	
	
	// METHODEN //
	public abstract void start();
	public abstract void update(float delta);
	public abstract void fixedUpdate(float delta);
	public abstract void draw(Kamera dieKamera);
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	public boolean isTouchingPlayer() {
		if (CMath.distance(derSpieler.getX(), derSpieler.getY(), x, y) < 1.5f)
			return true;
		
		return false;
	}
	
	public static void setSpielwelt(Spielwelt pSpielwelt) {
		dieSpielwelt = pSpielwelt;
		derSpieler = dieSpielwelt.getSpieler();
	}
}
