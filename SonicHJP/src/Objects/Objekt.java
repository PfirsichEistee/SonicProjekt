package Objects;

import Entities.Sonic;
import app.CMath;
import app.Kamera;

public abstract class Objekt {
	// ATTRIBUTE //
	protected float x, y;
	protected float w, h;
	
	public boolean playerWasInside;
	
	
	// KONSTRUKTOR //
	public Objekt(float px, float py, float pw, float ph) {
		x = px;
		y = py;
		w = pw;
		h = ph;
		
		playerWasInside = false;
	}
	
	
	// METHODEN //
	public abstract void draw(Kamera dieKamera);
	public abstract void onPlayerCollide(Sonic derSpieler);
	
	public boolean isPointInside(float px, float py) {
		//if (px > x && px < (x + w) && py > y && py < (y + h))
		//	return true;
		if (CMath.distance(x + (w / 2), y + (h / 2), px, py) < 1.2f)
			return true;
		
		return false;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}
