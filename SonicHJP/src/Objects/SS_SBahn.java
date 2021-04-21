package Objects;

import app.Kamera;
import javafx.scene.image.Image;

public class SS_SBahn extends SpezialStrecke {
	// ATTRIBUTE //
	private static Image dasImage = new Image("file:files/textures/s_loop.png");
	private int laenge;
	
	
	// KONSTRUKTOR //
	public SS_SBahn(float px, float py, int pLaenge) {
		super(px, py);
		laenge = pLaenge;
	}
	
	
	// METHODEN //
	@Override
	public void draw(Kamera dieKamera) {
		// 4x4 Tiles per Chunk (Chunk == 128x128px)
		
		for (int i = 0; i < laenge; i++) {
			if (i == 0)
				dieKamera.drawImageSection(dasImage, 0, 0, 192, 256, x, y, 6, 8);
			else
				dieKamera.drawImageSection(dasImage, 128 * 3, 0, 192, 256, x + 12 * i, y, 6, 8);
		}
	}
	
	@Override
	public void lateDraw(Kamera dieKamera) {
		dieKamera.drawImageSection(dasImage, 192, 0, 192, 256, x + 6, y, 6, 8);
		
		for (int i = 0; i < laenge; i++) {
			if (i == (laenge - 1))
				dieKamera.drawImageSection(dasImage, 576, 0, 192, 256, x + 6 + 12 * i, y, 6, 8);
			else
				dieKamera.drawImageSection(dasImage, 192, 0, 192, 256, x + 6 + 12 * i, y, 6, 8);
		}
	}
	
	@Override
	public boolean isPointInRange(float px, float py) {
		// TODO
		// Ist Sonic am Anfang oder Ende der Bahn? Wenn ja -> return true
		// BTW: bahnlaenge == 4 * 3 * laenge
		
		return false;
	}
	
	@Override
	public void getSonicX(float progress) {
		// TODO
	}
	
	@Override
	public void getSonicY(float progress) {
		// TODO
	}
}
