package Objects;

import Entities.Sonic;
import app.Kamera;
import app.Particle;
import javafx.scene.image.Image;

public class Ring extends Objekt {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/items/rings.png");
	public static int imageZaehler = 0;
	
	
	// KONSTRUKTOR //
	public Ring(float px, float py) {
		super(px - 0.5f, py - 0.5f, 1, 1);
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, (imageZaehler % 4) * 64, (imageZaehler / 4) * 64, 64, 64, x + (w - 0.7f) / 2, y + (h - 0.7f) / 2, 0.7f, 0.7f);
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		new Particle(1, x + (w / 2), y + (h / 2), 1);
		
		derSpieler.setRingCount(derSpieler.getRingCount() + 1);
	}
}
