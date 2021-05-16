package Objects;

import Entities.Sonic;
import app.Kamera;
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
		dieKamera.drawImageSection(dasImage, (imageZaehler % 4) * 64, (imageZaehler / 4) * 64, 64, 64, x - 0.35f, y - 0.35f, 0.7f, 0.7f);
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		
	}
}
