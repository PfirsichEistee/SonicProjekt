package Objects;

import Entities.Sonic;
import app.Kamera;
import javafx.scene.image.Image;

public class Checkpoint extends Objekt {
	// ATTRIBUTE //
	private static Image dasImage;
	private int imageZaehler;
	private boolean aktiv;
	
	
	// KONSTRUKTOR //
	public Checkpoint(float px, float py) {
		super(px, py, 0.75f, 2f, false);
		aktiv = false;
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		
	}
	
	
	public boolean getAktiv() {
		return aktiv;
	}
}
