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
	private static int phZaehler = 0;
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
		
		if (imageZaehler == 4) {
			phZaehler++;
			phZaehler %= 4;
			imageZaehler = 0;
		}
		
		dieKamera.drawImageSection(dasImage, phZaehler * 26, 0, 26, 26, x, y, 1, 1);
		
		if ((phZaehler % 2) == 0) {
			dieKamera.drawImageSection(dasImage, 0, 26, 14, 14, x + 0.175f, y + 0.3f, 0.65f, 0.65f);
		}
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		if (!dead && derSpieler.isDeadly()) {
			dead = true;
			new Particle(0, x + 0.5f, y + 0.5f, 1.5f);
			
			derSpieler.setForce(derSpieler.getSpeedX(), CMath.min(derSpieler.getSpeedY(), 0) + 6f);
		}
	}
}
