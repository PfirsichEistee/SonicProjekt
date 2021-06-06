package Objects;

import Entities.Sonic;
import app.CMath;
import app.Kamera;
import app.Particle;
import javafx.scene.image.Image;

public class Item extends Objekt {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/items/tv.png");
	public static int imageZaehler = 0;
	private int dieID;
	
	private boolean dead;
	
	// Items sollen wie Gegner sein: keine Kollisionen! ist einfacher
	
	
	// KONSTRUKTOR //
	public Item(int pID, float px, float py) {
		super(px, py, 0.75f, 0.75f);
		dieID = pID;
		
		dead = false;
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		if (dead) {
			dieKamera.drawImageSection(dasImage, 4 * 26, 0, 26, 26, x, y, 1, 1);
			return;
		}
		
		dieKamera.drawImageSection(dasImage, imageZaehler * 26, 0, 26, 26, x, y, 1, 1);
		
		if ((imageZaehler % 2) == 0) {
			dieKamera.drawImageSection(dasImage, 14 * dieID, 26, 14, 14, x + 0.175f, y + 0.3f, 0.65f, 0.65f);
		}
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		if (!dead && derSpieler.isDeadly()) {
			dead = true;
			new Particle(0, x + 0.5f, y + 0.5f, 1.5f);
			
			derSpieler.setForce(derSpieler.getSpeedX(), CMath.min(derSpieler.getSpeedY(), 0) + 6f);
			
			switch (dieID) {
				case(0): // Rings
					derSpieler.setRingCount(derSpieler.getRingCount() + 10);
					break;
				case(1): // Shield
					derSpieler.setShieldBonus();
					break;
				case(2): // Speed
					derSpieler.setSpeedBonus();
					break;
				case(3): // Invincibility
					derSpieler.setInvincibleBonus();
					break;
				case(4): // Lvl Up
					derSpieler.setLiveCount(derSpieler.getLiveCount() + 10);
					break;
				case(5): // Eggman
					derSpieler.hit(derSpieler.getSpeedX() * 0.75f, 6f);
					break;
			}
		}
	}
	
	public boolean isDead() {
		return dead;
	}
}
