package Objects;

import Entities.Sonic;
import app.Kamera;

public abstract class Objekt {
	// ATTRIBUTE //
	private float x, y;
	private float w, h;
	private boolean kollision;
	
	
	// KONSTRUKTOR //
	public Objekt(float px, float py, float pw, float ph, boolean pKollision) {
		x = px;
		y = py;
		w = pw;
		h = ph;
		kollision = pKollision;
	}
	
	
	// METHODEN //
	public abstract void draw(Kamera dieKamera);
	public abstract void onPlayerCollide(Sonic derSpieler);
}
