package Objects;

import Entities.Sonic;
import app.Kamera;

public abstract class Objekt {
	// ATTRIBUTE //
	protected float x, y;
	protected float w, h;
	
	
	// KONSTRUKTOR //
	public Objekt(float px, float py, float pw, float ph) {
		x = px;
		y = py;
		w = pw;
		h = ph;
	}
	
	
	// METHODEN //
	public abstract void draw(Kamera dieKamera);
	public abstract void onPlayerCollide(Sonic derSpieler);
}
