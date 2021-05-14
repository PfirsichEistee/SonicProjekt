package Entities;

import app.Kamera;
import app.Spielwelt;

public abstract class Gegner {
	// ATTRIBUTE //
	protected static Spielwelt dieSpielwelt;
	
	protected float x, y;
	
	
	// KONSTRUKTOR //
	public Gegner(float px, float py) {
		x = px;
		y = py;
	}
	
	
	// METHODEN //
	public abstract void update(float delta);
	public abstract void fixedUpdate(float delta);
	public abstract void draw(Kamera dieKamera);
	
	public static void setSpielwelt(Spielwelt pSpielwelt) {
		dieSpielwelt = pSpielwelt;
	}
}
