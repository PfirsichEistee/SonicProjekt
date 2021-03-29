package Objects;

import Entities.Sonic;
import app.Kamera;
import javafx.scene.image.Image;

public class Stachelblock extends Objekt {
	// ATTRIBUTE //
	private static Image dasImage;
	
	
	// KONSTRUKTOR //
	public Stachelblock(float px, float py) {
		super(px, py, 0.75f, 0.75f, true);
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		
	}
}
