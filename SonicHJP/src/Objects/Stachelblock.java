package Objects;

import Entities.Sonic;
import app.Kamera;
import javafx.scene.image.Image;

public class Stachelblock extends Objekt {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/misc/spikes.png");
	private float rotation;
	
	private int timer;
	private long lastTime;
	
	private boolean visible;
	
	
	// KONSTRUKTOR //
	public Stachelblock(float px, float py, float pRotation, int pTime) { // Time in ms !!!
		super(px, py, 1, 1);
		rotation = pRotation;
		timer = pTime;
		lastTime = System.currentTimeMillis();
		visible = true;
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		if (timer > 0 && (System.currentTimeMillis() - lastTime) >= timer) {
			lastTime = System.currentTimeMillis();
			visible = !visible;
		}
		
		if (visible)
			dieKamera.drawImage(dasImage, x, y, w, h, rotation);
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		if (derSpieler.isVulnerableToProjectiles()) {
			derSpieler.setKnockback(derSpieler.getSpeedX() * 0.75f, 6f);
		}
	}
	
	@Override
	public boolean isPointInside(float px, float py) {
		if (visible && px >= x && px <= (x + w) && py >= y && py < (y + h)) {
			return true;
		}
		return false;
	}
}
