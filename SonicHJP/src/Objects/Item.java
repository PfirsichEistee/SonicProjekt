package Objects;

import Entities.Sonic;
import app.Kamera;
import javafx.scene.image.Image;

public class Item extends Objekt {
	// ATTRIBUTE //
	private static Image[] dieImages;
	private int dieID;
	
	// Items sollen wie Gegner sein: keine Kollisionen! ist einfacher
	
	
	// KONSTRUKTOR //
	public Item(int pID, float px, float py) {
		super(px, py, 0.75f, 0.75f);
		dieID = pID;
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		
	}
	
	@Override
	public void onPlayerCollide(Sonic derSpieler) {
		
	}
}
