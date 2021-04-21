package Objects;

import app.CMath;
import app.Kamera;

public abstract class SpezialStrecke {
	// ATTRIBUTE //
	public float x, y;
	
	
	// KONSTRUKTOR //
	public SpezialStrecke(float px, float py) {
		x = px;
		y = py;
	}
	
	
	// METHODEN //
	public abstract void draw(Kamera dieKamera);
	public abstract void lateDraw(Kamera dieKamera);
	
	public abstract boolean isPointInRange(float px, float py);
	
	// Progress is the passed percentage of the track. 0.5 == sonic is halfway through
	public abstract void getSonicX(float progress);
	public abstract void getSonicY(float progress);
}
